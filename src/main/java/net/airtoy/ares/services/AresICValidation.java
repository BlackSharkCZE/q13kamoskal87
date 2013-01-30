package net.airtoy.ares.services;

import cz.kamoska.partner.config.MainConfig;
import net.airtoy.ares.Address;
import net.airtoy.ares.AresUserInfo;
import net.airtoy.ares.OrganizationType;
import net.airtoy.ares.jaxb.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;

/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
 */

public class AresICValidation implements ICValidation {

	private final Logger logger = Logger.getLogger(MainConfig.LOGGER_NAME);

	private String aresURL = "http://wwwinfo.mfcr.cz/cgi-bin/ares/darv_rzp.cgi?ico={0}";

	@Override
	public AresUserInfo getUser(String ic) {
		try {
			String queryURL = MessageFormat.format(aresURL, new Object[]{ic});
			URL url = new URL(queryURL);

				JAXBContext jc = JAXBContext.newInstance(AresOdpovedi.class);
//				JAXBContext jc = JAXBContext.newInstance("net.airtoy.ares.jaxb");
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				AresOdpovedi aresResponse = (AresOdpovedi) unmarshaller.unmarshal(url);
				List<OdpovedRZP> rzp = aresResponse.getOdpoved();

				AresUserInfo aresUserInfo = new AresUserInfo();

				if (rzp == null || rzp.size() == 0) {
					return null;
				} else {

					OdpovedRZP o = rzp.get(0);
					if (o.getE() != null) {
						logger.severe("Can not validate IC " + ic + ": " + o.getE().getET());
					} else {
						VypisRZP vypis = o.getVypisRZP().get(0);
						aresUserInfo.setCompany(vypis.getZAU().getOF());
						aresUserInfo.setOrganizationType(OrganizationType.valueOf(vypis.getZAU().getPF().getPFO().name()));

						Address address = new Address();
						AdresaARES adr = vypis.getAdresy().getA().get(0);
						address.setCity(adr.getN());
						address.setPsc(adr.getPSC());
						address.setStreet(adr.getNU());
						address.setCo(adr.getCO());
						address.setCd(adr.getCD().toString());
						aresUserInfo.setAddress(address);

						OsobaRZP osoba = vypis.getOsoby().getOsoba().get(0);
						aresUserInfo.setState(osoba.getNS());
						aresUserInfo.setFirstName(osoba.getJ());
						aresUserInfo.setLastName(osoba.getP());
						aresUserInfo.setCompany(vypis.getZAU().getOF());
						aresUserInfo.setAgent( aresUserInfo.getFirstName() + " " + aresUserInfo.getLastName() );
						return aresUserInfo;
					}

				}
			//}
		} catch (Exception ex) {
			logger.severe("Can not verify IC " + ic + ": " + ex.getMessage());
			logger.throwing(this.getClass().getSimpleName(), "getUser", ex);
		}
		return null;
	}

	//<editor-fold defaultstate="collapsed" desc="Getter and Setter">
	public String getAresURL() {
		return aresURL;
	}

	public void setAresURL(String aresURL) {
		this.aresURL = aresURL;
	}
	//</editor-fold>
}
