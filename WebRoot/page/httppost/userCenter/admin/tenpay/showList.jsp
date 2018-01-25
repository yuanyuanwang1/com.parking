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

	List<Map<String, Object>> list = (List<Map<String, Object>>) request
			.getAttribute("list");

	String parkNos = (String) request.getAttribute("parkNos");

	List<Map<String, Object>> levelList = (List<Map<String, Object>>) request
			.getAttribute("levelList");
%>

<div id="content" class="span10">
	<!-- content starts -->
	<div>
		<ul class="breadcrumb">
			<li><a href="#">车场微信信息列表</a></li>

		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="breadcrumb wells" style="height:40px;">
				<h2>
					<i class="icon-edit"></i> 在线使用车场微信列表
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
									<th>合作者ID(AppId)</th>
									<th>appSerect</th>
									<th>bankType</th>
									<th>feeType</th>
									<th>getCodeRequest</th>
									<th>签名类型(inputCharset)</th>
									<th>MCH_ID</th>
									<th>异步通知地址notifyUrl</th>
									<th>partner</th>
									<th>partnerKey</th>
									<th>scope</th>
									<th>redirect_url</th>
									<th>signType</th>
								</tr>
							</thead>
							<tbody>
								<%
									PageInfo listPageInfo = (PageInfo) request.getAttribute("pageInfo");

									if (listPageInfo != null) {

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
									<td><%=map.get("username")%></td>
									<td><%=map.get("pname")%></td>
									<td><%=map.get("appId")%></td>
									<td><%=map.get("appSerect")%></td>
									<td><%=map.get("bankType")%></td>
									<td><%=map.get("feeType")%></td>
									<td><%=map.get("getCodeRequest")%></td>
									<td><%=map.get("inputCharset")%></td>
									<td><%=map.get("MCH_ID")%></td>
									<td><%=map.get("notifyUrl")%></td>
									<td><%=map.get("partner")%></td>
									<td><%=map.get("partnerKey")%></td>
									<td><%=map.get("scope")%></td>
									<td><%=map.get("redirect_url")%></td>
									<td><%=map.get("signType")%></td>
									
								</tr>

								<%
									}
										}
								%>
							
							<tfoot>
								<tr>
									<td colspan="17"><%@ include
											file="/include/web/httppost/pageInfo.inc.jsp"%>
									</td>
								</tr>
							</tfoot>
							<%
								} else {
							%>
							<tr>
								<td colspan="17">暂无数据</td>
							</tr>
							<%
								}
							%>
							</tbody>

						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/include/web/httppost/foot.inc.jsp"%>

