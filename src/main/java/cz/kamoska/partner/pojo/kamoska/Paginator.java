package cz.kamoska.partner.pojo.kamoska;

import cz.kamoska.partner.entities.MessageEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 8.11.12
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
public class Paginator {

	private boolean newCreated = true;

	private Integer currentPage;
	private Integer maxPage;
	private Integer pageSize;
	private Integer unreadCount;
	private Integer allMessageCount;
	private List<MessageEntity> messageEntityList;

	public Paginator() {
		pageSize = 2;
		currentPage = 0;
		unreadCount = 0;
		maxPage = 0;
		allMessageCount = 0;
	}


	public boolean isLast() {
		return currentPage == maxPage-1;
	}

	public boolean isFirst() {
		return currentPage == 0;
	}


	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<MessageEntity> getMessageEntityList() {
		return messageEntityList;
	}

	public void setMessageEntityList(List<MessageEntity> messageEntityList) {
		this.messageEntityList = messageEntityList;
	}

	public Integer getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}

	public Integer getAllMessageCount() {
		return allMessageCount;
	}

	public void setAllMessageCount(Integer allMessageCount) {
		this.allMessageCount = allMessageCount;
	}

	public boolean isNewCreated() {
		return newCreated;
	}

	public void setNewCreated(boolean newCreated) {
		this.newCreated = newCreated;
	}
}
