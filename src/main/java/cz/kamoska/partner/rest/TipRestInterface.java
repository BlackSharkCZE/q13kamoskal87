package cz.kamoska.partner.rest;

import cz.kamoska.partner.beans.singletons.AdvertProviderModel;
import cz.kamoska.partner.enums.AdvertDisplayStyle;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

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

	/*
	@Context
	private Application app;
	@Context
	private UriInfo uri;
	@Context
	private HttpHeaders headers;
	@Context
	private Request request;
	@Context
	private ServletContext context;
	*/


	@GET()
	@Produces(value = "text/javascript")
	@Path("{section}/{type}/{count}")
	public String getTips(
		 final @PathParam(value = "section") String section,
		 final @PathParam(value = "type") AdvertDisplayStyle type,
		 final @PathParam(value = "count") Integer count,
		 @Context HttpServletRequest request) {

		StringBuilder urlBase = new StringBuilder(128);
		urlBase.append(request.getScheme()).append("://").append(request.getServerName());
		if (request.getServerPort() != 80 && request.getServerPort() != 443) {
			urlBase.append(":").append(request.getServerPort());
		}
		urlBase.append(request.getServletContext().getContextPath());
		return advertProviderModel.getAdvertJavaScript(section, type, count).replace("#{pageContext.request.contextPath}", urlBase);
	}


	@GET()
	@Produces(value = "text/plain")
	@Path("/cache-reload/{section}")
	public String reloadAdvertCache(
		 final @PathParam(value = "section") String section,
		 @Context HttpServletRequest request) {
		advertProviderModel.reloadCacheForSection(section);
		return "reload cache for section " + section + " done.";
	}


}
