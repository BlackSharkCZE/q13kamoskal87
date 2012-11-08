package cz.kamoska.partner.models.request;

import cz.kamoska.partner.dao.interfaces.MessageDaoInterface;
import cz.kamoska.partner.entities.MessageEntity;
import cz.kamoska.partner.enums.MessageType;
import cz.kamoska.partner.models.sessions.LoggedInPartner;
import cz.kamoska.partner.pojo.kamoska.Paginator;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.11.12
 * Time: 22:43
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class MessageModel {

	@Inject
	private LoggedInPartner loggedInPartner;

	@EJB
	private MessageDaoInterface messageDaoInterface;

	private MessageEntity selectedMessage;
	private Integer page;

	private Paginator paginator;

	public MessageModel() {
		page = 0;
		paginator = null;
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


	public void loadPaginator() {
		paginator.setMessageEntityList(messageDaoInterface.findForPartner(loggedInPartner.getPartner(), MessageType.NOTIFICATION, page * paginator.getPageSize(), paginator.getPageSize()));
		Long count = messageDaoInterface.getMessageCountForPartner(loggedInPartner.getPartner(), MessageType.NOTIFICATION);
		Long unreadCount = messageDaoInterface.getUnreadMessageCountForPartner(loggedInPartner.getPartner(), MessageType.NOTIFICATION);
		paginator.setAllMessageCount(count.intValue());
		paginator.setCurrentPage(page);
		paginator.setMaxPage((int) Math.ceil(count / (double) paginator.getPageSize()));
		paginator.setUnreadCount(unreadCount.intValue());
		paginator.setNewCreated(false);
	}


	public String showMessageDetail(Integer id) {
		return "show-message-detail";
	}


	public Paginator getPaginator() {
		if (paginator == null) {
			paginator = new Paginator();
			loadPaginator();
		}
		return paginator;
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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
