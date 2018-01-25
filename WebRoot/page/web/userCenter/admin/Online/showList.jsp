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
   
%>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">车场信息</a>
			</li>
			
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i> 车场信息列表
				</h2>

			</div>
			<div class="box-content">
				
			</div>
				<div class="row-fluid sortable">
					<div class="row-fluid sortable">
						<div class="tab-content default-tab" id="tab1">

							<table width="100%" cellpadding="8" border="solid 1px #ccc"
								class="imagetable tableNo">
								<thead>
									<tr style="color: black;">
										<th>车场编号</th>
										<th>车场名称</th>
										<th>登录时间</th>
										<th>转发器启动</th>
										<th>转发器连接次数</th>
										<th>转发器版本</th>
										<th>请求(成功/总数)</th>
										<th>错误信息</th>
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
					        	  <td><%=map.get("parkCode")%></td>
					        	  <td><%=map.get("parkName") %></td>
					        	  <td><%=map.get("WorkerStartTime") %></td>
					        	    <td><%=map.get("ConnectTime") %></td>
					        	   <td><%=map.get("WorkerConnectTimes") %></td>
					        	      <td><%=map.get("WorkerVersion") %></td>
					        	  <td><%=map.get("RequestCounter") %>/<%=map.get("RequestCounter") %></td>
					        	   <td><%=map.get("LastRequestFailMessage") %></td>
					        	  </tr>

					        <% }}%>
							        	  <tfoot>
											<tr>
												<td colspan="8"><%@ include
														file="/include/web/userCenter/admin/pageInfo.inc.jsp"%>
												</td>
											</tr>
										</tfoot>
							<%}else
					        {
					        	%>
					        	<tr><td colspan="8">暂无数据</td></tr>
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

