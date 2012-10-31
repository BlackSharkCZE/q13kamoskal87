package cz.kamoska.partner.pojo.fakturoid;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 31.10.12
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceLine {

	private String name;
	private String quantity;
	private String unitName;
	private BigDecimal unitPrice;
	private Integer vatRate;
	private boolean withVat;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@JsonProperty(value = "unit_name")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@JsonProperty(value = "unit_price")
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@JsonProperty(value = "vat_rate")
	public Integer getVatRate() {
		return vatRate;
	}

	public void setVatRate(Integer vatRate) {
		this.vatRate = vatRate;
	}

	@JsonProperty(value = "with_vat")
	public boolean isWithVat() {
		return withVat;
	}

	public void setWithVat(boolean withVat) {
		this.withVat = withVat;
	}
}
