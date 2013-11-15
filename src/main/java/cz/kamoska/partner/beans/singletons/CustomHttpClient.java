package cz.kamoska.partner.beans.singletons;

import cz.kamoska.partner.config.MainConfig;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 29.10.12
 * Time: 19:51
 */
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)

public class CustomHttpClient implements Serializable {

	private HttpParams httpParameters;
	private SchemeRegistry schemeRegistry;
	private DefaultHttpClient client;

	private ThreadSafeClientConnManager connManager;
	private CredentialsProvider credsProvider;

	private UsernamePasswordCredentials credentials;

	public CustomHttpClient() {
		httpParameters = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParameters, MainConfig.PARTNER_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, MainConfig.SOCKET_TIMEOUT);

		schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", MainConfig.HTTP_PORT, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", MainConfig.HTTPS_PORT, SSLSocketFactory.getSocketFactory()));
		httpParameters.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(MainConfig.PARTNER_CONNECTION_TIMEOUT));
		httpParameters.setParameter(CoreConnectionPNames.SO_TIMEOUT, new Integer(MainConfig.SOCKET_TIMEOUT));
		httpParameters.setParameter("http.conn-manager.timeout", new Integer(MainConfig.PARTNER_CONNECTION_TIMEOUT));
		httpParameters.setBooleanParameter("http.protocol.expect-continue", false);


		connManager = new ThreadSafeClientConnManager(schemeRegistry);
		client = new DefaultHttpClient(connManager, httpParameters);
		credentials =  new UsernamePasswordCredentials(MainConfig.FAKTUROID_LOGIN_EMAIL, MainConfig.FAKTUROID_API_TOKEN);

	}

	public UsernamePasswordCredentials getCredentials() {
		return credentials;
	}

	public DefaultHttpClient getClient() {
		return client;
	}
}
