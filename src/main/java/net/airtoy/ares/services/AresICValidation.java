package net.airtoy.ares.services;

import cz.kamoska.partner.support.Kamoska;
import net.airtoy.ares.Address;
import net.airtoy.ares.AresUserInfo;
import net.airtoy.ares.OrganizationType;
import net.airtoy.ares.jaxb.*;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
 */

public class AresICValidation implements ICValidation {

	@Inject
	@Kamoska
	private org.slf4j.Logger logger;

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
					logger.error("Can not validate IC " + ic + ": " + o.getE().getET());
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
					aresUserInfo.setAgent(aresUserInfo.getFirstName() + " " + aresUserInfo.getLastName());
					return aresUserInfo;
				}

			}
			//}
		} catch (Exception ex) {
			logger.error("Can not verify IC " + ic + ": " + ex.getMessage(), ex);
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
