<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sportsclub.model.Facility" %>
<!DOCTYPE html>
<html>
<head><title>Facilities</title><link rel="stylesheet" href="style.css"></head>
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
    <h2>Facilities</h2>
    <ul class="btn-list">
    <% 
        List<Facility> facilities = (List<Facility>) request.getAttribute("facilities");
        for (Facility f : facilities) {
    %>
        <li>
            <a class="btn-tile" href="units?facilityId=<%= f.getFacilityId() %>">
                <%= f.getFacilityName() %>
                <span class="sub"><%= f.getFacilityType() %> — View Units</span>
            </a>
        </li>
    <% } %>
</ul>
    <p><a href="dashboard">Back to Dashboard</a></p>
</div>
</body>
</html>
