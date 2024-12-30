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


@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Connection con;

	@Override
	public void init() 
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			ServletContext sr=getServletContext();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","9136");	        //String URL = "jdbc:mysql://localhost/collage";

			//con=DriverManager.getConnection(sr.getInitParameter("URL"),sr.getInitParameter("username"),sr.getInitParameter("password"));
	        //String URL = "jdbc:mysql://localhost/collage";
	       // String USERNAME = "root";
	       // String PASSWORD = "root";
	       // con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} 
		catch (SQLException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String email=request.getParameter("email");
		try 
		{
			Statement st = con.createStatement();
			int result=st.executeUpdate("DELETE FROM users WHERE email='"+email+"'");
			if(result>0)
			{
				out.println("<h1>"+"User Deleted "+"</h1>");
			}
			else
			{
				out.println("<h1>"+"User Not Found  "+"</h1>");
			}	
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

}
