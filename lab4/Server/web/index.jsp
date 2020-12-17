<%--
  Created by IntelliJ IDEA.
  User: X
  Date: 2020/11/24
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "lab2.*" %>
<html>
  <head>
    <title>Lab2-test</title>
    <link href="css/style.css" type="text/css" rel="stylesheet" />
  </head>
  <%
      session.setAttribute("sjk", new DB_conn_op());
  %>
  <script type="text/javascript">
      //onblur失去焦点事件，用户离开输入框时执行 JavaScript 代码：
      //函数1：验证Username格式
      function validate_username(username){
          if(username !="" && username.length <= 10)
          {
              document.getElementById("test_username").innerHTML = "<font color='green' size='3px'>√username格式正确</font>";
              return true;
          }else{
              document.getElementById("test_username").innerHTML = "<font color='red' size='3px'>username格式错误</font>";
              return false;
          }
      }
      function validate_username1(username){
          if(username !="" && username.length <= 10)
          {
              document.getElementById("test_username1").innerHTML = "<font color='green' size='3px'>√username格式正确</font>";
              return true;
          }else{
              document.getElementById("test_username1").innerHTML = "<font color='red' size='3px'>username格式错误</font>";
              return false;
          }
      }
      //函数2：验证Name格式
      function validate_name(name){
          if(name !="" && name.length <= 10)
          {
              document.getElementById("test_name").innerHTML = "<font color='green' size='3px'>√name格式正确</font>";
              return true;
          }else{
              document.getElementById("test_name").innerHTML = "<font color='red' size='3px'>name格式错误</font>";
              return false;
          }
      }

      //函数3：验证Age格式
      function validate_age(age){
          if(age.length==0)
              return true;
          var Age_reg=/^(?:[1-9][0-9]?|1[01][0-9]|120)$/;
          if(Age_reg.test(age))
          {
              document.getElementById("test_age").innerHTML = "<font color='green' size='3px'>√Age格式正确</font>";
              return true;
          }else{
              document.getElementById("test_age").innerHTML = "<font color='red' size='3px'>Age格式错误</font>";
              return false;
          }
      }
      //函数4：验证Teleno格式
      function validate_teleno(teleno){
          if(teleno.length==0)
              return true;
          var Teleno_reg=/^[1][3,4,5,7,8,9][0-9]{9}$/;
          if(Teleno_reg.test(teleno))
          {
              document.getElementById("test_teleno").innerHTML = "<font color='green' size='3px'>Teleno格式正确</font>";
              return true;
          }else{
              document.getElementById("test_teleno").innerHTML = "<font color='red' size='3px'>Teleno格式错误</font>";
              return false;
          }
      }
      //函数4：验证表单是否已经填好
      function validate_form(){
          var username = document.getElementById("username").value;
          var name = document.getElementById("name").value;
          var age = document.getElementById("age").value;
          var teleno = document.getElementById("teleno").value;
          if(validate_username(username)&&validate_name(name)&&validate_age(age)&&validate_teleno(teleno)){
              return true;
          }
          else
              return false;

      }
      function show_confirm()
      {
          var username = document.getElementById("username1").value;
          if(validate_username1(username)){
              var r=confirm("确认删除？");
              return r;
          }
          return false;
      }
  </script>

  <body>
  <h1 align = "center">添加一个Proson:</h1>
  <form action="Change_password" onsubmit="return validate_form()" method="get">
      <table>
          <tr>
              <td>Username：</td>
              <td><input type="text" id="username" name="username" placeholder="长度限制10" onblur="validate_username(this.value)"/></td>
              <td id="test_username"></td>
          </tr>
          <tr>
              <td>Name：</td>
              <td><input type="text" id="name" name="name" placeholder="长度限制20" onblur="validate_name(this.value)"/></td>
              <td id="test_name"></td>
          </tr>
          <tr>
              <td>Age：</td>
              <td><input type="text" id="age" name="age" placeholder="0-120整数" onblur="validate_age(this.value)" /></td>
              <td id="test_age"></td>
          </tr>
          <tr>
              <td>Teleno：</td>
              <td><input type="text" id="teleno" name="teleno" placeholder="11位数字" onblur="validate_teleno(this.value)"/></td>
              <td id="test_teleno"></td>
          </tr>
          <tr>
              <td>password：</td>
              <td><input type="password" id="password" name="password" placeholder="11位数字" /></td>
          </tr>
          <tr>
              <td></td>
              <td ><input type="submit" value="插入"/>
                  <input type="reset" value="重置"/>
              </td>
          </tr>
      </table>
  </form>
  <h1 align = "center">删除一个Proson:</h1>
  <form action="Delete" onsubmit="return show_confirm();" method="get">
      <table>
          <tr>
              <td>Username：</td>
              <td><input type="text" id="username1" name="username1" placeholder="长度限制10" onblur="validate_username1(this.value)"/></td>
              <td id="test_username1"></td>
          </tr>
          <tr>
              <td></td>
              <td ><input type="submit" value="删除"/>
                  <input type="reset" value="重置"/>
              </td>
          </tr>
      </table>
  </form>
  <p style="text-align: center"><a href="list.jsp">查看数据库</a></p>
  </body>
</html>
