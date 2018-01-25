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

    List<Map<String, Object>> levelList = (List<Map<String, Object>>) request.getAttribute("levelList");
    
    String parkId=(String)request.getAttribute("parkId");
   
%>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">车场线上订单信息</a>
			</li>
			
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i> 车场线上订单信息列表
				</h2>

			</div>
			<div class="box-content">
				<form method="get" action="<%=pageURI%>" class="form-horizontal">
					<fieldset>
						<legend>
								查询： <span class="basefont">起始日期：</span><input type="text"
									name="startDate" class="Wdate input-medium"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${startDate}" />&nbsp; <span
									class="basefont">终止日期：</span><input type="text" name="endDate"
									class="Wdate input-medium" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									value="${endDate}" />
							<%if(levelList!=null && levelList.size()>0)
							{
							 %> &nbsp;停车场：<select class="jqSel" name="parkNos" id="parkNos" style="vertical-align: middle;">
							 <% for(Map<String,Object> mapSelect:levelList){%>
							 		String parkId=<%=mapSelect.get("pcode")%>;
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
										<th>车场编号</th>
										<th>车场名称</th>
										<th>订单编号</th>
										<th>停车费用</th>
										<th>车牌号</th>
										<th>订单状态</th>
										<th>付款方式</th>
										<th>付款时间</th>
										<th>入场时间</th>
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
					        	  <td><%=map.get("parkNo")%></td>
					        	  <td><%=map.get("pname") %></td>
					        	  <td><%=map.get("order_Code") %></td>
					        	  <td><%=map.get("CarFee") %></td>
					        	  <td><%=map.get("CarNo") %></td>
					        	   <td>
					        	   <%if("unpay".equalsIgnoreCase(map.get("order_status").toString()))
					        		   {
					        		   %>未付款
					        		   <%}if("payed".equalsIgnoreCase(map.get("order_status").toString())){
					        			   %>已支付
						        	 <%   
					        		   }if("exchange".equalsIgnoreCase(map.get("order_status").toString())){
					        			   %>已出场
								        	 <%      
					        		   }%></td>
					        	      <td>
					        	      <%if("tenpay".equalsIgnoreCase(map.get("pay_type").toString())){
					        	    	  
					        	    	  %>微信支付
					        	     <%  }if("alipay".equalsIgnoreCase(map.get("pay_type").toString())){
					        	    	  %>支付宝支付
							        	     <%  
					        	     } %></td>
					        	  <td><%=map.get("pay_time") %></td>
					        	   <td><%=map.get("InDateTime") %></td>
					        	  </tr>

					        <% }}%>
							        	  <tfoot>
											<tr>
												<td colspan="9"><%@ include
														file="/include/web/userCenter/admin/pageInfo.inc.jsp"%>
												</td>
											</tr>
										</tfoot>
							<%}else
					        {
					        	%>
					        	<tr><td colspan="9">暂无数据</td></tr>
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

