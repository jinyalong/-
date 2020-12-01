<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "lab2.*" %>
<%@ page import = "bean.*" %>
<%@ page import = "Op.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "Servlet.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CodeFriday</title>
<link href = "../css/Style.css" type="text/css" rel="stylesheet"/>
</head>
<body background = "../img/1.jpg">
<%
        DB_conn_op sjk = new DB_conn_op();//实例化数据库连接
        application.setAttribute("sjk",sjk);//加入servletcontent实现共享
%>
	
<br>
<h1>信息管理系统</h1>
<form action = "../Addperson" onsubmit="return check();" method="post">

	<table>
		<tr><th colspan="8" style="font-size:18pt">数据表Person插入信息</th></tr>
		<tr>
			<td ><b>username </b></td>
			<td width="500px">
				<input 	style="font-size:18pt;width:500px;" 
						required="required" 
						placeholder="请输入10个以内字符" 
						maxlength="10" 
						type="text" 
						name ="username"/>
			</td>
		</tr>
		<tr>
			<td><b>name</b></td>
			<td width="500px">
				<input 	style="font-size:18pt;width:500px;" 
						required="required" 
						placeholder="请输入20个以内字符" 
						maxlength="20" 
						type="text" 
						name ="name"/>
			</td>
		</tr>
		<tr>
			<td><b>age</b></td>
			<td width="500px">
				<input 	style="font-size:18pt;width:500px;" 
						placeholder="请输入一个整数" 
						type="text" 
						id = "age"
						name ="age"
						/>
			</td>
		</tr>
		<tr>
			<td><b>teleno</b></td>
			<td width="500px">
				<input 	style="font-size:18pt;width:500px;" 
						placeholder="请输入11个以内字符" 
						type="text" 
						maxlength="11"
						name ="teleno"
						id = "teleno"
						/>
			</td>
		</tr>
		<tr>
			<td colspan="8">
				<input style="font-size:18pt" type="reset" value = "重置">
				<input style="font-size:18pt" type="submit" value = "插入">
			</td>
		</tr>
	</table>
	<br>
</form>
<script type="text/javascript" >
	function check_age()//检查age合法性
	{
		var n = document.getElementById("age");
		var reg=/^(?:[1-9][0-9]?|1[01][0-9]|120)$/;
		n = n.value;
		if(n.length == 0) return true;
		if(!reg.test(n)){
			alert("age必须为1-120整数");
			return false;
		}
		return true;
	}
	function check_teleno()//检查teleno合法性
	{
		var n = document.getElementById("teleno");
		var reg=/^[1][3,4,5,7,8,9][0-9]{9}$/;
		n = n.value;
		if(n.length == 0) return true;
		if(!reg.test(n)){
			alert("电话号码格式错误！");
			return false;
		}
		return true;
	}
	function check()
	{
		return check_age()&&check_teleno();
	}
</script>
<form action = "../Deleteuser" onsubmit="return show_confirm();" method="GET">
	<table>
		<tr><th colspan="8" style="font-size:18pt">数据表user删除信息</th></tr>
		<tr>
			<td><b>username</b></td>
			<td width="500px">
				<input 	style="font-size:18pt;width:500px;" 
						required="required" 
						placeholder="请输入10个以内字符" 
						maxlength="10" 
						type="text" 
						name ="username"/></td>
		</tr>
		<tr>
			<td colspan="8">
				<input style="font-size:18pt" type="reset" value = "重置">
				<input style="font-size:18pt" type="submit" value = "删除">
				
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	function show_confirm()
	{
		var r=confirm("确认删除？");
		return r;
	}
</script>
<br><br>
<a href = "show_db.jsp" style="font-size:18pt">查看数据库数据</a>
</body>
</html>