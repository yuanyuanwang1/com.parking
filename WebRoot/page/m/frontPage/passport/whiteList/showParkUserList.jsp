<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*"%>

<!DOCTYPE HTML>

<html>

<head>

<%
	String head_path = request.getContextPath();
	List<Map<String, Object>> dataList = (List<Map<String, Object>>) request.getAttribute("dataList");
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

		<span style="color:#289ED7;"> 
	     <strong>下发预约使用账号</strong></span>

		<section class="inner_content" style="padding-top: 10px;">

			<table>
				<tr style="color:#289ED7;">
				    <td>使用者姓名</td>
					<td style="padding-left: 21px;">用户名</td>
					<td style="padding-left: 50px;">操作</td>
				</tr>
				<tr><td style="padding-top: 17px;"></td></tr>
				
				<%
					if (dataList != null && dataList.size() > 0) {
						for (Map<String, Object> map : dataList) {
							
							%>
						<tr style="height: 15px"><td style="color:black;">
						<%=map.get("pname")%>
                        </td>
					<td style="color:black;padding-left: 30px;"><%=map.get("pcode")%></td>
					<td style="color:black;padding-left: 30px;"><a href="ParkModifyGet.action?pid=<%=map.get("pid") %>">修改</a> || <a href="ParkDeletePost.action?pid=<%=map.get("pid") %>">删除</a></td>
				</tr><tr style="color:#289ED7;"><td colspan="4">---------------------------------------------------------------------</td></tr>
						<%}}else {
				%>
				<tr style="color:red;padding-top: 40px;">
					<td colspan="3">暂无数据</td>
				</tr>
				<%
					}
				%>
			</table>
			</br> </br>
			<button type="submit" id="login_btn" onclick="history.go(-1)">返回</button>

		</section>

	</div>
</body>
</html>