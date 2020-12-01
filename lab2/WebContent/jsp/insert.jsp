<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "lab2.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "Op.*" %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF_8">
<title>插入</title>
<link href = "../css/Style.css" type="text/css" rel="stylesheet"/>
</head>
<body>

	<h1>数据库操作结果</h1>
	<h2>
		<%
			String username = (String)application.getAttribute("username1");
			int flag = (int)application.getAttribute("flag");
			if(flag == 1) out.println("更新成功："+username);
			else out.println("插入成功："+username);
		%>
	</h2>
		
<a href = "show_db.jsp" style="font-size:18pt">查看数据库数据</a>
<br>
<a href = "index.jsp" style="font-size:18pt">返回数据库操作</a>
</body>
</html>