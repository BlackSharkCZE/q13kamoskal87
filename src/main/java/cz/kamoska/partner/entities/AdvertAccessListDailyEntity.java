package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 5.11.12
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "advert_accesslist_daily")

@NamedQueries({
		@NamedQuery(name = "AdvertAccessListDailyEntity.getDisplayCountByFromDateAndToDate", query = "SELECT SUM(a.displayCount) FROM AdvertAccessListDailyEntity a WHERE a.forDate>=:fromDate AND a.forDate < :toDate")
}
)

@NamedNativeQueries({
	 @NamedNativeQuery(name="AdvertAccessListDailyEntity.native.getDisplatCoutByFromDateAndToDateAndPartnerID",
		  query = "select sum(acd.display_count) from advert_accesslist_daily acd\n" +
				"  join advert a on a.id = acd.advert_entity\n" +
				"  join advert_bundle ab on ab.id = a.bundle_id\n" +
				"  join partner p on p.id = ab.partnerentity_id\n" +
				"  where acd.for_date>=#fromDate and acd.for_date < #toDate and p.id = #partnerID")
})

public class AdvertAccessListDailyEntity {

	@Id
	@GeneratedValue(generator = "AdvertAccessListDailyIdSeqGen")
	@SequenceGenerator(name = "AdvertAccessListDailyIdSeqGen", sequenceName = "advert_accesslist_daily_id_seq", allocationSize = 1)
	private Integer id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "for_date")
	private Date forDate;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "advert_entity")
	private AdvertEntity advertEntity;

	@Column(name = "display_count")
	@NotNull
	private Integer displayCount;

	@PrePersist
	public void prePersist() {
		if (displayCount == null) {
			displayCount = 0;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getForDate() {
		return forDate;
	}

	public void setForDate(Date forDate) {
		this.forDate = forDate;
	}

	public AdvertEntity getAdvertEntity() {
		return advertEntity;
	}

	public void setAdvertEntity(AdvertEntity advertEntity) {
		this.advertEntity = advertEntity;
	}

	public Integer getDisplayCount() {
		return displayCount;
	}

	public void setDisplayCount(Integer displayCount) {
		this.displayCount = displayCount;
	}
}
