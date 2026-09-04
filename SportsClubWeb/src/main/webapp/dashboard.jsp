<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sportsclub.model.Member" %>
<%
    Member currentMember = (Member) session.getAttribute("currentMember");
    if (currentMember == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head><title>Dashboard</title><link rel="stylesheet" href="style.css"></head>
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
    <% if (request.getAttribute("error") != null) { %>
    <p class="error"><%= request.getAttribute("error") %></p>
<% } %>

<div class="hero">
    <div class="hero-text">
        <p class="hero-eyebrow">Member Dashboard</p>
        <h1>Welcome back,<br><%= currentMember.getName() %></h1>
        <p class="hero-sub">Book a court, manage your membership, and track your bookings — all in one place.</p>
    </div>
    <div class="hero-graphic">
        <svg viewBox="0 0 240 240" xmlns="http://www.w3.org/2000/svg">
            <rect x="10" y="10" width="220" height="220" rx="4" fill="none" stroke="#0F2A43" stroke-width="3"/>
            <line x1="120" y1="10" x2="120" y2="230" stroke="#0F2A43" stroke-width="2"/>
            <rect x="10" y="70" width="220" height="100" fill="none" stroke="#0F2A43" stroke-width="2"/>
            <circle cx="120" cy="120" r="34" fill="none" stroke="#0F2A43" stroke-width="2"/>
            <circle cx="120" cy="120" r="10" fill="#C6F135"/>
        </svg>
    </div>
</div>
    </div>
</body>
</html>