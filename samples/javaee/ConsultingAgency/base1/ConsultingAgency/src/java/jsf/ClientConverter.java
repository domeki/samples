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
import jpa.controllers.ClientJpaController;
import jpa.entities.Client;
import jpa.entities.ClientPK;

/**
 *
 * @author nb
 */
public class ClientConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        ClientPK id = getId(string);
        ClientJpaController controller = (ClientJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "clientJpa");
        return controller.findClient(id);
    }

    ClientPK getId(String string) {
        ClientPK id = new ClientPK();
        String[] params = new String[2];
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
            throw new IllegalArgumentException("string " + string + " is not in expected format. expected 2 ids delimited by " + delim);
        }
        params[p] = string.substring(grabStart);
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].replace(escape + delim, delim);
            params[i] = params[i].replace(escape + escape, escape);
        }
        id.setClientName(params[0]);
        id.setClientDepartmentNumber(Short.parseShort(params[1]));
        return id;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Client) {
            Client o = (Client) object;
            ClientPK id = o.getClientPK();
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
            return clientName + delim + clientDepartmentNumber;
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.Client");
        }
    }

}
