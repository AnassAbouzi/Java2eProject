<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.Mcsi.*" %>
<%@ page import="java.util.List" %>
<%
	Student user = (Student)session.getAttribute("user");
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
		<title>Master Csi</title>
	</head>
	<body>
		<div align="right">
		    Welcome, <%= user.getUsername() %>
		    <br>
		    <br>
		    <button class="logout" onclick="location.href='Logout'">Logout</button>
		</div>
		<h1>Details du virement</h1>
		
		<form action="PointsTransferServlet" method="POST">
		    <label for="userId">L'id de l'etudiant auquel vous voulez envoyer des points :</label>
		    <input type="number" id="userId" name="userId" required>
		    <br>
		    
		    <label for="points">Points:</label>
		    <input type="number" id="points" name="points" required>
		    <br>
		    
		    <label for="password">Tapez votre mot de passe pour verifier la transaction:</label>
		    <input type="text" id="password" name="password" required>
		    <br>
		    
		    <button type="reset" class="reset">Reset</button>
		    <button class="reset" onclick="location.href='homeStudent.jsp'" type="button">Annuler</button>
		    <button type="submit">Confirmer</button>
		</form>
		
	</body>
</html>