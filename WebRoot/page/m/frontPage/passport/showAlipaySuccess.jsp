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
<a href="IndexGet.action?parkId=${parkId }"><img src="<%=path%>/img/arrow_left.png" style="width:23px;height:31px;float: left;padding-top: 8px;"/></a>
<a href="IndexGet.action?parkId=${parkId }"><img src="<%=path%>/img/logo.png" style="width:60px;height:60px;vertical-align:middle;"/>&nbsp;<span style="color:#ffffff">停车自助收费</span></div><div class="title2 color1"></div></div></div></a>
<div class="shadow2"></div>
</div>

<% Map<String,Object> mapInfo=(Map<String,Object>)request.getAttribute("map"); %>

<% System.out.println(mapInfo); %>

<div class="box">
<div class="inputbox">
		 
    	<div  align="center"  >
    	<br/>
    	<table>
    	<tr><td>车牌号码：</td><td>${map.carNo }</td></tr>
    	<tr><td>进场时间：</td><td>${map.InDateTime}</td></tr>
    	<tr><td>停车费用：</td><td>${map.CarFee}&nbsp;元</td></tr>
    	</table>
    	<br/>
    	<table>
	<tr><td style="width:160px;"><span style="font-size:14px;">交费后在场免费时间至：</span></td><td><span style="font-size:14px;">${FreeTimeAfterCenterCharge}</span></td></tr>
    
    	</table>
    		
    		<%if(mapInfo.get("order_status")!=null){
    			if("exchange".equals(mapInfo.get("order_status")) || "exchange".equalsIgnoreCase(mapInfo.get("order_status").toString()))
    			{
    				%>
    				<p><strong><span style="color:#F93300;margin-top: 30px">您已支付停车费
    				<%if("001".equals(mapInfo.get("PARK_ID"))){
    					%>天下第一关
    				<% }if("002".equals(mapInfo.get("PARK_ID"))){
    					%>	
    					老龙头
    				<%}if("003".equals(mapInfo.get("PARK_ID"))){
    					%>孟姜女庙
    				<%}%>
    				</span></strong></p>
<p>&nbsp;</p>
    		<%	}else{
    			%>
    			<p><strong><span style="color:red">暂未交费,<a href="IndexGet.action?parkId=${parkId }" style="color:red">返回交费</a></span></strong></p>
<p>&nbsp;</p>
    		<%}
    		} %>


    	</div>
<div id="ContentID" style="display:none"></div>
 
</div></div>

<%@ include file="/include/web/frontPage/passport/foot.inc.jsp"%>