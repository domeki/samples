/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.controllers.AddressJpaController;
import jpa.entities.Address;

/**
 *
 * @author nb
 */
public class AddressConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        AddressJpaController controller = (AddressJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "addressJpa");
        return controller.findAddress(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Address) {
            Address o = (Address) object;
            return o.getAddressId() == null ? "" : o.getAddressId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.Address");
        }
    }

}
