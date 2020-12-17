package Servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.*;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab2.*;

//注册接口

/**
 * Servlet implementation class Addperson
 */
@WebServlet("/Add")
public class Add extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String age1 = request.getParameter("age");
        Integer age;
        if(age1.length()>0) age = Integer.valueOf(age1);
        else age = null;
        String teleno = request.getParameter("teleno");
        String password = request.getParameter("password");
        password = string2MD5(password);
        PrintWriter out = response.getWriter();
        PersonOp po = new PersonOp();
        UserOp uo = new UserOp();
        DB_conn_op sjk = null;
        try {
            sjk = new DB_conn_op();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject res = new JSONObject();
        boolean flag = false;//username是否已经存在
        try {
            flag = uo.findUser(username,sjk);
            if(!flag){
                uo.addUser(new User(username,password),sjk);
                po.addPerson(new Person(username,name,age,teleno),sjk);
                res.put("result","注册成功！");
            }
            else res.put("error","该用户名已被注册");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sjk.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println(res);
    }
    //MD5加密算法
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
