package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.config.MainConfig;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 1.11.12
 * Time: 20:48
 * To change this template use File | Settings | File Templates.
 */

@Singleton
@ApplicationScoped
public class MailerBean {


	private final Logger logger = Logger.getLogger(MainConfig.LOGGER_NAME);

	@Asynchronous
	public void sendEmail(final String body, final String subject, List<String> recipients, final String from, List<String> attachment, boolean html) {

		if (recipients != null && !recipients.isEmpty()) {

			HtmlEmail email = new HtmlEmail();
			try {
				email.setHostName(MainConfig.EMAIL_SMTP_HOST);
				email.setSmtpPort(MainConfig.EMAIL_SMTP_PORT);
				if (!MainConfig.EMAIL_SMTP_USER.isEmpty() && !MainConfig.EMAIL_SMTP_PASSWORD.isEmpty()) {
					email.setAuthenticator(new DefaultAuthenticator(MainConfig.EMAIL_SMTP_USER, MainConfig.EMAIL_SMTP_PASSWORD));
				}
				email.setTLS(MainConfig.EMAIL_SMTP_TLS);

				email.setFrom(from, MainConfig.EMAIL_FROM, "UTF-8");
				email.setSubject(subject);
				email.setCharset("UTF-8");


				if (html) {
					email.setHtmlMsg(body);
				} else {
					email.setTextMsg(body);
				}

				for (String s : recipients) {
					email.addTo(s);
				}

				if (attachment != null && !attachment.isEmpty()) {
					for (String s : attachment) {
						EmailAttachment at = new EmailAttachment();
						at.setPath(s);
						at.setDisposition(EmailAttachment.ATTACHMENT);
						String parts[] = s.split("/");
						if (parts.length > 0) {
							at.setName(parts[parts.length - 1]);
						}
					}
				}

				email.send();
			} catch (Exception e) {
				logger.severe("Can not send e-mail to " + recipients + " with subject: " + subject);
				logger.throwing(this.getClass().getSimpleName(), "sendEmail", e);
			}
		} else {
			logger.warning("Try send email with subject " + subject + " but recipients empty.");
		}
	}


}
