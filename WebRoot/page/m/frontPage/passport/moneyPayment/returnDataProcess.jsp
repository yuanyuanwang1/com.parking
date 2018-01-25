<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/newInclude/html_head.inc.jsp"%>
<style>
.alipayreturn{margin:50px 200px;}
</style>
<%@ include file="/include/newInclude/top.inc.jsp"%>
<div class="zxyd-body01">
	<div class="center013">
		<div class="title03">支付状态</div>
		<div class="alipayreturn">
			 <div class="lh01">
			 	<img src="<%=path %>/image/info01.png" width="60" />&nbsp;
			 	<span class="title04">正在处理数据，请稍等……  <span id="timeRedirect">5</span>秒后该窗口将关闭</span>
		 	</div>
		  	<div class="lh01" style="margin-top:20px;text-align:center;">
		  		<a href="<%=path%>/action/userCenter/tourist/order/listGet.action"><img src="<%=path %>/image/btn09.jpg" /></a>&nbsp;&nbsp;
		  		<a href="http://www.alipay.com">前往支付宝查看</a>
		  	</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			var time=5;
			setInterval(timeRedirectFun,1000);
			function timeRedirectFun(){
				if(time<=1){
					window.open("","_self"); 
					window.close();
				}
				if(time>0){
					time--;
					$("#timeRedirect").html(time);
				}
			}
			
		});
	</script>
</div>
<%@ include file="/include/newInclude/foot.inc.jsp"%>
