<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Book a Slot</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>Book a Slot</h2>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form action="book" method="post">
        <label>Unit ID:</label><br>
        <input type="number" name="unitId"
               value="<%= request.getAttribute("unitId") != null ? request.getAttribute("unitId") : "" %>" required><br><br>

        <label>Date:</label><br>
        <input type="date" name="date" required><br><br>

        <label>Start Time:</label><br>
        <input type="time" name="startTime" required><br><br>

        <label>Payment Method:</label><br>
        <select name="paymentMethod" required>
            <option value="Cash">Cash</option>
            <option value="Card">Card</option>
            <option value="UPI">UPI</option>
            <option value="Net Banking">Net Banking</option>
        </select><br><br>

        <p>Booking Fee: Rs. 300 (1 hour)</p>
        <p style="font-size:0.9em; color:gray;">Note: Booking fees are non-refundable upon cancellation.</p>
        <button type="submit">Confirm Booking</button>
    </form>
    <p><a href="dashboard">Back to Dashboard</a></p>
</div>
</body>
</html>