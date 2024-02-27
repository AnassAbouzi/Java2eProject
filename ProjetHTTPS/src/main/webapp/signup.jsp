<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="css/signup.css">
	</head>
	<body>
		<h1>Sign Up</h1>
	
		<form action="SignUpServlet" method="POST">
		    <label for="username">Username:</label>
		    <input type="text" id="username" name="username" required>
		    <br>
		
		    <label for="password">Password:</label>
		    <input type="password" id="password" name="password" required>
		    <br>
		
		    <label for="confirmPassword">Confirm Password:</label>
		    <input type="password" id="confirmPassword" name="confirmPassword" required>
		    <br>
		    
		    <button type="submit">Sign Up</button>
		    <button class="back" onclick="location.href='login.jsp'" type="button">I Have an account</button>
		</form>
	</body>
</html>