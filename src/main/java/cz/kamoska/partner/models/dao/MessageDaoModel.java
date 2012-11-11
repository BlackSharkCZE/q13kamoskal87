package cz.kamoska.partner.models.dao;

import cz.kamoska.partner.dao.interfaces.MessageDaoInterface;
import cz.kamoska.partner.entities.MessageEntity;
import cz.kamoska.partner.enums.MessageType;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.11.12
 * Time: 22:43
 * To change this template use File | Settings | File Templates.
 */
@Named
@SessionScoped
public class MessageDaoModel implements Serializable {

	@Inject
	private LoggedInPartner loggedInPartner;

	@EJB
	private MessageDaoInterface messageDaoInterface;

	private MessageEntity selectedMessage;
	private LazyDataModel<MessageEntity> lazyDataModel;

	private Integer allMessages;
	private Integer unreadMessages;

	public MessageDaoModel() {

		lazyDataModel = new LazyDataModel<MessageEntity>() {
			@Override
			public List<MessageEntity> load(int offset, int limit, String s, SortOrder sortOrder, Map<String, String> stringStringMap) {
				setRowCount(getMessageCountForLoggedInPartner(MessageType.NOTIFICATION).intValue());
				allMessages = getRowCount();
				unreadMessages = getUnreadMessageCountForLoggedInPartner(MessageType.NOTIFICATION).intValue();
				return findMessageEntityForLoggedInPartner(MessageType.NOTIFICATION, offset, limit);
			}
		};

	}

	/**
	 * Vraci seznam zprav pro prihlaseneho partnera, dany typ zpravy a strankovani - Nepouziva Cache. Dotazy vola primo na DB
	 *
	 * @param messageType typ zpravy ktery nas zajima {@link MessageType}
	 * @param offset      od kolikate pozice zpravy vracet
	 * @param limit       do jake pozice zpravy vracet
	 * @return nalezeny seznam zprav nebo Collections.emptyList()
	 */
	public List<MessageEntity> findMessageEntityForLoggedInPartner(MessageType messageType, int offset, int limit) {
		return messageDaoInterface.findForPartner(loggedInPartner.getPartner(), messageType, offset, limit);
	}

	/**
	 * Vraci celkovy pocet zprav pro prihlaseneho partnera partnera
	 *
	 * @param messageType typ zpravy, ktery nas zajima
	 * @return pocet zpro prihlasneho partnera
	 */
	public Long getMessageCountForLoggedInPartner(MessageType messageType) {
		return messageDaoInterface.getMessageCountForPartner(loggedInPartner.getPartner(), messageType);
	}

	public Long getUnreadMessageCountForLoggedInPartner(MessageType messageType) {
		return messageDaoInterface.getUnreadMessageCountForPartner(loggedInPartner.getPartner(), messageType);
	}

	public MessageEntity getSelectedMessage() {
		return selectedMessage;
	}

	public void setSelectedMessage(MessageEntity selectedMessage) {
		this.selectedMessage = selectedMessage;
	}

	public LazyDataModel<MessageEntity> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<MessageEntity> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public Integer getAllMessages() {
		return allMessages;
	}

	public void setAllMessages(Integer allMessages) {
		this.allMessages = allMessages;
	}

	public Integer getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(Integer unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
}
