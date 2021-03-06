package wxpay;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class MySSLSocketFactory extends SSLSocketFactory {

	private static MySSLSocketFactory mySSLSocketFactory = null;

	static {
		mySSLSocketFactory = new MySSLSocketFactory(createSContext());
	}

	private static SSLContext createSContext() {
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			sslcontext.init(null,
					new TrustManager[] { new TrustAnyTrustManager() }, null);
		} catch (KeyManagementException e) {
			e.printStackTrace();
			return null;
		}
		return sslcontext;
	}

	public static MySSLSocketFactory getInstance() {
		if (mySSLSocketFactory != null) {
			return mySSLSocketFactory;
		} else {
			return mySSLSocketFactory = new MySSLSocketFactory(createSContext());
		}
	}

	private MySSLSocketFactory(SSLContext sslContext) {
		super(sslContext);
		this.setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
	}

}