<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*"%>

<!DOCTYPE HTML>

<html>

<head>

<%
	String head_path = request.getContextPath();
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
	padding: 8px 9px 8px;
}

.jqSel {
	border: 1px solid rgba(0, 0, 0, 0.1);
	border-radius: 2px;
	color: #2b2b2b;
	padding: 8px 48px 8px;
}
</style>

<%
	String path = request.getContextPath();

	List<Map<String, Object>> list = (List<Map<String, Object>>) request
			.getAttribute("list");
%>

</head>
<body id="body" style="background: white;">

	<div class="fullscreen per_login" style="margin-top: 12px;">
		<div class="alert"></div>

		<span style="color:#289ED7;font-size: 20px">车场预约超时情况查询</span>

		<section class="inner_content" style="padding-top: 10px;">

			<form id="loginform" name="loginform" method="post"
				action="ParkOverTimePost.action">
				<table>
					<tr>
						<td><img src="<%=path%>/image/check1.png"
							style="width:45%;height:45%;vertical-align: top;">
						</td>
						<td><input id="FromTime" name="FromTime" type="date"
							value="${FromTime }" class="timeSel"
							style="vertical-align: top;" />
					</tr>
					<tr>
						<td style="padding-top: 15px;"></td>
					</tr>
					<tr>
						<td><img src="<%=path%>/image/check1.png"
							style="width:45%;height:45%;vertical-align: top;"></td>
						<td><input id="ToTime" name="ToTime" type="date"
							value="${ToTime }" class="timeSel" style="vertical-align: top;" />
						</td>
					</tr>
					<tr>
						<td style="padding-top: 15px;"></td>
					</tr>
					<tr>
						<td><img src="<%=path%>/image/titlename.png"
							style="width:45%;height:45%;vertical-align: top;">
						</td>
						<td><select class="jqSel" name="parkNos" id="parkNos"
							style="vertical-align: top;">
								<%
									if (list != null) {
										for (Map<String, Object> map : list) {
								%>
								<option value="<%=map.get("parkCode")%>"><%=map.get("parkName")%>车场
								</option>
								<%
									}
									}
								%>
						</select></td>
					</tr>
					<tr>
						<td style="padding-top: 15px;"></td>
					</tr>
					<tr>
						<td><img src="<%=path%>/image/search.png"
							style="width:45%;height:45%;">
						</td>
						<td><input id="carNo" name="carNo" type="text"
							value="" placeholder="请输入车牌号" class="timeSel" 
							style="vertical-align: top;margin-top: -10px;" />
							</div></td>
					</tr>
				</table>
				</br> </br> </br>
				<button type="submit" id="login_btn">查 询</button>

				</br> </br> </br>
				<button type="button" id="login_btn"
					onclick="javascript:window.location.href='ParkIndexGet.action' ">返回</button>
			</form>

		</section>

	</div>
</body>
</html>