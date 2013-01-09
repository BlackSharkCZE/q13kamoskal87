package cz.kamoska.partner.pojo.fakturoid;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.kamoska.partner.config.MainConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 27.10.12
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public class Invoice {

	private Integer id;
	private boolean proforma;
	private boolean partialProforma;
	private String number;
	private String variableSymbol;
	private String yourName;
	private String yourStreet;
	private String yourStreet2;
	private String yourCity;
	private String yourZip;
	private String yourCountry;
	private String yourRegistrationNo;
	private String yourVatNo;
	private String clientName;
	private String clientStreet;
	private String clientStreet2;
	private String clientCity;
	private String clientZip;
	private String clientCountry;
	private String clientRegistrationNo;
	private String clientVatNo;
	private Integer subjectId;
	private Integer generatorId;
	private Integer relatedId;
	private String token;
	private InvoiceStatus invoiceStatus;
	private String orderNumber;
	private Date issuedOn;    // datum vystaveni
	private Date taxableFulfillmentDue; // datum zdanitelneho plneni
	private Date due; // pocet dni nez bude po splatnosti
	private Date dueOn; // datum splatnosti
	private Date sentAt;
	private Date paidAt;
	private Date reminderSentAt;
	private Date acceptedAt;
	private Date canceledAt;
	private String note;
	private String footerNote;
	private String bankAccount;
	private String iban;
	private String swiftBic; /// BIC
	private PaymentMethod paymentMethod;
	private String currency;
	private BigDecimal exchangeRate; // kurz
	private String language;
	private Boolean transferredTaxLiability; // přenesená daňová povinnost
	private BigDecimal subtotal;
	private BigDecimal nativeSubtotal;
	private BigDecimal total;
	private BigDecimal nativeTotal;
	private BigDecimal remainingAmount;
	private BigDecimal remainingNativeAmount;
	private String htmlUrl;
	private String publicHtmlUrl;
	private String url;// API adresa faktury
	private String subjectUrl;
	private Date updatedAt;
	private List<InvoiceLine> lines;


	public Invoice() {
	}

	public Invoice(Integer subjectId, BigDecimal amountPrice) {
		this.subjectId = subjectId;
		proforma = true;
		lines = new ArrayList<>(1);
		total = amountPrice;
		InvoiceLine iLine = new InvoiceLine();
		iLine.setName(MainConfig.INVOICE_LINE_NAME);
		iLine.setQuantity("1");
		iLine.setUnitPrice(amountPrice);
		iLine.setVatRate(MainConfig.INVOICE_VAT_RATE);
		iLine.setWithVat(MainConfig.INVOICE_WITH_VAT);

		lines.add(iLine);
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isProforma() {
		return proforma;
	}

	public void setProforma(boolean proforma) {
		this.proforma = proforma;
	}

	@JsonProperty(value = "partial_proforma")
	public boolean isPartialProforma() {
		return partialProforma;
	}

	public void setPartialProforma(boolean partialProforma) {
		this.partialProforma = partialProforma;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@JsonProperty(value = "variable_symbol")
	public String getVariableSymbol() {
		return variableSymbol;
	}

	public void setVariableSymbol(String variableSymbol) {
		this.variableSymbol = variableSymbol;
	}

	@JsonProperty(value = "your_name")
	public String getYourName() {
		return yourName;
	}

	public void setYourName(String yourName) {
		this.yourName = yourName;
	}

	@JsonProperty(value = "your_street")
	public String getYourStreet() {
		return yourStreet;
	}

	public void setYourStreet(String yourStreet) {
		this.yourStreet = yourStreet;
	}

	@JsonProperty(value = "your_street2")
	public String getYourStreet2() {
		return yourStreet2;
	}

	public void setYourStreet2(String yourStreet2) {
		this.yourStreet2 = yourStreet2;
	}

	@JsonProperty(value = "your_city")
	public String getYourCity() {
		return yourCity;
	}

	public void setYourCity(String yourCity) {
		this.yourCity = yourCity;
	}

	@JsonProperty(value = "your_zip")
	public String getYourZip() {
		return yourZip;
	}

	public void setYourZip(String yourZip) {
		this.yourZip = yourZip;
	}

	@JsonProperty(value = "your_country")
	public String getYourCountry() {
		return yourCountry;
	}

	public void setYourCountry(String yourCountry) {
		this.yourCountry = yourCountry;
	}

	@JsonProperty(value = "your_registration_no")
	public String getYourRegistrationNo() {
		return yourRegistrationNo;
	}

	public void setYourRegistrationNo(String yourRegistrationNo) {
		this.yourRegistrationNo = yourRegistrationNo;
	}

	@JsonProperty(value = "your_vat_no")
	public String getYourVatNo() {
		return yourVatNo;
	}

	public void setYourVatNo(String yourVatNo) {
		this.yourVatNo = yourVatNo;
	}

	@JsonProperty(value = "client_name")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@JsonProperty(value = "client_street")
	public String getClientStreet() {
		return clientStreet;
	}

	public void setClientStreet(String clientStreet) {
		this.clientStreet = clientStreet;
	}

	@JsonProperty(value = "client_street2")
	public String getClientStreet2() {
		return clientStreet2;
	}

	public void setClientStreet2(String clientStreet2) {
		this.clientStreet2 = clientStreet2;
	}

	@JsonProperty(value = "client_city")
	public String getClientCity() {
		return clientCity;
	}

	public void setClientCity(String clientCity) {
		this.clientCity = clientCity;
	}

	@JsonProperty(value = "client_zip")
	public String getClientZip() {
		return clientZip;
	}

	public void setClientZip(String clientZip) {
		this.clientZip = clientZip;
	}

	@JsonProperty(value = "client_country")
	public String getClientCountry() {
		return clientCountry;
	}

	public void setClientCountry(String clientCountry) {
		this.clientCountry = clientCountry;
	}

	@JsonProperty(value = "client_registration_no")
	public String getClientRegistrationNo() {
		return clientRegistrationNo;
	}

	public void setClientRegistrationNo(String clientRegistrationNo) {
		this.clientRegistrationNo = clientRegistrationNo;
	}

	@JsonProperty(value = "client_vat_no")
	public String getClientVatNo() {
		return clientVatNo;
	}

	public void setClientVatNo(String clientVatNo) {
		this.clientVatNo = clientVatNo;
	}

	@JsonProperty(value = "subject_id", required = true)
	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@JsonProperty(value = "generator_id", required = false)
	public Integer getGeneratorId() {
		return generatorId;
	}

	@JsonProperty(value = "generator_id")
	public void setGeneratorId(Integer generatorId) {
		this.generatorId = generatorId;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	@JsonProperty(value = "related_id")
	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@JsonProperty(value = "status")
	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@JsonProperty(value = "order_number", required = false)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@JsonProperty(value = "issued_on")
	public Date getIssuedOn() {
		return issuedOn;
	}

	public void setIssuedOn(Date issuedOn) {
		this.issuedOn = issuedOn;
	}

	@JsonProperty(value = "taxable_fulfillment_due", required = false)
	public Date getTaxableFulfillmentDue() {
		return taxableFulfillmentDue;
	}

	public void setTaxableFulfillmentDue(Date taxableFulfillmentDue) {
		this.taxableFulfillmentDue = taxableFulfillmentDue;
	}

	@JsonProperty(required = false)
	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	@JsonProperty(value = "due_on")
	public Date getDueOn() {
		return dueOn;
	}

	public void setDueOn(Date dueOn) {
		this.dueOn = dueOn;
	}

	@JsonProperty(value = "sent_at")
	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	@JsonProperty(value = "paid_at")
	public Date getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(Date paidAt) {
		this.paidAt = paidAt;
	}

	@JsonProperty(value = "reminder_sent_at")
	public Date getReminderSentAt() {
		return reminderSentAt;
	}

	public void setReminderSentAt(Date reminderSentAt) {
		this.reminderSentAt = reminderSentAt;
	}

	@JsonProperty(value = "accepted_at")
	public Date getAcceptedAt() {
		return acceptedAt;
	}

	public void setAcceptedAt(Date acceptedAt) {
		this.acceptedAt = acceptedAt;
	}

	@JsonProperty(value = "cancelled_at")
	public Date getCanceledAt() {
		return canceledAt;
	}

	public void setCanceledAt(Date canceledAt) {
		this.canceledAt = canceledAt;
	}

	@JsonProperty(required = false)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@JsonProperty(value = "footer_note", required = false)
	public String getFooterNote() {
		return footerNote;
	}

	public void setFooterNote(String footerNote) {
		this.footerNote = footerNote;
	}

	@JsonProperty(value = "bank_account", required = false)
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@JsonProperty(required = false)
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	@JsonProperty(value = "swift_bic", required = false)
	public String getSwiftBic() {
		return swiftBic;
	}

	public void setSwiftBic(String swiftBic) {
		this.swiftBic = swiftBic;
	}

	@JsonProperty(value = "payment_method")
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@JsonProperty(required = false)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@JsonProperty(value = "exchange_rate", required = false)
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@JsonProperty(value = "transferred_tax_liability")
	public Boolean getTransferredTaxLiability() {
		return transferredTaxLiability;
	}

	public void setTransferredTaxLiability(Boolean transferredTaxLiability) {
		this.transferredTaxLiability = transferredTaxLiability;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	@JsonProperty(value = "native_subtotal")
	public BigDecimal getNativeSubtotal() {
		return nativeSubtotal;
	}

	public void setNativeSubtotal(BigDecimal nativeSubtotal) {
		this.nativeSubtotal = nativeSubtotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@JsonProperty(value = "native_total")
	public BigDecimal getNativeTotal() {
		return nativeTotal;
	}

	public void setNativeTotal(BigDecimal nativeTotal) {
		this.nativeTotal = nativeTotal;
	}

	@JsonProperty(value = "remaining_amount")
	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	@JsonProperty(value = "remaining_native_amount")
	public BigDecimal getRemainingNativeAmount() {
		return remainingNativeAmount;
	}

	public void setRemainingNativeAmount(BigDecimal remainingNativeAmount) {
		this.remainingNativeAmount = remainingNativeAmount;
	}

	@JsonProperty(value = "html_url")
	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	@JsonProperty(value = "public_html_url")
	public String getPublicHtmlUrl() {
		return publicHtmlUrl;
	}

	public void setPublicHtmlUrl(String publicHtmlUrl) {
		this.publicHtmlUrl = publicHtmlUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty(value = "subject_url")
	public String getSubjectUrl() {
		return subjectUrl;
	}

	public void setSubjectUrl(String subjectUrl) {
		this.subjectUrl = subjectUrl;
	}

	@JsonProperty(value = "updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<InvoiceLine> getLines() {
		return lines;
	}

	public void setLines(List<InvoiceLine> lines) {
		this.lines = lines;
	}
}
