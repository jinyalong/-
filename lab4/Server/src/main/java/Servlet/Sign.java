package Servlet;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;

import lab2.*;

//注册接口

/**
 * Servlet implementation class Sign
 */
@WebServlet("/Sign")
public class Sign extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sign() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        password = string2MD5(password);
        PrintWriter out = response.getWriter();
        UserOp uo = new UserOp();
        PersonOp po = new PersonOp();
        DB_conn_op sjk = null;
        try {
            sjk = new DB_conn_op();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject res = new JSONObject();
        boolean flag = false;//username是否存在
        try {
            flag = uo.findUser1(new User(username,password),sjk);
            if(flag){//如果找到user
                Person p = po.findPerson1(username,sjk);
                res.put("username",p.getUsername());
                res.put("name",p.getName());
                if(p.getAge()!=null)
                res.put("age",p.getAge().toString());
                else res.put("age","");
                if(p.getTeleno()!=null)
                res.put("teleno",p.getTeleno());
                else res.put("teleno","");
            }
            else res.put("error","登陆失败，用户名或密码错误");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(res);
        out.println(res);
        try {
            sjk.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.close();
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
