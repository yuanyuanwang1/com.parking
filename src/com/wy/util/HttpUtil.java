package com.wy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * @author Liuyang
 * @version 1.0
 */
public class HttpUtil {

	public static String httpPost(String uri, List<NameValuePair> list) {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		HttpPost httpPost = new HttpPost(uri);

		HttpResponse httpResponse = null;

		HttpEntity httpEntity = null;

		if (list != null && !list.isEmpty()) {

			try {
				httpEntity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		httpPost.setEntity(httpEntity);
		try {
			httpResponse = closeableHttpClient.execute(httpPost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (httpResponse == null) {

			return null;

		}

		if (httpResponse.getStatusLine().getStatusCode() != 200) {

			return null;

		}

		HttpEntity httpEntityResponse = httpResponse.getEntity();

		// try {
		// return EntityUtils.toString(httpEntityResponse, "UTF-8");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }// 取出应答字符串

		try {
			InputStream inStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inStream, "utf-8")); // 请注意这里的编码
			StringBuilder strber = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				strber.append(line);
			inStream.close();
			JSONObject jsonObj = new JSONObject(strber.toString());
			System.out.println(jsonObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// return null;
		}
		return null;

	}

	public static String httpGet(String uri) {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		HttpGet httpGet = new HttpGet(uri);

		HttpResponse httpResponse = null;

		try {
			httpResponse = closeableHttpClient.execute(httpGet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (httpResponse == null) {

			return null;

		}

		if (httpResponse.getStatusLine().getStatusCode() != 200) {

			return null;

		}

		HttpEntity httpEntityResponse = httpResponse.getEntity();

		try {
			return EntityUtils.toString(httpEntityResponse, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 取出应答字符串

		return null;

	}

}
