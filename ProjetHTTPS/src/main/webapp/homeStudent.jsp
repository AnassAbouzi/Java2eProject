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
		<div align="left">
			id : <%= user.getId() %>
			points : <%= user.getPoints() %>
		</div>
		<div align="right">
	    Welcome, <%= user.getUsername() %>
	    <br>
	    <br>
	    <button class="logout" onclick="location.href='Logout'">Logout</button>
		</div>
		<%
			String notification = (String)request.getAttribute("notification");
			if (notification != null && !notification.isEmpty()) {
		%> 
			<script>alert("<%= notification %>")</script>
		<%}%>
		<div align="center">
			<button onclick="location.href='unitList.jsp'">consulter toutes les modules</button>
			<br>
			<button onclick="location.href='virement.jsp'">Effectuer un virement de points</button>
			<br>
		</div>
	</body>
</html>