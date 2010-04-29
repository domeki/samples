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
import jpa.controllers.ConsultantJpaController;
import jpa.entities.Consultant;
import jsf.util.JsfUtil;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.IllegalOrphanException;
import jsf.util.PagingInfo;

/**
 *
 * @author nb
 */
public class ConsultantController {

    public ConsultantController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ConsultantJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "consultantJpa");
        pagingInfo = new PagingInfo();
        converter = new ConsultantConverter();
    }
    private Consultant consultant = null;
    private List<Consultant> consultantItems = null;
    private ConsultantJpaController jpaController = null;
    private ConsultantConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getConsultantCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getConsultantItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findConsultantEntities(), false);
    }

    public SelectItem[] getConsultantItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findConsultantEntities(), true);
    }

    public Consultant getConsultant() {
        if (consultant == null) {
            consultant = (Consultant) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentConsultant", converter, null);
        }
        if (consultant == null) {
            consultant = new Consultant();
        }
        return consultant;
    }

    public String listSetup() {
        reset(true);
        return "consultant_list";
    }

    public String createSetup() {
        reset(false);
        consultant = new Consultant();
        return "consultant_create";
    }

    public String create() {
        try {
            jpaController.create(consultant);
            JsfUtil.addSuccessMessage("Consultant was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("consultant_detail");
    }

    public String editSetup() {
        return scalarSetup("consultant_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        consultant = (Consultant) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentConsultant", converter, null);
        if (consultant == null) {
            String requestConsultantString = JsfUtil.getRequestParameter("jsfcrud.currentConsultant");
            JsfUtil.addErrorMessage("The consultant with id " + requestConsultantString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String consultantString = converter.getAsString(FacesContext.getCurrentInstance(), null, consultant);
        String currentConsultantString = JsfUtil.getRequestParameter("jsfcrud.currentConsultant");
        if (consultantString == null || consultantString.length() == 0 || !consultantString.equals(currentConsultantString)) {
            String outcome = editSetup();
            if ("consultant_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit consultant. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(consultant);
            JsfUtil.addSuccessMessage("Consultant was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentConsultant");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Consultant was successfully deleted.");
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

    public List<Consultant> getConsultantItems() {
        if (consultantItems == null) {
            getPagingInfo();
            consultantItems = jpaController.findConsultantEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return consultantItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "consultant_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "consultant_list";
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
        consultant = null;
        consultantItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Consultant newConsultant = new Consultant();
        String newConsultantString = converter.getAsString(FacesContext.getCurrentInstance(), null, newConsultant);
        String consultantString = converter.getAsString(FacesContext.getCurrentInstance(), null, consultant);
        if (!newConsultantString.equals(consultantString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
