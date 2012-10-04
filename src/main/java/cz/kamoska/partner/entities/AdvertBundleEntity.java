package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class AdvertBundleEntity {

	@Id
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "valid_from")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validFrom;

	@NotNull
	@Column(name = "valid_to")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validTo;

	@Column(name = "expiration_notification_sent")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationNotificationSent; // zda bylo odeslano upozorneni, ze bude slupina zrusena

	@ManyToOne(fetch = FetchType.EAGER)
	private AdvertPriceGroupEntity advertPriceGroupEntity;

	@OneToMany(orphanRemoval = true)
	private List<AdvertEntity> advertEntityList;

	@ManyToOne
	private PartnerEntity partnerEntity;

}
