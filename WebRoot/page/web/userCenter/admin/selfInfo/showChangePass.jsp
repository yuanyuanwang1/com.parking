<%@ page contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/userCenter/admin/html_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>

<style>

.textarea_class {height:150px;
       width:60%;}


</style>

<script type="text/javascript" language="javascript">
	$(document).ready(function() {

		$("#upPass").click(function() {
		
			checkUpPass();

		});
		
		
        $('#newPassword').keyup(function () {
        
		var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
		var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
		var enoughRegex = new RegExp("(?=.{6,}).*", "g"); 
	
		if (false == enoughRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-defule'); 
			 //密码小于六位的时候，密码强度图片都为灰色 
		} 
		else if (strongRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-strong'); 
			 //密码为八位及以上并且字母数字特殊字符三项都包括,强度最强 
		} 
		else if (mediumRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-medium'); 
			 //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等 
		} 
		else { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass('pw-weak'); 
			 //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的 
		} 
		return true; 
	});

	});
	
	
	function checkUpPass() {

		if ($("#oldPassword").val() == "") {
            $("#errorInfo").addClass("error_info_css");	
			$("#errorInfo").html("原密码不能为空,操作失败!");
			return false;
		}

		if ($("#newPassword").val() == "") {
            $("#errorInfo").addClass("error_info_css");	
            $("#errorInfo").html("新密码不能为空,操作失败!");
			return false;
		}

		if ($("#newPasswordVerify").val() == "") {
            $("#errorInfo").addClass("error_info_css");	
            $("#errorInfo").html("确认新密码内容不能为空,操作失败!");
			return false;
		}

		if ($("#newPassword").val() != $("#newPasswordVerify").val()) {
            $("#errorInfo").addClass("error_info_css");	
            $("#errorInfo").html("新密码与确认新密码内容不相同,操作失败!");
			return false;
		}

		$.ajax({
			type : "POST",
			url : "ChangePassPost.action?M=" + Math.random(),
			data : "oldPassword=" + $("#oldPassword").val().toString() + "&newPassword="
					+ $("#newPassword").val().toString(),
					
			dataType : "json",
			success : function(data) {
            	var result = eval("(" + data + ")");
            	
            	if (result.status == "oldPasswordError") {
            	
            		$("#errorInfo").addClass("error_info_css");	
            		$("#errorInfo").html("原密码输入错误，请重新输入");
					return false;
            	
            	}
            	
            	if (result.status == "changePassOK") {
            	
            		$("#errorInfo").addClass("error_info_css");	
            		$("#errorInfo").html("更新密码成功！");
            		alert("更新密码成功，请您用新密码重新登陆系统！");
            		window.location.reload("<%=path%>/action/web/frontPage/passport/LoginGet.action");
					return true;
            	
            	}

					}
				});

	}
	
</script>

<style>
<!-- /* 效果CSS开始 */
.tbl-txt {
	font-size: 14px;
}

.tbl-txt input {
	padding: 0 5px;
	height: 22px;
	line-height: 22px;
	margin-bottom: 6px;
}

.pw-strength {
	clear: both;
	position: relative;
	top: 8px;
	width: 180px;
}

.pw-bar {
	background: url(<%=path%>/image/pwd-1.png) no-repeat;
	position: relative;
	top: 1px;
	height: 14px;
	overflow: hidden;
	width: 179px;
}

.pw-bar-on {
	background: url(<%=path%>/image/pwd-2.png) no-repeat;
	width: 0px;
	height: 14px;
	position: absolute;
	top: 1px;
	left: 2px;
	transition: width .5s ease-in;
	-moz-transition: width .5s ease-in;
	-webkit-transition: width .5s ease-in;
	-o-transition: width .5s ease-in;
}

.pw-weak .pw-defule {
	width: 0px;
}

.pw-weak .pw-bar-on {
	width: 60px;
}

.pw-medium .pw-bar-on {
	width: 120px;
}

.pw-strong .pw-bar-on {
	width: 179px;
}

.pw-txt {
	padding-top: 2px;
	width: 180px;
	overflow: hidden;
}

.pw-txt span {
	color: #707070;
	float: left;
	font-size: 12px;
	text-align: center;
	width: 58px;
}
/* 效果CSS结束 */
-->
</style>

<div id="content" class="span10">
	<div class="row-fluid">
		<div class="box span12">
			<div class="box-header wells well-list">
				<h2 class="well-h2">
					<i class="icon-info-sign"></i>修改密码
				</h2>
			</div>
			<div class="box-content">
				<div class="form-horizontal">
					<div class="control-group" style="margin-left:-40px;">
						<label class="control-label" for="title">原密码：</label> <input
							type="password" id="oldPassword" name="oldPassword" value="" /> <span
							id="titleTip"></span>

					</div>

					<div class="control-group" style="margin-left:-40px;">
						<label class="control-label" for="object.content">新密码：</label><input
							type="password" id="newPassword" name="newPassword" value="" />
						<span id="object.contentTip"></span>

					</div>

					<div class="control-group" style="margin-left:-40px;">
						<label class="control-label" for="title">确认新密码：</label> <input
							type="password" id="newPasswordVerify" name="newPasswordVerify" value="" /> <span
							id="titleTip"></span>

					</div>
					
					<div class="control-group" style="margin-left:-40px;">
						<label class="control-label" >密码强度：</label>
						<div id="level" class="pw-strength" style="margin-left: 140px;">
							<div class="pw-bar"></div>
							<div class="pw-bar-on"></div>
							<div class="pw-txt">
								<span>弱</span> <span>中</span> <span>强</span>
							</div>
						</div>
					</div>
					
					<div id="errorInfo" style="font-size: 14px;color:#F00;padding-left: 50px;height:30px;margin-left:60px;">${errorInfo }</div>
					<div class="form-actions">
						<button id="upPass" class="btn btn-primary" style="margin-left:-140px;width:100px;"/>
						提&nbsp;&nbsp;&nbsp;交
						</button>
						&nbsp;
					</div>
				</div>
			</div>
		</div>
		<!--/span-->

	</div>
	<div class="clearfix"></div>
</div>

<div class="row-fluid sortable"></div>

<%@ include file="/include/web/userCenter/admin/foot.inc.jsp"%>
