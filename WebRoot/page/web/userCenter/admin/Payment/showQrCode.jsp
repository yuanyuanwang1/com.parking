<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/userCenter/admin/html_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>

<%
	String fast_path = request.getContextPath();
%>

<div id="content" class="span10">
	<div class="row-fluid">
		<div class="box span12">
			<div class="box-header wells well-list" style="border: 1px solid #ddd;">
				<h2 class="well-h2">
					<i class="icon-info-sign"></i>扫码支付二维码信息查看
				</h2>
			</div>
			<div class="box-content">
				生成规则：http://域名/com.wy.parking/action/m/frontPage/passport/IndexGet.action?parkId=0001
			</div>
		</div>
		<!--/span-->

	</div>
	<div class="clearfix"></div>
</div>

<div class="row-fluid sortable"></div>

<%@ include file="/include/web/userCenter/admin/foot.inc.jsp"%>
