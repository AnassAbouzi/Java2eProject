package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ModifyStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ModifyStudentServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			DataAccessObject.modifyUser(Integer.parseInt(request.getParameter("id")), request.getParameter("userName"), request.getParameter("password"), request.getParameter("unit_name"), Integer.parseInt(request.getParameter("points")));
			response.sendRedirect("adminPanel.jsp");
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
