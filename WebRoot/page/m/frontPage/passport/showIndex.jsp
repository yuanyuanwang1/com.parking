<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta name="applicable-device" content="mobile">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />

<%
	String parkId = (String)request.getAttribute("parkId");

   String pname = (String)request.getAttribute("pname");
%> 
<title>
<%=pname %>停车自助收费</title>
<%
	String head_path = request.getContextPath();
%> 
<link href="<%=head_path%>/script/epark/index_m.css?v=1" rel="stylesheet" type="text/css" />
<script src="<%=head_path%>/script/epark/jquery.js"></script>
<script type="text/javascript">
var cansubmit=false;
window.onload = function(){ 
	var payType="alipay";
	if(isWeiXin()){ 
		payType="tenpay";
	}
	$("#payType").val(payType);
} 
function isWeiXin(){ 
		var ua = window.navigator.userAgent.toLowerCase(); 
		if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
			return true; 
		};
		 if (ua.match(/QQ/i) == "qq") {
			 return true; 
		} ;
		return false;
} 
var _Car={};  
function initmy_cphm()
{
	 
	if(document.getElementById("my_cphm").value==''||document.getElementById("my_cphm").value=='车牌号码后5位/6位')
	{
		document.getElementById("my_cphm").value="车牌号码后5位/6位";
		document.getElementById("my_cphm").style.color='#CCCCCC';
	}else
	{
		document.getElementById("my_cphm").style.color='#000000';
	}
	 
}

function CheckCar()
{
	UpdateCar();
	if(!isRightNo(_Car.pjc+_Car.cjc+_Car.cphm))
	{
		alert('车牌输入错误，请重新输入！');
		return false;
	}	 
	return true;
	
}
function UpdateCar()
{
	_Car.pjc=document.getElementById("my_jc_select1").value;
	_Car.cjc=document.getElementById("my_jc_select2").value;
	_Car.cphm=document.getElementById("my_cphm").value; 
} 
function isRightNo(str)
{
	str=str.toUpperCase();
	var v=/(^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9](\d{5})|(\d{6})$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9](\d{5})|(\d{6})[挂学警军港澳]{1}$)/;
	return str.search(v)==-1?false:true;
}
 
function Jtrim(str){//删除空格
      var p=/\s/g;
      str=str.replace(p,"");  
      
      var tmp = ""; 
      for(var i=0;i<str.length;i++) 
      { 
      if(str.charCodeAt(i)>65248&&str.charCodeAt(i)<65375) 
      { 
      tmp += String.fromCharCode(str.charCodeAt(i)-65248); 
      } 
      else 
      { 
      tmp += String.fromCharCode(str.charCodeAt(i)); 
      } 
      } 
      return tmp 
      return str;
} 
  
function onFocusCphm(obj)
{
	if (obj.value =='车牌号码后5位/6位'){obj.value =''; obj.style.color='#000000';}
}
 
