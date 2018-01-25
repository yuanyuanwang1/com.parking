
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>管理后台</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="停车场智能管理">
<meta http-equiv="description" content="停车场智能管理">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
	String head_path = request.getContextPath();
%>
<link rel="shortcut icon" href="<%=head_path%>/image/favicon.ico"
	type="image/x-icon" />

<script src="<%=head_path%>/script/bootstrap-dropdown.js"></script>
<script src="<%=head_path%>/script/js/jquery.min.js"></script>
<script src="<%=head_path%>/script/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=head_path%>/style/validator.css" type="text/css"></link>
<script type="text/javascript" src="<%=head_path%>/script/formValidator/formValidator-4.0.1.min.js"></script>

<script type="text/javascript" src="<%=head_path%>/script/formValidator/formValidatorRegex.js"></script>

<script type="text/javascript" src="<%=head_path%>/script/DatePicker/WdatePicker.js"></script>


<!-- The styles -->
<link id="bs-css" href="<%=head_path%>/style/bootstrap-cerulean.css" rel="stylesheet">

<style type="text/css">
body {
	padding-bottom: 40px;
	font-family: '微软雅黑';
}

.sidebar-nav {
	padding: 9px 0;
}
</style>


<style>
#main-menu-span {
	margin-left: 7px;
	float: left;
}

#content {
	margin-left: 10px;
	float: right;
}
</style>

</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href="#"
					style="width:220px;height:55px;cursor:default"> <img alt=""
					style="width:220px;height:100px;margin-top:-24px;"
					src="<%=head_path%>/image/logo_park.png">
				</a>
				<!-- user dropdown starts -->
				<div class="btn-group pull-right login-user"
					style="padding-top: 4px;padding-left: 70px;margin-top: 16px;">
					<div class="btn-group" style="width:40px;">
						<span style="margin-left: -22px;"> <strong>admin </strong>
						</span>
						<image class="dropdown-toggle" data-toggle="dropdown"
							src="<%=head_path%>/image/pull_down.png" />
						<ul class="dropdown-menu" role="menu">
							<li><a
								href="<%=head_path%>/action/web/userCenter/admin/selfInfo/ChangePassGet.action">修改密码</a>
							</li>
							<li><a
								href="<%=head_path%>/action/web/userCenter/admin/LogoutGet.action">注销</a>
							</li>
						</ul>
					</div>

				</div>

				<a
					href="<%=head_path%>/action/web/userCenter/admin/IndexGet.action"
					style=" text-decoration:none; color:white;">
					<div class="btn-group pull-right work-index"
						style="width:111px;height:42px;padding-top: 10px;padding-left: 70px;margin-top: 6px;">
						<span class="hidden-phone" style="margin-left: -12px;"><strong>工作台</strong>
						</span>
					</div> </a>
				<!-- user dropdown ends -->
			</div>
		</div>
	</div>