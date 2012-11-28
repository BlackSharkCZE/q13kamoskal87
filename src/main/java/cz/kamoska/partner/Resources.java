package cz.kamoska.partner;

import cz.kamoska.partner.config.MainConfig;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 16.11.12
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public class Resources {


	@Produces
	Logger getLogger(InjectionPoint ip) {
//		return Logger.getLogger(ip.getMember().getDeclaringClass());
		return Logger.getLogger(MainConfig.LOGGER_NAME);
	}

	@Produces
	FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
