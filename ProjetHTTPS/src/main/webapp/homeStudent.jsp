<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.Mcsi.*" %>
<%@ page import="java.util.List" %>
<%
	UserBean user = (UserBean)session.getAttribute("user");
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
		<div align="justify">
			<button onclick="location.href='unitList.jsp'">consulter toutes les modules</button>
			<button onclick="location.href='virement.jsp'">Effectuer un virement de points</button>
		</div>
	</body>
</html>