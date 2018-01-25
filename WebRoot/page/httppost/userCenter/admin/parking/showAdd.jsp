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
			<li><a href="#">新增车场管理</a></li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header wells well-list">
				<h2>
					<i class="icon-edit"></i> 添加车场
				</h2>
			</div>
			<div class="box-content">
				<div class="box-content">
					<form method="post" action="AddPost.action" class="form-horizontal" id="form1">

						<div class="control-group">
							<label class="control-label" for="pcode">车场编号：</label> <input
								type="text" id="pcode" name="pcode" class="input-xlarge" /> <span
								id="pcodeTip" class="required_dis"></span>
						</div>

						<div class="control-group">
							<label class="control-label" for="username">登录用户名：</label> <input
								type="text" class="input-xlarge" id="username"
								name="username" /> <span id="usernameTip"
								class="required_dis"></span>
						</div>

						<div class="control-group">
							<label class="control-label" for="password">设置密码：</label> <input
								type="text" class=" input-xlarge" id="password"
								name="password" />

						</div>
						
					   <div class="control-group">
							<label class="control-label" for="pname">单位名称：</label> <input
								type="text" class="input-xlarge" id="pname"
								name="pname" />
						</div>
						
								
						<div class="control-group">
							<label class="control-label" for="levelStatus">是否含有多级停车场：</label>
							<label><input name="levelStatus" type="radio" value="1" />是 </label>
							<label><input name="levelStatus" type="radio" value="0" checked="checked"/>否 </label>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="descriptions">备注：</label>
							<textarea name="descriptions" id="descriptions" class="text3"
								cols="30" rows="2"></textarea>
						</div>


				  <div class="control-group" style="padding-left:70px;">
						<button type="button" class="btn btn-primary" id="addBtn">添&nbsp;&nbsp;加</button>
						&nbsp;&nbsp;&nbsp;&nbsp; <a class="btn btn-primary"
							href="ListGet.action">返回</a>
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

$("#password").formValidator({onShow:"",onFocus:"",onCorrect:""}).inputValidator({min:2,max:20,onError:"请设置登录密码"});

$("#addBtn").click(function() {
	
	add();

	});
	
function add(){

$.ajax({
	type : "POST",
	url : "AddPost.action?M=" + Math.random(),
	data : $("#form1").serialize(),
	dataType:"json",
	success : function(data) {
	
    	var d = eval("("+data+")");
    	
    	var loginResult = d.resultCode;
    	
    	var errInfo = d.message;
    	    	
    	if (loginResult == "0") {
    	
    		alert("车场添加成功!");
    		
    		return true;
    	
    	}
    	
    	if (loginResult != "0") {
    		
    	
    		alert("车场添加失败，请重新再试！!"+errInfo);
    		
			return false;

		}

	}
});
}
</script>

