<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Master CSI</title>
		<link rel="stylesheet" href="css/loginCSS.css">
	</head>
	<body>
		<div class="container">
    
        <img src="resources/logo.png" alt="Logo">

        <h2>Master Cryptographie et Sécurité de l'Information</h2>
        <br>
        <form action="LoginServlet" method="post">
            <label for="username"><b>Username</b></label>
            <input type="text" placeholder="Enter your username" name="username" required>

            <label for="password"><b>Password</b></label>
            <input type="password" placeholder="Enter your password" name="password" required>

            <button type="submit">Login</button>
        </form>

        <p>Don't have an account? <a href="signup.jsp">Sign up</a></p>
    </div>
	</body>
</html>