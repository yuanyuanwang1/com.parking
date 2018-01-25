<%@ page contentType="text/html;charset=utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
	String head_path = request.getContextPath();
%>
<title>智能停车场软件系统</title>
<link rel="shortcut icon" href="<%=head_path %>/img/favicon.ico" />

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="智能停车场软件系统">
<meta http-equiv="description" content="智能停车场软件系统">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript" src="<%=head_path%>/script/web/frontPage/passport/all.js"></script>

<link id="bs-css" href="<%=head_path%>/style/bootstrap-cerulean.css"
	rel="stylesheet"></link>


<script type="text/javascript" src="<%=head_path%>/script/web/frontPage/passport/jquery.SuperSlide.2.1.1.js"></script>

<script src="<%=head_path%>/script/jquery.min.js"></script>
<script src="<%=head_path%>/script/bootstrap.min.js"></script>
<script src="<%=head_path%>/script/web/frontPage/passport/main.js"></script>
<link href='<%=head_path%>/style/web/frontPage/passport/master.css'rel='stylesheet'>
<link href='<%=head_path%>/style/web/frontPage/passport/base.css' rel='stylesheet'>
<link href='<%=head_path%>/style/web/frontPage/passport/bootstrap.min.css' rel='stylesheet'>
<link href='<%=head_path%>/style/web/frontPage/passport/main.css' rel='stylesheet'>
<script type="text/javascript" src="<%=head_path%>/script/web/frontPage/passport/top.js"></script>
<script type="text/javascript" src="<%=head_path%>/script/web/frontPage/passport/video.min.js"></script>
<script type="text/javascript" src="<%=head_path%>/script/web/frontPage/passport/videojs-ie8.min.js"></script>
<link href='<%=head_path%>/style/web/frontPage/passport/video-js.css' rel='stylesheet'>
<script type="text/javascript" src="<%=head_path%>/script/DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=head_path%>/script/formValidator/formValidator-4.0.1.min.js"></script>
<script type="text/javascript" src="<%=head_path%>/script/web/frontPage/passport/nicescroll.js"></script>
<script type="text/javascript" src="<%=head_path%>/script/formValidator/formValidatorRegex.js"></script>

<script>

var windowheight=0;
var iWindowWidth=0;
var minwidth=1003;
$(document).ready(function(e) {
    windowheight=$(window).height();
    iWindowWidth=$(window).width();
});
$(window).resize(function(){
	windowheight=$(window).height();
    iWindowWidth=$(window).width();
	if(iWindowWidth<minwidth)
	{
		$('body').width(minwidth);
	}
	else
	{
		$('body').width(iWindowWidth);
	}
	
});
</script>

</head>
<body>