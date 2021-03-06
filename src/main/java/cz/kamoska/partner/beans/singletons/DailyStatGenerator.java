package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.support.Kamoska;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 5.11.12
 * Time: 20:48
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)

public class DailyStatGenerator {

	@Inject
	@Kamoska
	private Logger logger;

	@Resource(name = "jdbc/kamoska")
	private DataSource dataSource;

//	@Schedule(hour = "1", minute = "5", second = "0")
	@Schedule(hour = "0", minute = "40", second = "0")
	public void generateDailyStats() {

		try {
			logger.info("Generate daily stats!");
			processInternal();
			logger.info("Generate daily stats - DONE");
		} catch (Exception e) {
			logger.error("Error executing generateDailyStats", e);
		}

	}

	private void processInternal() {
		int res = 0;
		ResultSet rs = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
			rs = ps.executeQuery();
			if (rs != null) {
				rs.next();
				res = rs.getInt(1);
				if (rs.wasNull()) {
					logger.error("Can not generate dailyStats. ResultSet column 0 is NULL");
				} else {
					if (res > 0) {
						logger.error("Executing SQL function to generate daily stats successful");
					} else {
						logger.error("Executing SQL function to generate daily stats return unexpected value: " + res + ". Should be 1");
					}
				}
			} else {
				logger.error("Can not generate dailyStats. ResultSet for query " + query + " is null.");
			}
		} catch (Exception e) {
			logger.error("Can not execute query " + query, e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}

	}


	final String query = "select * from fnc_generate_daily_stats()";

}
