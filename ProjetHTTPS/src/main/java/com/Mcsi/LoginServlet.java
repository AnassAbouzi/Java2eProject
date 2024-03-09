package com.Mcsi;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
	//servlet qui gere les login
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//creation d'une nouvelle session
		HttpSession session = request.getSession(true);
		//recuperation du nom de l'utilisateur et mot de passe
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserBean userBean = new UserBean(username, password);
        //le mot de passe est hashe dans la methode validate 
        //avant qu'il soit comparer avec le hash dans la base de donnees
        if (userBean.validate()) {
        	//invalidation de session apres "sessionTimeoutInSeconds" second
        	int sessionTimeoutInSeconds = 1800;
        	session.setMaxInactiveInterval(sessionTimeoutInSeconds);
        	String role = userBean.getRole();
        	if (role.equals("admin")) {
        		session.setAttribute("user", userBean);
        		response.sendRedirect("adminPanel.jsp");
        	} else if (role.equals("teacher")) {
        		Teacher teacher = new Teacher(username, password, null);
        		teacher.validate();
        		session.setAttribute("user", teacher);
        		response.sendRedirect("homeTeacher.jsp");
        	} else {
        		Student student = new Student(username, password, 0);
        		student.validate();
        		session.setAttribute("user", student);
        		response.sendRedirect("homeStudent.jsp");
        	}
        } else {
            response.sendRedirect("login.jsp");
        }
	}

}
