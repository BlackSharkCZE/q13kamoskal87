package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.11.12
 * Time: 14:13
 * Historie zobrazovani reklam pro konkretni casovou znacku
 */
@Entity
@Table(name = "advert_accesslist_actual")
@NamedQueries({
	 @NamedQuery(name = "AdvertAccessListEntity.getDisplayCountByFromDateAndToDate", query = "SELECT COUNT(a) FROM AdvertAccessListEntity a WHERE a.dateCreated>=:fromDate AND a.dateCreated<:toDate")
})

@NamedNativeQueries({@NamedNativeQuery(name = "AdvertAccessListEntity.native.getDisplayCountByFromDateAndToDateAndPartnerID",
	 query = "select count(aaa.id) from advert_accesslist_actual aaa\n" +
		  "  join advert a on a.id = aaa.advert_id\n" +
		  "  join advert_bundle ab on ab.id = a.bundle_id\n" +
		  "  join partner p on p.id = ab.partnerentity_id\n" +
		  "  where aaa.datecreated>=#fromDate and aaa.datecreated < #toDate and p.id = #partnerID")})

public class AdvertAccessListEntity {

	@Id
	@GeneratedValue(generator = "advert_accesslist_actual_id_seq")
	@SequenceGenerator(name = "advert_accesslist_actual_id_seq", sequenceName = "advert_accesslist_actual_id_seq", allocationSize = 50)
	@NotNull
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "advert_id")
	@NotNull
	private AdvertEntity advertEntity;

	@Column(name = "referrer", length = 1024)
	private String referrer;

	public AdvertAccessListEntity() {
	}

	public AdvertAccessListEntity(AdvertEntity advertEntity, Date dateCreated) {
		this.advertEntity = advertEntity;
		this.dateCreated = dateCreated;
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

	public AdvertEntity getAdvertEntity() {
		return advertEntity;
	}

	public void setAdvertEntity(AdvertEntity advertEntity) {
		this.advertEntity = advertEntity;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
}
