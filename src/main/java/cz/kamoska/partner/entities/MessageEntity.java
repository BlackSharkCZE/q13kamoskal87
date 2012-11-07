package cz.kamoska.partner.entities;

import cz.kamoska.partner.enums.MessageType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.11.12
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "message")

@NamedQueries({
		@NamedQuery(name = "Message.findByPartnerIDAndTypeAndPublished", query = "SELECT m FROM MessageEntity m WHERE m.partner.id = :partnerID AND m.messageType = :messageType AND m.publishDate <= CURRENT_TIMESTAMP ORDER BY m.publishDate DESC"),
		@NamedQuery(name = "Message.getCountByPartnerIDAndTypeAndPublished", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.partner.id = :partnerID AND m.messageType = :messageType AND m.publishDate <= CURRENT_TIMESTAMP"),
		@NamedQuery(name = "Message.getUnreadCountByPartnerIDAndTypeAndPublished", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.partner.id = :partnerID AND m.messageType = :messageType AND m.publishDate <= CURRENT_TIMESTAMP AND m.read IS NULL")
})

public class MessageEntity {

	@Id
	@GeneratedValue(generator = "messageIDGen")
	@SequenceGenerator(name = "messageIDGen", sequenceName = "message_id_seq", allocationSize = 1)
	private Integer id;


	@NotNull
	@Column(name = "body", columnDefinition = "TEXT")
	private String body;

	@Column(name = "date_created")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "publish_date")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishDate; // kdy to bude vypublikovano

	@Column(name = "read")
	@Temporal(TemporalType.TIMESTAMP)
	private Date read;

	@Column(name = "group_uid")
	@NotNull
	private String groupUid;

	@OneToOne
	@JoinColumn(name = "partner_id")
	@NotNull
	private PartnerEntity partner;

	@Column(name = "message_type")
	@Enumerated(EnumType.STRING)
	private MessageType messageType;

	@NotNull
	@Column(name = "title")
	private String title;


	@PrePersist
	public void prePersist() {
		if (dateCreated == null) {
			dateCreated = Calendar.getInstance().getTime();
		}
	}

	public MessageEntity() {
		this.messageType = MessageType.ALERT;
	}

	@Override
	public String toString() {
		return "MessageEntity{" +
				"id=" + id +
				", title='" + title + '\'' +
				", messageType=" + messageType +
				", partner=" + partner +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getRead() {
		return read;
	}

	public void setRead(Date read) {
		this.read = read;
	}

	public String getGroupUid() {
		return groupUid;
	}

	public void setGroupUid(String groupUid) {
		this.groupUid = groupUid;
	}

	public PartnerEntity getPartner() {
		return partner;
	}

	public void setPartner(PartnerEntity partner) {
		this.partner = partner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
}
