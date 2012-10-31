package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 31.10.12
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name ="invoices")
public class Invoice {

	@Id
	@GeneratedValue(generator = "invoiceIDGenerator")
	@SequenceGenerator(name = "invoiceIDGenerator", sequenceName = "invoices_id_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "date_created")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;


	@Column(name = "paid")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "advert_bundle_id")
	private AdvertBundleEntity advertBundleEntity;

	@Column(name = "fakturoid_id")
	@NotNull
	private Integer fakturoidId;

	@Column(name = "fakturoid_url")
	@NotNull
	private String fakturoidUrl;


	@PrePersist
	public void prepersist() {
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

	public Date getPaid() {
		return paid;
	}

	public void setPaid(Date paid) {
		this.paid = paid;
	}

	public AdvertBundleEntity getAdvertBundleEntity() {
		return advertBundleEntity;
	}

	public void setAdvertBundleEntity(AdvertBundleEntity advertBundleEntity) {
		this.advertBundleEntity = advertBundleEntity;
	}

	public Integer getFakturoidId() {
		return fakturoidId;
	}

	public void setFakturoidId(Integer fakturoidId) {
		this.fakturoidId = fakturoidId;
	}

	public String getFakturoidUrl() {
		return fakturoidUrl;
	}

	public void setFakturoidUrl(String fakturoidUrl) {
		this.fakturoidUrl = fakturoidUrl;
	}
}
