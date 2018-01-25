<%@ page contentType="text/html;charset=utf-8"%>
<%
	String path = request.getContextPath();

    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

%>

	<link rel="stylesheet" href="<%=path%>/plugins/ueditor/docimp/webuploader.css">
 	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/plugins/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>    

<div class="container-fluid">
		<div class="row-fluid">