<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "lab2.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "Op.*" %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>删除</title>
<link href = "../css/Style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<% 
		String username = (String)application.getAttribute("username2");
		int flag =(int)application.getAttribute("flag1");
	%>
	<h1>数据库操作结果</h1>
	<h2>
		<%
			if(flag == 1) out.println("删除成功："+username);
			else out.println("删除失败，"+username+"不存在");
		%>
	</h2>
<a href = "show_db.jsp" style="font-size:18pt">查看数据库数据</a>
</body>
</html>