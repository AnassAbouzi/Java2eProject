package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AddTeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AddTeacherServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String username = (String)request.getParameter("userName");
			String password = (String)request.getParameter("password");
			String unit_name = (String)request.getParameter("unit_name");
			DataAccessObject.addUser(username, password, "teacher", unit_name, 0);
			response.sendRedirect("adminPanel.jsp");
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}