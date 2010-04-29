/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import jpa.controllers.ClientJpaController;
import jpa.entities.Client;
import jsf.util.JsfUtil;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.IllegalOrphanException;
import jpa.entities.ClientPK;
import jsf.util.PagingInfo;

/**
 *
 * @author nb
 */
public class ClientController {

    public ClientController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ClientJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "clientJpa");
        pagingInfo = new PagingInfo();
        converter = new ClientConverter();
    }
    private Client client = null;
    private List<Client> clientItems = null;
    private ClientJpaController jpaController = null;
    private ClientConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getClientCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getClientItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findClientEntities(), false);
    }

    public SelectItem[] getClientItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findClientEntities(), true);
    }

    public Client getClient() {
        if (client == null) {
            client = (Client) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentClient", converter, null);
        }
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public String listSetup() {
        reset(true);
        return "client_list";
    }

    public String createSetup() {
        reset(false);
        client = new Client();
        client.setClientPK(new ClientPK());
        return "client_create";
    }

    public String create() {
        try {
            jpaController.create(client);
            JsfUtil.addSuccessMessage("Client was successfully created.");
        } catch (IllegalOrphanException oe) {
            JsfUtil.addErrorMessages(oe.getMessages());
            return null;
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("client_detail");
    }

    public String editSetup() {
        return scalarSetup("client_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        client = (Client) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentClient", converter, null);
        if (client == null) {
            String requestClientString = JsfUtil.getRequestParameter("jsfcrud.currentClient");
            JsfUtil.addErrorMessage("The client with id " + requestClientString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String clientString = converter.getAsString(FacesContext.getCurrentInstance(), null, client);
        String currentClientString = JsfUtil.getRequestParameter("jsfcrud.currentClient");
        if (clientString == null || clientString.length() == 0 || !clientString.equals(currentClientString)) {
            String outcome = editSetup();
            if ("client_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit client. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(client);
            JsfUtil.addSuccessMessage("Client was successfully updated.");
        } catch (IllegalOrphanException oe) {
            JsfUtil.addErrorMessages(oe.getMessages());
            return null;
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentClient");
        ClientPK id = converter.getId(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Client was successfully deleted.");
        } catch (IllegalOrphanException oe) {
            JsfUtil.addErrorMessages(oe.getMessages());
            return null;
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<Client> getClientItems() {
        if (clientItems == null) {
            getPagingInfo();
            clientItems = jpaController.findClientEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return clientItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "client_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "client_list";
    }

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset(boolean resetFirstItem) {
        client = null;
        clientItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Client newClient = new Client();
        newClient.setClientPK(new ClientPK());
        String newClientString = converter.getAsString(FacesContext.getCurrentInstance(), null, newClient);
        String clientString = converter.getAsString(FacesContext.getCurrentInstance(), null, client);
        if (!newClientString.equals(clientString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
