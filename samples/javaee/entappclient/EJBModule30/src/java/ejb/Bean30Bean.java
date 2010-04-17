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
@Stateless(mappedName="Bean30")
public class Bean30Bean implements Bean30Remote, Bean30Remote2 {

    public String getResult() {
        return "This is EJB 3.0 Bean";
    }

    public String getResult2() {
        return "This is EJB 3.0 Bean 2";
    }
    
  
}
