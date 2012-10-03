package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "advert_groups")
public class AdvertGroupEntity {

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

	//todo zaradit skupinu do cenove skupiny

}
