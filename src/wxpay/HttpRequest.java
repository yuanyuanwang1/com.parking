package wxpay;

import org.apache.commons.httpclient.NameValuePair;

/* *
 *类名：HttpRequest
 *功能：Http请求对象的封装
 *详细：封装Http请求
 *版本：3.3
 *日期：2011-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class HttpRequest {

	/** HTTP GET method */
	public static final String METHOD_GET = "GET";

	/** HTTP POST method */
	public static final String METHOD_POST = "POST";

	/**
	 * 默认的请求编码方式
	 */
	private String charset = "GBK";

	/**
	 * 请求发起方的ip地址
	 */
	private String clientIp;

	private int connectionTimeout = 0;

	/**
	 * 默认的请求方式
	 */
	private String method = METHOD_POST;

	/**
	 * Post方式请求时组装好的参数值对
	 */
	private NameValuePair[] parameters = null;

	/**
	 * Get方式请求时对应的参数
	 */
	private String queryString = null;

	/**
	 * 请求返回的方式
	 */
	private HttpResultType resultType = HttpResultType.BYTES;

	private int timeout = 0;

	/**
	 * 待请求的url
	 */
	private String url = null;

	public HttpRequest(HttpResultType resultType) {
		super();
		this.resultType = resultType;
	}

	/**
	 * @return Returns the charset.
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @return Returns the clientIp.
	 */
	public String getClientIp() {
		return clientIp;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public String getMethod() {
		return method;
	}

	public NameValuePair[] getParameters() {
		return parameters;
	}

	public String getQueryString() {
		return queryString;
	}

	public HttpResultType getResultType() {
		return resultType;
	}

	public int getTimeout() {
		return timeout;
	}

	public String getUrl() {
		return url;
	}

	/**
	 * @param charset
	 *            The charset to set.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @param clientIp
	 *            The clientIp to set.
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setParameters(NameValuePair[] parameters) {
		this.parameters = parameters;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public void setResultType(HttpResultType resultType) {
		this.resultType = resultType;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
