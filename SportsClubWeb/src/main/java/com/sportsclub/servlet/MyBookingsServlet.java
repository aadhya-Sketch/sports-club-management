package com.sportsclub.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.dao.BookingDAO;
import com.sportsclub.model.Booking;
import com.sportsclub.model.Member;

@WebServlet("/myBookings")
public class MyBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDAO = new BookingDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("currentMember");
        if (member == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Booking> bookings = bookingDAO.getBookingsByMember(member.getMemberId());
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("myBookings.jsp").forward(request, response);
    }
}