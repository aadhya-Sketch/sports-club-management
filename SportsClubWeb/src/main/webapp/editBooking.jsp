<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Change Slot</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>Change Slot</h2>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form action="editBooking" method="post">
        <input type="hidden" name="bookingId" value="<%= request.getAttribute("bookingId") %>">
        <input type="hidden" name="unitId" value="<%= request.getAttribute("unitId") %>">

        <label>New Date:</label><br>
        <input type="date" name="date" required><br><br>

        <label>New Start Time:</label><br>
        <input type="time" name="startTime" required><br><br>

        <button type="submit" class="primary">Confirm Change</button>
    </form>
    <p><a href="myBookings" class="plain-link">Back to My Bookings</a></p>
</div>
</body>
</html>