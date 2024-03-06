<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.Mcsi.*" %>
<%@ page import="java.util.List" %>
<%
	Teacher user = (Teacher)session.getAttribute("user");
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
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Master Csi</title>\
		<link rel="stylesheet" href="css/addPoint.css">
	</head>
	<body>
	    <div class="header">
	        <div class="user-welcome">
	            Welcome, <%= user.getUsername() %>
	            <br>
	            <br>
	            <button class="logout" onclick="location.href='Logout'">Logout</button>
	        </div>
	    </div>
	    <h1>Details du virement</h1>
	    <form action="AddPointsServlet" method="POST" class="points-form">
	        <input type="hidden" id="studentId" name="studentId" value="<%=request.getParameter("id") %>" />
	
	        <label for="points">Points:</label>
	        <input type="number" id="points" name="points" required>
	        <br>
	
	        <label for="password">Tapez votre mot de passe pour verifier la transaction:</label>
	        <input type="password" id="password" name="password" required>
	        <br>
	
	        <div class="buttons">
	            <button type="reset" class="reset">Reset</button>
	            <button class="cancel" onclick="location.href='homeTeacher.jsp'" type="button">Annuler</button>
	            <button type="submit">Confirmer</button>
	        </div>
	    </form>
	</body>
</html>