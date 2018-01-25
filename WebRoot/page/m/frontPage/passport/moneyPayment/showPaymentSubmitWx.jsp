<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<%@ include file="/include/m/frontPage/htmlHead.inc.jsp"%>
<script language="javascript">

$(function() {

	callpay();

});

function callpay() {
	WeixinJSBridge.invoke('getBrandWCPayRequest',<%=request.getAttribute("requestHtml")%>, function(res) {
					WeixinJSBridge.log(res.err_msg);
					alert(res.err_code + res.err_desc + res.err_msg);
	});
}
</script>

<%@ include file="/include/include/foot_m.inc.jsp"%>