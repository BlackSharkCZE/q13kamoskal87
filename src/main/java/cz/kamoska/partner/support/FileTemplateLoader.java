package cz.kamoska.partner.support;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 19.11.12
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class FileTemplateLoader implements Serializable {

	@Inject @Kamoska
	private org.slf4j.Logger logger;

	public String loadFileFromResources(String fileName) {

		StringBuilder template = new StringBuilder(1024);
		String line;
		try (
				InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
				InputStreamReader istr = new InputStreamReader(resourceAsStream);
				BufferedReader reader = new BufferedReader(istr);
		) {
			while ((line = reader.readLine()) != null) {
				template.append(line);
			}
		} catch (Exception e) {
			logger.info("Can not read file "+fileName+" template", e);
		}
		return template.toString();

	}

}
