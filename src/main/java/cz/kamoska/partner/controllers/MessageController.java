package cz.kamoska.partner.controllers;

import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.MessageDaoInterface;
import cz.kamoska.partner.entities.MessageEntity;
import cz.kamoska.partner.models.dao.MessageDaoModel;
import cz.kamoska.partner.support.Kamoska;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;

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

	@Inject @Kamoska
private org.slf4j.Logger logger;

	@Inject
	private MessageDaoModel messageDaoModel;
	@EJB
	private MessageDaoInterface messageDaoInterface;

	public String showMessageDetail() {
		if (messageDaoModel.getSelectedMessage() != null) {
			if (messageDaoModel.getSelectedMessage().getRead() == null) {
				messageDaoModel.getSelectedMessage().setRead(Calendar.getInstance().getTime());
				SaveDomainResult<MessageEntity> updateResult = messageDaoInterface.update(messageDaoModel.getSelectedMessage());
				if (updateResult.success) {
					messageDaoModel.setSelectedMessage(updateResult.item);
				} else {
					logger.warn("Can not mark message " + messageDaoModel.getSelectedMessage() + " as READ");
				}
			}
		}
		return "zprava";
//		return "show-message-detail";
	}

	public String showMessagesList() {
		return "show-messages-list";
	}

}
