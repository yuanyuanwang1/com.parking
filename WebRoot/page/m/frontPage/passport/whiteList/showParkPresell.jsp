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

<script src="<%=head_path%>/style/whiteList/js/jquery-1.7.2.min.js"
	type="text/javascript"></script>

<script type="text/javascript" src="<%=head_path%>/script/DatePicker/WdatePicker.js"></script>
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

List<Map<String, Object>> list = (List<Map<String, Object>>) request
.getAttribute("list");
%>

</head>
<body id="body" style="background: white;">

	<div class="fullscreen per_login" style="margin-top: 12px;">
		<div class="alert"></div>

		<span style="color:#289ED7;font-size: 20px">下发预约</span>

		<section class="inner_content" style="padding-top: 10px;">

			<form id="form1" name="form1" method="post">
				<div class="join">
				
				   <%if(list!=null && list.size()>0)
						{
						%>
						<div class="piaochecked on_check">
						<label class="control-label" for="CarNo">下发到停车场：</label>
						<% for(Map<String,Object> map:list){%>
						 
						   <input name="parkNos" type="checkbox" style="height:20px;width:20px;" class="radioclass input" value="<%=map.get("parkCode") %>">
                 <span style="vertical-align:top;"><%=map.get("parkCode") %><%=map.get("parkName") %></span>
									
					 <%}%> 
						</div>
						<%}%>
				
					<label>车牌号</label>

					<div>

						<input id="CarNo" name="CarNo" placeholder="请输入车牌号" type="text"
							value="" class="timeSel" style="vertical-align: top;" />
					</div>
					<label>发行类型</label>
					<div>
						<select type="text" id="CardType" name="CardType">
							<option value="临时车">临时车</option>
							<option value="月租车">月租车</option>
							<option value="储值车">储值车</option>
							<option value="贵宾车">贵宾车</option>

						</select>
					</div>

					<label>截止有效期</label>

					<div>						
					<input id="CardIndate" name="CardIndate" type="date"
							value="${dateNow }" class="timeSel"
							style="vertical-align: top;" />
					</div>

					<label>车辆类型</label>
					<div>
						<select type="text" id="CarType" name="CarType">
							<option value="1型车">1型车</option>
							<option value="2型车">2型车</option>
							<option value="3型车">3型车</option>
							<option value="4型车">4型车</option>

						</select>
					</div>
					<label>车主姓名</label>
					<div>
						<input id="MasterName" name="MasterName" placeholder="请输入车主姓名"
							type="text" value="" class="timeSel" style="vertical-align: top;" />
					</div>
					<label>联系地址</label>
					<div>
						<input id="MasterAddr" name="MasterAddr" placeholder="请输入联系地址"
							type="text" value="" class="timeSel" style="vertical-align: top;" />
					</div>
					<br />
					<button type="button" id="addBtn">下 发</button>
					<br /> <br />
					<button type="button" id="login_btn" onclick="history.go(-1)">返
						回</button>
			</form>
		</section>

	</div>
	<script>
		$("#addBtn").click(function() {

			add();

		});

		function add() {

			if ($("#CarNo").val() == "") {

				alert("请输入车牌号");

				return false;
			}

			$.ajax({
				type : "POST",
				url : "ParkPresellPost.action?M=" + Math.random(),
				data : $("#form1").serialize(),
				dataType : "json",
				success : function(data) {

					var d = eval("(" + data + ")");

					var loginResult = d.resultCode;

					var errInfo = d.message;

					if (loginResult == "0") {

						alert("下发预约成功!");

						return true;

					}

					if (loginResult != "0") {

						alert("下发预约失败，请重新再试！!" + errInfo);

						return false;

					}

				}
			});
		}
	</script>

</body>
</html>