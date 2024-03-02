package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class PointsTransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PointsTransferServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Student student = (Student)session.getAttribute("user");
			String password = request.getParameter("password");
			int points = Integer.parseInt(request.getParameter("points"));
			if (!student.getPassword().equals(password)) {
				response.sendRedirect("virement.jsp?error='Wrong password'");
			}
			int status = DataAccessObject.sendPoints(student, Integer.parseInt(request.getParameter("userId")), points);
			if (status == -2) {
				response.sendRedirect("virement.jsp?error='Not enough points'");
			} else if (status == 0) {
				response.sendRedirect("virement.jsp?error='Database error'");
			} else if (status == 1) {
				String notification = "virement avec success.";
				request.setAttribute("notification", notification);
				student.setPoints(student.getPoints() - points);
				request.getRequestDispatcher("homeStudent.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
