package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:54
 * Jedna se o zpravu, kterou pise administrator danemu partnerovi
 */
@Entity
@Table(name = "message")
public class MessageEntity {

	@Id
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@NotNull
	@Column(name = "title")
	@Size(max = 128)
	private String title;

	@NotNull
	@Column(name = "body", columnDefinition = "TEXT")
	private String body;

	@NotNull
	@Column(name = "display_since")
	@Temporal(TemporalType.TIMESTAMP)
	private Date displaySince; // od kdy se bude zprava zobrazovat


}
