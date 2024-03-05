package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AddStudentToUnitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AddStudentToUnitServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Teacher teacher = (Teacher)session.getAttribute("user");
			String unit_name = teacher.getUnit_name();
			int id = Integer.parseInt(request.getParameter("studentId"));
			String username = (String)request.getParameter("userName");
			if (DataAccessObject.verify(username) != 1) {
				response.sendRedirect("homeTeacher.jsp?error='student doesn't exist'");
			}
			int status = DataAccessObject.addStudentToUnit(id, unit_name);
			if (status == 1) {
				String notification = username + " est ajouter au module avec success.";
				request.setAttribute("notification", notification);
			}
			response.sendRedirect("homeTeacher.jsp");
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
