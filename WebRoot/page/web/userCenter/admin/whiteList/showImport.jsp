<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/userCenter/admin/html_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>

<%List<Map<String,Object>> levelList=(List<Map<String,Object>>)request.getAttribute("levelList"); %>


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
			<a class="btn btn-success" href="<%=head_path %>/down/template.xls" style="float: right;"/>下载模板</a>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header wells well-list">
				<h2>
					<i class="icon-edit"></i> 批量导入发行车辆
				</h2>
			</div>
       
			<div class="box-content">
				<div class="box-content">
					<form method="post" action="ImportPost.action" class="form-horizontal" id="form1" enctype="multipart/form-data">
					
					<%if(levelList!=null && levelList.size()>0)
						{
						%>
						<div class="control-group">
						<label class="control-label" for="CarNo" > <span style="vertical-align:middle;" >下发到停车场：</span></label>
						<% for(Map<String,Object> map:levelList){%>
						 
						 <input type="checkbox" style="vertical-align:middle;" name="parkNos" value="<%=map.get("pcode") %>">
						 <span style="vertical-align:middle;" ><%=map.get("pname") %></span>
									
					 <%}%> 
						</div>
						<%}%>
					
					
					
						<div class="control-group">
							<label class="control-label" for="file">选择文件：</label> <input
								type="file" id="file" name="file" class="input-xlarge" accept="application/vnd.ms-excel"/> <span
								id="fileTip" class="required_dis"></span>
						</div>

                     <div id="errorBlock" class="help-block" data-spy="scroll" style="padding-left: 30px;color:red;"></div>
                     <br/>

				  <div class="control-group" style="padding-left:70px;">
						<button type="button" class="btn btn-primary" style="width:100px;" id="addBtn" onclick="impok();">上&nbsp;&nbsp;传</button>
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
function impok(){
 
	var attachmentFileName=document.getElementById("file");
	if(attachmentFileName==null)
	{
		$("#errorBlock").html("请选择上传文件");
		return;
	}
	 var formData = new FormData($( "#form1" )[0]);  
	//上传
	$.ajax({ 
		 type: 'POST',  
         data: formData,  
         async: false,  
         cache: false,  
         contentType: false,  
         processData: false,              
         url: "ImportPost.action",//请求的action路径  
        error: function () {//请求失败处理函数  
     	   alert("上传失败!");  
     	   return false;  
        },  
        success : function(data) { //请求成功后处理函数。    
			var errorInfo="";
			var row=0;
			var dataObj = eval("(" + data + ")");//转换为json对象        
			if(dataObj.appcode=="-1"){
			$.each(dataObj.list, function(i,item){
			    row=item.rownum+1;
				errorInfo+="Excel中第"+row+"行"+item.errmsg+"<br/>";
			});
			$("#errorBlock").html(errorInfo);
			}else if(dataObj.appcode=="0"){
				$("#errorBlock").html(dataObj.errmsg);
			}else if(dataObj.appcode=="1")
			{
				if(dataObj.levelCode=="0")
				{
					
					alert("上传成功");
					
				}
				else if(dataObj.levelCode!="0"){
			
				alert(dataObj.levelPcode1+"的上传失败   原因："+dataObj.levelMsg1);	
				
				}
			}
		}
});

}
</script>
<%@ include file="/include/web/userCenter/admin/foot.inc.jsp"%>
