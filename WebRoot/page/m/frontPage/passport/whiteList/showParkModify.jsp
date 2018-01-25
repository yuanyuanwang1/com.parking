<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE HTML>

<html>

<head>

<%
	String head_path = request.getContextPath();
	List<Map<String, Object>> dataList = (List<Map<String, Object>>) request
			.getAttribute("dataList");
%>

<title>车场下发预约管理</title>

<meta charset="utf-8" />

<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<meta
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport" />

<meta content="yes" name="apple-mobile-web-app-capable" />

<meta content="black" name="apple-mobile-web-app-status-bar-style" />

<meta content="telephone=yes" name="format-detection" />

<meta content="email=no" name="format-detection" />


<link href="<%=head_path%>/style/whiteList/public.css" rel="stylesheet">

<link href="<%=head_path%>/style/whiteList/main.css" rel="stylesheet">

<script src="<%=head_path%>/style/whiteList/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<style>
.timeSel {
	border: 1px solid rgba(0, 0, 0, 0.1);
	border-radius: 2px;
	color: #2b2b2b;
	padding: 8px 9px 8px;
}

.jqSel {
	border: 1px solid rgba(0, 0, 0, 0.1);
	border-radius: 2px;
	color: #2b2b2b;
	padding: 8px 48px 8px;
}
</style>

<%
	String path = request.getContextPath();
%>

</head>
<body id="body" style="background: white;">

	<div class="fullscreen per_login" style="margin-top: 12px;">
		<div class="alert"></div>

		<span style="color:#289ED7;font-size: 20px">修改账户</span>

		<section class="inner_content" style="padding-top: 10px;">

			<form id="form1" name="form1" method="post" >
			
			<input id="pid" name="pid" type="hidden" value="${parkingUser.pid }"/>
							
				<div class="join">
					<label>账号名称</label>

					<div>

						<input id="pcode" name="pcode" 
							type="text" value="${parkingUser.pcode }" class="timeSel" style="vertical-align: top;" />
					</div>
					<label>密码</label>
					<div>

						<input id="password" name="password"  type="text"
							value="${parkingUser.password }" class="timeSel" style="vertical-align: top;" />
					</div>

					<label>使用者姓名</label>

					<div>

						<input id="pname" name="pname"
							type="text" value="${parkingUser.pname }" class="timeSel" style="vertical-align: top;" />
					</div>

					<label>描述</label>
					<div>
						<input type="textarea" style="height:80px;" value="${parkingUser.descriptions }"
							name="descriptions" id="descriptions" />
					</div>
					<br />
					<button type="button" id="addBtn">修 改</button>
					<br /><br />
					<button type="button" id="login_btn" onclick="history.go(-1)">返
						回</button>
			</form>
		</section>

	</div>
<script>


$("#addBtn").click(function() {

	add();

});

	
function add(){
	
	if ($("#pcode").val() == "") {
		
		alert("请填写账号名称");

		return false;
	}

	if ($("#password").val() == "") {
		
		alert("请设置登录密码");

		return false;
	}
	
	if ($("#pname").val() == "") {
		
		alert("请填写使用者姓名");

		return false;
	}
	


$.ajax({
	type : "POST",
	url : "ParkModifyPost.action?M=" + Math.random(),
	data : $("#form1").serialize(),
	dataType:"json",
	success : function(data) {
	
    	var d = eval("("+data+")");
    	
    	var loginResult = d.resultCode;
    	
    	var errInfo = d.message;
    	    	
    	if (loginResult == "0") {
    	
    		alert("账号信息修改成功!");
    		
    		return true;
    	
    	}
    	
    	if (loginResult != "0") {
    		
    	
    		alert("账号信息修改失败，请重新再试！!"+errInfo);
    		
			return false;

		}

	}
});
}
</script>

</body>
</html>