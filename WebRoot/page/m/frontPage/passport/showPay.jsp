<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta name="applicable-device" content="mobile">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<%
	String parkId = (String)request.getAttribute("parkId");
%> 
<title>
<%if("001".equalsIgnoreCase(parkId)) {
	%>
	天下第一关-罗成
<%}if("002".equalsIgnoreCase(parkId)){
	%>
	老龙头
<%} if("003".equalsIgnoreCase(parkId)){
	%>
	孟姜女庙
<%}if("004".equalsIgnoreCase(parkId)){
	%>
	天下第一关-西关
<%}%>
停车自助收费</title>
<%
	String path = request.getContextPath();

%> 

</head>
<link href="<%=path%>/script/epark/index_m.css?v=1" rel="stylesheet" type="text/css" />
<link href="<%=path%>/script/epark/index_m.css?v=1" rel="stylesheet" type="text/css" />
<script src="<%=path%>/script/epark/jquery.js"></script>
<body>
<div class="mobilebody" style="height:500px;">
<div align="center">
<div class="topline"><div align="center" class="top_title1" style="padding-top:10px;"><div class="title1 color1">
<a href="IndexGet.action?parkId=${ParkNo }"><img src="<%=path%>/img/arrow_left.png" style="width:23px;height:31px;float: left;padding-top: 8px;"/></a>
<a href="IndexGet.action?parkId=${ParkNo }"><img src="<%=path%>/image/logo_park.png" style="width:60px;height:60px;vertical-align:middle;"/>&nbsp;<span style="color:#ffffff">停车自助收费</span></div><div class="title2 color1"></div></div></div></a>
<div class="shadow2"></div>
</div>
<%
	String userAgentType = (String) request.getAttribute("userAgentType");
	Map<String,Object> map=(Map<String,Object>)request.getAttribute("map");
%>
<div class="box">
<div class="inputbox">
		 <br/>
    	<div  align="center"  >
    	<table>
    	<tr><td style="text-align:right">车&nbsp;&nbsp;牌&nbsp;&nbsp;号&nbsp;&nbsp;码：</td><td>${map.CarNo }</td></tr>
    	<tr><td style="text-align:right">进&nbsp;&nbsp;场&nbsp;&nbsp;时&nbsp;&nbsp;间：</td><td>${map.InDateTime }</td></tr>
    	<tr><td style="text-align:right">停&nbsp;&nbsp;车&nbsp;&nbsp;费&nbsp;&nbsp;用：</td><td>${map.carfee }</td></tr>
    	</table>
    	<br/>
    	</div>
    	<%if (!StringUtils.equals(userAgentType, "wx")) {%>
	<div style="clear:both"></div>
	
	<div  align="center"
		style="width:100%;height:50px;line-height:50px;">
		<div
			style="width:80%;font-size:17px;color:#ffffff;float:left;height:50px;line-height:50px">
			&nbsp;<img src="<%=path%>/img/duose_80x80.png"
				style="width:30px;padding-top:10px;line-height:50px;position: absolute;">&nbsp;
				<font style="height:50px;line-height:50px"><span style="padding-left: 23px;">支付宝支付</span></font>
		</div>
		<div
			style="width:20%;float:left;font-size:17px;height:50px;line-height:50px;display: none">
			<input type="radio" style="height:50px;line-height:50px" id="payType"
				name="payType" value="alipay"
				<%if (!StringUtils.equals(userAgentType, "wx")) {
				out.print("checked");
			}%> />
		</div>
	</div>
	<%}else {%>

	<div style="clear:both"></div>

	<div
		style="width:100%;height:50px;line-height:50px;">
		<div
			style="width:80%;font-size:17px;color:#ffffff;float:left;height:50px;line-height:50px">
		<center>	&nbsp;<img src="<%=path%>/img/weixin.png"
				style="width:30px;padding-top:10px;line-height:50px;position: absolute;">&nbsp;
				<font style="height:50px;line-height:50px"><span style="padding-left: 24px;">微信支付</span></font></center>
		</div>
		<div
			style="width:20%;float:left;font-size:17px;height:50px;line-height:50px;display: none">
			<input type="radio" style="height:50px;line-height:50px" id="payType"
				name="payType" value="wxpay"
				<%if (StringUtils.equals(userAgentType, "wx")) {
				out.print("checked");
			}%> />
		</div>
	</div>
		<%} %>
    	
         <div class="querybtn">
		 	<input id="submit1Button" class="btn" value="继续支付" type="button">
		</div>
		<br/>
			<span style="padding-left: 20px;">交费时间剩余：</span><span style="font-size:20px;color:red;font-weight:bold" id="showCloseTime"> 180 </span>
		<script>
		var pay = false; 
		var timeClose = 180;
		function run(){
			if (!pay) {

				if(timeClose == 0){
		   			window.location.href='IndexGet.action';
		   			return false;
				}
				timeClose = timeClose * 1 - 1;
				var s = document.getElementById("showCloseTime");
				
				s.innerHTML = " " + timeClose + "秒 ";
				setTimeout("run();", 1000);

			}
		}
		setTimeout("run();", 1000); 
		</script>
		<br/>
    </div>
<div id="ContentID" style="display:none"></div>
 
</div>

<script type="text/javascript" language="javascript">
	$(function() {

		$("#submit1Button").click(function() {
		
		if ($("input:radio[name=payType]:checked").val() == "wxpay") {
		
         location.href = "<%=request.getParameter("tenpayUrl")%>";

		
		} else {
		
			location.href = "<%=path%>/action/m/frontPage/passport/moneyPayment/PaymentSubmitGet.action?orderId=${pid}";
		
		}

		});

	});
</script>
<%@ include file="/include/web/frontPage/passport/foot.inc.jsp"%>