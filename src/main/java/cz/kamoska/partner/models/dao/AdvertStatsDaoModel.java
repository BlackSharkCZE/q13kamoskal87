package cz.kamoska.partner.models.dao;

import cz.kamoska.partner.dao.interfaces.AdvertAccessListActualInterface;
import cz.kamoska.partner.dao.interfaces.AdvertAccessListDailyDaoInterface;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 24.11.12
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class AdvertStatsDaoModel implements Serializable {

	@EJB
	private AdvertAccessListActualInterface advertAccessListActualInterface;
	@EJB
	private AdvertAccessListDailyDaoInterface advertAccessListDailyDaoInterface;

	public Long getDisplayAllCountCurrentMonth() {

		Calendar fromDate = Calendar.getInstance();
		fromDate.set(Calendar.DAY_OF_MONTH,1);
		fromDate.set(Calendar.HOUR_OF_DAY, 0);
		fromDate.set(Calendar.MINUTE, 0);
		fromDate.set(Calendar.SECOND, 1);

		Calendar toDate = Calendar.getInstance();
		toDate.set(Calendar.DAY_OF_MONTH,1);
		int month = toDate.get(Calendar.MONTH);
		month++;
		if (month > Calendar.DECEMBER) {
			month = Calendar.JANUARY;
		}
		toDate.set(Calendar.MONTH,month);

		Long s1 = advertAccessListActualInterface.getDisplayCountByFromDateAndToDate(fromDate.getTime(), toDate.getTime());
		Long s2 = advertAccessListDailyDaoInterface.getDisplayCountByFromDateAndToDate(fromDate.getTime(), toDate.getTime());
		return s1 + s2;

	}

}
