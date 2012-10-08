package cz.kamoska.partner.support;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * @author blackshark
 * @version 0.1
 * @since 0.1
 */
@Singleton
@ApplicationScoped
public class MailerBean {

	public static final String EMAIL_FROM = "partner@kamoska.cz";

	private final String EMAIL_SMTP_HOST = "localhost";
	private final int EMAIL_SMTP_PORT = 25;
	private final String EMAIL_USER = "";
	private final String EMAIL_PASSWORD = "";
	private final boolean EMAIL_TLS = false;

	private final Logger logger = Logger.getLogger(MailerBean.class);

	@Asynchronous
	public void sendEmail(final String body, final String subject, List<String> recipients, final String from, List<String> attachment, boolean html) {

		if (recipients != null && !recipients.isEmpty()) {

			HtmlEmail email = new HtmlEmail();
			try {
				email.setHostName(EMAIL_SMTP_HOST);
				email.setSmtpPort(EMAIL_SMTP_PORT);
				if (!EMAIL_USER.isEmpty() && !EMAIL_PASSWORD.isEmpty()) {
					email.setAuthenticator(new DefaultAuthenticator(EMAIL_USER, EMAIL_PASSWORD));
				}
				email.setTLS(EMAIL_TLS);

				email.setFrom(from, EMAIL_FROM, "UTF-8");
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
				logger.error("Can not send e-mail to " + recipients + " with subject: " + subject, e);
			}
		} else {
			logger.warn("Try send email with subject " + subject + " but recipients empty.");
		}
	}

}
