<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@page import="com.wy.dao.PageInfo"%>

<!DOCTYPE HTML>

<html>

<head>

<%
	String head_path = request.getContextPath();
    String pageURI = "ParkOverTimePost.action";
	List<Map<String, Object>> dataList = (List<Map<String, Object>>) request.getAttribute("dataList");
	String parkCode=(String)request.getAttribute("parkCode");
	
	String searchValue=(String)request.getAttribute("searchValue");
%>

<meta charset="utf-8" />

<title>车场下发预约管理</title>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<meta
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport" />

<meta content="yes" name="apple-mobile-web-app-capable" />

<meta content="black" name="apple-mobile-web-app-status-bar-style" />

<meta content="telephone=yes" name="format-detection" />

<meta content="email=no" name="format-detection" />


<link href="<%=head_path%>/style/whiteList/public.css" rel="stylesheet">

<link href="<%=head_path%>/style/whiteList/login.css" rel="stylesheet">
<style>
.timeSel {
	border: 1px solid rgba(0, 0, 0, 0.1);
	border-radius: 2px;
	color: #2b2b2b;
	padding: 8px 9px 7px;
}
</style>

</head>
<body id="body" style="background: white;">

	<div class="fullscreen per_login" style="margin-top: 12px;">
		<div class="alert"></div>

		<span style="color:#289ED7;"> <strong>下发预约超时明细</strong>
		</span>

		<section class="inner_content" style="padding-top: 10px;">
         <form method="get" action="<%=pageURI%>" class="form-horizontal">
         			<table>
				<tr style="color:#289ED7;">
					<td>车牌号</td>
					<td style="padding-left: 26px;">车场名称</td>
					<td style="padding-left: 24px;">超时时间(天)</td>
				</tr>
				<tr>
					<td style="padding-top: 17px;"></td>
				</tr>
				<%
										PageInfo listPageInfo = (PageInfo) request.getAttribute("pageInfo");
								
								     if(listPageInfo!=null){

										int count = (listPageInfo.getPageNum() - 1)
												* listPageInfo.getPageSize();

										List<Map<String, Object>> objectList = (List<Map<String, Object>>) listPageInfo
												.getItems();

										if (objectList != null && !objectList.isEmpty()) {
											for (Map<String, Object> map : objectList) {

												count++;
									%>
				<tr style="height: 15px;" >
					<td style="color:black;width: 63px"><%=map.get("CarNo")%></td>
					<td style="color:black;padding-left: 25px;width: 63px"><%=map.get("ParkName")%></td>
					<td style="color:black;padding-left: 30px;width: 50px"><%=map.get("OverTime")%></td>
				</tr>
					<tr style="color:#289ED7;">
					<td colspan="5" style="padding-top:8px;padding-bottom:8px;">
					<div style="border-bottom: 1px solid #ccc!important;border-color: #289ED7!important;"></div>
					</td>
				</tr>
				<%}}%>
				<tfoot>
					<tr>
						<td colspan="13"><%@ include
								file="/include/web/frontPage/passport/pageInfo.inc.jsp"%>
						</td>
					</tr>
				</tfoot>
				<%	}else {
				%>
				<tr style="color:red;padding-top: 40px;">
					<td colspan="3">暂无数据</td>
				</tr>
				<%
					}
				%>
			</table>
			</br> </br>
			<button type="button" id="login_btn" onclick="javascript:window.location.href='ParkOverTimeGet.action'">返回</button>
</form>
		</section>

	</div>
</body>
</html>