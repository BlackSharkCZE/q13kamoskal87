package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.MessageDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.MessageEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.enums.MessageType;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.11.12
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class MessageDaoInterfaceImpl extends DaoTemplate<MessageEntity> implements MessageDaoInterface {

	@Override
	public List<MessageEntity> findForPartner(PartnerEntity partnerEntity, MessageType messageType, int offset, int limit) {

		Map<String, Object> params = new HashMap<>(2);
		params.put("partnerID", partnerEntity.getId());
		params.put("messageType", messageType);
		return findListByNamedQuery("Message.findByPartnerIDAndTypeAndPublished", offset, limit, params);
	}


	@Override
	public Long getMessageCountForPartner(PartnerEntity partnerEntity, MessageType messageType) {
		try {
			Long count = (Long) em.createNamedQuery("Message.getCountByPartnerIDAndTypeAndPublished").setParameter("partnerID", partnerEntity.getId()).setParameter("messageType", messageType).getSingleResult();
			return count;
		} catch (Exception e) {
			logger.error("Can not read Message Count for partner " + partnerEntity + " and message type " + messageType, e);
		}
		return -1L;
	}

	@Override
	public Long getUnreadMessageCountForPartner(PartnerEntity partnerEntity, MessageType messageType) {
		try {
			Long count = (Long) em.createNamedQuery("Message.getUnreadCountByPartnerIDAndTypeAndPublished").setParameter("partnerID", partnerEntity.getId()).setParameter("messageType", messageType).getSingleResult();
			return count;
		} catch (Exception e) {
			logger.error("Can not read UnreadMessage Count for partner " + partnerEntity + " and message type " + messageType, e);
		}
		return -1L;
	}


}
