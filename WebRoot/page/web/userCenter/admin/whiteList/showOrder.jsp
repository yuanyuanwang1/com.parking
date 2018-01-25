<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/userCenter/admin/html_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>

<%
	String jqId = (String) request.getAttribute("jqId");

	List<Map<String, Object>> dataList = (List<Map<String, Object>>) request
			.getAttribute("dataList");
	
	List<Map<String,Object>> list=(List<Map<String,Object>>)request.getAttribute("list"); 
%>

<style>
.text3 {
	background: #fffffff;
	border: 1px solid #DDDDDD;
	width: 80%;
	color: #000000;
	height: 200px;
}
</style>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">车辆发行管理</a></li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header wells well-list">
				<h2>
					<i class="icon-edit"></i> 下发预约信息
				</h2>
			</div>
			<div class="box-content">
				<div class="box-content">
					<form method="post" action="OrderPost.action" class="form-horizontal">
					
					<input type="hidden" value="1" name="flag" id="flag"/>


					<%if(list!=null && list.size()>0)
						{
						%>
						<div class="control-group">
						<label class="control-label" for="CarNo">下发到停车场：</label>
						<% for(Map<String,Object> map:list){%>
						 
						 <input type="checkbox" style="vertical-align:middle;" name="parkNos" value="<%=map.get("pcode") %>">
						 <span style="vertical-align:middle;" ><%=map.get("pname") %></span>
									
					 <%}%> 
						</div>
						<%}%>
					
						<div class="control-group">
							<label class="control-label" for="cardCode">车牌号：</label> <input
								type="text" id="cardCode" name="cardCode" class="input-xlarge" /> <span
								id="cardCodeTip" class="required_dis"></span>
						</div>


						<div class="control-group">
							<label class="control-label" for="startTime">开始时间：</label> <input
								type="text" class="Wdate input-xlarge" id="startTime"
								name="startTime" onclick="WdatePicker()" /> <span
								id="startTimeTip" class="required_dis"></span>
						</div>
						<div class="control-group">
							<label class="control-label" for="endTime">结束时间：</label> <input
								type="text" class="Wdate input-xlarge" id="endTime"
								name="endTime" onclick="WdatePicker()" /> <span id="endTimeTip"
								class="required_dis"></span>
						</div>

                      <div class="control-group" style="padding-left:70px;">
						<button type="button" class="btn btn-primary" id="addBtn">添&nbsp;&nbsp;加</button>
						
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<script>

$("#addBtn").click(function() {
	
	add();

	});
	
function add(){

	var els =document.getElementsByName("parkNos");
	
	var parkNos="";
	
	for (var i = 0, j = els.length; i < j; i++){
		
		  if(els[i].checked){
			
			  parkNos=parkNos+els[i].value+",";
	        }
	}

$.ajax({
	type : "POST",
	url : "OrderPost.action?M=" + Math.random(),
	data : "cardCode=" + $("#cardCode").val().toString() + "&startTime="
			+ $("#startTime").val().toString()+ "&endTime="
			+ $("#endTime").val().toString()+ "&flag="
			+ $("#flag").val().toString()+"&parkNos="+ parkNos,
			
		dataType:"json",
	success : function(data) {
	
    	var d = eval("("+data+")");
    	
    	var loginResult = d.resultCode;
    	
    	var errInfo = d.message;
    	    	
    	if (loginResult == "0") {
    	
    		alert("下发预约成功!");
    		
    		return true;
    	
    	}
    	
    	if (loginResult != "0") {
    		
    	
    		alert("下发预约失败，请重新再试！!"+errInfo);
    		
			return false;

		}

	}
});
}
</script>
