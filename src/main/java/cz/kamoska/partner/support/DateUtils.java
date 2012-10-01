package cz.kamoska.partner.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author blackshark
 * @version 0.1
 * @since 0.1
 */
public class DateUtils {

	public static SimpleDateFormat getIso8601() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	}

	public static SimpleDateFormat getCzech() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	}



	public static Date getFirstDayOfThisMonth() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getLastDayOfThisMonth() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDayAfter(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		try {
			return sdf.parse(sdf.format(cal.getTime()));
		} catch (ParseException ex) {
			return null;
		}
	}


	public static Date getSimpleDate(Date timestamp) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(timestamp);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Calendar getPreviousMonthDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar;
	}

	public static Calendar getPreviousMonthEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		return calendar;
	}
}
