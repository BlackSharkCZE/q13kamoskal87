package cz.kamoska.partner.entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 20:47
 * Jedna se o cenove zarazeni prodavaneho baliku. 1. balik 5ti reklam je za 1000,- na rok, 2. balik 5ti reklam je za 900,- na rok
 */

@NamedQueries({
		@NamedQuery(name = "AdvertPriceGroupEntity.findAll", query = "SELECT a FROM AdvertPriceGroupEntity  a ORDER BY a.oIndex ASC"),
		@NamedQuery(name = "AdvertPriceGroupEntity.findNextForOIndex", query = "SELECT a FROM AdvertPriceGroupEntity  a WHERE a.oIndex <= :oindex ORDER BY a.oIndex DESC"),
})

@Entity
@Table(name = "advert_price_group")
public class AdvertPriceGroupEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "AdvertPriceGroupEntityIdGenerator")
	@SequenceGenerator(name = "AdvertPriceGroupEntityIdGenerator", sequenceName = "advert_price_group_id_seq", allocationSize = 1)
	private Integer id;

	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@NotNull
	@Column(name = "name")
	@Size(max	 = 256)
	private String name;

	@NotNull
	@Column(name = "price_czk")
	@Digits(integer =6, fraction = 2)
	private BigDecimal priceCzk;

	@NotNull
	@Column(name = "duration_days")
	@Min(value = 1)
	private Integer duration;

	@NotNull
	@Column(name = "oindex")
	private Integer oIndex;		// urcuje poradi cenove skupiny. Pro 2. AdvertGroup pouzij advert_price_group s oindex=2, nebo tu s nejvyssim oindex

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

	public BigDecimal getPriceCzk() {
		return priceCzk;
	}

	public void setPriceCzk(BigDecimal priceCzk) {
		this.priceCzk = priceCzk;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getoIndex() {
		return oIndex;
	}

	public void setoIndex(Integer oIndex) {
		this.oIndex = oIndex;
	}
}
