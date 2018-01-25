<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.wy.dao.PageInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/userCenter/admin/html_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>

<%
	String pageURI = "ListGet.action";

    List<Map<String, Object>> list = (List<Map<String, Object>>) request.getAttribute("list");
    
    String parkNos =(String)request.getAttribute("parkNos");
    
    String parkCode=(String)request.getAttribute("parkCode");
    
    List<Map<String, Object>> levelList = (List<Map<String, Object>>) request.getAttribute("levelList");
%>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">车场支付宝绑定信息列表</a>
			</li>
			
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i> 在线使用车场列表
					
					<%if("1".equalsIgnoreCase(parkCode)){ %>
					<div style="float:right;">
					<a class="btn btn-primary" href="AddGet.action" >新增车场</a>
				</div>
				<%} %>
				</h2>

			</div>
			<div class="box-content">

				<form method="get" action="<%=pageURI%>" class="form-horizontal">				
							车场查询： <input type="text" name="searchValue" value="${searchValue}"
								class="input-xlarge">
							<button type="submit" class="btn btn-primary">查询</button>
						</legend>
					</fieldset>
				</form>
			</div>
				<div class="row-fluid sortable">
					<div class="row-fluid sortable">
						<div class="tab-content default-tab" id="tab1">

							<table width="100%" cellpadding="8" border="solid 1px #ccc"
								class="imagetable tableNo">
								<thead>
									<tr style="color: black;">
										<th>车场号码</th>
										<th>车场名称</th>
										<th>车场单位名称</th>
										<th>是否包含多个车场</th>
										<th>创建时间</th>
										<th>备注</th>
										<th>操 作</th>
									</tr>
								</thead>
								<tbody>
								<%
										PageInfo listPageInfo = (PageInfo) request.getAttribute("pageInfo");
								
								     if(listPageInfo!=null){

										int count = (listPageInfo.getPageNum() - 1)
												* listPageInfo.getPageSize();

										List<Map<String, Object>> objectList = (List<Map<String, Object>>) listPageInfo
												.getItems();

										if (objectList != null && !objectList.isEmpty()) {
											for (Map<String, Object> map : objectList) {

												count++;
									%>
					        	  <tr>
					        	  <td><a href="<%=head_path%>/action/web/frontPage/passport/LoginGet.action" style="color:black"/><%=map.get("pcode")%></a></td>
					        	  <td><a href="<%=head_path%>/action/web/frontPage/passport/LoginGet.action" style="color:black"/><%=map.get("username") %></a></td>
					        	  <td><%=map.get("pname") %></td>
					        	   <td>
					        	   <%if(map.get("level_status")!=null){
					        		   if("1".equalsIgnoreCase(map.get("level_status").toString())){
					        			   %>
					        			   包含多个车场
					        		  <% }if("0".equalsIgnoreCase(map.get("level_status").toString())){ %>
					        			  暂无其他车场
					        		<%  }
					        	   } %>
					        	</td>
					        	  <td><%=map.get("create_time") %></td>
					        	  <td>
					        	  <%if(map.get("descriptions")==null){%>  
					        		  暂无描述
					        		 <%}else{ %>
					        	  <%=map.get("descriptions") %>
					        	  <%} %></td>
					        	  <td>
					        	   <%if(map.get("level_status")!=null){
					        		   if("1".equalsIgnoreCase(map.get("level_status").toString())){%>
					        			    <a href="OtherListGet.action?pid=<%=map.get("pid") %>" style="color: #000;">其他车车</a>&nbsp;&nbsp;
					        		  
					        	  <%  }}%>
					        	  <a href="ModifyGet.action?pid=<%=map.get("pid") %>" style="color: #000;">修改支付宝信息</a>&nbsp;&nbsp;					        	  
					        	  </td>
					        	  </tr>

					        <% }}%>
							        	  <tfoot>
											<tr>
												<td colspan="7"><%@ include
														file="/include/web/userCenter/admin/pageInfo.inc.jsp"%>
												</td>
											</tr>
										</tfoot>
							<%}else
					        {
					        	%>
					        	<tr><td colspan="7">暂无数据</td></tr>
					        <%  }%>
								</tbody>
								
							</table>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>
<%@ include file="/include/web/userCenter/admin/foot.inc.jsp"%>

