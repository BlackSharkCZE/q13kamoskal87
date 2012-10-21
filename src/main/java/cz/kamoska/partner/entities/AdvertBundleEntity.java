package cz.kamoska.partner.entities;

import cz.kamoska.partner.enums.AdvertState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:43
 * Jedna se o prodavany balik 5ti reklam.
 */
@Entity
@Table(name = "advert_bundle")

@NamedQueries({
		@NamedQuery(name = "AdvertBundleEntity.findByPartnerId", query = "SELECT a FROM AdvertBundleEntity a where a.partnerEntity.id = :partnerID")
})
public class AdvertBundleEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "AdvertBundleIdGenerator")
	@SequenceGenerator(name = "AdvertBundleIdGenerator", sequenceName = "advert_bundle_id_seq", allocationSize = 1)
	private Integer id;

	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@NotNull
	@Column(name = "name")
	private String name;

	@Column(name = "valid_from")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validFrom;

	@Column(name = "valid_to")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validTo;

	@Column(name = "expiration_notification_sent")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationNotificationSent; // zda bylo odeslano upozorneni, ze bude slupina zrusena

	@ManyToOne(fetch = FetchType.EAGER)
	private AdvertPriceGroupEntity advertPriceGroupEntity;

	@OneToMany(orphanRemoval = true, mappedBy = "bundleEntity")
	@OrderBy(value = "dateCreated")
	private List<AdvertEntity> advertEntityList;

	@ManyToOne
	@JoinColumn(nullable = false)
	private PartnerEntity partnerEntity;


	@NotNull
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private AdvertState status;

	@Column(name = "reject_message", columnDefinition = "TEXT")
	private String rejectMessage;


	@Transient()
	public int getAdvertCount() {
		if (advertEntityList == null || advertEntityList.isEmpty()) {
			return 0;
		} else {
			return advertEntityList.size();
		}
	}

	public AdvertBundleEntity() {
		status = AdvertState.WAITING_TO_ACK;
	}

	public Object[] getAdvertSpacer() {
		int a = advertEntityList == null ? 5 : 5 - advertEntityList.size();
		return new Object[a];
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public Date getExpirationNotificationSent() {
		return expirationNotificationSent;
	}

	public void setExpirationNotificationSent(Date expirationNotificationSent) {
		this.expirationNotificationSent = expirationNotificationSent;
	}

	public AdvertPriceGroupEntity getAdvertPriceGroupEntity() {
		return advertPriceGroupEntity;
	}

	public void setAdvertPriceGroupEntity(AdvertPriceGroupEntity advertPriceGroupEntity) {
		this.advertPriceGroupEntity = advertPriceGroupEntity;
	}

	public List<AdvertEntity> getAdvertEntityList() {
		return advertEntityList;
	}

	public void setAdvertEntityList(List<AdvertEntity> advertEntityList) {
		this.advertEntityList = advertEntityList;
	}

	public PartnerEntity getPartnerEntity() {
		return partnerEntity;
	}

	public void setPartnerEntity(PartnerEntity partnerEntity) {
		this.partnerEntity = partnerEntity;
	}

	public AdvertState getStatus() {
		return status;
	}

	public void setStatus(AdvertState status) {
		this.status = status;
	}

	public String getRejectMessage() {
		return rejectMessage;
	}

	public void setRejectMessage(String rejectMessage) {
		this.rejectMessage = rejectMessage;
	}
}
