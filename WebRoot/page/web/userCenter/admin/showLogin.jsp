<%@ page contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/userCenter/admin/html_login_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<div id="Layer1" style="position:absolute; width:100%; height:100%; z-index:-1">  

<img src="<%=head_path%>/image/login_bg.jpg" height="100%" width="100%"/>  
</div>

<div class="login-bg">
	<div class="login-logo-new">
</div></div>
		<div class="login-bg">
			<div class="login-box-new">
				<div class="title">管理后台</div>
				<div class="form">
						<div class="input">
						 <label for="UserName"  class="login-label"><img src="<%=path%>/image/user_login.png" style="width:40px;margin-top: -20px;"/></label>
							<input type="text" name="username" id="username" class="input-mini input_text" />
						</div>
						<div class="input">
						<label for="UserName" class="login-label"><img src="<%=path%>/image/pwd_login.png" style="width:40px;margin-top: -20px;"/></label>
							<input type="password" name="password" id="password" class="input-small input_text"/>
						</div>				
							<div style="font-size: 14px;color:#F00;margin-left:100px;" id="errorInfo"><label></label></div>
						<div>
							<div style="float:right;margin-right:10px;"><input type="button" id="logBtn" value="登&nbsp;录" class="btn btn-primary" style="width:280px;height:35px;font-size:14px;font-weight:bold;"  /></div>
						</div>
				</div>
			<!--/span-->
		</div>
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
<%@ include file="/include/web/userCenter/admin/foot.inc.jsp"%>
