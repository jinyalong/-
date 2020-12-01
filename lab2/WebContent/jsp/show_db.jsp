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
<title>查看数据库</title>
<link href = "../css/Style.css" type="text/css" rel="stylesheet"/>
</head>
<body background = "../img/2.jpg">
<% 
	ServletContext context = this.getServletContext(); 
	DB_conn_op sjk = (DB_conn_op)context.getAttribute("sjk");
%>
	<h1>数据表user信息</h1>
	<table>
		<tr>
			<th style="font-size:18pt">username</th>
			<th style="font-size:18pt">password</th>
		</tr>
	
	<%
		String sql = "select * from users";
		ResultSet rs = sjk.query(sql);
		while(rs.next()) {
			String username = rs.getString("username");
	   	 	String password = rs.getString("pass");
	%>
	    	<tr>
	    		<td><%= username %></td>
	    		<td><%= password %></td>
	    	</tr>
	<%
		}
		rs.close();
	%>	
	</table>
	<br>
	<h1>数据表person信息</h1>
	<table>
		<tr>
			<th style="font-size:18pt">username</th>
			<th style="font-size:18pt">name</th>
			<th style="font-size:18pt">age</th>
			<th style="font-size:18pt">teleno</th>
		</tr>
	
	<%
		String sql1 = "select * from person";
		ResultSet rs1 = sjk.query(sql1);
		while(rs1.next()) {
			String username = rs1.getString("username");
		    String name = rs1.getString("name");//主键
		    Integer age = rs1.getInt("age");
		    if(age==0) age = null;
		    String teleno = rs1.getString("teleno");
		    if(teleno.length()==0) teleno = null;		
	%>
	    	<tr>
	    		<td><%= username %></td>
	    		<td><%= name %></td>
	    		<td><%= age %></td>
	    		<td><%= teleno %></td>
	    	</tr>
	<%
		}
		rs1.close();
	%>	
	</table>
	
<a href = "index.jsp" style="font-size:18pt">返回数据库操作</a>
</body>
</html>