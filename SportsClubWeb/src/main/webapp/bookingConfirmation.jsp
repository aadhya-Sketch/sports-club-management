<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Booking Confirmed</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>Booking Confirmed!</h2>
    <p>Booking ID: <%= request.getAttribute("bookingId") %></p>
    <p>Payment Method: <%= request.getAttribute("paymentMethod") %></p>
    <p><%= request.getAttribute("paymentResult") %></p>

    <% if (request.getAttribute("qrImage") != null) { %>
        <h3>Scan to Pay via UPI</h3>
        <img src="<%= request.getAttribute("qrImage") %>" alt="UPI QR Code" width="250">
    <% } %>

    <p><a href="dashboard">Back to Dashboard</a></p>
</div>
</body>
</html>