/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.presentation;

import boundary.MessageFacade;
import entities.Message;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author nb
 */
@ManagedBean(name="MessageView")
@RequestScoped
public class MessageView {

     // Injects the MessageFacade session bean using the @EJB annotation
    @EJB
    private MessageFacade messageFacade;

    

    // Creates a new field
    private Message message;

    // Creates a new instance of Message
    public MessageView() {
       this.message = new Message();
    }

    // Calls getMessage to retrieve the message
    public Message getMessage() {
       return message;
    }

    // Returns the total number of messages
    public int getNumberOfMessages(){
       return messageFacade.findAll().size();
    }

    // Saves the message and then returns the string "theend"
    public String postMessage(){
       this.messageFacade.create(message);
       return "theend";
    }


}
