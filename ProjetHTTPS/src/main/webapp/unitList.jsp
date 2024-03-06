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
		<title>Master CSI</title>
		<link rel="stylesheet" href="css/unitList.css">
	</head>
	<body>
	    <div class="header">
	        <div class="back-button">
	            <button onclick="location.href='homeStudent.jsp'">Back</button>
	        </div>
	        <div class="user-welcome">
	            Welcome, <%= user.getUsername() %>
	            <br>
	            <br>
	            <button class="logout" onclick="location.href='Logout'">Logout</button>
	        </div>
	    </div>
	    <h2>Modules</h2>
	    <div class="units-table">
	        <table>
	            <thead>
	                <tr class="back">
	                    <th>Professeur</th>
	                    <th>Module</th>
	                </tr>
	            </thead>
	            <tbody>
	            <% List<String[]> teachers_units = DataAccessObject.getUnits(user.getId());
	                for (String[] teacher_unit : teachers_units) { %>
	                    <tr>
	                        <td><%= teacher_unit[0] %></td>
	                        <td><%= teacher_unit[1] %></td>
	                    </tr>
	            <% } %>
	            </tbody>
	        </table>
	    </div>
	</body>
</html>