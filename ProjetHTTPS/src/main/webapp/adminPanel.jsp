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
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Master CSI</title>
		<link rel="stylesheet" href="css/adminPanel.css">
	</head>
	<body>
		<div align="right">
	    Welcome, <%= user.getUsername() %>
	    <br>
	    <br>
	    <button class="logout" onclick="location.href='Logout'">Logout</button>
		</div> 
		<h2>Admin Panel</h2>
		<p>In this page you can manage all users</p>
		
		<div class="tab">
			<button class="tablinks" onclick="openCity(event, 'Students')">Students</button>
			<button class="tablinks" onclick="openCity(event, 'Teachers')">Teachers</button>
		</div>
		
		<div id="Students" class="tabcontent">
			<table>
	        	<thead>
	            	<tr class="back">
		                <th>ID</th>
		                <th>USER NAME</th>
		                <th>PASSWORD</th>
		                <th>POINTS</th>
		                <th>ACTION</th>
		            </tr>
		        </thead>
	        	<tbody>
		        <%
		        	List<UserBean> students = DataAccessObject.getUsers("student");
		        	for (UserBean student : students) {
		        %>
		        	<tr>
		        		<td><%= student.getId() %></td>
		        		<td><%= student.getUsername() %></td>
		        		<td><%= student.getPassword() %></td>
		        		<td><%= student.getPoints() %></td>
		        		<td>
		        			<button class="modify" onclick="location.href='Delete?id=<%= student.getId() %>'">modifier</button>
		        			<button class="delete" onclick="location.href='Delete?id=<%= student.getId() %>'">Supprimer</button>
		        		</td>
		        	</tr>
	        	<%}%>
	        	</tbody>
	      	</table>
	 		<button class="add-unit" onclick="location.href='addStudent.jsp'" type="button">Ajouter un etudiant</button>
		</div>
		
		<div id="Teachers" class="tabcontent">
		  <table>
	        	<thead>
	            	<tr class="back">
		                <th>ID</th>
		                <th>USER NAME</th>
		                <th>PASSWORD</th>
		                <th>ACTION</th>
		            </tr>
		        </thead>
	        	<tbody>
		        <%
		        	List<UserBean> teachers = DataAccessObject.getUsers("teacher");
		        	for (UserBean teacher : teachers) {
		        %>
		        	<tr>
		        		<td><%= teacher.getId() %></td>
		        		<td><%= teacher.getUsername() %></td>
		        		<td><%= teacher.getPassword() %></td>
		        		<td>
		        			<button class="modify" onclick="location.href='Delete?id=<%= teacher.getId() %>'">modifier</button>
		        			<button class="delete" onclick="location.href='Delete?id=<%= teacher.getId() %>'">Supprimer</button>
		        		</td>
		        	</tr>
	        	<%}%>
	        	</tbody>
	      	</table>
	      	<button class="add-unit" onclick="location.href='addTeacher.jsp'" type="button">Ajouter un professeur</button>
		</div>
		
		<script src="js/tabs.js"></script>
	</body>
</html>