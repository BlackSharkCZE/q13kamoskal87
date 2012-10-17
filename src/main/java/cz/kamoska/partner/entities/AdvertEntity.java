package cz.kamoska.partner.entities;

import cz.kamoska.partner.enums.AdvertState;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:32
 * Jedna se o vlastni reklamu. Obrazek, titulek, text, urladres a zarazeni do skupin stranek, kde se ma zobrazovat
 */
@Entity
@Table(name = "advert")
public class AdvertEntity {

	@Id
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Size(max = 50)
	@Column(name = "title")
	@NotNull
	private String title;

	@Size(max = 90)
	@Column(name = "body")
	@NotNull
	private String body;

	@NotNull
	@Column(name = "url")
	@Size(max = 1024)
	private String url;

	@NotNull
	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private AdvertState state;

	@OneToMany(fetch = FetchType.EAGER)
	private List<SectionEntity> sectionEntityList;

	@NotNull
	@Min(0)
	@Column(name = "view_count")
	private Integer viewCount;

	@Column(name = "reject_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date rejectDate;

	@Column(name = "reject_message", columnDefinition = "TEXT")
	private String rejectMessage;

	@NotNull
	@OneToOne
	@JoinColumn(name = "picture_id")
	private PictureEntity pictureEntity;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AdvertState getState() {
		return state;
	}

	public void setState(AdvertState state) {
		this.state = state;
	}

	public List<SectionEntity> getSectionEntityList() {
		return sectionEntityList;
	}

	public void setSectionEntityList(List<SectionEntity> sectionEntityList) {
		this.sectionEntityList = sectionEntityList;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Date getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(Date rejectDate) {
		this.rejectDate = rejectDate;
	}

	public String getRejectMessage() {
		return rejectMessage;
	}

	public void setRejectMessage(String rejectMessage) {
		this.rejectMessage = rejectMessage;
	}
}
