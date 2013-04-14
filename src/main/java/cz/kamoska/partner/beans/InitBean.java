package cz.kamoska.partner.beans;

import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertPriceGroupDaoInterface;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.dao.interfaces.SectionDaoInterface;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.entities.SectionEntity;
import cz.kamoska.partner.enums.PartnerGroups;
import cz.kamoska.partner.support.Kamoska;
import net.airtoy.encryption.MD5;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 10.10.12
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)

public class InitBean implements Serializable {

	@Inject
	@Kamoska
	private org.slf4j.Logger logger;

	private static final String DEFAULT_LOGIN = "admin@kamoska.cz";
	private static final String DEFAULT_PASSWORD = "adminadmin";

	@EJB
	private PartnerDaoInterface partnerDao;
	@EJB
	private AdvertPriceGroupDaoInterface advertPriceGroupDaoInterface;
	@EJB
	private SectionDaoInterface sectionDaoInterface;

	@Resource(name = "jdbc/kamoska")
	private DataSource dataSource;


	@PostConstruct
	public void init() {
		if (!partnerDao.createLoginRoleView()) {
			logger.info("Can not create Login_Role_view");
		}
		if (partnerDao.getPartnerCountByGroup(PartnerGroups.GROUP_ADMIN) == 0) {
			//zadny administrator v systemu neni, takze se vytvori defaultni
			PartnerEntity partnerEntity = new PartnerEntity();
			partnerEntity.setActivated(Calendar.getInstance().getTime());
			partnerEntity.setCity("Praha");
			partnerEntity.setCompany("Kamoska a.s.");
			partnerEntity.setDateCreated(Calendar.getInstance().getTime());
			partnerEntity.setEmail(DEFAULT_LOGIN);
			partnerEntity.setIc("0");
			partnerEntity.setPassword(MD5.md5hexa(DEFAULT_PASSWORD));
			partnerEntity.setPhoneNumber("420");
			partnerEntity.setPsc("10000");
			partnerEntity.setStreet("Goblinniho povstani");
			partnerEntity.setVatPayer(false);
			partnerEntity.setRoles(Arrays.asList(PartnerGroups.GROUP_ADMIN.toString()));

			SaveDomainResult<PartnerEntity> saveResult = partnerDao.save(partnerEntity);
			if (saveResult.success) {
				logger.info("Default partner " + partnerEntity + " created with password: " + DEFAULT_PASSWORD + " and login: " + DEFAULT_LOGIN);
			} else {
				logger.error("There is not ADMIN in database and it is not possible to create admin!");
			}
		}

		if (advertPriceGroupDaoInterface.getAll().isEmpty()) {
			logger.info("There is not any AdvertPriceGroup. Create default.");
			// nejosu zadne platebni skupiny, takze si vytvorime 1. testovaci
			AdvertPriceGroupEntity apge = new AdvertPriceGroupEntity();
			apge.setDateCreated(Calendar.getInstance().getTime());
			apge.setDuration(365);
			apge.setName("První sada reklam");
			apge.setoIndex(1);
			apge.setPriceCzk(BigDecimal.valueOf(1200));
			SaveDomainResult<AdvertPriceGroupEntity> save = advertPriceGroupDaoInterface.save(apge);
			if (save.success) {
				logger.info("Default APGE created: " + save.item);
			} else {
				logger.error("Can not save default APGE: " + apge);
			}

		}

		List<SectionEntity> sectionEntities = sectionDaoInterface.findAll();
		if (sectionEntities == null || sectionEntities.isEmpty()) {
			//zadne sekce nejsou vytvorene, takze je vytvorime
			createSections();
		} else {
			logger.info("There are sections in DB: " + sectionEntities);
		}
		initDatabaseFunctions();
	}

	private void createSections() {

		SectionEntity s1 = new SectionEntity();
		s1.setAlwaysSelected(false);
		s1.setName("sex");
		s1.setUrlName("sex");
		SaveDomainResult<SectionEntity> saveResult = sectionDaoInterface.save(s1);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s1);
		}
		SectionEntity s2 = new SectionEntity();
		s2.setAlwaysSelected(false);
		s2.setName("culture");
		s2.setUrlName("culture");

		saveResult = sectionDaoInterface.save(s2);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s2);
		}
		SectionEntity s3 = new SectionEntity();
		s3.setAlwaysSelected(false);
		s3.setName("fashion");
		s3.setUrlName("fashion");
		saveResult = sectionDaoInterface.save(s3);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s3);
		}
		SectionEntity s4 = new SectionEntity();
		s4.setAlwaysSelected(false);
		s4.setName("bloggers");
		s4.setUrlName("bloggers");
		saveResult = sectionDaoInterface.save(s4);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s4);
		}
		SectionEntity s5 = new SectionEntity();
		s5.setAlwaysSelected(false);
		s5.setName("healthy");
		s5.setUrlName("healthy");
		saveResult = sectionDaoInterface.save(s5);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s5);
		}
		SectionEntity s6 = new SectionEntity();
		s6.setAlwaysSelected(true);
		s6.setName("main-page");
		s6.setUrlName("main-page");
		saveResult = sectionDaoInterface.save(s6);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s6);
		}


	}

	/**
	 * Vytvari potrebne databazove funkce
	 */
	private void initDatabaseFunctions() {
		final String query = sqlFunctionCreateCommand;
		try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

			int res = ps.executeUpdate();
			logger.info("Function to generate daily stats create successful");
		} catch (Exception e) {
			logger.error("Can not execute query " + query, e);
		}
	}


	private final String sqlFunctionCreateCommand = "-- Function: fnc_generate_daily_stats()\n" +
		 "\n" +
		 "-- DROP FUNCTION fnc_generate_daily_stats();\n" +
		 "\n" +
		 "CREATE OR REPLACE FUNCTION fnc_generate_daily_stats()\n" +
		 "  RETURNS integer AS\n" +
		 "$BODY$BEGIN\n" +
		 "\n" +
		 "insert into advert_accesslist_daily(id, display_count, for_date, advert_entity)\n" +
		 "select \n" +
		 "nextval('advert_accesslist_daily_id_seq') as id, \n" +
		 "count(id) as display_count, \n" +
		 "'yesterday'::date as for_date,\n" +
		 "advert_id as advert_entity\n" +
		 "from advert_accesslist_actual\n" +
		 "where datecreated::date = 'yesterday'::date\n" +
		 "group by advert_id;\n" +
		 "\n" +
		 "\n" +
		 "delete from advert_accesslist_actual where datecreated::date = 'yesterday'::date;\n" +
		 "return 1;\n" +
		 "\n" +
		 "END;$BODY$\n" +
		 "  LANGUAGE plpgsql VOLATILE\n" +
		 "  COST 100;\n" +
		 "ALTER FUNCTION fnc_generate_daily_stats()\n" +
		 "  OWNER TO \"partner.kamoska.cz\";\n" +
		 "COMMENT ON FUNCTION fnc_generate_daily_stats() IS 'Generuje denni statistiky za predesly den';\n";
}
