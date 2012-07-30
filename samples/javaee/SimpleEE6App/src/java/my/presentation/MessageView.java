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
@ManagedBean(name = "MessageView")
@RequestScoped
public class MessageView {
    @EJB
    private MessageFacade messageFacade;
    
    private Message message;

    /**
     * Creates a new instance of MessageView
     */
    public MessageView() {
        this.message = new Message();
    }
    
    public Message getMessage() {
        return message;
    }
    
    public int getNumberOfMessages() {
        return messageFacade.findAll().size();
    }
    
    public String postMessage() {
        this.messageFacade.create(message);
        return "theend";
    }
}
