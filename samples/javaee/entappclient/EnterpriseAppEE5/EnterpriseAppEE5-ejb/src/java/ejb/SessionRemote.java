/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import javax.ejb.Remote;

/**
 *
 * @author nb
 */
@Remote
public interface SessionRemote {

    String getResult();
    
}
