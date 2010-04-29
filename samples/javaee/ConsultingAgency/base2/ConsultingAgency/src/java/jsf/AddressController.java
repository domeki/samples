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
import jpa.controllers.AddressJpaController;
import jpa.entities.Address;
import jsf.util.JsfUtil;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.IllegalOrphanException;
import jsf.util.PagingInfo;

/**
 *
 * @author nb
 */
public class AddressController {

    public AddressController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (AddressJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "addressJpa");
        pagingInfo = new PagingInfo();
        converter = new AddressConverter();
    }
    private Address address = null;
    private List<Address> addressItems = null;
    private AddressJpaController jpaController = null;
    private AddressConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getAddressCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getAddressItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findAddressEntities(), false);
    }

    public SelectItem[] getAddressItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findAddressEntities(), true);
    }

    public Address getAddress() {
        if (address == null) {
            address = (Address) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAddress", converter, null);
        }
        if (address == null) {
            address = new Address();
        }
        return address;
    }

    public String listSetup() {
        reset(true);
        return "address_list";
    }

    public String createSetup() {
        reset(false);
        address = new Address();
        return "address_create";
    }

    public String create() {
        try {
            jpaController.create(address);
            JsfUtil.addSuccessMessage("Address was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("address_detail");
    }

    public String editSetup() {
        return scalarSetup("address_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        address = (Address) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAddress", converter, null);
        if (address == null) {
            String requestAddressString = JsfUtil.getRequestParameter("jsfcrud.currentAddress");
            JsfUtil.addErrorMessage("The address with id " + requestAddressString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String addressString = converter.getAsString(FacesContext.getCurrentInstance(), null, address);
        String currentAddressString = JsfUtil.getRequestParameter("jsfcrud.currentAddress");
        if (addressString == null || addressString.length() == 0 || !addressString.equals(currentAddressString)) {
            String outcome = editSetup();
            if ("address_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit address. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(address);
            JsfUtil.addSuccessMessage("Address was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentAddress");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Address was successfully deleted.");
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

    public List<Address> getAddressItems() {
        if (addressItems == null) {
            getPagingInfo();
            addressItems = jpaController.findAddressEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return addressItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "address_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "address_list";
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
        address = null;
        addressItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Address newAddress = new Address();
        String newAddressString = converter.getAsString(FacesContext.getCurrentInstance(), null, newAddress);
        String addressString = converter.getAsString(FacesContext.getCurrentInstance(), null, address);
        if (!newAddressString.equals(addressString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
