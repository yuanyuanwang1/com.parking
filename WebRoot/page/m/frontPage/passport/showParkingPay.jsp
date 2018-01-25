<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta name="applicable-device" content="mobile">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
停车自助收费</title>
<%
	String path = request.getContextPath();

%> 

</head>
<link href="<%=path%>/script/epark/index_m.css?v=1" rel="stylesheet" type="text/css" />
<link href="<%=path%>/script/epark/index_m.css?v=1" rel="stylesheet" type="text/css" />
<script src="<%=path%>/script/epark/jquery.js"></script>
<body>

<%
	Map map = (Map) request.getAttribute("map");

	String returnUrl=(String)request.getAttribute("returnUrl");
	
	System.out.println("returnUrl"+returnUrl);
%>

<input type="hidden" value="<%=returnUrl %>" name="returnUrl" id="returnUrl"/>

<script type="text/javascript" language="javascript">
	function onBridgeReady(){
		   WeixinJSBridge.invoke(
		       'getBrandWCPayRequest', {
		           "appId":"<%=request.getAttribute("appId") %>", 
		           "timeStamp":"<%=request.getAttribute("timeStamp") %>",
		           "nonceStr":"<%=request.getAttribute("nonceStr") %>",
		           "package":"<%=request.getAttribute("packageValue") %>",     
		           "signType":"MD5",         //微信签名方式：     
		           "paySign":"<%=request.getAttribute("paySign") %>" //微信签名 
		       },
		       function(res){  
		    	   var urlInfo=document.getElementById("returnUrl").value;

		           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
		        	   alert("微信支付成功!");
		 			//window.location.href="http://www.sztigerwong.com/com.wy.parking/action/m/frontPage/passport/AlipaySuccessGet.action?out_trade_no=<%=map.get("order_code")%>";
		        	   window.location.href=urlInfo+"/com.wy.parking/action/m/frontPage/passport/AlipaySuccessGet.action?out_trade_no=<%=map.get("order_code")%>";
						
		           }else if (res.err_msg == "get_brand_wcpay_request:cancel") {
						alert("用户取消支付!");
						window.location.href=urlInfo+"/com.wy.parking/action/m/frontPage/passport/AlipaySuccessGet.action?out_trade_no=<%=map.get("order_code")%>";
						
					} else {
						alert("支付失败!");
 			//window.location.href="http://www.sztigerwong.com/com.wy.parking/action/m/frontPage/passport/AlipaySuccessGet.action?out_trade_no=<%=map.get("order_code")%>";
						window.location.href=urlInfo+"/com.wy.parking/action/m/frontPage/passport/AlipaySuccessGet.action?out_trade_no=<%=map.get("order_code")%>";
								
					}
		       }
		   ); 
		}
		if (typeof WeixinJSBridge == "undefined"){
		   if( document.addEventListener ){
		       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		   }else if (document.attachEvent){
		       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
		       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		   }
		}else{
		   onBridgeReady();
		} 


</script>