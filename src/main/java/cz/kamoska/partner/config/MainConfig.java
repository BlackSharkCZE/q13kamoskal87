package cz.kamoska.partner.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 17.10.12
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
public class MainConfig {

	private static final Logger logger = Logger.getLogger(MainConfig.class);

	public static final String SRC_FILE_FOLDER = "src";
	public static String IMAGE_STORE_ROOT_PATH = "/srv/partner.kamoska.cz/images";
	public static final int ADVERT_CACHE_SIZE = 100;

	static {

		/*
		try {
			PropertiesConfiguration configuration = new PropertiesConfiguration("main-config.properties");
			IMAGE_STORE_ROOT_PATH = configuration.getString("IMAGE_STORE_ROOT_PATH", "/srv/partner.kamoska.cz/images");
		} catch (ConfigurationException e) {
			logger.error("Can not load configuration. ", e);
		}
	*/
	}

	public static final int PARTNER_CONNECTION_TIMEOUT = 5000;


	public static final int SOCKET_TIMEOUT = 5000;
	public static final int HTTP_PORT = 80;
	public static final int HTTPS_PORT = 443;

	public static final String FAKTUROID_DOMAIN = "fakturoid.cz";
	public static final String FAKTUROID_API_URL = "https://partnerkamoska.fakturoid.cz/api/v1/";
//	public static final String FAKTUROID_API_URL = "http://seznam.cz";
	public static final String FAKTUROID_API_TOKEN = "a44432585a05b2d162d703a9044ebaa88ef3412f";
	public static final String INVOICE_LINE_NAME = "Sada 5ti reklam Sponzorovaný tip na webu Kámoška.cz v délce trvání 1 roku.";
	public static final Integer INVOICE_VAT_RATE = 20;
	public static final boolean INVOICE_WITH_VAT = true;
	public static final String EMAIL_SMTP_HOST = "localhost";
	public static final int EMAIL_SMTP_PORT = 25;
	public static final String EMAIL_SMTP_USER = "";


	public static final String EMAIL_SMTP_PASSWORD = "";
	public static final boolean EMAIL_SMTP_TLS = false;
	public static final String EMAIL_FROM = "partner@kamoska.cz";

	public static final String EMAIL_NEW_PASSWORD_SUBJECT = "Zapomenute heslo: partner.kamoska.cz";
	public static final String EMAIL_REGISTER_SUBJECT = "Registrace: partner.kamoska.cz";
}
