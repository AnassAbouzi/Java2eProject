package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AddPointsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AddPointsServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Teacher teacher = (Teacher)session.getAttribute("user");
			String password = request.getParameter("password");
			int points = Integer.parseInt(request.getParameter("points"));
			int id = Integer.parseInt(request.getParameter("studentId"));
			if (!teacher.getPassword().equals(password)) {
				response.sendRedirect("virement.jsp?error='Wrong password'");
			}
			int status = DataAccessObject.addPoints(id, points);
			if (status == 0) {
				response.sendRedirect("virement.jsp?error='Database error'");
			} else if (status == 1) {
				String notification = "ajout de " + points + " points avec success.";
				request.setAttribute("notification", notification);
				request.getRequestDispatcher("homeTeacher.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
