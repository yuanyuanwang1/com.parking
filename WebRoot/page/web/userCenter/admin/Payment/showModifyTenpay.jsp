<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.wy.dao.PageInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<%@ include file="/include/web/userCenter/admin/html_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>


<style>
.text3 {
	background: #fffffff;
	border: 1px solid #DDDDDD;
	width: 60%;
	color: #000000;
	height: 130px;
}
</style>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">车场微信信息管理</a></li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header wells well-list">
				<h2>
					<i class="icon-edit"></i> 修改车场微信信息
				</h2>
			</div>
			<div class="box-content">
				<div class="box-content">
					<form method="post" action="ModifyTenpayPost.action" class="form-horizontal" id="form1">
					
					<input type="hidden" name="CommonUserPid" value="${commonUser.pid }"/>
					
					<input type="hidden" name="pid" value="${map.pid }"/>

						<div class="control-group">
							<label class="control-label" for="pcode">车场编号：</label>${commonUser.pcode }
						</div>
						
					   <div class="control-group">
							<label class="control-label" for="pname">单位名称：</label> ${commonUser.pname }
						</div>
						
						
						<div class="control-group">
							<label class="control-label" for="pname">AppId：</label> <input
								type="text" class="input-xlarge" id="appId"
								name="appId" value="${map.appId}"/>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="pname">partnerKey：</label> <input
								type="text" class="input-xlarge" id="partnerKey"
								name="partnerKey" value="${map.partnerKey}"/>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="pname">partner：</label> <input
								type="text" class="input-xlarge" id="partner"
								name="partner" value="${map.partner}"/>
						</div>
						
						
							
						<div class="control-group">
							<label class="control-label" for="pname">appSerect：</label> <input
								type="text" class="input-xlarge" id="appSerect"
								name="appSerect" value="${map.appSerect}"/>
						</div>
						
						
							
						<div class="control-group">
							<label class="control-label" for="pname">notifyUrl(异步通知地址)：</label> <input
								type="text" class="input-xlarge" id="notifyUrl"
								name="notifyUrl" value="${map.notifyUrl}"/>
						</div>
						
								
						<div class="control-group">
							<label class="control-label" for="pname">redirectUrl：</label> <input
								type="text" class="input-xlarge" id="redirectUrl"
								name="redirectUrl" value="${map.redirect_url}"/>
						</div>
						
						
							
						<div class="control-group">
							<label class="control-label" for="pname">MCH_ID：</label> <input
								type="text" class="input-xlarge" id="mchId"
								name="mchId" value="${map.MCH_ID}"/>
						</div>
						
						


				  <div class="control-group" style="padding-left:70px;">
						<button type="submit" class="btn btn-primary" id="addBtn">修&nbsp;&nbsp;改</button>
						&nbsp;&nbsp;&nbsp;&nbsp; <a class="btn btn-primary"
							href="ListTenpayGet.action">返回</a>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/include/web/userCenter/admin/foot.inc.jsp"%>