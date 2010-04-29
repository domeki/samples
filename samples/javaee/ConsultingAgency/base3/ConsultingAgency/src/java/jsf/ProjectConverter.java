/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.controllers.ProjectJpaController;
import jpa.entities.Project;
import jpa.entities.ProjectPK;

/**
 *
 * @author nb
 */
public class ProjectConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        ProjectPK id = getId(string);
        ProjectJpaController controller = (ProjectJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "projectJpa");
        return controller.findProject(id);
    }

    ProjectPK getId(String string) {
        ProjectPK id = new ProjectPK();
        String[] params = new String[3];
        int p = 0;
        int grabStart = 0;
        String delim = "#";
        String escape = "~";
        Pattern pattern = Pattern.compile(escape + "*" + delim);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String found = matcher.group();
            if (found.length() % 2 == 1) {
                params[p] = string.substring(grabStart, matcher.start());
                p++;
                grabStart = matcher.end();
            }
        }
        if (p != params.length - 1) {
            throw new IllegalArgumentException("string " + string + " is not in expected format. expected 3 ids delimited by " + delim);
        }
        params[p] = string.substring(grabStart);
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].replace(escape + delim, delim);
            params[i] = params[i].replace(escape + escape, escape);
        }
        id.setClientName(params[0]);
        id.setClientDepartmentNumber(Short.parseShort(params[1]));
        id.setProjectName(params[2]);
        return id;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Project) {
            Project o = (Project) object;
            ProjectPK id = o.getProjectPK();
            if (id == null) {
                return "";
            }
            String delim = "#";
            String escape = "~";
            String clientName = id.getClientName();
            clientName = clientName == null ? "" : clientName.replace(escape, escape + escape);
            clientName = clientName.replace(delim, escape + delim);
            Object clientDepartmentNumberObj = id.getClientDepartmentNumber();
            String clientDepartmentNumber = clientDepartmentNumberObj == null ? "" : String.valueOf(clientDepartmentNumberObj);
            clientDepartmentNumber = clientDepartmentNumber.replace(escape, escape + escape);
            clientDepartmentNumber = clientDepartmentNumber.replace(delim, escape + delim);
            String projectName = id.getProjectName();
            projectName = projectName == null ? "" : projectName.replace(escape, escape + escape);
            projectName = projectName.replace(delim, escape + delim);
            return clientName + delim + clientDepartmentNumber + delim + projectName;
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.Project");
        }
    }

}
