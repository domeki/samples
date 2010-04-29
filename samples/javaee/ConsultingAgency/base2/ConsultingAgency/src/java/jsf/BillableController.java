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
import jpa.controllers.BillableJpaController;
import jpa.entities.Billable;
import jsf.util.JsfUtil;
import jpa.controllers.exceptions.NonexistentEntityException;
import jsf.util.PagingInfo;

/**
 *
 * @author nb
 */
public class BillableController {

    public BillableController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (BillableJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "billableJpa");
        pagingInfo = new PagingInfo();
        converter = new BillableConverter();
    }
    private Billable billable = null;
    private List<Billable> billableItems = null;
    private BillableJpaController jpaController = null;
    private BillableConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getBillableCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getBillableItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findBillableEntities(), false);
    }

    public SelectItem[] getBillableItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findBillableEntities(), true);
    }

    public Billable getBillable() {
        if (billable == null) {
            billable = (Billable) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentBillable", converter, null);
        }
        if (billable == null) {
            billable = new Billable();
        }
        return billable;
    }

    public String listSetup() {
        reset(true);
        return "billable_list";
    }

    public String createSetup() {
        reset(false);
        billable = new Billable();
        return "billable_create";
    }

    public String create() {
        try {
            jpaController.create(billable);
            JsfUtil.addSuccessMessage("Billable was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("billable_detail");
    }

    public String editSetup() {
        return scalarSetup("billable_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        billable = (Billable) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentBillable", converter, null);
        if (billable == null) {
            String requestBillableString = JsfUtil.getRequestParameter("jsfcrud.currentBillable");
            JsfUtil.addErrorMessage("The billable with id " + requestBillableString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String billableString = converter.getAsString(FacesContext.getCurrentInstance(), null, billable);
        String currentBillableString = JsfUtil.getRequestParameter("jsfcrud.currentBillable");
        if (billableString == null || billableString.length() == 0 || !billableString.equals(currentBillableString)) {
            String outcome = editSetup();
            if ("billable_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit billable. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(billable);
            JsfUtil.addSuccessMessage("Billable was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentBillable");
        Long id = new Long(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Billable was successfully deleted.");
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

    public List<Billable> getBillableItems() {
        if (billableItems == null) {
            getPagingInfo();
            billableItems = jpaController.findBillableEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return billableItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "billable_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "billable_list";
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
        billable = null;
        billableItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Billable newBillable = new Billable();
        String newBillableString = converter.getAsString(FacesContext.getCurrentInstance(), null, newBillable);
        String billableString = converter.getAsString(FacesContext.getCurrentInstance(), null, billable);
        if (!newBillableString.equals(billableString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
