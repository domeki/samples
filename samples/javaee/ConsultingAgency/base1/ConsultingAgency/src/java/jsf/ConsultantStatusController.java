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
import jpa.controllers.ConsultantStatusJpaController;
import jpa.entities.ConsultantStatus;
import jsf.util.JsfUtil;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.IllegalOrphanException;
import jsf.util.PagingInfo;

/**
 *
 * @author nb
 */
public class ConsultantStatusController {

    public ConsultantStatusController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ConsultantStatusJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "consultantStatusJpa");
        pagingInfo = new PagingInfo();
        converter = new ConsultantStatusConverter();
    }
    private ConsultantStatus consultantStatus = null;
    private List<ConsultantStatus> consultantStatusItems = null;
    private ConsultantStatusJpaController jpaController = null;
    private ConsultantStatusConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getConsultantStatusCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getConsultantStatusItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findConsultantStatusEntities(), false);
    }

    public SelectItem[] getConsultantStatusItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findConsultantStatusEntities(), true);
    }

    public ConsultantStatus getConsultantStatus() {
        if (consultantStatus == null) {
            consultantStatus = (ConsultantStatus) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentConsultantStatus", converter, null);
        }
        if (consultantStatus == null) {
            consultantStatus = new ConsultantStatus();
        }
        return consultantStatus;
    }

    public String listSetup() {
        reset(true);
        return "consultantStatus_list";
    }

    public String createSetup() {
        reset(false);
        consultantStatus = new ConsultantStatus();
        return "consultantStatus_create";
    }

    public String create() {
        try {
            jpaController.create(consultantStatus);
            JsfUtil.addSuccessMessage("ConsultantStatus was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("consultantStatus_detail");
    }

    public String editSetup() {
        return scalarSetup("consultantStatus_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        consultantStatus = (ConsultantStatus) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentConsultantStatus", converter, null);
        if (consultantStatus == null) {
            String requestConsultantStatusString = JsfUtil.getRequestParameter("jsfcrud.currentConsultantStatus");
            JsfUtil.addErrorMessage("The consultantStatus with id " + requestConsultantStatusString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String consultantStatusString = converter.getAsString(FacesContext.getCurrentInstance(), null, consultantStatus);
        String currentConsultantStatusString = JsfUtil.getRequestParameter("jsfcrud.currentConsultantStatus");
        if (consultantStatusString == null || consultantStatusString.length() == 0 || !consultantStatusString.equals(currentConsultantStatusString)) {
            String outcome = editSetup();
            if ("consultantStatus_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit consultantStatus. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(consultantStatus);
            JsfUtil.addSuccessMessage("ConsultantStatus was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentConsultantStatus");
        Character id = new Character(idAsString.charAt(0));
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("ConsultantStatus was successfully deleted.");
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

    public List<ConsultantStatus> getConsultantStatusItems() {
        if (consultantStatusItems == null) {
            getPagingInfo();
            consultantStatusItems = jpaController.findConsultantStatusEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return consultantStatusItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "consultantStatus_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "consultantStatus_list";
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
        consultantStatus = null;
        consultantStatusItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        ConsultantStatus newConsultantStatus = new ConsultantStatus();
        String newConsultantStatusString = converter.getAsString(FacesContext.getCurrentInstance(), null, newConsultantStatus);
        String consultantStatusString = converter.getAsString(FacesContext.getCurrentInstance(), null, consultantStatus);
        if (!newConsultantStatusString.equals(consultantStatusString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
