<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sportsclub.model.Membership" %>
<!DOCTYPE html>
<html>
<head><title>Membership</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>Buy or Renew Membership</h2>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <%
        Membership activeMembership = (Membership) request.getAttribute("activeMembership");
        if (activeMembership != null) {
    %>
        <div class="membership-status">
            <p class="hero-eyebrow">Current Membership</p>
            <p><strong><%= activeMembership.getMembershipType() %></strong></p>
            <p>Start Date: <%= activeMembership.getStartDate() %></p>
            <p>End Date: <%= activeMembership.getEndDate() %></p>
        </div>
    <%
        }
    %>

    <form action="buyMembership" method="post">
        <label>Membership Type:</label><br>
        <select name="membershipType" required>
            <option value="Monthly">Monthly — Rs. 500</option>
            <option value="Quarterly">Quarterly — Rs. 1000</option>
            <option value="Yearly">Yearly — Rs. 1500</option>
        </select><br><br>

        <label>Start Date:</label><br>
        <input type="date" name="startDate" required><br><br>

        <label>Payment Method:</label><br>
        <select name="paymentMethod" required>
            <option value="Cash">Cash</option>
            <option value="Card">Card</option>
            <option value="UPI">UPI</option>
            <option value="Net Banking">Net Banking</option>
        </select><br><br>

        <button type="submit" class="primary">
            <%= activeMembership != null ? "Renew Membership" : "Purchase Membership" %>
        </button>
    </form>
    <p><a href="dashboard" class="plain-link">Back to Dashboard</a></p>
</div>
</body>
</html>