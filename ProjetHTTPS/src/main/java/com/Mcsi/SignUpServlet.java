package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public SignUpServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confPasswod = request.getParameter("confirmPassword");
		if (!password.equals(confPasswod)) {
			// Passwords do not match
            response.sendRedirect("signup.jsp?error=password_mismatch");
            return;
		}
		try {
			int status = DataAccessObject.verify(username);
			if (status == 1) {
				response.sendRedirect("signup.jsp?error=user_already_existes");
				return;
			}
			DataAccessObject.createUser(username, password);
			response.sendRedirect("login.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("signup.jsp?error=registration_failed");
		}
		
	}

}
