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
import jpa.controllers.RecruiterJpaController;
import jpa.entities.Recruiter;
import jsf.util.JsfUtil;
import jpa.controllers.exceptions.NonexistentEntityException;
import jsf.util.PagingInfo;

/**
 *
 * @author nb
 */
public class RecruiterController {

    public RecruiterController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (RecruiterJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "recruiterJpa");
        pagingInfo = new PagingInfo();
        converter = new RecruiterConverter();
    }
    private Recruiter recruiter = null;
    private List<Recruiter> recruiterItems = null;
    private RecruiterJpaController jpaController = null;
    private RecruiterConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getRecruiterCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getRecruiterItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findRecruiterEntities(), false);
    }

    public SelectItem[] getRecruiterItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findRecruiterEntities(), true);
    }

    public Recruiter getRecruiter() {
        if (recruiter == null) {
            recruiter = (Recruiter) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentRecruiter", converter, null);
        }
        if (recruiter == null) {
            recruiter = new Recruiter();
        }
        return recruiter;
    }

    public String listSetup() {
        reset(true);
        return "recruiter_list";
    }

    public String createSetup() {
        reset(false);
        recruiter = new Recruiter();
        return "recruiter_create";
    }

    public String create() {
        try {
            jpaController.create(recruiter);
            JsfUtil.addSuccessMessage("Recruiter was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("recruiter_detail");
    }

    public String editSetup() {
        return scalarSetup("recruiter_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        recruiter = (Recruiter) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentRecruiter", converter, null);
        if (recruiter == null) {
            String requestRecruiterString = JsfUtil.getRequestParameter("jsfcrud.currentRecruiter");
            JsfUtil.addErrorMessage("The recruiter with id " + requestRecruiterString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String recruiterString = converter.getAsString(FacesContext.getCurrentInstance(), null, recruiter);
        String currentRecruiterString = JsfUtil.getRequestParameter("jsfcrud.currentRecruiter");
        if (recruiterString == null || recruiterString.length() == 0 || !recruiterString.equals(currentRecruiterString)) {
            String outcome = editSetup();
            if ("recruiter_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit recruiter. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(recruiter);
            JsfUtil.addSuccessMessage("Recruiter was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentRecruiter");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Recruiter was successfully deleted.");
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

    public List<Recruiter> getRecruiterItems() {
        if (recruiterItems == null) {
            getPagingInfo();
            recruiterItems = jpaController.findRecruiterEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return recruiterItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "recruiter_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "recruiter_list";
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
        recruiter = null;
        recruiterItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Recruiter newRecruiter = new Recruiter();
        String newRecruiterString = converter.getAsString(FacesContext.getCurrentInstance(), null, newRecruiter);
        String recruiterString = converter.getAsString(FacesContext.getCurrentInstance(), null, recruiter);
        if (!newRecruiterString.equals(recruiterString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
