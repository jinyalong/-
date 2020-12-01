package Servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Op.*;
import lab2.*;


/**
 * Servlet implementation class Deleteuser
 */
@WebServlet("/Deleteuser")
public class Deleteuser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    PersonOp po = new PersonOp();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deleteuser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		ServletContext sc = getServletConfig().getServletContext();
        UserOp uo = new UserOp();
        String username =(String)request.getParameter("username");  
        int flag = 0;
        try {
    	   flag = uo.deleteUser_Username(username,(DB_conn_op)sc.getAttribute("sjk"));
        } catch (Exception e) {
		// TODO Auto-generated catch block
        	e.printStackTrace();
        }
	   	sc.setAttribute("username2", username);//保存上下文
        sc.setAttribute("flag1", flag);//保存上下文
        response.sendRedirect(request.getContextPath()+"/jsp/drop.jsp");//重定向
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
