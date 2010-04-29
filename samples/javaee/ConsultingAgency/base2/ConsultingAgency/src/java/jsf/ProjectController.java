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
import jpa.controllers.ProjectJpaController;
import jpa.entities.Project;
import jsf.util.JsfUtil;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.IllegalOrphanException;
import jpa.entities.ProjectPK;
import jsf.util.PagingInfo;

/**
 *
 * @author nb
 */
public class ProjectController {

    public ProjectController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ProjectJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "projectJpa");
        pagingInfo = new PagingInfo();
        converter = new ProjectConverter();
    }
    private Project project = null;
    private List<Project> projectItems = null;
    private ProjectJpaController jpaController = null;
    private ProjectConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getProjectCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getProjectItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findProjectEntities(), false);
    }

    public SelectItem[] getProjectItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findProjectEntities(), true);
    }

    public Project getProject() {
        if (project == null) {
            project = (Project) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentProject", converter, null);
        }
        if (project == null) {
            project = new Project();
        }
        return project;
    }

    public String listSetup() {
        reset(true);
        return "project_list";
    }

    public String createSetup() {
        reset(false);
        project = new Project();
        project.setProjectPK(new ProjectPK());
        return "project_create";
    }

    public String create() {
        try {
            jpaController.create(project);
            JsfUtil.addSuccessMessage("Project was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("project_detail");
    }

    public String editSetup() {
        return scalarSetup("project_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        project = (Project) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentProject", converter, null);
        if (project == null) {
            String requestProjectString = JsfUtil.getRequestParameter("jsfcrud.currentProject");
            JsfUtil.addErrorMessage("The project with id " + requestProjectString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        project.getProjectPK().setClientName(project.getClient().getClientPK().getClientName());
        project.getProjectPK().setClientDepartmentNumber(project.getClient().getClientPK().getClientDepartmentNumber());
        String projectString = converter.getAsString(FacesContext.getCurrentInstance(), null, project);
        String currentProjectString = JsfUtil.getRequestParameter("jsfcrud.currentProject");
        if (projectString == null || projectString.length() == 0 || !projectString.equals(currentProjectString)) {
            String outcome = editSetup();
            if ("project_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit project. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(project);
            JsfUtil.addSuccessMessage("Project was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentProject");
        ProjectPK id = converter.getId(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Project was successfully deleted.");
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

    public List<Project> getProjectItems() {
        if (projectItems == null) {
            getPagingInfo();
            projectItems = jpaController.findProjectEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return projectItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "project_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "project_list";
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
        project = null;
        projectItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Project newProject = new Project();
        newProject.setProjectPK(new ProjectPK());
        String newProjectString = converter.getAsString(FacesContext.getCurrentInstance(), null, newProject);
        String projectString = converter.getAsString(FacesContext.getCurrentInstance(), null, project);
        if (!newProjectString.equals(projectString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
