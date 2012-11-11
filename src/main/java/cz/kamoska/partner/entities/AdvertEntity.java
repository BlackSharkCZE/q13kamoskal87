package cz.kamoska.partner.entities;

import cz.kamoska.partner.enums.AdvertState;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
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
@NamedQueries({
		@NamedQuery(name = "AdvertEntity.findAllWaitingToACK", query = "SELECT a FROM AdvertEntity a WHERE a.state=cz.kamoska.partner.enums.AdvertState.WAITING_TO_ACK ORDER BY a.dateCreated ASC"),
		@NamedQuery(name = "AdvertEntity.countInState", query = "SELECT COUNT(a) FROM AdvertEntity a WHERE a.state=:state ")
})


@NamedNativeQueries({
		@NamedNativeQuery(
				name = "AdvertEntity.native.findLessUsedBySection",
				query = "select a.* from advert a left join \n" +
						"(select \n" +
						"  sum (display_count) as display_count, advert_entity \n" +
						"  from (\n" +
						"select dl.display_count, dl.advert_entity from advert_accesslist_daily dl \n" +
						"union\n" +
						"select count(al.id) as display_count, al.advert_id as advert_entity from advert_accesslist_actual al group by advert_id    \n" +
						") as s\n" +
						"group by s.advert_entity) foo on a.id = foo.advert_entity\n" +
						"  join advert_bundle ab on a.bundle_id = ab.id\n" +
						"  join advert_section asct on asct.advertentity_id = a.id\n" +
						"  join section sec on sec.id = asct.sectionentitylist_id\n" +
						"  where a.accept_date is not null\n" +
						"  and ab.status='ACTIVE'\n" +
						"  and a.state='ACTIVE'\n" +
						"  and sec.url_name = #sectionUrlName\n" +
						"  and ab.valid_to > now()\n" +
						"  order by foo.display_count asc\n" +
						"  ",
				resultClass = AdvertEntity.class)
})
public class AdvertEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "AdvertEntityIdGen")
	@SequenceGenerator(name = "AdvertEntityIdGen", sequenceName = "advert_id_seq", allocationSize = 10)
	private Integer id;

	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	@OrderBy
	private Date dateCreated;


	@Column(name = "accept_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date acceptDate;


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

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bundle_id")
	private AdvertBundleEntity bundleEntity;

	@PrePersist
	public void prePersist() {
		if (dateCreated == null) {
			dateCreated = Calendar.getInstance().getTime();
		}
		if (viewCount == null) {
			viewCount = 0;
		}
	}


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

	public PictureEntity getPictureEntity() {
		return pictureEntity;
	}

	public void setPictureEntity(PictureEntity pictureEntity) {
		this.pictureEntity = pictureEntity;
	}

	public AdvertBundleEntity getBundleEntity() {
		return bundleEntity;
	}

	public void setBundleEntity(AdvertBundleEntity bundleEntity) {
		this.bundleEntity = bundleEntity;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
}
