<%@ page contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%
	String path = request.getContextPath();
%>
<%@ include file="/include/web/frontPage/passport/html_head.inc.jsp"%>


<div id="Layer1" style="position:absolute; width:100%; height:100%; z-index:-1">  
<img src="<%=path%>/image/login_bg.jpg" height="100%" width="100%"/>  
</div>

<div class="login-bg">
	<div class="login-logo-new" style="    background: url(../image/bg_tm.png);">
</div></div>
		<div class="login-bg">
			<div class="login-box-new">
				
				<div style="float:right; width:40px; height:20px;display: none;" id="code_show">
				<img src="<%=path%>/img/pc.jpg" style="width:40px;" onclick="qrcode_show();"/>
				</div>
				<div class="title" id="pc_title">智能停车场软件系统</div>
				<div class="title" style="display: none;" id="code_title">扫码下载</div>
				<div class="form">
					<form method="post" action="LoginPost.action" id="form1">
					<div id="pc_shows">
						<div style="float:left; width:40px; height:20px;padding-left: 30px;">
						<img src="<%=path%>/image/login_user_blue.png" style="width:60%;padding-left: 12px;padding-top: 4px" /></div>
						<div style="float:left; width:200px; height:20px;">
						<input type="text" name="username" id="username" class="input_text" placeholder="请输入用户名"/>
						</div>
		
					   <div style="float:left; width:40px; height:20px;padding-left: 30px;padding-top: 40px;">
					   <img src="<%=path%>/image/login_password_blue.png" style="width:60%;padding-left: 12px;padding-top: 4px" /></div>
						<div style="float:left; width:200px; height:20px;padding-top: 40px;">
						<input type="password" name="password" id="password" class="input_text" placeholder="请输入 密码"/>
						</div>
						<div style="clear:both"></div>
						<div class="login-btn">
							<div style="float:right;margin-right:24px;">
							<input type="button" value="登录" class="btn" id="logBtn" style="width:215px;height:35px;font-size:14px;font-weight:bold;letter-spacing:12px;"  /></div>
						</div>
						<div style="font-size: 14px;color:#F00;margin-left:100px;" id="errorInfo"><label></label></div>
					</div>
					</form>
					
					</div>
			<!--/span-->
			
		</div>
	   <div class="login-foot" style="height:50px;width:400px;position:fixed;right:260px;bottom:50px;">
	   </div>	
   <div class="login-foot" style="height:50px;width:400px;position:fixed;right:20px;bottom:20px;"> </div>
			
	<script type="text/javascript">
	$(document).ready(function() {

		$("#logBtn").click(function() {
			
		checkLogin();

		});

	});
	

	function checkLogin() {
		

		if ($("#username").val() == "") {
			$("#errorInfo").html("用户名不能为空！");
			return false;
		}

		if ($("#password").val() == "") {
			 $("#errorInfo").html("密码不能为空");
			return false;
		}
		$.ajax({
			type : "POST",
			url : "LoginPost.action?M=" + Math.random(),
			data : "username=" + $("#username").val().toString() + "&password="
					+ $("#password").val().toString(),
					
				dataType:"json",
			success : function(data) {
			
            	var d = eval("("+data+")");
            	
            	var loginResult = d.loginResult;
            	
            	var errInfo = d.errorInfo;
            	
            
            	
            	if (loginResult == "loginError") {
            	
            		$("#errorInfo").html(errInfo);
            		
            		return false;
            	
            	}
            	
            	if (loginResult == "loginOK") {
            		
            	
            		location.href = "<%=path%>/action/web/userCenter/admin/IndexGet.action";
            		
					return true;

				}

			}
		});
	}
	</script>
	
</div>


</body>
</htm>