package cz.kamoska.partner.beans;

import cz.kamoska.partner.dao.domains.SaveDomainResult;
import cz.kamoska.partner.dao.interfaces.PartnerDaoInterface;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.enums.PartnerGroups;
import net.airtoy.encryption.MD5;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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
public class InitBean implements Serializable  {

	private final Logger logger = Logger.getLogger(InitBean.class);

	private  static final String DEFAULT_LOGIN = "admin@kamoska.cz";
	private static final String DEFAULT_PASSWORD = "adminadmin";

	@EJB
	private PartnerDaoInterface partnerDao;

	@PostConstruct
	public void init() {
		if (!partnerDao.createLoginRoleView()) {
			logger.info("Can not create Login_Role_view");
		};
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
	}

}
