package cz.kamoska.partner.support;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author blackshark
 * @version 0.1
 * @since 0.1
 */
public class FacesMessageCreate {

    public static FacesMessage createMessage(FacesMessage.Severity severity, String message) {
        FacesMessage msg = new FacesMessage(message);
        msg.setSeverity(severity);
        return msg;
    }

    public static void addMessage(FacesMessage.Severity severity, String message, FacesContext context) {
        context.addMessage(null, createMessage(severity, message));
    }

    public static void addMessage(String clientID, FacesMessage.Severity severity, String message, FacesContext context) {
        context.addMessage(clientID, createMessage(severity, message));
    }

}
