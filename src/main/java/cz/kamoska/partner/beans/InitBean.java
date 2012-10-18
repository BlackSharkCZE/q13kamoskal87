package cz.kamoska.partner.beans;

import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.AdvertPriceGroupDaoInterface;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.dao.interfaces.SectionDaoInterface;
import cz.kamoska.partner.entities.AdvertPriceGroupEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.entities.SectionEntity;
import cz.kamoska.partner.enums.PartnerGroups;
import net.airtoy.encryption.MD5;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class InitBean implements Serializable {

	private final Logger logger = Logger.getLogger(InitBean.class);

	private static final String DEFAULT_LOGIN = "admin@kamoska.cz";
	private static final String DEFAULT_PASSWORD = "adminadmin";

	@EJB
	private PartnerDaoInterface partnerDao;
	@EJB
	private AdvertPriceGroupDaoInterface advertPriceGroupDaoInterface;
	@EJB
	private SectionDaoInterface sectionDaoInterface;

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
			partnerEntity.setRoles(Arrays.asList(new String[]{PartnerGroups.GROUP_ADMIN.toString()}));

			SaveDomainResult<PartnerEntity> saveResult = partnerDao.save(partnerEntity);
			if (saveResult.success) {
				logger.info("Default partner " + partnerEntity + " created with password: " + DEFAULT_PASSWORD + " and login: " + DEFAULT_LOGIN);
			} else {
				logger.fatal("There is not ADMIN in database and it is not possible to create admin!");
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
		}

	}

	private void createSections() {

		SectionEntity s1 = new SectionEntity();
		s1.setAlwaysSelected(false);
		s1.setName("sex");
		SaveDomainResult<SectionEntity> saveResult = sectionDaoInterface.save(s1);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s1);
		}
		SectionEntity s2 = new SectionEntity();
		s2.setAlwaysSelected(false);
		s2.setName("culture");
		saveResult = sectionDaoInterface.save(s2);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s2);
		}
		SectionEntity s3 = new SectionEntity();
		s3.setAlwaysSelected(false);
		s3.setName("fashion");
		saveResult = sectionDaoInterface.save(s3);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s3);
		}
		SectionEntity s4 = new SectionEntity();
		s4.setAlwaysSelected(false);
		s4.setName("bloggers");
		saveResult = sectionDaoInterface.save(s4);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s4);
		}
		SectionEntity s5 = new SectionEntity();
		s5.setAlwaysSelected(false);
		s5.setName("healthy");
		saveResult = sectionDaoInterface.save(s5);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s5);
		}
		SectionEntity s6 = new SectionEntity();
		s6.setAlwaysSelected(true);
		s6.setName("main-page");
		saveResult = sectionDaoInterface.save(s6);
		if (!saveResult.success) {
			logger.error("Can not create Section " + s6);
		}


	}

}
