package cz.kamoska.partner.models.application;

import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.enums.AdvertDisplayStyle;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;

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
public class AdvertProviderModel implements Serializable {

	private final Logger logger = Logger.getLogger(AdvertProviderModel.class);

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

	}

	/**
	 * Vrati reklamu z pozadovane sekce
	 * @param sectionUrlName URL_NAME pozadovane sekce
	 * @return reklama, ktera se zarazena do pozadovane sekce
	 */
	public AdvertEntity getAdvertEntityBySection(final String sectionUrlName) {
		//todo implement body
		return new AdvertEntity();
	}

	public String getAdvertJavaScript(final String sectionUrlName, final AdvertDisplayStyle advertDisplayStyle, final Integer advertCount) {
		StringBuilder sb = new StringBuilder(4096);
		for (int a = 0; a < advertCount; a++) {
			AdvertEntity aEntity = getAdvertEntityBySection(sectionUrlName);
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
					.replace("{ADVERT_BODY}", aEntity.getBody());
			sb.append(body.replace("{BODY}", body));
		}
		return sb.toString();

	}

}
