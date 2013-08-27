package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.beans.AccessListBean;
import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.interfaces.AdvertDaoInterface;
import cz.kamoska.partner.dao.interfaces.SectionDaoInterface;
import cz.kamoska.partner.entities.AdvertAccessListEntity;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.entities.SectionEntity;
import cz.kamoska.partner.enums.AdvertDisplayStyle;
import cz.kamoska.partner.pojo.kamoska.AdvertViewWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.11.12
 * Time: 14:39
 * Zajistuje rychle vygenerovani zdrojoveho kodu JS pro pozadovanou reklamu. Reklamy nacita z Cache
 */
@ApplicationScoped
@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)

public class AdvertProviderModel implements Serializable {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final Integer LIFE = 10;

	@EJB
	private SectionDaoInterface sectionDaoInterface;
	@EJB
	private AdvertDaoInterface advertDaoInterface;
	@EJB
	private AccessListBean accessListBean;

	private Map<String, Queue<AdvertViewWrapper>> advertsCache;


	private String verticalAdvertTemplate;
	private String horizontalAdvertTemplate;

	private final String jsTemplate;


	public AdvertProviderModel() {
		jsTemplate = "document.write('{BODY}');";
	}



	@PostConstruct
	public void postConstruct() {


		try {
			InputStream hInputStream = this.getClass().getClassLoader().getResourceAsStream("tip-template-h.html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(hInputStream));
			String line;
			StringBuilder sb = new StringBuilder(2048);
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			horizontalAdvertTemplate = sb.toString().replace("\\n", "");
			sb.delete(0, sb.length());
			reader.close();
			hInputStream.close();
		} catch (IOException e) {
			logger.error("Can not load template for tip-template-h.html", e);
			horizontalAdvertTemplate = "<div style='font-weight:bold;color:red'>Can not load tip-template-h.html template</div>";
		}

		try {
			InputStream hInputStream = this.getClass().getClassLoader().getResourceAsStream("tip-template-v.html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(hInputStream));
			String line;
			StringBuilder sb = new StringBuilder(2048);
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			verticalAdvertTemplate = sb.toString().replace("\\n", "");
			sb.delete(0, sb.length());
			reader.close();
			hInputStream.close();
		} catch (IOException e) {
			logger.error("Can not load template for tip-template-h.html", e);
			verticalAdvertTemplate = "<div style='font-weight:bold;color:red'>Can not load tip-template-v.html template</div>";
		}

		List<SectionEntity> allSections = sectionDaoInterface.findAll();
		advertsCache = new HashMap<>(allSections.size());
		for (SectionEntity section : allSections) {
			logger.info("Create cache for " + section.getUrlName());
			advertsCache.put(section.getUrlName(), new ArrayBlockingQueue<AdvertViewWrapper>(MainConfig.ADVERT_CACHE_SIZE));
			List<AdvertEntity> advertEntities = advertDaoInterface.findLessUsedBySection(section.getUrlName(), MainConfig.ADVERT_CACHE_SIZE, null);
			logger.info("Adverts for section "+section.getUrlName()+": " + (advertEntities != null ? advertEntities.size() : 0));
			if (advertEntities.isEmpty()) {
				logger.warn("There is not adverts for section : " + section);
			} else {
				logger.info("Add " + advertEntities.size() + " adverts to cache " + section.getName());
				for (AdvertEntity ae : advertEntities) {
					advertsCache.get(section.getUrlName()).add(new AdvertViewWrapper(ae, 0));
				}
			}
		}
	}

	/**
	 * Vrati reklamu z pozadovane sekce
	 *
	 * @param sectionUrlName URL_NAME pozadovane sekce
	 * @return reklama, ktera se zarazena do pozadovane sekce
	 */
	public AdvertEntity getAdvertEntityBySection(final String sectionUrlName) {
		AdvertEntity res = null;
		Queue<AdvertViewWrapper> adv = advertsCache.get(sectionUrlName);
		if (adv != null) {
			//todo nacteni z cache by mozna mohlo byt synchronizovane nebo by dana queue mohla byt v synchronized obalce
			AdvertViewWrapper av = adv.poll();
			if (av != null) {
				if (av.viewCount > LIFE) {
					logger.info("Advert display reach maximum value " + LIFE);
					reloadCacheForSection(sectionUrlName);
					res = getAdvertEntityBySection(sectionUrlName);
				} else {
					av.viewCount++;
					if (!adv.add(av)) {
						logger.error("Can not return back " + adv);
					}
					res = av.advertEntity;
					accessListBean.save(new AdvertAccessListEntity(av.advertEntity, Calendar.getInstance().getTime()));
				}
			} else {
				logger.error("Can not poll Advert from cache for section " + sectionUrlName);
				reloadCacheForSection(sectionUrlName);
			}
		}
		return res;
	}

	public void reloadCacheForSection(String sectionUrlName) {
		Queue<AdvertViewWrapper> adv = advertsCache.get(sectionUrlName);
		Iterator iter = adv.iterator();
		List<Integer> exludeIDS = new ArrayList<>(adv.size());
		while (iter.hasNext()) {
			exludeIDS.add(((AdvertViewWrapper) iter.next()).advertEntity.getId());
		}
		List<AdvertEntity> ade = advertDaoInterface.findLessUsedBySection(sectionUrlName, MainConfig.ADVERT_CACHE_SIZE - adv.size(), exludeIDS);
		for (AdvertEntity ae : ade) {
			adv.add(new AdvertViewWrapper(ae, 0));
		}
	}


	public String getAdvertJavaScript(final String sectionUrlName, final AdvertDisplayStyle advertDisplayStyle, final Integer advertCount) {
		StringBuilder sb = new StringBuilder(4096);
		for (int a = 0; a < advertCount; a++) {
			AdvertEntity aEntity = getAdvertEntityBySection(sectionUrlName);
			if (aEntity != null) {
				String adv;
				switch (advertDisplayStyle) {
					case VERTICAL:
						adv = verticalAdvertTemplate;
						break;
					case HORIZONTAL:
						adv = horizontalAdvertTemplate;
						break;
					default:
						adv = "";
				}
				String body = adv.replace("{ADVERT_ID}", String.valueOf(aEntity.getId()))
					 .replace("{PICTURE_ID}", aEntity.getPictureEntity() != null ? String.valueOf(aEntity.getPictureEntity().getId()) : "")
					 .replace("{ADVERT_TITLE}", aEntity.getTitle())
					 .replace("{ADVERT_ORIG_URL}", aEntity.getUrl())
					 .replace("{ADVERT_BODY}", aEntity.getBody());
				sb.append(jsTemplate.replace("{BODY}", body));

			}
		}
		return sb.toString();

	}

	public String getAdvertHtml(String sectionUrlName, AdvertDisplayStyle advertDisplayStyle, Integer advertCount) {
		for (int a = 0; a < advertCount; a++) {
			AdvertEntity aEntity = getAdvertEntityBySection(sectionUrlName);
			if (aEntity != null) {
				String adv;
				switch (advertDisplayStyle) {
					case VERTICAL:
						adv = verticalAdvertTemplate;
						break;
					case HORIZONTAL:
						adv = horizontalAdvertTemplate;
						break;
					default:
						adv = "";
				}
				return adv.replace("{ADVERT_ID}", String.valueOf(aEntity.getId()))
					 .replace("{PICTURE_ID}", aEntity.getPictureEntity() != null ? String.valueOf(aEntity.getPictureEntity().getId()) : "")
					 .replace("{ADVERT_TITLE}", aEntity.getTitle())
					 .replace("{ADVERT_ORIG_URL}", aEntity.getUrl())
					 .replace("{ADVERT_BODY}", aEntity.getBody());

			}
		}
		return "<p>Can not load Tip</p>";
	}
}
