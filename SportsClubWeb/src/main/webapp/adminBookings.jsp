<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportsclub.model.Booking" %>
<!DOCTYPE html>
<html>
<head><title>All Bookings</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>All Bookings</h2>
    <%
        List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
        if (bookings.isEmpty()) {
    %>
        <p>No bookings found.</p>
    <% } else { %>
        <table border="1" cellpadding="6">
            <tr>
                <th>Booking ID</th><th>Member ID</th><th>Unit ID</th>
                <th>Date</th><th>Time</th><th>Status</th>
            </tr>
            <% for (Booking b : bookings) { %>
            <tr>
                <td><%= b.getBookingId() %></td>
                <td><%= b.getMemberId() %></td>
                <td><%= b.getUnitId() %></td>
                <td><%= b.getBookingDate() %></td>
                <td><%= b.getStartTime() %> - <%= b.getEndTime() %></td>
                <td><%= b.getBookingStatus() %></td>
            </tr>
            <% } %>
        </table>
    <% } %>
    <p><a href="adminDashboard">Back to Admin Dashboard</a></p>
    </div>
</body>
</html>