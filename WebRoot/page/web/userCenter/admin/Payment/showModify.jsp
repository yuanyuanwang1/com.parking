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
			<li><a href="#">车场支付宝信息管理</a></li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header wells well-list">
				<h2>
					<i class="icon-edit"></i> 修改车场支付宝信息
				</h2>
			</div>
			<div class="box-content">
				<div class="box-content">
					<form method="post" action="ModifyPost.action" class="form-horizontal" id="form1">
					
					<input type="hidden" name="CommonUserPid" value="${commonUser.pid }"/>
					
					<input type="hidden" name="pid" value="${map.pid }"/>

						<div class="control-group">
							<label class="control-label" for="pcode">车场编号：</label>${commonUser.pcode }
						</div>
						
					   <div class="control-group">
							<label class="control-label" for="pname">单位名称：</label> ${commonUser.pname }
						</div>
						
						
						<div class="control-group">
							<label class="control-label" for="pname">payId(支付宝账号)：</label> <input
								type="text" class="input-xlarge" id="payId"
								name="payId" value="${map.PAY_ID}"/>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="pname">alipayPartner(商户Id)：</label> <input
								type="text" class="input-xlarge" id="alipayPartner"
								name="alipayPartner" value="${map.ALIPAY_PARTNER}"/>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="pname">alipayKey(私钥)：</label> <input
								type="text" class="input-xlarge" id="alipayKey"
								name="alipayKey" value="${map.alipay_Key}"/>
						</div>
						
						
							
						<div class="control-group">
							<label class="control-label" for="pname">callBackUrl(支付成功返回地址)：</label> <input
								type="text" class="input-xlarge" id="callBackUrl"
								name="callBackUrl" value="${map.call_Back_Url}"/>
						</div>
						
						
							
						<div class="control-group">
							<label class="control-label" for="pname">notifyUrl(异步通知地址)：</label> <input
								type="text" class="input-xlarge" id="notifyUrl"
								name="notifyUrl" value="${map.notify_Url}"/>
						</div>
						
						
							
						<div class="control-group">
							<label class="control-label" for="pname">merchantUrl(支付中断返回地址)：</label> <input
								type="text" class="input-xlarge" id="merchantUrl"
								name="merchantUrl" value="${map.merchant_Url}"/>
						</div>
						
						


				  <div class="control-group" style="padding-left:70px;">
						<button type="submit" class="btn btn-primary" id="addBtn">修&nbsp;&nbsp;改</button>
						&nbsp;&nbsp;&nbsp;&nbsp; <a class="btn btn-primary"
							href="ListGet.action">返回</a>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/include/web/userCenter/admin/foot.inc.jsp"%>