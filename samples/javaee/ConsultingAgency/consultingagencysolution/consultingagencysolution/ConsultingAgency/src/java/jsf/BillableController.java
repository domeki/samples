/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;
import jpa.controllers.BillableJpaController;
import jpa.entities.Billable;
import jsf.util.JsfUtil;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Consultant;
import jsf.util.AuthenticationPhaseListener;
import jsf.util.PagingInfo;

/**
 *
 * @author nb
 */
public class BillableController {

    private Map<String, Object> searchParameters = null;
    private static final String[] properties = {"billableId", "startDate", "endDate", "hours", "hourlyRate", "description", "project", "consultantId"};

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
//            pagingInfo.setItemCount(jpaController.getBillableCount());
            pagingInfo.setItemCount(getItemCount());
        }
        return pagingInfo;
    }

//    public SelectItem[] getBillableItemsAvailableSelectMany() {
//        return JsfUtil.getSelectItems(jpaController.findBillableEntities(), false);
//    }
//
//    public SelectItem[] getBillableItemsAvailableSelectOne() {
//        return JsfUtil.getSelectItems(jpaController.findBillableEntities(), true);
//    }
    public Billable getBillable() {
        if (billable == null) {
            billable = (Billable) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentBillable", converter, null);
        }
        if (billable == null) {
            billable = getNewBillable();
        }
        return billable;
    }

    public String listSetup() {
        reset(true);
        return "billable_list";
    }

    public String createSetup() {
        reset(false);
        billable = getNewBillable();
        return "billable_create";
    }

    public String create() {
        if (isOperationForbidden()) {
            return null;
        }

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
        if (isOperationForbidden()) {
            return null;
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
            if (isOperationForbidden()) {
                return null;
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
        if (isOperationForbidden()) {
            return null;
        }

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
            tweakSearchParameters();
            String query = getQueryForParameters("select object(o) from Billable as o");
            billableItems = jpaController.findBillableEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem(), query, searchParameters);
        }
        return billableItems;
    }

    public String next() {
        reset(false, false);
        getPagingInfo().nextPage();
        return "billable_list";
    }

    public String prev() {
        reset(false, false);
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

    private void reset(boolean resetFirstItem, boolean resetSearchParameters) {
        billable = null;
        billableItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
        if (resetSearchParameters) {
            searchParameters = null;
        }
    }

    private void reset(boolean resetFirstItem) {
        reset(resetFirstItem, true);
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Billable newBillable = getNewBillable();
        String newBillableString = converter.getAsString(FacesContext.getCurrentInstance(), null, newBillable);
        String billableString = converter.getAsString(FacesContext.getCurrentInstance(), null, billable);
        if (!newBillableString.equals(billableString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

    //Code added
    public Short getSearchHours() {
        if (billable == null) {
            return null;
        }
        short billableHours = billable.getHours();
        if (billableHours == -1) {
            return null;
        }
        return new Short(billableHours);
    }

    public void setSearchHours(Short hours) {
        if (billable == null) {
            return;
        }
        short hoursAsPrimitive = hours == null ? -1 : hours.shortValue();
        billable.setHours(hoursAsPrimitive);
    }

    private Billable getNewBillable() {
        Billable result = new Billable();
        Consultant consultant = AuthenticationPhaseListener.getLoggedInConsultant();
        result.setConsultantId(consultant);
        result.setHourlyRate(consultant.getHourlyRate());
        result.setBillableHourlyRate(consultant.getBillableHourlyRate());
        return result;
    }

    private boolean isOperationForbidden() {
        boolean forbidden = false;
        Consultant loggedInConsultant = AuthenticationPhaseListener.getLoggedInConsultant();
        if (loggedInConsultant == null) {
            forbidden = true;
        } else if (billable == null) {
            //destroy operation
            String consultantOfCurrentBillableAsString = JsfUtil.getRequestParameter("jsfcrud.timecard.consultantOfCurrentBillable");
            if (consultantOfCurrentBillableAsString != null) {
                Integer consultantOfCurrentBillableAsInteger = new Integer(consultantOfCurrentBillableAsString);
                if (!consultantOfCurrentBillableAsInteger.equals(loggedInConsultant.getConsultantId())) {
                    forbidden = true;
                }
            }
        } else if (!loggedInConsultant.equals(billable.getConsultantId())) {
            //detail or edit operation
            forbidden = true;
        }
        if (forbidden) {
            JsfUtil.addErrorMessage("You do not have permission to perform the requested operation.");
        }
        return forbidden;
    }

    public String searchSetup() {
        reset(false);
        billable = new Billable();
        billable.setHours((short) -1);
        return "billable_search";
    }

    public String search() {
        searchParameters = new HashMap<String, Object>();
        Object[] values = {billable.getBillableId(), billable.getStartDate(), billable.getEndDate(), new Short(billable.getHours()), billable.getHourlyRate(), billable.getDescription(), billable.getProject(), null};
        for (int i = 0; i < properties.length; i++) {
            if (values[i] != null) {
                if ("hours".equals(properties[i])) {
                    if (((Short) values[i]).shortValue() == -1) {
                        continue;
                    }
                } else if (values[i] instanceof String) {
                    String trimmedValue = ((String) values[i]).trim();
                    if (trimmedValue.length() == 0) {
                        continue;
                    }
                    trimmedValue = trimmedValue.replaceAll("~", "~~");
                    trimmedValue = trimmedValue.replaceAll("%", "~%");
                    values[i] = "%" + trimmedValue + "%";
                }
                searchParameters.put(properties[i], values[i]);
            }
        }
        reset(true, false);
        return "billable_list";
    }

    public int getItemCount() {
        tweakSearchParameters();
        String query = getQueryForParameters("select count(o) from Billable as o");
        return jpaController.getBillableCount(query, searchParameters);
    }

    private String getQueryForParameters(String baseQuery) {
        if (searchParameters == null) {
            return baseQuery;
        }
        StringBuffer where = new StringBuffer();
        for (int i = 0; i < properties.length; i++) {
            if (searchParameters.containsKey(properties[i])) {
                if (where.length() > 0) {
                    where.append(" and ");
                }
                if ("startDate".equals(properties[i])) {
                    where.append("(o.startDate is null or o.startDate >= :startDate) and (o.endDate is null or o.endDate >= :startDate)");
                } else if ("endDate".equals(properties[i])) {
                    where.append("(o.startDate is null or o.startDate <= :endDate) and (o.endDate is null or o.endDate <= :endDate)");
                } else if (searchParameters.get(properties[i]) instanceof String) {
                    where.append("o." + properties[i] + " like :" + properties[i] + " escape '~'");
                } else {
                    where.append("o." + properties[i] + " = :" + properties[i]);
                }
            }
        }
        if (where.length() > 0) {
            baseQuery += " where " + where;
        }
        return baseQuery;
    }

    private void tweakSearchParameters() {
        if (searchParameters == null) {
            searchParameters = new HashMap<String, Object>();
        }
        if (searchParameters.get(properties[properties.length - 1]) == null) {
            Consultant loggedInConsultant = AuthenticationPhaseListener.getLoggedInConsultant();
            searchParameters.put(properties[properties.length - 1], loggedInConsultant);
        }
    }

    public void validateEndDate(FacesContext facesContext, UIComponent component, Object value) {
        ValueHolder startDateInputField = (ValueHolder) facesContext.getViewRoot().findComponent("form1:startDate");
        Date startDate = (Date) startDateInputField.getLocalValue();
        if (startDate != null) {
            Date endDate = (Date) value;
            if (startDate.after(endDate)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "The end date cannot precede the start date.", null));
            }
        }
    }

    // fields and methods for Ajax validation
    private HtmlInputHidden ajaxValidationHidden = new HtmlInputHidden();

public HtmlInputHidden getAjaxValidationHidden() {
    return ajaxValidationHidden;
}

public void setAjaxValidationHidden(HtmlInputHidden hidden) {
    this.ajaxValidationHidden = hidden;
}

public void ajaxValidationHiddenChanged(javax.faces.event.ValueChangeEvent vce) {
    FacesContext.getCurrentInstance().renderResponse();
    ajaxValidationHidden.setValue("value");
}
}
