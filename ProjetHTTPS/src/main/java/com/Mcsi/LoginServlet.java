package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserBean userBean = new UserBean(username, password);
        if (userBean.validate()) {
        	session.setAttribute("user", userBean);
        	int sessionTimeoutInSeconds = 60;
        	session.setMaxInactiveInterval(sessionTimeoutInSeconds);
        	String role = userBean.getRole();
        	if (role.equals("admin")) {
        		response.sendRedirect("adminPanel.jsp");
        	} else if (role.equals("teacher")) {
        		response.sendRedirect("homeTeacher.jsp");
        	} else {
        		response.sendRedirect("homeStudent.jsp");
        	}
        } else {
            response.sendRedirect("login.jsp");
        }
	}

}
