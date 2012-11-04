package cz.kamoska.partner.rest;

import cz.kamoska.partner.enums.AdvertDisplayStyle;
import cz.kamoska.partner.models.application.AdvertProviderModel;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.11.12
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@Path("tip")
public class TipRestInterface {

	@EJB
	private AdvertProviderModel advertProviderModel;

	@GET()
	@Produces(value = "text/javascript")
	@Path("{section}/{type}/{count}")
	public String getTips(final @PathParam(value = "section") String section, final @PathParam(value = "type")AdvertDisplayStyle type, final @PathParam(value = "count") Integer count) {
		return "var s=\"Hello World;\"";
	}

}
