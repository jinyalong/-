<%--
  Created by IntelliJ IDEA.
  User: X
  Date: 2020/11/25
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "lab2.*" %>
<%@ page import="java.sql.ResultSet" %>
<html>
<head>
    <title>查看数据库数据</title>
    <link href = "css/style-mini.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%
    DB_conn_op sjk = (DB_conn_op)session.getAttribute("sjk");
%>
<h1>数据表user:</h1>
<table>
    <tr>
        <th style="font-size:18pt">username</th>
        <th style="font-size:18pt">password</th>
    </tr>

    <%
        String sql = "select * from users";
        ResultSet rs = sjk.query(sql);
        while(rs.next()) {
            String username = ((ResultSet) rs).getString("username");
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
<h1>数据表person:</h1>
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

<a href = "index.jsp">返回</a>
</body>
</html>
