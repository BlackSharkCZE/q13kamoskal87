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
	public static String IMAGE_STORE_ROOT_PATH = null;

	static {

		try {
			PropertiesConfiguration configuration = new PropertiesConfiguration("main-config.properties");
			IMAGE_STORE_ROOT_PATH = configuration.getString("IMAGE_STORE_ROOT_PATH", "/srv/partner.kamoska.cz/images");
		} catch (ConfigurationException e) {
			logger.error("Can not load configuration. ", e);
		}

	}

}
