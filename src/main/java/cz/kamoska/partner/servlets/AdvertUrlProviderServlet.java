package cz.kamoska.partner.servlets;

import cz.kamoska.partner.dao.interfaces.AdvertDaoInterface;
import cz.kamoska.partner.entities.AdvertEntity;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.11.12
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "AdvertUrlProviderServlet", urlPatterns = {"/advert"})
public class AdvertUrlProviderServlet extends HttpServlet {

	private final Logger logger = Logger.getLogger(AdvertUrlProviderServlet.class);

	@EJB
	private AdvertDaoInterface advertDaoInterface;

	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
			logger.warn("Can not redirect to advert link, because missing ID parameter in Request!");
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} else {

			Integer advertId = null;
			try {
				advertId = Integer.parseInt(request.getParameter("id"));
			} catch (NumberFormatException e) {
				logger.warn("Can not redirect to advert link, because invalid ID ("+request.getParameter("id")+") in request!");
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}

			if (advertId != null) {
				AdvertEntity advert = advertDaoInterface.findByPrimaryKey(AdvertEntity.class, advertId);
				if (advert != null) {
					response.sendRedirect(advert.getUrl().startsWith("http://") ? advert.getUrl() : "http://"+advert.getUrl() );
				} else {
					logger.error("Can not find advert with ID "+ advertId);
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
}
