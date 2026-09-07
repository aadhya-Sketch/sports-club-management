<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Admin Dashboard</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>Admin Dashboard</h2>
    <ul class="btn-list">
        <li><a class="btn-tile" href="adminBookings">All Bookings</a></li>
        <li><a class="btn-tile" href="adminRevenue">Total Revenue</a></li>
        <li><a class="btn-tile" href="logout">Logout</a></li>
    </ul>
    </div>
</body>
</html>
