package cz.kamoska.partner.servlets.filters;

import cz.kamoska.partner.dao.interfaces.AdvertBundleDaoInterface;
import cz.kamoska.partner.models.sessions.LoggedInPartner;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 13.10.12
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
@WebFilter(filterName = "PartnerHomePageServletFilter", urlPatterns = "/partner/index.jspx")
public class PartnerHomePageServletFilter implements Filter {

	@Inject
	private LoggedInPartner loggedInPartner;


	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

		HttpServletRequest request = (HttpServletRequest) req;
		if (request.getRequestURI().equals("/partner/index.jspx")) {
			if (loggedInPartner != null && loggedInPartner.getPartner() != null) {
				if (loggedInPartner.getPartner().getAdvertBundleEntityList() == null || loggedInPartner.getPartner().getAdvertBundleEntityList().isEmpty()) {
					//nema zalozenou zadnou skupinu, takze ho posleme jinam
					request.getRequestDispatcher("welcome.jspx").forward(req, resp);
					return;
				}
			}
		}
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {
	}

}
