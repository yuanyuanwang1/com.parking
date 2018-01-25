<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/httppost/html_flow_head.inc.jsp"%>

<%@ include file="/include/web/httppost/top.inc.jsp"%>

<%@ include file="/include/web/httppost/menu_left.inc.jsp"%>

<%
	String fast_path = request.getContextPath();
%>

<div id="content" class="span10">
	<div class="row-fluid">
		<div class="box span12">
			<div class="box-header wells well-list" style="border: 1px solid #ddd;">
				<h2 class="well-h2">
					<i class="icon-info-sign"></i>个人信息查看
				</h2>
			</div>
			<div class="box-content">
				<form id="form1" method="post" class="form-horizontal">
					<div class="control-group" style="margin-left:-30px;">
						<label class="control-label" for="title">登&nbsp;陆&nbsp;名：</label>&nbsp;&nbsp;<label style="margin-top:6px;">${map.username }</label>
					</div>
					<div class="control-group" style="margin-left:-30px;">
						<label class="control-label" for="title">用&nbsp;户&nbsp;名：</label>&nbsp;&nbsp;<label style="margin-top:6px;">${map.pname }</label>
					</div>
					<div class="control-group" style="margin-left:-30px;">
						<label class="control-label" for="">停车场编号：</label>
                  &nbsp;&nbsp;<label style="margin-top:6px;">${map.pcode }</label>
					</div>
					<div class="control-group" style="margin-left:-30px;">
						<label class="control-label" for="title">创建时间：</label>&nbsp;&nbsp;<label style="margin-top:6px;">
						<fmt:formatDate value="${map.create_time }" type="date"
							pattern="yyyy-MM-dd HH:mm:ss" /></label>

					</div>
				</form>
			</div>
		</div>
		<!--/span-->

	</div>
	<div class="clearfix"></div>
</div>

<div class="row-fluid sortable"></div>

<%@ include file="/include/web/httppost/foot.inc.jsp"%>
