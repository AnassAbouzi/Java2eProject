package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DeleteStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DeleteStudent() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Teacher teacher = (Teacher)session.getAttribute("user");
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			DataAccessObject.deleteStudentFromUnit(teacher.getId(), studentId);
			response.sendRedirect("homeTeacher.jsp");
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
