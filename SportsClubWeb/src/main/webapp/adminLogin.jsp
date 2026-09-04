<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Admin Login</title><link rel="stylesheet" href="style.css"></head>
<body>
    <nav class="navbar">
    <a href="login.jsp" class="brand">SPORTS CLUB</a>
</nav>
<div class="page">
    <h2>Admin Login</h2>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form action="adminLogin" method="post">
        <label>Username:</label><br>
        <input type="text" name="username" required><br><br>
        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>
    <p><a href="login.jsp">Back to Member Login</a></p>
</div>
</body>
</html>