package Servlet;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab2.DB_conn_op;
import bean.*;
import Op.*;

/**
 * Servlet implementation class Addperson
 */
@WebServlet("/Addperson")
public class Addperson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Addperson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		ServletContext context = this.getServletContext();   
		String username = new String(request.getParameter("username").getBytes("iso-8859-1"),"UTF-8");
		String name = new String(request.getParameter("name").getBytes("iso-8859-1"),"UTF-8");
		String age1 = request.getParameter("age");
		Integer age;
		if(age1.length()>0) age = Integer.valueOf(age1);
		else age = null;
		String teleno = request.getParameter("teleno");
		PersonOp po = new PersonOp();
		int flag = 0;
		try {
			flag = po.addPerson(new Person(username,name,age,teleno),(DB_conn_op)context.getAttribute("sjk"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.setAttribute("username1",username);
		context.setAttribute("flag",flag);
		response.sendRedirect(request.getContextPath()+"/jsp/insert.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
