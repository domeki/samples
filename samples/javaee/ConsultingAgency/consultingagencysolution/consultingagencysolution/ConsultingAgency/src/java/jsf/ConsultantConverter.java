/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.controllers.ConsultantJpaController;
import jpa.entities.Consultant;

/**
 *
 * @author nb
 */
public class ConsultantConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        ConsultantJpaController controller = (ConsultantJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "consultantJpa");
        return controller.findConsultant(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Consultant) {
            Consultant o = (Consultant) object;
            return o.getConsultantId() == null ? "" : o.getConsultantId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.Consultant");
        }
    }

}
