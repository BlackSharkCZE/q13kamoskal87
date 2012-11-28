package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.config.MainConfig;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
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
public class DailyStatGenerator {

	private final Logger logger = Logger.getLogger(MainConfig.LOGGER_NAME);

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
			logger.severe("Error executing generateDailyStats");
			logger.throwing(this.getClass().getSimpleName(), "generateDailyStats", e);
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
					logger.severe("Can not generate dailyStats. ResultSet column 0 is NULL");
				} else {
					if (res > 0) {
						logger.severe("Executing SQL function to generate daily stats successful");
					} else {
						logger.severe("Executing SQL function to generate daily stats return unexpected value: " + res + ". Should be 1");
					}
				}
			} else {
				logger.severe("Can not generate dailyStats. ResultSet for query " + query + " is null.");
			}
		} catch (Exception e) {
			logger.severe("Can not execute query " + query);
			logger.throwing(this.getClass().getSimpleName(), "processInternal", e);
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
