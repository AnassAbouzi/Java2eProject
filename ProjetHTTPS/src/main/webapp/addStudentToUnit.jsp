<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.Mcsi.*" %>
<%
	Teacher teacher = (Teacher)session.getAttribute("user");
	try {
		if (teacher == null) {
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
	    Welcome, <%= teacher.getUsername() %>
	    <br>
	    <br>
	    <button class="logout" onclick="location.href='Logout'">Logout</button>
		</div> 

		<h1>Add New Student</h1>
		
		<form action="AddStudentToUnitServlet" method="POST">
			<label for="studentId">Id de l'etudiant</label>
			<input type="number" id="studentId" name="studentId" required>
			<br>
		
		    <label for="userName">User Name de l'etudiant:</label>
		    <input type="text" id="userName" name="userName" required>
		    <br>
		    
		    <button type="reset" class="reset">Reset</button>
		    <button class="reset" onclick="location.href='homeTeacher.jsp'" type="button">Annuler</button>
		    <button type="submit">Confirmer</button>
		</form>
	
	</body>
</html>