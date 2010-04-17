/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclientfortest;

import ejb.Bean30Remote;
import ejb.Bean30Remote2;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author nb
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NamingException {
        InitialContext ctx = new InitialContext();
        Bean30Remote br = (Bean30Remote) ctx.lookup("Bean30#ejb.Bean30Remote");
        System.err.println("EJB message is: " + br.getResult());
        Bean30Remote2 br2 = (Bean30Remote2) ctx.lookup("Bean30#ejb.Bean30Remote2");
        System.err.println("EJB message 2 is: " + br2.getResult2());
    }
}
