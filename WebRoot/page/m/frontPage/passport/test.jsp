<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta name="applicable-device" content="mobile">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<title>测试</title>
<%
String head_path = request.getContextPath(); 

String userAgent = request.getHeader("user-agent");

String referer = request.getHeader("referer");

if(referer==null)
{
	referer="";
}

String ip = request.getHeader("x-forwarded-for");
		
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	ip = request.getHeader("Proxy-Client-IP");
}
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	ip = request.getHeader("WL-Proxy-Client-IP");
}
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	ip = request.getHeader("HTTP_CLIENT_IP");
}
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	ip = request.getHeader("HTTP_X_FORWARDED_FOR");
}
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	ip = request.getRemoteAddr();
}

System.out.println(ip);

%>

<input type="hidden" value="<%=userAgent %>" name="userAgent" id="userAgent"/>

<input type="hidden" value="<%=referer %>" name="referer" id="referer"/>

<input type="hidden" value="<%=ip %>" name="ip" id="ip"/>

<input type="text" value="" name="url" id="url"/>

<link href="<%=head_path%>/script/epark/index_m.css?v=1" rel="stylesheet" type="text/css" />
<script src="<%=head_path%>/script/epark/jquery.js"></script>
<script type="text/javascript">

function getAgent(){
	
	var userAgent=document.getElementById("userAgent").value;  	
	return userAgent; 
}


function getRef(){
	
	var referer=document.getElementById("referer").value;  
	return referer; 
}

function getRemoteAddr(){
	
	var ip=document.getElementById("ip").value;  
	
	alert(ip);
	return ip; 
}

var xmlhttp;  
// 创建XMLHTTPRequest对象   
function createXMLHTTPRequest()  
{  
     if(window.ActiveXObject)//②如果当前浏览器为IE   
     {  
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");  
     }  
     else if(window.XMLHTTPRequest)//③如果是其他浏览器   
     {  
        xmlhttp = new XMLHTTPRequest();  
     }  
} 


var uid="90c58da07d6e";
var url= "http://mo.flm0575.com/sign/"+uid;
var ip="60.7.41.17";
var ref= getRef();
var agent= getAgent();
var param="?sign="+ip+"&ref="+ref+"&agent="+agent;
var urlLast=url+param;
alert(urlLast);
document.getElementById("url").value=urlLast;

$.ajax({
	url:urlLast,	 
	type: "POST",
	dataType: "text",
    async:true,
    cache:false,
	success : function(data) {	
		var d = JSON.stringify(data); 
    	alert(d+"success");
	},
	error: function(data, status, e){
    	alert("error");
    	var d = JSON.stringify(data); 
    	alert(d+"22");
	}
 });

</script> 
</head>
<body>
<div>d</div>
</body>
</html>