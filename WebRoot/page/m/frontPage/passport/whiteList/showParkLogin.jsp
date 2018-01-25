<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.math.BigDecimal"%>

<!DOCTYPE HTML>

<html>

<head>

<%
	String head_path = request.getContextPath();
	String jqId = (String) request.getAttribute("jqId");
%>

<meta charset="utf-8" />

<title>车场下发预约管理</title>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<meta
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport" />

<meta content="yes" name="apple-mobile-web-app-capable" />

<meta content="black" name="apple-mobile-web-app-status-bar-style" />

<meta content="telephone=yes" name="format-detection" />

<meta content="email=no" name="format-detection" />

<link href="<%=head_path%>/style/whiteList/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=head_path%>/style/whiteList/head.css" rel="stylesheet" type="text/css" />
<link href="<%=head_path%>/style/whiteList/foot.css" rel="stylesheet" type="text/css" />
<link rel='stylesheet' type='text/css' href='<%=head_path%>/style/whiteList/signup.css' />
<script src="<%=head_path%>/style/whiteList/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=head_path%>/style/whiteList/js/jquery.autocomplete.js" type="text/javascript"></script>
<script src="<%=head_path%>/style/whiteList/js/jquery.touchScroll.js" type="text/javascript"></script>	
<style>
input::-ms-input-placeholder{text-align: center;}
input::-webkit-input-placeholder{text-align: center;}
</style>	
</head>
<body>
	<div class="header" id="header">
		车场下发预约管理登录</span> <a class="search" href="search@r=search"></a>
	</div>
	<section class="signup">
		<!--<div class='bread_new'>用户名注册</div> -->
		<div class="form">
				<ul>
					<li><input class='tipInput' tiptext='用户名/手机号' type="text" placeholder="用户名" name="username" id="username"></li>
					<li><input class='password' tar='password'  placeholder="密码"  type="password" name="password" id="password"> </li>
			<li id="errorInfo" >
			</li>
					<li><input id='logBtn' type="submit" class="btn" value="登 录" />
					</li>
				</ul>
		</div>
	</section>
	<script type="text/javascript" language="javascript">
	$(document).ready(function() {

		$("#logBtn").click(function() {

			checkLogin();

		});

	});

	function checkLogin() {

		if ($("#username").val() == "") {
            $("#errorInfo").addClass("error_info_css");	
			$("#errorInfo").html("用户名不能为空！");
			// $("#username").focus();
			return false;
		}

		if ($("#password").val() == "") {
            $("#errorInfo").addClass("error_info_css");	
            $("#errorInfo").html("密码不能为空");
			// $("#password").focus();
			return false;
		}

		$.ajax({
			type : "POST",
			url : "ParkLoginPost.action?M=" + Math.random(),
			data : "username=" + $("#username").val().toString() + "&password="
					+ $("#password").val().toString(),
					
				dataType:"json",
			success : function(data) {
			
            	var d = eval("("+data+")");
            	
            	var loginResult = d.loginResult;
            	
            	if (loginResult == "loginError") {
            	
            		$("#errorInfo").addClass("error_info_css");	
            
            		$("#errorInfo").html("用户名或密码错误！登录失败");
            		
            		return false;
            	
            	}
            	
            	if (loginResult == "loginOK") {
            		
            		location.href = "ParkIndexGet.action";
            		
					return true;

				}

			}
		});

	}
</script>
</body>
</html>

