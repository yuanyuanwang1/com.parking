<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/userCenter/admin/html_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>

<%List<Map<String,Object>> list=(List<Map<String,Object>>)request.getAttribute("list"); 
Map<String,Object> resultMap=(Map<String,Object>)request.getAttribute("resultMap");
%>


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
			<li><a href="#">车辆发行管理</a></li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header wells well-list">
				<h2>
					<i class="icon-edit"></i> 添加发行车辆
				</h2>
			</div>
			<div class="box-content">
				<div class="box-content">
					<form method="post" action="AddPost.action" class="form-horizontal" id="form1">
					
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
							<label class="control-label" for="CarNo">车牌号：</label> <input
								type="text" id="CarNo" name="CarNo" class="input-xlarge" /> <span
								id="CarNoTip" class="required_dis"></span>
						</div>

						<div class="control-group">
							<label class="control-label" for="CardType">发行类型：</label> <select
								type="text" id="CardType" name="CardType">
								<option value="临时车">临时车</option>
								<option value="月租车">月租车</option>
								<option value="储值车">储值车</option>
								<option value="贵宾车">贵宾车</option>

							</select>
						</div>

						<div class="control-group">
							<label class="control-label" for="CardIndate">截止有效期：</label> <input
								type="text" class="Wdate input-xlarge" id="CardIndate"
								name="CardIndate" onclick="WdatePicker()" value="${dateNow }"/> <span
								id="CardIndateTip" class="required_dis"></span>
						</div>
						<div class="control-group">
							<label class="control-label" for="CardAmount">车辆余额：</label> <input
								type="text" class="input-xlarge" id="CardAmount"
								name="CardAmount" /> <span id="CardAmountTip"
								class="required_dis"></span>
						</div>

						<div class="control-group">
							<label class="control-label" for="CarType">车辆类型：</label> <select
								type="text" class="input-xlarge" id="CarType"
								name="CarType" />
								
								<%if(resultMap!=null){
									%>
									<option value="<%=resultMap.get("Car1TypeName")%>"><%=resultMap.get("Car1TypeName")%></option>
									<option value="<%=resultMap.get("Car2TypeName")%>"><%=resultMap.get("Car2TypeName")%></option>
									<option value="<%=resultMap.get("Car3TypeName")%>"><%=resultMap.get("Car3TypeName")%></option>
									<option value="<%=resultMap.get("Car4TypeName")%>"><%=resultMap.get("Car4TypeName")%></option>
								<%}else{ %>
									<option value="蓝牌车">蓝牌车</option>
									<option value="渣土车">渣土车</option>
									<option value="非渣车">非渣车</option>
									<option value="内部车">内部车</option>
								<%} %>
							</select>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="MasterName">车主姓名：</label> <input
								type="text" class=" input-xlarge" id="MasterName"
								name="MasterName" />

						</div>
						
					   <div class="control-group">
							<label class="control-label" for="MasterTel">联系电话：</label> <input
								type="text" class="input-xlarge" id="MasterTel"
								name="MasterTel" />
						</div>
						
					    <div class="control-group">
							<label class="control-label" for="MasterAddr">联系地址：</label> <input
								type="text" class="input-xlarge" id="MasterAddr"
								name="MasterAddr" />
						</div>
						
						 <div class="control-group">
							<label class="control-label" for="PayAmount">付款金额：</label> <input
								type="text" class="input-xlarge" id="PayAmount"
								name="PayAmount" />
						</div>
						
						
					   <div class="control-group">
							<label class="control-label" for="ParkPosition">所属车位：</label>
								<textarea name="ParkPosition" id="ParkPosition" class="text3"
								cols="30" rows="2"></textarea>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="touristTime">备注：</label>
							<textarea name="Remark" id="Remark" class="text3"
								cols="30" rows="2"></textarea>
						</div>


				  <div class="control-group" style="padding-left:70px;">
						<button type="button" class="btn btn-primary" style="width:100px;" id="addBtn">添&nbsp;&nbsp;加</button>
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




$("#CarNo").formValidator({onShow:"",onFocus:"",onCorrect:""}).inputValidator({min:6,onError:"请填写车牌号"}).regexValidator({regExp:"^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$",onError:"车牌号格式不正确"});

$("#MasterName").formValidator({onShow:"",onFocus:"",onCorrect:""}).inputValidator({min:2,max:20,onError:"请填写车主姓名"});



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
	url : "AddPost.action?M=" + Math.random(),
	data : "CarNo=" + $("#CarNo").val().toString() + "&CardType="
			+ $("#CardType").val().toString()+ "&CardIndate="
			+ $("#CardIndate").val().toString()+ "&CardAmount="
			+ $("#CardAmount").val().toString()+ "&CarType="
			+ $("#CarType").val().toString()+ "&MasterName="
			+ $("#MasterName").val().toString()+ "&MasterTel="
			+ $("#MasterTel").val().toString()+ "&MasterAddr="
			+ $("#MasterAddr").val().toString()+ "&ParkPosition="
			+ $("#ParkPosition").val().toString()+ "&PayAmount="
			+ $("#PayAmount").val().toString()+ "&Remark="
			+ $("#Remark").val().toString()+"&parkNos="+ parkNos,
			
		dataType:"json",
	success : function(data) {
	
    	var d = eval("("+data+")");
    	
    	var loginResult = d.resultCode;
    	
    	var errInfo = d.message;
    	    	
    	if (loginResult == "0") {
    	
    		alert("白名单添加成功!");
    		
    		return true;
    	
    	}
    	
    	if (loginResult != "0") {
    		
    	
    		alert("白名单添加失败，请重新再试！!"+errInfo);
    		
			return false;

		}

	}
});
}
</script>

