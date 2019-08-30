<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>邮件群发</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
    <link rel="stylesheet" type="text/css" href="css/body.css">
    <script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>  
    <style>
#header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;
}
#nav {
    line-height:30px;
    background-color:#eeeeee;
    height:600px;
    width:300px;
    float:left;
    padding:5px;	      
}
#section {
    float:left;
    padding:10px;	 	 
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
   padding:5px;	 	 
}
</style> 
</head>

<body>
		<%
            String id = request.getParameter("id");  //用户名admin_no
         %>  
<div id="header">
<h1>邮件服务器系统后台管理</h1>
</div>

<div id="nav">
<table width="100%" border="0" align="center" cellpadding="0" callspacing="0" bgcolor="#E0FFFF">
<tr>
<td valign="top" >
  <div data-role="content">
    <div data-role="collapsible">
		<h1>服务器管理管理</h1> 
		<div data-role="content">
			<a href="daohang1.jsp" data-role="button" style="background:#ADE8FF">启停服务</a>
			<a href="daohang2.jsp" data-role="button" style="background:#ADE8FF">设置参数</a>
		</div>
    </div>
	<div data-role="collapsible">
		<h1>邮件群发</h1> 
		<div data-role="content">
			<a href="daohang3.jsp" data-role="button" style="background:#00BFFF">发布新邮件</a>
		</div>
    </div>
	<div data-role="collapsible">
		<h1>拦截管理</h1> 
		<div data-role="content">
			<a href="daohang4.jsp" data-role="button" style="background:#99CC99">拦截设置</a>
		</div>
    </div>
	<div data-role="collapsible">
		<h1>用户管理</h1> 
		<div data-role="content">
			<a href="daohang5.jsp" data-role="button" style="background:#CCFF66">添加用户</a>
			<a href="daohang6.jsp" data-role="button" style="background:#CCFF66">用户列表</a>
		</div>
    </div>
	<div data-role="collapsible">
		<h1>日志管理</h1> 
		<div data-role="content">
			<a href="daohang7.jsp" data-role="button" style="background:#FF9966">日志管理</a>
		</div>
    </div>
  </div>
</td>
</table>
</div>

<div id="section">
</br>
</br>
</br>
<div class="grid">
	<form onsubmit="return checkMail()" method="post" action="SetArgument">
		<div id="one">
			<label>邮件主题</label>
			<input type = "text" name = "title" placeholder="输入邮件标题" id="mailTitle" value="">
			</br>
			<label id="recList">邮件收件人</label>
			<input type = "email" name = "receiver" placeholder="输入收件人,多个收件人用空格隔开" id="mailRecv" value="">			
			</br>
			<label>邮件正文</label>
			<textarea name="content"style="width:700px ; height:300px" id="mailContent"></textarea>
			</br>
			</br>
			<input type = "submit" class = "operating" value = "发送" >
		</div>
	</form>
</div>

</div>

<div id="footer">
邮件服务器系统后台管理
</div>
</body>
<script>
function checkMail() {	
    if (document.getElementById("mailTitle").value != ""){
    	console.log("sadads");
        if (document.getElementById("mailRecv").value != ""){
            if ((document.getElementById("mailContent").value == "")){
                alert("请输入邮件内容");
            }
            else{
            	return true;
            }
        }
        else {
            //alert("请输入至少一位收件人");
            return false;
        }
    }
    else{
        alert("请输入邮件标题");
    }
    console.log(document.getElementById("mailTitle").value);
    return false;
}
</script>
</html>