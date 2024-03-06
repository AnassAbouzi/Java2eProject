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
		<title>Master Csi</title>
		<link rel="stylesheet" href="css/homeTeacher.css">
	</head>
	<body>
	    <div class="header">
	        <div class="user-info">
	            <div class="left-info">
	                <p>ID: <%= user.getId() %></p>
	                <p>Unit: <%= user.getUnit_name() %></p>
	            </div>
	            <div class="right-info" align="right">
	                Welcome, <%= user.getUsername() %>
	                <br>
	                <br>
	                <button class="logout" onclick="location.href='Logout'">Logout</button>
	            </div>
	        </div>
	    </div>
	    <% 
	        String notification = (String)request.getAttribute("notification");
	        if (notification != null && !notification.isEmpty()) {
	    %> 
	        <script>alert("<%= notification %>")</script>
	    <%}%>
	    <table class="students-table">
	        <thead>
	            <tr class="back">
	                <th>ID</th>
	                <th>USER NAME</th>
	                <th>POINTS</th>
	                <th>ACTION</th>
	            </tr>
	        </thead>
	        <tbody>
	        <% List<Student> students = DataAccessObject.getStudentsInUnit(user.getUnit_name());
	            for (Student student : students) { %>
	                <tr>
	                    <td><%= student.getId() %></td>
	                    <td><%= student.getUsername() %></td>
	                    <td><%= student.getPoints() %></td>
	                    <td>
	                        <button class="addPoints" onclick="location.href='addPoints.jsp?id=<%= student.getId() %>'">Ajouter des points</button>
	                        <form action="DeleteStudent" method="post">
	                            <input type="hidden" id="studentId" name="studentId" value="<%= student.getId()%>">
	                            <button class="delete" type="submit">Supprimer</button>
	                        </form>
	                    </td>
	                </tr>
	        <% } %>
	        </tbody>
	    </table>
	    <button class="add-unit" onclick="location.href='addStudentToUnit.jsp'" type="button">Ajouter un etudiant</button>
	</body>
</html>