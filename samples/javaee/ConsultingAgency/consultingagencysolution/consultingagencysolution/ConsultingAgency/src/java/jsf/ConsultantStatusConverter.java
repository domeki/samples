/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.controllers.ConsultantStatusJpaController;
import jpa.entities.ConsultantStatus;

/**
 *
 * @author nb
 */
public class ConsultantStatusConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Character id = new Character(string.charAt(0));
        ConsultantStatusJpaController controller = (ConsultantStatusJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "consultantStatusJpa");
        return controller.findConsultantStatus(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ConsultantStatus) {
            ConsultantStatus o = (ConsultantStatus) object;
            return o.getStatusId() == null ? "" : o.getStatusId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.ConsultantStatus");
        }
    }

}
