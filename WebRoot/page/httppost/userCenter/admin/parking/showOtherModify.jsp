<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.wy.dao.PageInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/httppost/html_flow_head.inc.jsp"%>

<%@ include file="/include/web/httppost/top.inc.jsp"%>

<%@ include file="/include/web/httppost/menu_left.inc.jsp"%>


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
			<li><a href="#">车场管理</a></li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header wells well-list">
				<h2>
					<i class="icon-edit"></i> ${commonUser.pname}修改车场信息
				</h2>
			</div>
			<div class="box-content">
				<div class="box-content">
					<form method="post" action="OtherModifyPost.action" class="form-horizontal" id="form1">
					
					<input type="hidden" name="pid" value="${pid}"/>
					
					<div class="control-group">
							<label class="control-label"  style="vertical-align: middle;">主管车场：</label>
							<span style="vertical-align: middle;"> ${commonUser.pname}</span>
						</div>

						<div class="control-group">
							<label class="control-label" for="pcode">车场编号：</label> <input
								type="text" id="pcode" name="pcode" class="input-xlarge" value="${commonUser.pcode }" /> <span
								id="pcodeTip" class="required_dis"></span>
						</div>

						<div class="control-group">
							<label class="control-label" for="username">登录用户名：</label> <input
								type="text" class="input-xlarge" id="username"
								name="username" value="${commonUser.username }"/> <span id="usernameTip"
								class="required_dis"></span>
						</div>

						
					   <div class="control-group">
							<label class="control-label" for="pname">单位名称：</label> <input
								type="text" class="input-xlarge" id="pname"
								name="pname" value="${commonUser.pname }"/>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="descriptions">备注：</label>
							<textarea name="descriptions" id="descriptions" class="text3"
								cols="30" rows="2">${commonUser.descriptions }</textarea>
						</div>


				  <div class="control-group" style="padding-left:70px;">
						<button type="button" class="btn btn-primary" id="addBtn">修&nbsp;&nbsp;改</button>
						&nbsp;&nbsp;&nbsp;&nbsp; <a class="btn btn-primary"
							href="OtherListGet.action?pid=${commonUser.createUserId }">返回</a>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>


<script>

$.formValidator.initConfig({formID:"form1",autoTip:false,onError:function(msg){alert(msg);},inIframe:true});


$("#pcode").formValidator({onShow:"",onFocus:"",onCorrect:""}).inputValidator({min:1,onError:"请填写车场编号"});

$("#username").formValidator({onShow:"",onFocus:"",onCorrect:""}).inputValidator({min:2,max:20,onError:"请填写登录用户名"});


$("#addBtn").click(function() {
	
	add();

	});
	
function add(){

$.ajax({
	type : "POST",
	url : "OtherModifyPost.action?M=" + Math.random(),
	data : $("#form1").serialize(),
	dataType:"json",
	success : function(data) {
	
    	var d = eval("("+data+")");
    	
    	var loginResult = d.resultCode;
    	
    	var errInfo = d.message;
    	    	
    	if (loginResult == "0") {
    	
    		alert("修改车场信息成功!");
    		
    		return true;
    	
    	}
    	
    	if (loginResult != "0") {
    		
    	
    		alert("修改车场信息失败，请重新再试！!"+errInfo);
    		
			return false;

		}

	}
});
}
</script>

