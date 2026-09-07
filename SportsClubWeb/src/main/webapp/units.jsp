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
    <ul class="btn-list">
    <% 
        List<FacilityUnit> units = (List<FacilityUnit>) request.getAttribute("units");
        for (FacilityUnit u : units) {
    %>
        <li>
            <a class="btn-tile" href="book?unitId=<%= u.getUnitId() %>">
                <%= u.getUnitName() %>
                <span class="sub">Status: <%= u.getStatus() %> — Book This Unit</span>
            </a>
        </li>
    <% } %>
</ul>
    <p><a href="facilities">Back to Facilities</a></p>
</div>
</body>
</html>
