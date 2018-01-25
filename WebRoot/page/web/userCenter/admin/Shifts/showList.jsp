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
			<li><a href="#">交接班记录查询</a>
			</li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i>交接班记录列表
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
							 %> 停车场：<select class="jqSel" name="parkNos" id="parkNos" style="vertical-align: middle;">
							
								 <c:forEach items="${levelList}" var="item">
							 <option <c:if test="${parkNos==item.pcode}">selected="selected"</c:if> value="${item.pcode}">${item.pname }</option>
	                         </c:forEach>
							</select>
						<%}%>
							</legend>
					</fieldset>
					<fieldset>
						<legend>
							操作员姓名：<input type="text" value="${OperatorName}" name="OperatorName" id="OperatorName">
								<button type="submit" class="btn btn-primary" >查询</button>
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
										<th>上班时间</th>
										<th>下班时间</th>
										<th>上班操作员</th>
										<th>接班人员</th>
										<th>应收金额</th>
										<th>实收金额</th>
										<th>平台支付金额</th>
										<th>储值车扣费金额</th>
										<th>是否下班</th>
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
					        	  <td><%=map.get("InDateTime") %></td>
					        	  <td><%=map.get("OutDateTime") %></td>
					        	  <td><%=map.get("InOperatorName") %></td>
					        	   <td><%=map.get("OutOperatorName") %></td>
					        	   <td><%=map.get("CarFee") %></td>
					        	   <td><%=map.get("PayAmount") %></td>
					        	   <td><%=map.get("CardPayAmount") %></td>
					        	   <td><%=map.get("ValueAmount") %></td>
					        	    <td><%=map.get("OutFlag") %></td>
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

