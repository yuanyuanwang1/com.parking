<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.wy.dao.PageInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/userCenter/admin/html_flow_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>


<%
	String pageURI = "ListGet.action";

    List<Map<String, Object>> list = (List<Map<String, Object>>) request.getAttribute("list");
    
    Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");
    
    String parkNos =(String)request.getAttribute("parkNos");
    
    List<Map<String, Object>> levelList = (List<Map<String, Object>>) request.getAttribute("levelList");
%>

<input type="hidden" id="DateString" name="DateString" id="DateString" value="${resultMap.DateString}"/>
<input type="hidden" id="HourString" name="HourString" id="HourString" value="${resultMap.HourString}"/>
<input type="hidden" id="tCardCar1Num" name="tCardCar1Num" id="tCardCar1Num" value="${resultMap.tCardCar1Num}"/>
<input type="hidden" id="mCardCar1Num" name="mCardCar1Num" id="mCardCar1Num" value="${resultMap.mCardCar1Num}"/>
<input type="hidden" id="vCardCar1Num" name="vCardCar1Num" id="vCardCar1Num" value="${resultMap.vCardCar1Num}"/>
<input type="hidden" id="nCardCar1Num" name="nCardCar1Num" id="nCardCar1Num" value="${resultMap.nCardCar1Num}"/>
<input type="hidden" id="ManNum" name="ManNum" id="ManNum" value="${resultMap.ManNum}"/>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">车流量统计</a>
			</li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i>车流量统计
				</h2>
			</div>
			<div class="box-content">
				<form method="get" action="<%=pageURI%>" class="form-horizontal">
					<fieldset>
						<legend>
								查询： <span class="basefont">起始日期：</span><input type="text"
									name="FromTime" class="Wdate input-medium"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${FromTime}" />&nbsp; <span
									class="basefont">终止日期：</span><input type="text" name="ToTime"
									class="Wdate input-medium" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									value="${ToTime}" />
									
							<%if(levelList!=null && levelList.size()>0)
							{
							 %> &nbsp;停车场：<select class="jqSel" name="parkNos" id="parkNos" style="vertical-align: middle;">
							 <% for(Map<String,Object> mapSelect:levelList){%>
							 		String pcode=<%=mapSelect.get("pcode")%>;
								    <option <c:if test="${parkNos==pcode}">selected="selected"</c:if> value="<%=mapSelect.get("pcode") %>"><%=mapSelect.get("pname") %></option>
						 <%}%> 
							</select>
						<%}%>
						
								<button type="submit" class="btn btn-primary">查询</button>
	</legend>
					</fieldset>
				</form>
			</div>
				<div class="row-fluid sortable">
					<div class="row-fluid sortable">
						<div class="tab-content default-tab" id="tab1">
                         <div id="show"></div>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>
<script>

var bar = function (id,title,data){
	//展示的id
	this.id = '';

	//标题
	this.title = '';

	//数据
	this.data = '';

	//宽
	this.width = 800;
	
	//背景图片位置
	this.bgimg = '<%=head_path%>/img/plan.gif';
	
	//动画速度
	this.speed = 1000;

	//投票总数
	var num_all = 0;
	this.show = function (){
		//添加一个table对象
		$("#"+this.id).append("<table width='"+this.width+"' cellpadding=0 cellspacing=20 border=0 style='font-size:12px;' ></table>")

		$("#"+this.id+" table").append("<tr><td colspan=3 align='center' ><span style='font-size:18px ;color:#444'>"+this.title+"</span></td></tr>")

		//计算总数
		$(this.data).each(function(i,n){
			num_all += parseInt(n[1]);
		})

		var that = this;

		//起始
		var s_img = [0,-52,-104,-156,-208,-260,-0,-52];
		//中间点起始坐标
		var c_img = [-312,-339,-367,-395,-420,-447,-312,-339];
		//结束
		var e_img = [-26,-78,-130,-182,-234,-286,-26,-78];
		var that = this;
		var div;
		$(this.data).each(function(i,n){
			
			//计算比例
			var bili = (n[1]*100/num_all).toFixed(2);
			
			//计算图片长度, *0.96是为了给前后图片留空间
			var img = parseFloat(bili)*0.96;
	
			if(img>0)
			{
				div = "<div style='width:3px;height:16px;background:url("+that.bgimg+") 0px "+s_img[i]+"px ;float:left;'></div><div fag='"+img+"' style='width:0%;height:16px;background:url("+that.bgimg+") 0 "+c_img[i]+"px repeat-x ;float:left;'></div><div style='width:3px;height:16px;background:url("+that.bgimg+") 0px "+e_img[i]+"px ;float:left;'></div>";
			}
			else
			{
				div='';
			}
			$("#"+that.id+" table").append("<tr><td width='30%' align='right' >"+n[0]+"：</td><td width='60%' bgcolor='#fffae2' >"+div+"</td><td width='10%' nowrap >"+n[1]+"("+bili+"%)</td></tr>")
		})
		
		this.play();
	}

	this.play = function (){
		var that = this;		
		$("#"+this.id+" div").each(function(i,n){
			if($(n).attr('fag'))
			{
				$(n).animate( { width: $(n).attr('fag')+'%'}, that.speed )
			}
		})
	}
}
</script>
<script>
var data = [];

var value2 = $('#tCardCar1Num').val();
var value3 = $('#mCardCar1Num').val();
var value4 = $('#vCardCar1Num').val();
var value5 = $('#nCardCar1Num').val();
var value6 = $('#ManNum').val();

if (value2.length > 0) {

    data[0] = ["临时车", value2];
}
if (value3.length > 0) {

    data[1] = ["月租车", value3];
}
if (value4.length > 0) {

    data[2] = ["储值车", value4];
}
if (value5.length > 0) {

    data[3] = ["贵宾车", value5];
}
if (value6.length > 0) {

    data[4] = ["人工开闸", value6];
}

var title = '车流量统计';
var bar1 = new bar();
bar1.id = 'show';
bar1.title = title;
bar1.data = data;
bar1.show();
</script>

<%@ include file="/include/web/userCenter/admin/foot.inc.jsp"%>

