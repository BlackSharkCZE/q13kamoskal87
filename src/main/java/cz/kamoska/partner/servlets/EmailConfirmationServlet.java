package cz.kamoska.partner.servlets;

import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 12.10.12
 * Time: 21:38
 * Servlet je navazany na URL: /activate/*, kde * je confirmation hash z emailu a provadi aktivaci uctu (overeni pomoci emailu)
 */
@WebServlet(name = "EmailConfirmationServlet", urlPatterns = "/activate/*")
public class EmailConfirmationServlet extends HttpServlet {

	private static final String ACTIVATE_ERROR_PAGE = "/activate-email-error.jspx";
	private static final String ACTIVATE_SUCCESS_PAGE = "/activate-email-success.jspx";

	private final Logger logger = Logger.getLogger(MainConfig.LOGGER_NAME);

	@EJB
	private PartnerDaoInterface partnerDaoInterface;

	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String url[] = request.getRequestURI().split("/");
		if (url != null && url.length > 1) {
			final String cHash = url[url.length - 1];
			PartnerEntity partnerEntity = partnerDaoInterface.findByEmailConfirmationHash(cHash);
			if (partnerEntity != null) {
				partnerEntity.setActivated(Calendar.getInstance().getTime());
				SaveDomainResult<PartnerEntity> updateResult = partnerDaoInterface.update(partnerEntity);
				if (updateResult.success) {
					logger.info("Partner " + partnerEntity + " activated!");
					request.getRequestDispatcher(ACTIVATE_SUCCESS_PAGE).forward(request, response);
				} else {
					logger.info("Partner "+ partnerEntity + " can not been activated.");
					request.getRequestDispatcher(ACTIVATE_ERROR_PAGE).forward(request, response);
				}
			} else {
				logger.warning("Can not find Partner by Confirmation Hash " + cHash);
				request.getRequestDispatcher(ACTIVATE_ERROR_PAGE).forward(request, response);
			}
		} else {
			logger.warning("Can not activate partner with null or emepty confirmation Hash. Request: " + request.getRequestURI());
			request.getRequestDispatcher(ACTIVATE_ERROR_PAGE).forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
}
