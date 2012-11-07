package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.enums.MessageType;
import cz.kamoska.partner.pojo.kamoska.DefaultMessage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 7.11.12
 * Time: 20:05
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class DefaultMessages implements Serializable {


	public enum MessageCategory {
		FIRST_MESSAGE_BUNDLE,
		TIPS_NOT_DISPLAY_IN_BUNDLE,
		TIPS_PROFORMA_CREATED
	}

	@PostConstruct
	public void postConstruct() {
	}

	public DefaultMessage getMessageFor(MessageCategory messageCategory) {

		switch (messageCategory) {
			case FIRST_MESSAGE_BUNDLE:
				return new DefaultMessage("Byla vytvořena první sada reklam “{BUNDLE_NAME}”", "Právě vám byla vytvořena první sada reklam. Reklamu v této sadě vytvoříte jednoduše kliknutím na tlačítko “Nová reklama”. Pokud se vám nelíbí název sady reklam, je možné ho kliknutím na tento název editovat.", MessageType.NOTIFICATION);
			case TIPS_NOT_DISPLAY_IN_BUNDLE:
				return new DefaultMessage("Reklamy v sadě ”{BUNDLE_NAME}” se nezobrazují. Důvod: proforma faktura č. {INVOICE_NUMBER} není uhrazená.", "", MessageType.ALERT);
			case TIPS_PROFORMA_CREATED:
				return new DefaultMessage("Byla vám vystavená proforma faktura č. {INVOICE_NUMBER}", "Byla vám vystavená proforma faktura č. 1-2012-0452 za založení sady \"{INVOICE_NUMBER}\". Tato sada není aktivní a reklamy v ní se nezobrazují. Po uhrazení proformy se schválené reklamy této sady začnou automaticky zobrazovat.", MessageType.ALERT);
			default:
				return null;
		}
	}


}
