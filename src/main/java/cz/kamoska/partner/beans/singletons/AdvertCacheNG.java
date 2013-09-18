package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.beans.AdvertContainer;
import cz.kamoska.partner.dao.interfaces.AdvertDaoInterface;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.entities.SectionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 17.9.13
 * Time: 18:53
 * <p>
 * Nacita veschny inzeraty, radi a prirazuje jim obalku s poctem zobrazeni podle ktereho jsou nasledne razeni
 * </p>
 */

@ApplicationScoped
@Singleton
@Startup
public class AdvertCacheNG implements Serializable {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private LinkedHashSet<AdvertContainer> availableAdverts;

	@Inject
	private AdvertDaoInterface advertDaoInterface;

	private AtomicBoolean loadingCache = new AtomicBoolean();

	private Date lastRealodCache;


	@PostConstruct
	public void init() {
		createCache();
	}

	public void reload() {
		logger.info("Call Reload Cache!");
		if (!loadingCache.get()) {
			createCache();
		}
	}

	private synchronized void createCache() {
		loadingCache.set(true);
		logger.info("Load All Advert In System To Cache");
		List<AdvertEntity> allActive = advertDaoInterface.findAllActive();
		logger.info(allActive.size() + " Active Advert Found!");


		long start = System.currentTimeMillis();
		logger.info("Create Cache Start!");

		availableAdverts = new LinkedHashSet<>(allActive.size());
		for (AdvertEntity advert : allActive) {
			AdvertContainer container = new AdvertContainer();
			container.advertEntity = advert;
			container.displayCount = 0;
			container.sections = new ArrayList<>(5);
			for (SectionEntity sectionEntity : advert.getSectionEntityList()) {
				container.sections.add(sectionEntity.getUrlName());
			}
			availableAdverts.add(container);
		}

		long end = System.currentTimeMillis();
		loadingCache.set(false);
		lastRealodCache = Calendar.getInstance().getTime();
		logger.info("Create Cache End! Duration: " + (end - start) + " [ms]");
	}

	public AdvertEntity getNextAdvertEntity(final String sectionName) {
		logger.debug("Request for advert in section " + sectionName);
		AdvertEntity res = null;
		if (sectionName != null && availableAdverts != null && availableAdverts.size() > 0 && loadingCache.get() == false) {
			AdvertContainer advertContainer = null;
			synchronized (this) {
				Iterator<AdvertContainer> iterator = availableAdverts.iterator();

				while (iterator.hasNext()) {
					advertContainer = iterator.next();
					if (advertContainer.sections.contains(sectionName)) {
						iterator.remove();
						advertContainer.displayCount++;
						break;
					}
				}
				if (advertContainer != null) {
					availableAdverts.add(advertContainer);
					res = advertContainer.advertEntity;
				} else {
					logger.warn("Can not find AdvertEntity in Cache for section " + sectionName);
				}
			}
		} else {
			logger.warn("Invalid Section Name " + sectionName + " or no AvailableAdverts!");
		}
		return res;
	}

	public Date getLastRealodCache() {
		return lastRealodCache;
	}
}
