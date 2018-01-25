<%@ page contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
	String left_path = request.getContextPath();

	
	String menuId = request.getParameter("menuId");
	
	String menuIds = request.getParameter("menuIds");
	
	if (StringUtils.isBlank(menuId)) {
		
	
		menuId = (String) session.getAttribute("menuId");
	
	}else
	{
		session.setAttribute("menuId",menuId);
	}
	
	if (StringUtils.isBlank(menuIds)) {
		
		menuIds = (String) session.getAttribute("menuIds");
	
	}else
	{
		session.setAttribute("menuIds",menuIds);
	}
	

	if (StringUtils.isBlank(menuId)) {
	
		menuId = "selfInfo";
	
	}
%>

<div style="margin-top: 5px;">
	<div class="span2">
		<div
			style="background: #221d23;margin-left: -20px;margin-top: -11px;height: 1000px;">
			<div class="well nav-collapse sidebar-nav" style="margin-left:10%;">
				<ul class="nav nav-tabs nav-stacked main-menu"
					style="height:800px;">
					<li class="nav-header-top"
						style="color: white;font-size:15px;margin-left: -2px;margin-top: -12px;"><br />
					<img src="<%=left_path%>/image/menu.png"
						style="width:20px;height:20px;">&nbsp;&nbsp;&nbsp;全部功能</li>
					<li class="nav-header-top"
						style="border-bottom: 1px solid #f5f5f5;"></li>
						
					<li id="1010" class="nav-header hidden-tablet menu_fuji" style="background-image:url(<%=left_path %>/img/left_0.png);" menuId="1010">&nbsp;&nbsp;&nbsp;车辆发行管理</li>
					
					<li class="menu_ziji" menuId="1010"><a
						menuId="11010" id="11010"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/whiteList/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;车辆发行</span>
					</a>
					</li>
					
					<li class="menu_ziji" menuId="1010"><a
						menuId="11030" id="11030"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/whiteList/OrderGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;车辆下发预约</span>
					</a>
					</li>
					
					<li class="menu_ziji" menuId="1010"><a
						menuId="11040" id="11040"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/whiteList/OrderLastGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;车辆延期</span>
					</a>
					</li>
					
					
										<li class="menu_ziji" menuId="1010"><a
						menuId="11020" id="11020"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/whiteList/OrderChargeGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;车辆充值</span>
					</a>
					</li>
					
						
					<li id="1020" class="nav-header hidden-tablet menu_fuji" menuId="1020" style="background-image:url(<%=left_path %>/img/left_1.png);">&nbsp;&nbsp;&nbsp;车辆查询管理</li>
					
					 
					<li class="menu_ziji" menuId="1020"><a
						menuId="21020" id="21020"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/InCar/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;在场车辆查询</span>
					</a>
					</li>
						<li class="menu_ziji" menuId="1020"><a
						menuId="31020" id="31020"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/OutCar/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;出场车辆查询</span>
					</a>
					</li>
					
					
				<li id="1040" class="nav-header hidden-tablet menu_fuji" style="background-image:url(<%=left_path %>/img/left_2.png);" menuId="1040">&nbsp;&nbsp;&nbsp;多车场管理</li>
					
					<li class="menu_ziji" menuId="1040"><a
						menuId="11040" id="11040"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/OtherParking/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;其他车场管理</span>
					</a>
					</li>
					
					<li id="1050" class="nav-header hidden-tablet menu_fuji" style="background-image:url(<%=left_path %>/img/left_3.png);" menuId="1050">&nbsp;&nbsp;&nbsp;在线车场列表</li>
					
					<li class="menu_ziji" menuId="1050"><a
						menuId="11050" id="11050"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/Online/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;在线车场</span>
					</a>
					</li>
					
					<li id="1030" class="nav-header hidden-tablet menu_fuji" style="background-image:url(<%=left_path %>/img/left_0.png);" menuId="1030">&nbsp;&nbsp;&nbsp;车流量统计</li>
					
					<li class="menu_ziji" menuId="1030"><a
						menuId="11030" id="11030"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/FlowRate/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;车流量统计</span>
					</a>
					</li>
					
				<li id="1070" class="nav-header hidden-tablet menu_fuji" style="background-image:url(<%=left_path %>/img/left_2.png);" menuId="1070">&nbsp;&nbsp;&nbsp;交接班记录</li>
					
					<li class="menu_ziji" menuId="1070"><a
						menuId="11070" id="11070"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/Shifts/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;交接班记录</span>
					</a>
					</li>

				<li id="1090" class="nav-header hidden-tablet menu_fuji" style="background-image:url(<%=left_path %>/img/left_4.png);" menuId="1090">&nbsp;&nbsp;&nbsp;收费记录</li>
					
					<li class="menu_ziji" menuId="1090"><a
						menuId="11090" id="11090"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/ChargeRecord/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;收费记录</span>
					</a>
					</li>
					
					<li id="1060" class="nav-header hidden-tablet menu_fuji" style="background-image:url(<%=left_path %>/img/left_1.png);" menuId="1060">&nbsp;&nbsp;&nbsp;支付信息管理</li>
					
					<li class="menu_ziji" menuId="1060"><a
						menuId="11060" id="11060"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/Payment/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;支付宝信息管理</span>
					</a>
					</li>
					<li class="menu_ziji" menuId="1060"><a
						menuId="21060" id="21060"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/Payment/ListTenpayGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;微信信息管理</span>
					</a>
					</li>
					
						<li class="menu_ziji" menuId="1060"><a
						menuId="31060" id="31060"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/Order/ListGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;线上订单信息</span>
					</a>
					</li>
						<li class="menu_ziji" menuId="1060"><a
						menuId="31060" id="31060"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/Payment/QrCodeGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;二维码规则查看</span>
					</a>
					</li>
					
					<li id="1080" class="nav-header hidden-tablet menu_fuji" menuId="1080" style="background-image:url(<%=left_path %>/img/left_2.png);">&nbsp;&nbsp;&nbsp;基础设置</li>
							<li class="menu_ziji" menuId="1080"><a
						menuId="11080" id="11080"
						class="ajax-link" hrefs="<%=left_path%>/action/web/userCenter/admin/selfInfo/ChangePassGet.action">
							<img src="<%=left_path%>/img/icon_tool.png" /><span class="hidden-tablet"
							style="line-height: 14px;display: inline-block;">&nbsp;&nbsp;修改密码</span>
					</a>
					</li>
			
					<label id="for-is-ajax" class="hidden-tablet" for="is-ajax"
						style="margin-top: 30px;"><a
						href="<%=left_path%>/action/web/userCenter/admin/LogoutGet.action"
						id="logout">退出</a> </label>
				</ul>

			</div>

		<script type="text/javascript">
		$(function() {
			
			$(".menu_fuji").click(function() {
				var menuId=$(this).attr("menuId");
				$(".menu_ziji").each(function(){
					if($(this).attr("menuId")==menuId){$(this).show(200);}
					else{$(this).hide(200);}
				});
				$(this).siblings(".menu_fuji").css("color","#ffffff");
				$(this).css("color","#ff6600");
				$(this).siblings(".menu_fuji").css("color","#ffffff");
			});
			
			$(".menu_fuji_w").click(function(){
				var menuId=$(this).attr("menuId");
				$(".menu_ziji").each(function(){
					if($(this).attr("menuId")==menuId){$(this).show(200);}
					else{$(this).hide(200);}
				});
			});
			
			var menuIdPage="<%=menuId%>";
			$(".menu_ziji").each(function() {
				if ($(this).attr("menuId") == menuIdPage) {
					$(this).css("display", "block");
				}
			});
			
			var menuIdPage="<%=menuIds%>";
			var menuIdParent="<%=menuId%>";
				$(".menu_ziji a").each(function() {
					if ($(this).attr("menuId") == menuIdPage) {
						var mainStr="#"+menuIdPage;
						$(mainStr).css("color","#ff6600");
						$(".menu_ziji a").css("display", "block");
					}
					if ($(this).parent().attr("menuId") == menuIdParent) {
						var mainParentStr="#"+menuIdParent;
						$(mainParentStr).css("color","#ff6600");
						$(".menu_ziji a").css("display", "block");
					}
				});
				
				$(".menu_ziji a").click(function() {
					var menuId = $(this).parent().attr("menuId");
					var menuIds=$(this).attr("menuId");
					var urls = $(this).attr("hrefs");
					if (urls.indexOf("?") >= 0) {
						window.location.href = urls + "&menuId=" + menuId+ "&menuIds=" + menuIds;
					} else {
						window.location.href = urls + "?menuId=" + menuId+ "&menuIds=" + menuIds;
					}

				});
			});
		</script>
		</div>
	</div>
</div>
<!-- left menu ends -->