/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.controllers.BillableJpaController;
import jpa.entities.Billable;

/**
 *
 * @author nb
 */
public class BillableConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        BillableJpaController controller = (BillableJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "billableJpa");
        return controller.findBillable(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Billable) {
            Billable o = (Billable) object;
            return o.getBillableId() == null ? "" : o.getBillableId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.Billable");
        }
    }

}
