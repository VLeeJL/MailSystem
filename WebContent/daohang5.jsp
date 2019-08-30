<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加用户</title>
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
<script type="text/javascript">  
	function run(){  
		if(document.form1.username.value==''){
			alert('用户名为空！');  
			document.form1.username.focus();  
			return false;
		}
		if(document.form1.password0.value != document.form1.password1.value){  
			alert('密码不一致！');  
			document.form1.passward1.focus();  
			return false;  
		} 		
		return true;  
	}  
	function run2(){
		var form = document.form1;
		// 设置提交的路径
		form.action = "AddUserServlet";
		form.method = "post";
		// 提交表单
		if (run())
		form.submit();
	}
</script> 
</head>

<body>
<div id="header">
<h1>邮件服务器系统后台管理</h1>
</div>

<div id="nav">
<table width="100%" border="0" align="center" cellpadding="0" callspacing="0" bgcolor="#E0FFFF">
<tr>
<td valign="top" >
  <div data-role="content">
    <div data-role="collapsible">
		<h1>服务器参数管理</h1> 
		<div data-role="content">
			<a href="daohang1.jsp" data-role="button" style="background:#ADE8FF">启停服务</a>
			<a href="SetArgumentServlet" data-role="button" style="background:#ADE8FF">设置参数</a>
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
			<a href="AccountManagerServlet" data-role="button" style="background:#CCFF66">用户列表</a>
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
	<form onsubmit="return run()" name="form1"  id="formId">
		<div id="one">
			<input type = "text" name = "username" placeholder="输入用户名" id="input2" value="">
			</br>
			<input type = "password" name = "password0" placeholder="输入密码" id="input2" value="">
			</br>
			<input type = "password" name = "password1" placeholder="确认密码" id="input2" value="">
			</br>
			<input type="radio" name="authority" value="1" checked style="width:50px">管理员
			<input type="radio" name="authority" value="2" style="width:50px">普通用户
			</br>
			<input type="button" value="注册" onclick="run2()">
		</div>
	</form>
</div>

</div>

<div id="footer">
邮件服务器系统后台管理
</div>

</body>
</html>