<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Verify Email</title><link rel="stylesheet" href="style.css"></head>
<body>
<nav class="navbar">
    <a href="login.jsp" class="brand">SPORTS CLUB</a>
</nav>
<div class="page">
    <h2>Enter Verification Code</h2>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form action="verifyOtp" method="post">
        <label>Code sent to your email:</label><br>
        <input type="text" name="otp" required><br><br>

        <label>Create Password:</label><br>
        <input type="password" name="password" required><br><br>

        <button type="submit">Complete Registration</button>
    </form>
</div>
</body>
</html>