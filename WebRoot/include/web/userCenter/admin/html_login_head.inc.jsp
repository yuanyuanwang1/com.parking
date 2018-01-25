<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.chento.dao.PageInfo"%>
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
<meta http-equiv="keywords" content="管理后台">
<meta http-equiv="description" content="管理后台">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
	String head_path = request.getContextPath();
%>

<link rel="shortcut icon" href="<%=head_path%>/image/favicon.ico"
	type="image/x-icon" />
<link rel="stylesheet" href="<%=head_path%>/style/validator.css"
	type="text/css"></link>
<script type="text/javascript"
	src="<%=head_path%>/script/formValidator/formValidator-4.0.1.min.js"></script>

<script type="text/javascript"
	src="<%=head_path%>/script/formValidator/formValidatorRegex.js"></script>

<script type="text/javascript"
	src="<%=head_path%>/script/DatePicker/WdatePicker.js"></script>
<script src="<%=head_path%>/script/kindeditor/kindeditor-min.js"></script>

<script src="<%=head_path%>/script/bootstrap-dropdown.js"></script>

<!-- The styles -->
<link id="bs-css" href="<%=head_path%>/style/bootstrap-cerulean.css"
	rel="stylesheet">
<link href="<%=head_path%>/style/bootstrap-responsive.css"
	rel="stylesheet">
<link href="<%=head_path%>/style/charisma-app.css" rel="stylesheet">
<link href="<%=head_path%>/style/jquery-ui-1.8.21.custom.css"
	rel="stylesheet">
<link href="<%=head_path%>/style/fullcalendar.css" rel="stylesheet">
<link href="<%=head_path%>/style/fullcalendar.print.css"
	rel="stylesheet" media="print">
<link href="<%=head_path%>/style/chosen.css" rel="stylesheet">
<link href="<%=head_path%>/style/uniform.default.css" rel="stylesheet">
<link href="<%=head_path%>/style/colorbox.css" rel="stylesheet">
<link href="<%=head_path%>/style/jquery.cleditor.css" rel="stylesheet">
<link href="<%=head_path%>/style/jquery.noty.css" rel="stylesheet">
<link href="<%=head_path%>/style/noty_theme_default.css"
	rel="stylesheet">
<link href="<%=head_path%>/style/elfinder.min.css" rel="stylesheet">
<link href="<%=head_path%>/style/elfinder.theme.css" rel="stylesheet">
<link href="<%=head_path%>/style/jquery.iphone.toggle.css"
	rel="stylesheet">
<link href="<%=head_path%>/style/opa-icons.css" rel="stylesheet">
	<script src="<%=head_path%>/script/js/jquery.min.js"></script>
	<script src="<%=head_path%>/script/js/bootstrap.min.js"></script>


</head>
