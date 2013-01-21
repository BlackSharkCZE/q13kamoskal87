package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.enums.MessageType;
import cz.kamoska.partner.pojo.kamoska.DefaultMessage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

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
		TIPS_PROFORMA_CREATED,
		INVOICE_PAID,
		ADVERT_ACCEPT,
		ADVERT_REJECT,
		EXTEND_PROFORMA_CREATED
	}

	@PostConstruct
	public void postConstruct() {
	}

	public DefaultMessage getMessageFor(MessageCategory messageCategory) {
		switch (messageCategory) {
			case FIRST_MESSAGE_BUNDLE:
				return new DefaultMessage("Byla vytvořena sada reklam “{BUNDLE_NAME}”", "Právě vám byla vytvořena nová sada reklam. Reklamu v této sadě vytvoříte jednoduše kliknutím na tlačítko “Nová reklama”. Pokud se vám nelíbí název sady reklam, je možné ho kliknutím na tento název editovat.", MessageType.NOTIFICATION);
			case TIPS_NOT_DISPLAY_IN_BUNDLE:
				return new DefaultMessage("Reklamy v sadě ”{BUNDLE_NAME}” se nezobrazují. Důvod: proforma faktura č. {INVOICE_NUMBER} není uhrazená.", "", MessageType.ALERT);
			case TIPS_PROFORMA_CREATED:
				return new DefaultMessage("Byla vám vystavená proforma faktura č. {INVOICE_NUMBER}", "Byla vám vystavená proforma faktura č. {INVOICE_NUMBER} za založení sady \"{BUNDLE_NAME}\". Tato sada není aktivní a reklamy v ní se nezobrazují. Po uhrazení proformy se schválené reklamy této sady začnou automaticky zobrazovat.", MessageType.NOTIFICATION);
			case INVOICE_PAID:
				return new DefaultMessage("Proforma č. {PROFORMA_NUMBER} je zaplacená.","Proforma faktura č. {PROFORMA_NUMBER} je zaplacená. Byla vám vystavená faktura č. {INVOICE_NUMBER} jako doklad o provedené platbě. Sada \"{BUNDLE_NAME}\" je aktivní do {VALID_TO} a reklamy v ní se zobrazují.",MessageType.NOTIFICATION);
			case ADVERT_ACCEPT:
				return new DefaultMessage("Schválená reklama - {ADVERT_TITLE}","Vaše reklama {ADVERT_TITLE} byla schválena a je aktivní.",MessageType.NOTIFICATION);
			case ADVERT_REJECT:
				return new DefaultMessage("Neschválená reklama - {ADVERT_TITLE}","Vaše reklama {ADVERT_TITLE} nebyla schválena. Důvod neschválení najdete v konkrétní sadě přímo u reklamy. Po ostranění nedostatků bude reklama opět předána ke schválení.",MessageType.NOTIFICATION);
			case EXTEND_PROFORMA_CREATED:
				return new DefaultMessage("Byla vám vystavená proforma faktura č. {INVOICE_NUMBER}","Byla vám vystavena proforma faktura č. {INVOICE_NUMBER} za prodloužení platnosti sady \"{BUNDLE_NAME}\". Pokud nebude proforma faktura uhrazená, reklamy v této sadě se přestanou zobrazovat {EXPIRE}.",MessageType.NOTIFICATION);
			default:
				return null;
		}
	}
}
