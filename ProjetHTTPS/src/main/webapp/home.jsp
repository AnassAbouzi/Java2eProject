<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.Mcsi.*" %>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	if (user == null) {
		response.sendRedirect("login.jsp");
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Master CSI</title>
		<link rel="stylesheet" href="css/loginCSS.css">
	</head>
	<body>
		<h2>Welcome, <%= user.getUsername() %></h2>
		<h3>Units:</h3>
	    <%--<table>
	        <thead>
	            <tr class="back">
	                <th>Order</th>
	                <th>Unit Name</th>
	                <th>Professor</th>
	                <th>Hour</th>
	                <th>Action</th>
	            </tr>
	            <%
	            String JDBC_URL = "jdbc:mysql://localhost:3306/university";
	            String JDBC_USER = "root";
	            String JDBC_PASSWORD = "";
	            
	            int numberUnit = 0;
	            int orderNumber = 1; // Counter for order number
	            
	            try {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	                PreparedStatement pst = con.prepareStatement("SELECT * FROM units");
	                ResultSet rs = pst.executeQuery();
	                while(rs.next()){
	            %>
	            <tr>
	                <th><%= orderNumber++ %></th>
	                <th><%= rs.getString(2) %></th>
	                <th><%= rs.getString(3) %></th>
	                <th><%= rs.getString(4) %></th>
	                <th>
	                    <% if ("admin".equals(role)) { %>
	                        <button class="delete" onclick="location.href='Delete?id=<%= rs.getString(1) %>'">Delete</button>
	                    <% } %>
	                </th>
	            </tr>  	
	            <%
	                }
	                
	                pst = con.prepareStatement("SELECT COUNT(*) FROM units");
	                rs = pst.executeQuery();
	                rs.next();
	                numberUnit = rs.getInt(1);
	                rs.close();
	                pst.close();
	                con.close();
	            } catch (Exception e) {
	                System.out.println(e);
	            }
	            %>       
	        </thead>
	    </table>
	    
	    Number of Units : <%=numberUnit %>
	    
	    <h3>Add New Unit:</h3>
	    <form action="AddUnitServlet" method="post">
	        <!-- Update the "Add Unit" button style -->
	        <% if ("admin".equals(user.getRole())) { %>
	            <button class="add-unit" onclick="location.href='addUnit.jsp'" type="button">Add New Unit</button>
	        <% } %>
	        
	        <br>
	        <br>
	        <!-- Update the "Logout" button style -->
	        <button class="logout" onclick="location.href='Logout'" type="button">Logout</button>
	    </form>
	    --%>
	</body>
</html>