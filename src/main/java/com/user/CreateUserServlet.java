package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlets")
public class CreateUserServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Connection connection;
	public void init() 
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			//ServletContext sr = getServletContext();
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","9136");
			if (connection != null) 
			{
				System.out.println("Connected to the database");
			} 
			else 
			{
				System.out.println("Not connected");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("useremail");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phonenumber");

		try 
		{
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate("INSERT INTO users (firstName, lastName, email, password, phoneNumber) VALUES ('" + firstName + "','" + lastName + "','" + email + "','" + password + "','" + phoneNumber + "')");
			if (result > 0)
			{
				out.print("<h1>User created</h1>");
			}
			else 
			{
				out.print("<h1>Error creating user</h1>");
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void destroy()
	{
		try 
		{
			connection.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
