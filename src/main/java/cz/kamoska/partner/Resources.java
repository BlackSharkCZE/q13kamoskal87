package cz.kamoska.partner;

import org.apache.log4j.Logger;

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
		return Logger.getLogger(ip.getMember().getDeclaringClass());
	}

	@Produces
	FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
