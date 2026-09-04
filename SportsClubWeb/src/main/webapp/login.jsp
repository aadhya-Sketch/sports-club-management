<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Sports Club - Login</title><link rel="stylesheet" href="style.css"></head>
<body>
<nav class="navbar">
    <a href="login.jsp" class="brand">SPORTS CLUB</a>
</nav>
<div class="page">
    <h2>Login</h2>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form action="login" method="post">
        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>
        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>
    <p>New here? <a href="register">Register</a></p>
    <p><a href="adminLogin.jsp">Admin Login</a></p>
</div>
</body>
</html>