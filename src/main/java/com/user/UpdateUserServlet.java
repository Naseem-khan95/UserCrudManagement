package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update")

public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;

	public void init() 
	{
		try 
		{
			//System.out.println("init()");
			Class.forName("com.mysql.jdbc.Driver");
			//ServletContext sr=getServletContext();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","9136");	        //String URL = "jdbc:mysql://localhost/collage";

			//connection=DriverManager.getConnection(sr.getInitParameter("url"),sr.getInitParameter("username"),sr.getInitParameter("password"));
			
			//connection = DriverManager.getConnection("jdbc:mysql://localhost/collage", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		//System.out.println("doPost()");
		String email = request.getParameter("user_email");
		String password = request.getParameter("password");

		try {
			Statement statement = con.createStatement();
			int result = statement.executeUpdate("update users set password='"+password+"' where email='"+email+"'");
			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.print("<H1>Password Updated</H1");
			} else {
				out.print("<H1>Error Creating the User</H1>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		try {
			System.out.println("destroy()");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
