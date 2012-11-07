package cz.kamoska.partner.models.application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.11.12
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@Named
public class BasicUrl {

	private final String partnerHomePage = "/partner/index.jspx";
	private final String partnerInvoices = "/partner/prehled-faktur.jspx";
	private final String partnerTipsList = "/partner/prehled-tipu.jspx";
	private final String partnerNotification = "/partner/zpravy.jspx";


	public String getPartnerHomePage() {
		return partnerHomePage;
	}

	public String getPartnerNotification() {
		return partnerNotification;
	}

	public String getPartnerTipsList() {
		return partnerTipsList;
	}

	public String getPartnerInvoices() {
		return partnerInvoices;
	}
}
