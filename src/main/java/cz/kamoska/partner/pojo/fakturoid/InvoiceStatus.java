package cz.kamoska.partner.pojo.fakturoid;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 27.10.12
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
public enum InvoiceStatus {

	OPEN("open"), SENT("sent"), OVERTUDE("overtude"),PAID("paid");


	private final String value;

	private InvoiceStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static InvoiceStatus getByKey(final String key) {
		for (InvoiceStatus is : InvoiceStatus.values()) {
			if (is.getValue().equals(key)) {
				return is;
			}
		}
		throw  new EnumConstantNotPresentException(InvoiceStatus.class, key);
	}
}
