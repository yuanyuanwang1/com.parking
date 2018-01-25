<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>

<html>

<head>

<%
	String head_path = request.getContextPath();
%>

<title>停车场数据统计分析</title>

<meta charset="utf-8" />

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
.timeSel{
    border: 1px solid rgba(0, 0, 0, 0.1);
    border-radius: 2px;
    color: #2b2b2b;
    padding: 8px 9px 8px;
}
.jqSel{
    border: 1px solid rgba(0, 0, 0, 0.1);
    border-radius: 2px;
    color: #2b2b2b;
    padding: 8px 48px 8px;
}
a{
color: #289ED7;
font-family: microsoft yahei;
}
</style>

<%
	String path = request.getContextPath();

	String roleId=(String)request.getAttribute("roleId");
%>

</head>
<body id="body">

	<div style="margin-top: 12px;">
		<div class="alert"></div>

<div><span style="color:#289ED7;width:600px;">停车场下发预约管理</span></td>
<span style="color:#289ED7;">
<a href="ParkPassWordGet.action">
<img src="<%=path%>/image/user.png" style="width: 6%;float: right;padding-right: 10px;"/></span>
</a>
</div>
         
         

		<section style="padding-top: 10px;">
		
			<form id="loginform" name="loginform" method="post" action="parkPost.action">
			
			<%if("admin".equalsIgnoreCase(roleId))
				{%>
				<table>
				<tr><td colspan="4" style="height:20px;"></td></tr>
				<tr><td style="width:25%;"><a href="ParkSelGet.action"><img src="<%=path%>/image/four.png" style="width: 95%;padding-left: 6px"/></a></td>
				<td style="width:25%"><a href="ParkAddGet.action"><img src="<%=path%>/image/list_user.png"  style="width: 90%;padding-left: 26px"/></a></td>
				<td style="width:25%"><a href="ParkUserListGet.action"><img src="<%=path%>/image/city_license.png"  style="width: 95%;padding-left: 36px"/></a></td>
				<td style="width:25%"></td></tr>
				<tr><td colspan="4" style="height:10px;"></td></tr>
				<tr style="text-align: center;width: 200px">
				<td style="width:25%"><a href="ParkSelGet.action">查询管理</a></td>
				<td style="width:33%"><a href="ParkAddGet.action"><span style="padding-left: 50px">新增账号</span></a></td>
				<td style="width:33%"><a href="ParkUserListGet.action"><span style="padding-left: 50px">账号列表</span></a></td>
				<td style="width:0%">&nbsp;</td></tr>
				
				</table>
			<%} %>	
				<%if("1".equalsIgnoreCase(roleId))
				{%>
				<table>
				<tr><td colspan="4" style="height:20px;"></td></tr>
				<tr><td style="width:25%;"><a href="ParkPresellGet.action"><img src="<%=path%>/image/white_list.jpg" style="width: 95%;padding-left: 6px"/></a></td>
				<td style="width:25%"><a href="ParkPresellListSelGet.action"><img src="<%=path%>/image/order.png"  style="width: 75%;padding-left: 26px"/></a></td>
				<td style="width:25%"></td>
				<td style="width:25%"></td></tr>
				<tr><td colspan="4" style="height:10px;"></td></tr>
				<tr style="text-align: center;width: 200px">
				<td style="width:25%"><a href="ParkPresellGet.action">下发预约</a></td>
				<td style="width:33%"><a href="ParkPresellListSelGet.action"><span style="padding-left: 30px">预约列表</span></a></td>
				<td style="width:33%"></td>
				<td style="width:0%">&nbsp;</td></tr>
				
				</table>
	<%} %>
			</form>

		</section>

	</div>
</body>
</html>