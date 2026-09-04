<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportsclub.model.Booking" %>
<!DOCTYPE html>
<html>
<head><title>My Bookings</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>My Bookings</h2>

    <% if (request.getAttribute("message") != null) { %>
        <p class="message"><%= request.getAttribute("message") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <%
        List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
        if (bookings.isEmpty()) {
    %>
        <p>You have no bookings yet.</p>
    <%
        } else {
    %>
        <table border="1" cellpadding="6">
            <tr>
                <th>Booking ID</th>
                <th>Unit ID</th>
                <th>Date</th>
                <th>Time</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <% for (Booking b : bookings) { %>
            <tr>
                <td><%= b.getBookingId() %></td>
                <td><%= b.getUnitId() %></td>
                <td><%= b.getBookingDate() %></td>
                <td><%= b.getStartTime() %> - <%= b.getEndTime() %></td>
                <td><%= b.getBookingStatus() %></td>
                <td>
                    <% if ("Confirmed".equals(b.getBookingStatus())) { %>
                        <form action="cancelBooking" method="post" style="display:inline;">
                            <input type="hidden" name="bookingId" value="<%= b.getBookingId() %>">
                            <button type="submit" onclick="return confirm('Cancel this booking? Note: booking fees are non-refundable.');">Cancel</button>
                        </form>
                    <% } else { %>
                        —
                    <% } %>
                </td>
            </tr>
            <% } %>
        </table>
    <%
        }
    %>

    <p><a href="dashboard">Back to Dashboard</a></p>
</div>
</body>
</html>