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
	</head>
	<body>
		<div align="left">
			<button onclick="location.href='homeStudent.jsp'">Back</button>
		</div>
		<div align="right">
		    Welcome, <%= user.getUsername() %>
		    <br>
		    <br>
		    <button class="logout" onclick="location.href='Logout'">Logout</button>
		</div> 
		<h2>Units</h2>
		<table align="center" width="100%">
	        	<thead>
	            	<tr class="back">
		                <th>Teacher</th>
		                <th>Unit</th>
		            </tr>
		        </thead>
	        	<tbody>
		        <%
		        	List<String[]> teachers_units = DataAccessObject.getUnits(user.getId());
		        	for (String[] teacher_unit : teachers_units) {
		        %>
		        	<tr>
		        		<td align="center"><%= teacher_unit[0] %></td>
		        		<td align="center"><%= teacher_unit[1] %></td>
		        	</tr>
	        	<%}%>
	        	</tbody>
	      	</table>
	</body>
</html>