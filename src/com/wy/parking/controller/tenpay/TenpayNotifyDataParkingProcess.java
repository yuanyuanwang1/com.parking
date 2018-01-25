/**
 * 
 */
package com.wy.parking.controller.tenpay;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wy.superClass.SuperAction;
import com.wy.util.ExceptionUtil;
/**
 * 支付回调
 * 
 * @author wy
 */
public class TenpayNotifyDataParkingProcess extends SuperAction {

	public static String getException(Exception e) {
		
		System.out.println("--------------微信支付回调------------------");
		if (e == null) {

			return null;

		}
		
		StackTraceElement[] ste = e.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(e.getMessage() + "");
		for (int i = 0; i < ste.length; i++) {
			sb.append(ste[i].toString() + "");
		}
		return sb.toString();
	}

	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 解析结果存储在HashMap
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	private Logger logger = Logger.getLogger(TenpayNotifyDataParkingProcess.class);

	private String resultInfo = null;

	private TenpayService tenpayParkingService = null;
	
	

	public TenpayService getTenpayParkingService() {
		return tenpayParkingService;
	}

	public void setTenpayParkingService(TenpayService tenpayParkingService) {
		this.tenpayParkingService = tenpayParkingService;
	}

	@Override
	public String execute() {

		try {
			logger.info("停车场微信支付成功调用到服务！");

			System.out.println("停车场微信支付成功调用到服务");

			// 微信post过来的参数
			// Map notifyParams = request.getParameterMap();
			Map<String, String> notifyParams = parseXml(request);

			System.out.println("text" + notifyParams);

			System.out.println(notifyParams);

			System.out.println(tenpayParkingService);

			resultInfo = tenpayParkingService.alipayNotifyProcess(notifyParams);

			System.out.println("resultInfo" + resultInfo);

			logger.info("resultInfo：" + resultInfo);

		} catch (Throwable e) {

			logger.error(ExceptionUtil.getException(e));
			resultInfo = "fail";
		}

		return SUCCESS;

	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}


}
