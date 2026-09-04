<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Total Revenue</title><link rel="stylesheet" href="style.css"></head>
<body>
    <nav class="navbar">
    <a href="adminDashboard" class="brand">SPORTS CLUB — ADMIN</a>
    <div class="nav-links">
        <a href="adminBookings">All Bookings</a>
        <a href="adminRevenue">Revenue</a>
        <a href="logout">Logout</a>
    </div>
</nav>
<div class="page">
    <h2>Total Revenue</h2>
    <p>Rs. <%= request.getAttribute("revenue") %></p>
    <p><a href="adminDashboard">Back to Admin Dashboard</a></p>
</div>
</body>
</html>