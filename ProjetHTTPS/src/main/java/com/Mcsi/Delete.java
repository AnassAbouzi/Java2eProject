package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public Delete() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Ne pas creer une nouvelle session s'elle n'existe pas
		if (session != null) {
	        int id = Integer.parseInt(request.getParameter("id"));
	        DataAccessObject.deleteUser(id);
	        response.sendRedirect("adminPanel.jsp");
		} else {
	        response.sendRedirect("login.jsp");
	    }
	}

}
