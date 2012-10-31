package cz.kamoska.partner.pojo.fakturoid;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 27.10.12
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public enum PaymentMethod {

	BANK("bank"), CASH("cash"), COD("cod");

	private final String value;

	private PaymentMethod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static PaymentMethod getByKey(final String key) {
		for (PaymentMethod pm : PaymentMethod.values()) {
			if (pm.getValue().equals(key)) {
				return pm;
			}
		}
		throw  new EnumConstantNotPresentException(PaymentMethod.class, key);
	}
}
