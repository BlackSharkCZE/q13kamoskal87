package cz.kamoska.partner.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:06
 * Entita rika, ktere zpravy jsou prirazene kteremu uzivateli a zda je jiz cetl a zda ji smazal
 */
@Entity
@Table(name = "message_status")
public class MessagePartnerStatusEntity {

	@Id
	@Column(name = "id")
	private Integer id;


	@NotNull
	@OneToOne
	@JoinColumn(name = "partner_id")
	private PartnerEntity partner;

	@NotNull
	@OneToOne
	@JoinColumn(name = "message_id")
	private MessageEntity message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "read")
	private Date read;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted")
	private Date deleted;



}
