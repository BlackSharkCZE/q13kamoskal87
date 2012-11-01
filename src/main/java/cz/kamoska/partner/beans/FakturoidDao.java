package cz.kamoska.partner.beans;

import cz.kamoska.partner.beans.singletons.CustomHttpClient;
import cz.kamoska.partner.beans.singletons.JSONMapper;
import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.entities.InvoiceEntity;
import cz.kamoska.partner.entities.PartnerEntity;
import cz.kamoska.partner.pojo.fakturoid.Invoice;
import cz.kamoska.partner.pojo.fakturoid.Subject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 29.10.12
 * Time: 20:01
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class FakturoidDao {

	private final Logger logger = Logger.getLogger(FakturoidDao.class);

	@EJB
	private JSONMapper jsonMapper;
	@EJB
	private CustomHttpClient customHttpClient;


	public Integer registerPartner(PartnerEntity partnerEntity) {
		Subject subject = new Subject(partnerEntity);

		final String serializedData = jsonMapper.serialize(subject);
		HttpPost post = null;
		HttpEntity entity = null;
		if (serializedData != null) {
			final String url = MainConfig.FAKTUROID_API_URL+"subjects.json";
			logger.info("Create Subject on URL: " + url);
			 post = new HttpPost(url);

			try {
				logger.info("Post data: " + serializedData);
				post.addHeader(new BasicScheme().authenticate(customHttpClient.getCredentials(), post));
				post.setHeader("Content-type","application/json");
				post.setEntity(new StringEntity(serializedData));
				post.setHeader("User-Agent","partnerkamoska (sticha@kamoska.cz)");
			} catch (Exception e) {
				logger.error("Can not add Subject entity to POST", e);
				if (post != null) {
					post.abort();
				}
				return null;
			}

			DefaultHttpClient client = customHttpClient.getClient();

			try {
				HttpResponse response = client.execute(post);
				int statusCode = response.getStatusLine().getStatusCode();
				switch (statusCode) {
					case HttpServletResponse.SC_CREATED:
						// uzivatel byl vytvoreny
						entity = response.getEntity();
						final String strResponse = EntityUtils.toString(entity);
						logger.info("New subject created on fakturoid. Response:" + strResponse);
						Subject savedSubject = (Subject) jsonMapper.deserialize(strResponse, Subject.class);
						if (savedSubject != null) {
							return savedSubject.getId();
						} else {
							logger.error("Can not parse response from fakturoid: " + strResponse);
						}
						break;
					default:
						logger.warn("Subject (" + subject + ") was not created! Response status code: " + statusCode);
						break;
				}
			} catch (Exception e) {
				logger.error("Can not register partner, because can not post data " + serializedData + " to URL: " + url, e);
			} finally {
				if (entity != null) {
					try {
						EntityUtils.consume(entity);
					} catch (IOException e) {
					}
				}
				if (post != null) {
					post.abort();
				}
			}

		} else {
			logger.error("Can not register partner, because can not serialize Subject: " + subject);
		}
		return 0;
	}


	public InvoiceEntity createInvoice(cz.kamoska.partner.pojo.fakturoid.Invoice invoice) {
		final String serializedData = jsonMapper.serialize(invoice);
		HttpPost post = null;
		HttpEntity entity = null;
		if (serializedData != null) {
			final String url = MainConfig.FAKTUROID_API_URL+"invoices.json";
			logger.info("Create Invoice on URL: " + url);
			post = new HttpPost(url);

			try {
				logger.info("Post data: " + serializedData);
				post.addHeader(new BasicScheme().authenticate(customHttpClient.getCredentials(), post));
				post.setHeader("Content-type","application/json");
				post.setEntity(new StringEntity(serializedData));
				post.setHeader("User-Agent","partnerkamoska (sticha@kamoska.cz)");
			} catch (Exception e) {
				logger.error("Can not add Invoice entity to POST", e);
				if (post != null) {
					post.abort();
				}
				return null;
			}

			DefaultHttpClient client = customHttpClient.getClient();

			try {
				HttpResponse response = client.execute(post);
				int statusCode = response.getStatusLine().getStatusCode();
				switch (statusCode) {
					case HttpServletResponse.SC_CREATED:
						InvoiceEntity res = new InvoiceEntity();
						entity = response.getEntity();
						final String strResponse = EntityUtils.toString(entity);
						logger.info("New Invoice Created on fakturoid. Response:" + strResponse);

						Invoice respInvoice = (Invoice) jsonMapper.deserialize(strResponse, Invoice.class);
						/*
						Header[] locations = response.getHeaders("location");
						if (locations!=null) res.setFakturoidUrl(locations[0].getValue());
						*/
						res.setFakturoidUrl(respInvoice.getPublicHtmlUrl());
						if (respInvoice!=null) res.setFakturoidId(respInvoice.getId());
						res.setDateCreated(Calendar.getInstance().getTime());

						if (res.getFakturoidUrl() != null && res.getFakturoidId() != null) {
							return res;
						} else {
							logger.error("Can not parse response for Invoice from fakturoid: " + strResponse);
						}
						break;
					default:
						logger.warn("Subject (" + invoice + ") was not created! Response status code: " + statusCode);
						break;
				}
			} catch (Exception e) {
				logger.error("Can not create Invoice because can not POST data!" + serializedData + " to URL: " + url, e);
			} finally {
				if (entity != null) {
					try {
						EntityUtils.consume(entity);
					} catch (IOException e) {
					}
				}
				if (post != null) {
					post.abort();
				}
			}

		} else {
			logger.error("Can not create Invoice because can not serialize data: " + invoice);
		}
		return null;
	}

}
