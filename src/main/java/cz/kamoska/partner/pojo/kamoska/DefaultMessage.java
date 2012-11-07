package cz.kamoska.partner.pojo.kamoska;

import cz.kamoska.partner.enums.MessageType;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 7.11.12
 * Time: 20:06
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMessage {

	private String title;
	private String body;
	private MessageType messageType;

	public DefaultMessage(String title, String body, MessageType messageType) {
		this.title = title;
		this.body = body;
		this.messageType = messageType;
	}

	public void replaceAll(final String replacement, final String value) {
		title = title.replace(replacement, value);
		body = body.replace(replacement, value);
	}

	public DefaultMessage() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}


}
