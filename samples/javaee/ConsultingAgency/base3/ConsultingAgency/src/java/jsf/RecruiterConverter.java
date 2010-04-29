/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.controllers.RecruiterJpaController;
import jpa.entities.Recruiter;

/**
 *
 * @author nb
 */
public class RecruiterConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        RecruiterJpaController controller = (RecruiterJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "recruiterJpa");
        return controller.findRecruiter(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Recruiter) {
            Recruiter o = (Recruiter) object;
            return o.getRecruiterId() == null ? "" : o.getRecruiterId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.Recruiter");
        }
    }

}
