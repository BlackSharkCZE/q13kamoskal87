package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:29
 * Sekce stranek ve kterych muze byt reklama zobrazovana
 */
@Entity
@Table(name  = "section")
public class SectionEntity {

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
	@Column(name = "allways_selected")
	private Boolean alwaysSelected = Boolean.FALSE;	// zda je vzdy vybrana a tedy neni mozne ji vybrat

}
