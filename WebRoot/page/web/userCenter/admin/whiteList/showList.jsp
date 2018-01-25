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
    
    List<Map<String, Object>> levelList = (List<Map<String, Object>>) request.getAttribute("levelList");
%>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">发行车辆信息</a>
			</li>
			
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i> 发行车辆列表
					<div style="float:right;">
					<a class="btn btn-success" href="ImportGet.action" style="width:50px;">导入</a>
					<a class="btn btn-primary" href="AddGet.action" style="width:50px;">新增</a>
				</div>
				</h2>

			</div>
			<div class="box-content">
				<form method="get" action="<%=pageURI%>" class="form-horizontal">
					<fieldset>
						<legend>
							<%if(levelList!=null && levelList.size()>0)
							{
							 %> 停车场：<select class="jqSel" name="parkNos" id="parkNos" style="vertical-align: middle;">
							
								 <c:forEach items="${levelList}" var="item">
							 <option <c:if test="${parkNos==item.pcode}">selected="selected"</c:if> value="${item.pcode}">${item.pname }</option>
	                         </c:forEach>
							</select>
						<%}%>
							
							车牌号： <input type="text" name="CarNo" value="${CarNo}"
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
										<th>车牌号码</th>
										<th>车牌类型</th>
										<th>截止有效期</th>
										<th>车辆余额</th>
										<th>车辆类型</th>
										<th>车主姓名</th>
										<th>联系电话</th>
										<th>联系地址</th>
										<th>所属车位</th>
										<th>付款金额</th>
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
					        	  <td><%=map.get("CarNo")%></td>
					        	  <td><%=map.get("CardType") %></td>
					        	  <td><%=map.get("CardIndate") %></td>
					        	   <td><%=map.get("CardAmount") %></td>
					        	  <td><%=map.get("CarType") %></td>
					        	  <td><%=map.get("MasterName") %></td>
					        	   <td><%=map.get("MasterTel") %></td>
					        	   <td><%=map.get("MasterAddr") %></td>
					        	   <td><%=map.get("ParkPosition") %></td>
					        	   <td><%=map.get("PayAmount") %></td>
					        	  <td><a href="DeletePost.action?CarNo=<%=map.get("CarNo")%>&parkCode=${parkNos}" style="color: #000;">删除</a></td>
					        	  </tr>

					        <% }}%>
							        	  <tfoot>
											<tr>
												<td colspan="13"><%@ include
														file="/include/web/userCenter/admin/pageInfo.inc.jsp"%>
												</td>
											</tr>
										</tfoot>
							<%}else
					        {
					        	%>
					        	<tr><td colspan="13">暂无数据</td></tr>
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

