<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <%
    if ((String)session.getAttribute("user_name_ad")==null&&session.isNew())
    { //判断是否已登出，并且判断是否是新用户，在未登陆情况下，就不能访问Home_Page_Ad
      //如果是旧用户，例如刷新页面再次请求页面的时候，就不需要再次登陆
		%>
		<script>
			alert("管理员未登录，请重新登录");
 			window.document.location.href = "Main.jsp";
 		</script>
		<%
    }
    String ad_name=(String)session.getAttribute("user_name_ad");
  %>
    
<head lang="en">
    <meta charset="UTF-8">
    <title>主页面</title>
    <link rel="stylesheet" href="CSS/public.css"/>
    <link rel="stylesheet" href="CSS/style.css"/>
</head>
<body>
<%
	String System_Name="root";
	String System_Passwd="liuxing961222";
	String System_dbName="user_pass";
	String url="jdbc:mysql://localhost/"+System_dbName+"?user="+System_Name+"&password="+System_Passwd;
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection con=DriverManager.getConnection(url);
	Statement sta=con.createStatement();
	/**************问题，怎么捕获呢***************/
	String user_name=request.getParameter("user_name");
 %>
<!--头部-->
<header class="publicHeader">
    <h1>联盟信息管理系统</h1>

    <div class="publicHeaderR">
        <p><span>下午好！</span><span style="color: #fff21b"> <%=(String)session.getAttribute("user_name_ad")%></span> , 欢迎你！</p>
        <a href="Main.jsp">
        <%session.removeAttribute("user_name_ad"); %>退出</a>
    </div>
</header>
<!--时间-->
<section class="publicTime">
    <span id="time">2015年1月1日 11:11  星期一</span>
</section>
<!--主体内容-->
<section class="publicMian">
    <div class="left">
        <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
        <nav>
            <ul class="list">
                <li ><a href="Schedule_Manager.jsp">赛程信息管理</a></li>
                <li><a href="providerList.html">球队信息管理</a></li>
                <li><a href="userList.html">球员信息管理</a></li>
                <li><a href="password.html">密码修改</a></li>
                <li><a href="Main.jsp"><%session.removeAttribute("user_name_ad"); %>退出系统</a></li>
            </ul>
        </nav>
    </div>
    <div class="right">
        <img class="wColck" src="images/clock.jpg" alt=""/>
        <div class="wFont">
            <h2><%=ad_name%></h2>
            <p>欢迎来到联盟信息管理系统!</p>
			<span id="hours"></span>
        </div>
    </div>
</section>
<footer class="footer">
</footer>
</body>
</html>