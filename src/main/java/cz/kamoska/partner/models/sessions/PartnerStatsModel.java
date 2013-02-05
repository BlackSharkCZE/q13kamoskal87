package cz.kamoska.partner.models.sessions;

import cz.kamoska.partner.dao.interfaces.AdvertAccessListActualInterface;
import cz.kamoska.partner.dao.interfaces.AdvertAccessListDailyDaoInterface;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 9.12.12
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
@SessionScoped
@Named
public class PartnerStatsModel implements Serializable {

	private Date fromDate;
	private Date toDate;

	@EJB
	private AdvertAccessListActualInterface advertAccessListActualInterface;
	@EJB
	private AdvertAccessListDailyDaoInterface advertAccessListDailyDaoInterface;


	@Inject
	private LoggedInPartner loggedInPartner;

	@PostConstruct
	public void postConstruct() {
		Calendar c = Calendar.getInstance();
		toDate = new Date(c.getTimeInMillis());
		c.add(Calendar.DAY_OF_MONTH, -30);
		fromDate = new Date(c.getTimeInMillis());
	}

	@RolesAllowed("partner")
	public Long getAdvertDisplayCount() {
		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		Long actual = advertAccessListActualInterface.getDisplayCountByFromDateAndToDateAndPartnerID(fromDate, c.getTime(), loggedInPartner.getPartner().getId());
		Long daily = advertAccessListDailyDaoInterface.getDisplayCountByFromDateAndToDateAndPartnerID(fromDate, c.getTime(), loggedInPartner.getPartner().getId());
		return actual + daily;
	}

	public Long getAdvertDisplayCountBySystem() {

		Calendar c = Calendar.getInstance();
//		c.set(Calendar.DAY_OF_MONTH,1);
		c.add(Calendar.DAY_OF_MONTH, -30);

		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.DAY_OF_MONTH, 1);

		Long dCount = advertAccessListDailyDaoInterface.getDisplayCountByFromDateAndToDate(c.getTime(), c2.getTime());
		Long aCount = advertAccessListActualInterface.getDisplayCountByFromDateAndToDate(c.getTime(), c2.getTime());
		return dCount + aCount;

	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
