/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package enterpriseappee5;

import ejb.SessionRemote;
import javax.ejb.EJB;

/**
 *
 * @author nb
 */
public class Main {
    @EJB
    private static SessionRemote sessionBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.err.println("result = " + sessionBean.getResult());
    }

}
