package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 17.10.12
 * Time: 18:52
 */
@Entity
@Table(name = "picture")
public class PictureEntity {

	@Id
	@GeneratedValue(generator = "pictureIDGen")
	@SequenceGenerator(name = "pictureIDGen", sequenceName = "picture_id_seq", allocationSize = 5)
	private Integer id;

	@NotNull
	@Column(name = "orig_name")
	private String origName;

	@NotNull
	@Column(name = "system_name")
	private String systemName;

	@Min(1)
	@NotNull
	@Column(name = "size")
	private Long size;

	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Min(60)
	@NotNull
	@Column(name = "orig_width")
	private Integer origWidth;

	@Min(60)
	@NotNull
	@Column(name = "orig_height")
	private Integer origHeight;

	@NotNull
	@Column(name = "content_type")
	private String contentType;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "partner_id")
	private PartnerEntity partnerEntity;

	@PrePersist
	public void prePersist() {
		if (dateCreated == null) {
			dateCreated = Calendar.getInstance().getTime();
		}
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getOrigWidth() {
		return origWidth;
	}

	public void setOrigWidth(Integer origWidth) {
		this.origWidth = origWidth;
	}

	public Integer getOrigHeight() {
		return origHeight;
	}

	public void setOrigHeight(Integer origHeight) {
		this.origHeight = origHeight;
	}

	public PartnerEntity getPartnerEntity() {
		return partnerEntity;
	}

	public void setPartnerEntity(PartnerEntity partnerEntity) {
		this.partnerEntity = partnerEntity;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
