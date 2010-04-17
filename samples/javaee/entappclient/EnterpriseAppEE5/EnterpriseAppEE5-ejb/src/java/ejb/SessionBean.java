/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import javax.ejb.Stateless;

/**
 *
 * @author nb
 */
@Stateless
public class SessionBean implements SessionRemote {

    public String getResult() {
        return "This is EJB 3.0 Bean";
    }
    
}
