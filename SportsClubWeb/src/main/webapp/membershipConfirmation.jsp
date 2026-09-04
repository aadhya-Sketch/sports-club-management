<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Membership Confirmed</title><link rel="stylesheet" href="style.css"></head>
<body>
<nav class="navbar">
    <a href="dashboard" class="brand">SPORTS CLUB</a>
    <div class="nav-links">
        <a href="facilities">Facilities</a>
        <a href="myBookings">My Bookings</a>
        <a href="buyMembership">Membership</a>
        <a href="logout">Logout</a>
    </div>
</nav>
<div class="page">
    <h2>Membership Purchased!</h2>
    <p>Type: <%= request.getAttribute("membershipType") %></p>
    <p>Amount: Rs. <%= request.getAttribute("amount") %></p>
    <p>Valid Until: <%= request.getAttribute("endDate") %></p>
    <p><%= request.getAttribute("paymentResult") %></p>
    <p><a href="dashboard">Back to Dashboard</a></p>
    </div>
    </body>
</html>