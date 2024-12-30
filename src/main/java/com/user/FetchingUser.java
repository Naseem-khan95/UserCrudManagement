package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FetchingUser")
public class FetchingUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           // ServletContext sr = getServletContext();
//            String url = sr.getInitParameter("URL");b
//            String username = sr.getInitParameter("username");
//            String password = sr.getInitParameter("password");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","9136");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("DB connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("user_email");
        String password = request.getParameter("password");

        if (email == null || password == null) {
            out.println("<h1>Invalid input</h1>");
            return;
        }

        String findByEmail = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(findByEmail)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                String phonenumber=rs.getString("phoneNumber");

                out.println("<h1>User Details</h1>");
                out.println("<p>First Name: " + firstName + "</p>");
                out.println("<p>Last Name: " + lastName + "</p>");
                out.println("<p>Email: " + userEmail + "</p>");
                out.println("<p>Password: " + userPassword + "</p>");
                out.println("<p>Phone Number: " + phonenumber + "</p>");
            } else {
                out.println("<h1>User Not Found</h1>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h1>Error fetching user details</h1>");
        }
    }

    @Override
    public void destroy() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
