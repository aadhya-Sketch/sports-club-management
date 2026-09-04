<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportsclub.model.FacilityUnit" %>
<!DOCTYPE html>
<html>
<head><title>Units</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>Available Units</h2>
    <ul>
        <% 
            List<FacilityUnit> units = (List<FacilityUnit>) request.getAttribute("units");
            for (FacilityUnit u : units) {
        %>
            <li>
                <%= u.getUnitName() %> — <%= u.getStatus() %>
                — <a href="book?unitId=<%= u.getUnitId() %>">Book This Unit</a>
            </li>
        <% } %>
    </ul>
    <p><a href="facilities">Back to Facilities</a></p>
</div>
</body>
</html>