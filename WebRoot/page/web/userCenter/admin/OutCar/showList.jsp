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
			<li><a href="#">出场车辆查询</a>
			</li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i> 出场车辆列表
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
							</legend>
					</fieldset>
					<fieldset>
						<legend>
							车牌号：<input type="text" value="${CarNo}"  name="CarNo" id="CarNo">
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
									<th>编号</th>
									<th>停车场编号</th>
										<th>车牌号码</th>
										<th>车牌类型</th>
										<th>车辆类型</th>
										<th>入场时间</th>
										<th>入场方式</th>
										<th>出场时车道</th>
										<th>出场时间</th>
										<th>出场操作员姓名</th>
										<th>出厂方式</th>
										
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
					        	  <td><%=map.get("RecordNo") %></td>
					        	  <td><%=map.get("ParkNo")%></td>
					        	  <td><%=map.get("CarNo") %></td>
					        	  <td><%=map.get("CardType") %></td>
					        	  <td><%=map.get("CarType") %></td>
					        	   <td><%=map.get("InDateTime") %></td>
					        	   <td><%=map.get("InStyle") %></td>
					        	   <td><%=map.get("OutTrackName") %></td>
					        	   <td><%=map.get("OutDateTime") %></td>
					        	   <td><%=map.get("OutOperatorName") %></td>
					        	   <td><%=map.get("OutStyle") %></td>
					        	  </tr>
					        <% }}%><tfoot>
										<tr>
											<td colspan="11"><%@ include
													file="/include/web/userCenter/admin/pageInfo.inc.jsp"%>
											</td>
										</tr>
									</tfoot>
						   <%}else
					        {
					        	%>
					        	<tr><td colspan="11">暂无数据</td></tr>
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

