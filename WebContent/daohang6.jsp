<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="com.entity.Account"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日志管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
    <link rel="stylesheet" type="text/css" href="css/body.css">
    <script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>  

<link rel="stylesheet" type="text/css" href="css/body.css">
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
	<%int count = (int)request.getAttribute("limitpage");%>
    function check(thisForm){
	var page = thisForm.page.value;
	if(page==null || page==""){
          alert("跳转目标页不能为空！");
          return false;
       } 
	if(page < 1 || page > <%=count%>){
        alert("跳转目标页非法！");
        return false;
     } 
	return true;
    }
    </script>
</head>


<body class = "bgcolor-1">

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
<h2>用户管理</h2> 
<div class="grid">
	<form method="post" action="ScreenServlet">
		<div id="one">
			<input type = "text" name = "screen" placeholder="输入关键词" id="input2" value="">
			<input type = "submit" class = "operating" value = "搜索" >
		</div>
	</form>
</div>
</div>
<br>
<form method="post" action="" >
<table id="miyazaki" style="width:80%">
		<thead>
		<tr style="height:40px"><td tyle="width:20px">用户名</td><td align="center">权限</td><td>状态</td></td></thead>	
		<%
			ArrayList<Account> pageList = (ArrayList<Account>)request.getSession().getAttribute("pageList");
			for (Account p : pageList) {				
		%>
		<tbody>	
			<td ><%=p.getAccount_name() %></td>
			<td><input type="range" name="points" min="1" max="2" style="width:10px"/></td>
			<!--  <td><a href="未输入?id=<%=p.getAccount_id()%>"><%=p.getAuthority() %></a></td>-->
			<td><a href="未输入?id=<%=p.getAccount_id()%>"><%=p.getDisabled() %></a></td>			
		<%
			}
		%>
		</tbody>
	</table>
	<div class = "grid">
		<div id = "one"><button class = "operating"><%=request.getAttribute("last")%></button></div>
		<div id = "two"><%=request.getAttribute("bar")%></div>
		<div id = "three"><button class = "operating"><%=request.getAttribute("next")%></button></div>
	</div>
	<br>
</form>

	<div class = "grid">
		<form action="ScreenServlet?page=" onsubmit="return check(this)">
		
		<div id = "one">
			<input type = "text" name = "page" placeholder="输入页数" id="input1">
			<input type = "submit" class = "operating" value = "跳转" >
		</div>
		</form>
	</div>
	
</body>
</html>