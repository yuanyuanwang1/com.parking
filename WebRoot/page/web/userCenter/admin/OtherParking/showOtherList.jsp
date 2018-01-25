<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.wy.dao.PageInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<%@ include file="/include/web/userCenter/admin/html_head.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/top.inc.jsp"%>

<%@ include file="/include/web/userCenter/admin/menu_left.inc.jsp"%>

<%
	String pageURI = "OtherListGet.action";
%>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">${commonUser.pname }的其他车场信息列表</a>
			</li>
			
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i> 在线使用车场列表
					<div style="float:right;">
					<a class="btn btn-primary" href="OtherAddGet.action?pid=${commonUser.pid }" >新增其他车场</a>
				</div>
				</h2>

			</div>
			<div class="box-content">

				<form method="get" action="<%=pageURI%>" class="form-horizontal">	
				<input type="hidden" name="pid" value="${ commonUser.pid}"/>
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
					        	  <td><%=map.get("pcode")%></td>
					        	  <td><%=map.get("username") %></td>
					        	  <td><%=map.get("pname") %></td>
					        	  <td><%=map.get("create_time") %></td>
					        	  <td><%if(map.get("descriptions")==null){%>  
					        		  暂无描述
					        		 <%}else{ %>
					        	  <%=map.get("descriptions") %>
					        	  <%} %></td>
					        	  <td>
					        	  <a href="OtherModifyGet.action?pid=<%=map.get("pid") %>&id=${commonUser.pid }" style="color: #000;">修改</a>&nbsp;&nbsp;
					        	  <a href="OtherDeletePost.action?pid=<%=map.get("pid") %>&id=${commonUser.pid }" style="color: #000;">删除</a>&nbsp;&nbsp;
					        	  
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