function onblurCphm(obj)
{   obj.value=Jtrim(obj.value);
	obj.value=obj.value.toUpperCase();
	if (obj.value ==''){obj.value='车牌号码后5位/6位';obj.style.color='#CCCCCC';}else if(obj.value!='车牌号码后5位/6位'){obj.style.color='#000000';}
} 
$(document).ready(function() {	
	initmy_cphm();
 
});
function getInpark()
{
	if(!CheckCar())
	{
		return false;
	}else
	{ 
		var carno=_Car.pjc+_Car.cjc+_Car.cphm;
		var  parkId=$("#parkId").val();
		$.ajax({
			url:"InparkGetJson.action",
			data : {
						"plateText" : carno,
						"parkId":parkId
					},		 
			dataType : "json",
			success : function(data) {	
				   var map = JSON.parse(data);
				   if (map.appCode=="1"){
		 				$("#plateText").val(map.plateText);	 			 
		 				$("#checkInTime").val(map.checkInTime);	 			 
		 				$("#costFee").val(map.costFee);
		 				 
		 				$("#sellType").val(map.sellType);
		 				$("#presellBeginTime").val(map.presellBeginTime);
		 				$("#presellEndTime").val(map.presellEndTime);
		 				$("#FreeTimeAfterCenterCharge").val(map.FreeTimeAfterCenterCharge);
		 				cansubmit=true;
				   }else{
					   $("#plateText").val("");	 			 
		 				$("#checkInTime").val("");	 			 
		 				$("#costFee").val("");
		 				 
		 				$("#sellType").val("");
		 				$("#presellBeginTime").val("");
		 				$("#presellEndTime").val("");
		 				$("#FreeTimeAfterCenterCharge").val("");
		 				alert("无法查询车辆在场信息，请到出口缴费！");  
						cansubmit=false;
				   }
			},
			error: function(data, status, e){					
				alert("网络异常，请到出口缴费！");  
				cansubmit=false;
			}
		 });
	}
}
function btnsubmit()
{     
	if ( cansubmit){
			var	costFee=	$("#costFee").val();
			if (costFee == null || costFee == undefined || costFee == ''){				
				alert("请先查询车辆在场信息！");  
				return;
			}else{	
				if (costFee<=0){
					alert("无停车费用，无需支付！");  
					return;
				}
				
			}
			$("#my_form").submit();	 
		} else{
			alert("请先查询车辆在场信息！");  
		}
	 
}
</script>   
</head>
<body>
<div class="mobilebody">
<div align="center">
<div class="topline"><div align="center" class="top_title1" style="padding-top:10px;"><div class="title1 color1">
<img src="<%=head_path%>/image/logo_park.png" style="width:60px;height:60px;vertical-align:middle;"/>&nbsp;<%=pname %>停车自助收费</div><div class="title2 color1"></div></div></div>
<div class="shadow2"></div>
</div>
<div class="box">
<form name="my_form" method="post" id="my_form" action="OrderPost.action" >
	<div class="inputbox">
		<div class="top2">
			<div class="leftbox">
			  
			 <div class="hphmjc">
			 	<div class="hphmjc1">
                    <select name="province_sn" id="my_jc_select1" style="width:100%; height:40px;"  onChange = ""  class="myselect">
          <option selected="selected" value="粤">粤</option>
          <option value="冀">冀</option>
          <option value="浙">浙</option>
          <option value="京">京</option>
          <option value="沪">沪</option>
          <option value="川">川</option>

          <option value="津">津</option>
          <option value="渝">渝</option>
          <option value="鄂">鄂</option>
          <option value="赣">赣</option>
          <option value="冀">冀</option>
          <option value="蒙">蒙</option>

          <option value="鲁">鲁</option>
          <option value="苏">苏</option>
          <option value="辽">辽</option>
          <option value="吉">吉</option>
          <option value="皖">皖</option>
          <option value="湘">湘</option>

          <option value="黑">黑</option>
          <option value="琼">琼</option>
          <option value="贵">贵</option>
          <option value="桂">桂</option>
          <option value="云">云</option>
          <option value="藏">藏</option>

          <option value="陕">陕</option>
          <option value="甘">甘</option>
          <option value="宁">宁</option>
          <option value="青">青</option>
          <option value="豫">豫</option>
          <option value="闽">闽</option>

          <option value="新">新</option>
          <option value="晋">晋</option>
        </select>
				</div>
				<div class="hphmjc2">				
                <select name="city_sn" id="my_jc_select2" style="width:100%; height:40px;" onChange = ""  class="myselect">
          <option selected="selected" value="C">C</option>
          <option value="B">B</option>
          <option value="A">A</option>

          <option value="D">D</option>
          <option value="E">E</option>
          <option value="F">F</option>
          <option value="G">G</option>
          <option value="H">H</option>
          <option value="I">I</option>

          <option value="J">J</option>
          <option value="K">K</option>
          <option value="L">L</option>
          <option value="M">M</option>
          <option value="N">N</option>
          <option value="O">O</option>

          <option value="P">P</option>
          <option value="Q">Q</option>
          <option value="R">R</option>
          <option value="S">S</option>
          <option value="T">T</option>
          <option value="U">U</option>

          <option value="V">V</option>
          <option value="W">W</option>
          <option value="X">X</option>
          <option value="Y">Y</option>
          <option value="Z">Z</option>
        </select>
				</div>
			 </div>
			</div>
			<div class="rightbox">
				 
				<div class="hphmno">				
                <input name="cphm" type="text" id="my_cphm" size="10" style="width:100%"    onFocus="onFocusCphm(this)"  onblur="onblurCphm(this)" class="myinput"  />
				
				</div>
			</div>
		</div>
		
       
		<div class="querybtn">
		<input id="getinpark" class="btn" onclick="getInpark();" value="在场查询" type="button">
		</div>
	</div>  
	<div class="inputbox">
		 
    	<div  align="center"  >
    		车牌号码：<input name="orderInfo.carNo" type="text" id="plateText" value=""   readonly="true" style="width: 180px;margin:3px;border:0;background:transparent;"/><br>
    		进场时间：<input name="orderInfo.InDateTime" type="text" id="checkInTime" value=""   readonly="true" style="border:0;background:transparent;width: 200px;margin:3px;"/><br>
    		停车费用(元)：<input name="orderInfo.CarFee" type="text" id="costFee" value=""   readonly="true" readonly="true" style="border:0;background:transparent;width: 180px;margin:3px;"/><br>
    		<input name="orderInfo.parkNo" type="hidden" id="parkId" value="<%=parkId %>"   readonly="true" readonly="true" style="width: 180px;margin:3px;"/> 
    		<input name="orderInfo.cardType" type="hidden" id="sellType" value=""   readonly="true" readonly="true" style="width: 180px;margin:3px;"/> 
    		<input name="orderInfo.payType" type="hidden" id="payType" value=""   readonly="true" readonly="true" style="width: 180px;margin:3px;"/>
    		
    	</div>
         <div class="querybtn">
		 	 <input id="btn_submit" class="btn" onclick="btnsubmit();" value="支付" type="button">
		</div>
		<br/>
    </div>
</form>
  
<div id="ContentID" style="display:none"></div>
 
</div>
<div align="center">
<div class="shadow3"></div>
</div>
<div class="bottomline" style="height:124px">
	<div align="center" class="top_title" style="padding-bottom:10px;">
		<div class="title2 color1" style="text-align: left;">1、输入车牌号，查询车辆在场情况及收费情况！</div> 
		<div class="title2 color1" style="text-align: left;">2、确认信息及金额无误点击支付按钮！</div> 
		<div class="title2 color1" style="text-align: left;">3、交易成功概不退款！</div> 
		<div class="title2 color1" style="text-align: left;color: red;"><strong>4、扫码支付暂不支持开具发票！</strong></div> 
<br/><br/><br/><br/><br/><br/>
	</div>
</div>
</div>
</div>
</body>
</html>