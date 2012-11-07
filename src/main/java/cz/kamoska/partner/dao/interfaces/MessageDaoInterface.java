package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.MessageEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.enums.MessageType;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface MessageDaoInterface extends DaoInterface<MessageEntity> {


	List<MessageEntity> findForPartner(PartnerEntity partnerEntity, MessageType messageType, int offset, int limit);

	Long getMessageCountForPartner(PartnerEntity partnerEntity, MessageType messageType);

	Long getUnreadMessageCountForPartner(PartnerEntity partnerEntity, MessageType messageType);
}
