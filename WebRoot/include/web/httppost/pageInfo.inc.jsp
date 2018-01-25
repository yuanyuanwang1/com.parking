<%@ page contentType="text/html;charset=utf-8"%>
<%
	String queryString = request.getQueryString();

	String match = "pageNum=";

	queryString = com.wy.dao.PageInfo.changeQueryString(
			queryString, match);

	if (queryString == null || "".equals(queryString.trim())) {

		pageURI = pageURI + "?pageNum=";

	} else {

		pageURI = pageURI + "?" + queryString + "&pageNum=";

	}

	com.wy.dao.PageInfo pageInfo = (com.wy.dao.PageInfo) request
			.getAttribute("pageInfo");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<div id="pageInfo" class="minLineHeight">
	<span
		style="<%if (pageInfo.getPageNum() <= 1) {

				out.print("display:none");

			}%>">
		<a style="color: black;" href="<%=pageURI%>1" target="_self">首页</a>
	</span>

	<span
		style="<%if (pageInfo.getPageNum() <= 1) {

				out.print("display:none");

			}%>">
		<a style="color: black;" href="<%=pageURI%><%=pageInfo.getPrePageNum()%>" target="_self">上一页</a>
	</span>

	<span>
		第<%=pageInfo.getPageNum()%>页共<%=pageInfo.getTotalPages()%>页
	</span>

	<span
		style="<%if (pageInfo.getPageNum() == pageInfo.getLastPageNum()) {

				out.print("display:none");

			}%>">
		<a style="color: black;" href="<%=pageURI%><%=pageInfo.getNextPageNum()%>" target="_self">下一页</a>
	</span>

	<span
		style="<%if (pageInfo.getPageNum() == pageInfo.getLastPageNum()) {

				out.print("display:none");

			}%>">
		<a style="color: black;" href="<%=pageURI%><%=pageInfo.getLastPageNum()%>" target="_self">末页</a>
	</span>
</div>
