package cz.kamoska.partner.entities;

import cz.kamoska.partner.enums.InvoiceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
@Table(name = "invoices")

@NamedQueries({
		@NamedQuery(name = "InvoiceEntity.findAllForPartner", query = "SELECT i FROM InvoiceEntity i WHERE i.advertBundleEntity.partnerEntity.id=:partnerID ORDER BY i.dateCreated DESC"),
		@NamedQuery(name = "InvoiceEntity.findAllProformaForPartner", query = "SELECT i FROM InvoiceEntity i WHERE i.advertBundleEntity.partnerEntity.id=:partnerID AND i.invoiceType=cz.kamoska.partner.enums.InvoiceType.PROFORMA ORDER BY i.dateCreated DESC"),
		@NamedQuery(name = "InvoiceEntity.findNotPaid", query = "SELECT i FROM InvoiceEntity i WHERE i.paid IS NULL"),

		@NamedQuery(name = "InvoiceEntity.findNotPaidForBundleNotDisplay",
				query = "SELECT i FROM InvoiceEntity i " +
						"WHERE i.invoiceType=cz.kamoska.partner.enums.InvoiceType.PROFORMA " +
						"AND i.paid IS NULL " +
						"AND ( i.advertBundleEntity.validTo IS NULL OR i.advertBundleEntity.validTo < CURRENT_TIMESTAMP ) " +
						"AND i.advertBundleEntity.partnerEntity.id = :partnerID")
})


@NamedNativeQueries({
		@NamedNativeQuery(
				name = "InvoiceEntity.native.findEndigNotPaid",
				query = "select i.*  from advert_bundle ab join invoices i on i.advert_bundle_id = ab.id where i.invoicetype='PROFORMA' and i.paid is null and (   (ab.valid_to - now()) < '15 day' ) and ab.partnerentity_id = #partnerID",
				resultClass = InvoiceEntity.class)
})

public class InvoiceEntity {

	@Id
	@GeneratedValue(generator = "invoiceIDGenerator")
	@SequenceGenerator(name = "invoiceIDGenerator", sequenceName = "invoices_id_seq", allocationSize = 1)
	private Integer id;

	@NotNull
	@Column(name = "number", length = 50)
	private String number;

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

	@NotNull
	@Enumerated(EnumType.STRING)
	private InvoiceType invoiceType;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "price_inc_vat", precision = 10, scale = 2)
	private BigDecimal priceIncVat;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "invoice_id")
	private InvoiceEntity invoice = null;        // zakladem je proforma faktura, a k ni je po zaplaceni dostupna normalni faktura


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

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "InvoiceEntity{" +
				"id=" + id +
				", number='" + number + '\'' +
				", dateCreated=" + dateCreated +
				", paid=" + paid +
				", advertBundleEntity=" + advertBundleEntity +
				", fakturoidId=" + fakturoidId +
				", fakturoidUrl='" + fakturoidUrl + '\'' +
				", invoiceType=" + invoiceType +
				'}';
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPriceIncVat() {
		return priceIncVat;
	}

	public void setPriceIncVat(BigDecimal priceIncVat) {
		this.priceIncVat = priceIncVat;
	}

	public InvoiceEntity getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceEntity invoice) {
		this.invoice = invoice;
	}

}
