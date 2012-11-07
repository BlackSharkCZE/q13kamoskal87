package cz.kamoska.partner.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.11.12
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class MessageController {

	public String showMessageDetail() {
		return "show-message-detail";
	}

	public String showMessagesList() {
		return "show-messages-list";
	}

}
