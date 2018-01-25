<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.wy.dao.PageInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file="/include/web/httppost/html_flow_head.inc.jsp"%>

<%@ include file="/include/web/httppost/top.inc.jsp"%>

<%@ include file="/include/web/httppost/menu_left.inc.jsp"%>

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
			<li><a href="#">车场支付宝信息列表</a>
			</li>
			
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i> 在线使用车场支付宝列表
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
										<th>支付宝账号</th>
										<th>合作者ID</th>
										<th>商户公钥</th>
										<th>支付成功返回地址</th>
										<th>异步通知地址</th>
										<th>操作中断返回地址</th>
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
					        	   <td>
					        	 <%=map.get("PAY_ID") %>
					        	</td>
					        	  <td><%=map.get("ALIPAY_PARTNER") %></td>
					        	   <td><%=map.get("ALIPAY_key") %></td>
					        	  <td><%=map.get("call_Back_Url") %></td>
					        	  <td><%=map.get("notify_Url") %></td>
					              <td><%=map.get("merchant_Url") %></td>
					              
					        	 
					        	  </tr>

					        <% }}%>
							        	  <tfoot>
											<tr>
												<td colspan="10"><%@ include
														file="/include/web/httppost/pageInfo.inc.jsp"%>
												</td>
											</tr>
										</tfoot>
							<%}else
					        {
					        	%>
					        	<tr><td colspan="10">暂无数据</td></tr>
					        <%  }%>
								</tbody>
								
							</table>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>

<%@ include file="/include/web/httppost/foot.inc.jsp"%>

