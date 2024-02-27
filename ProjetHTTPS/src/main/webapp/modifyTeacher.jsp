<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.Mcsi.*" %>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	int id = Integer.parseInt(request.getParameter("id"));
	try {
		if (user == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="css/addStudent.css">
	</head>
	<body>
		<div align="right">
	    Welcome, <%= user.getUsername() %>
	    <br>
	    <br>
	    <button class="logout" onclick="location.href='Logout'">Logout</button>
		</div> 

		<h1>Update Teacher (id : <%= id %>)</h1>
		
		<form action="ModifyTeacherServlet" method="POST">
			<input type="hidden" name="id" value=<%= id %>>
		    <label for="userName">Teacher User Name:</label>
		    <input type="text" id="userName" name="userName" required>
		    <br>
		    
		    <label for="password">Password:</label>
		    <input type="text" id="password" name="password" required>
		    <br>
		    
		    <label for="unit_name">Unit Name:</label>
		    <input type="text" id="unit_name" name="unit_name" required>
		    <br>
		    
		    <button type="reset" class="reset">Reset</button>
		    <button class="reset" onclick="location.href='adminPanel.jsp'" type="button">Annuler</button>
		    <button type="submit">Confirmer</button>
		</form>
	
	</body>
</html>