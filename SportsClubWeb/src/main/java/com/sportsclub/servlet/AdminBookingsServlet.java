package com.sportsclub.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.dao.BookingDAO;
import com.sportsclub.model.Booking;

@WebServlet("/adminBookings")
public class AdminBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDAO = new BookingDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("isAdmin") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }
        List<Booking> bookings = bookingDAO.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("adminBookings.jsp").forward(request, response);
    }
}