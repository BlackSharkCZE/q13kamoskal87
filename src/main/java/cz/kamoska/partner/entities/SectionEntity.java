package cz.kamoska.partner.entities;

import org.eclipse.persistence.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:29
 * Sekce stranek ve kterych muze byt reklama zobrazovana
 */
@NamedQueries({
		@NamedQuery(name = "SectionEntity.findAll", query = "SELECT s FROM SectionEntity s ORDER BY s.id ASC"),
		@NamedQuery(name = "SectionEntity.findAllAlwaysSelected", query = "SELECT s FROM SectionEntity s WHERE s.alwaysSelected=true ORDER BY s.id ASC"),
})

@Entity
@Table(name  = "section")
@Index(name = "URL_NAME_INDEX", columnNames = {"url_name"})
public class SectionEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "sectionIdSeg")
	@SequenceGenerator(name = "sectionIdSeg", sequenceName = "section_id_seq", allocationSize = 5)
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

	@NotNull
	@Column(name = "url_name", length = 15)
	private String urlName;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SectionEntity that = (SectionEntity) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@PrePersist
	public void prePersist() {
		if (dateCreated == null) {
			dateCreated = Calendar.getInstance().getTime();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAlwaysSelected() {
		return alwaysSelected;
	}

	public void setAlwaysSelected(Boolean alwaysSelected) {
		this.alwaysSelected = alwaysSelected;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
}
