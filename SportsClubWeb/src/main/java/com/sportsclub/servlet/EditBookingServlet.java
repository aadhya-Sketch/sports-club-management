package com.sportsclub.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.dao.BookingDAO;
import com.sportsclub.model.Member;

@WebServlet("/editBooking")
public class EditBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDAO = new BookingDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("currentMember") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.setAttribute("bookingId", request.getParameter("bookingId"));
        request.setAttribute("unitId", request.getParameter("unitId"));
        request.getRequestDispatcher("editBooking.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("currentMember");
        if (member == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int unitId = Integer.parseInt(request.getParameter("unitId"));
            Date newDate = Date.valueOf(request.getParameter("date"));
            Time newStartTime = Time.valueOf(request.getParameter("startTime") + ":00");

            if (newDate.toLocalDate().isBefore(java.time.LocalDate.now())) {
                request.setAttribute("error", "Cannot reschedule to a past date.");
                request.setAttribute("bookingId", bookingId);
                request.setAttribute("unitId", unitId);
                request.getRequestDispatcher("editBooking.jsp").forward(request, response);
                return;
            }

            Time newEndTime = new Time(newStartTime.getTime() + 3600000);

            String result = bookingDAO.rescheduleBooking(
                    bookingId, member.getMemberId(), newDate, newStartTime, newEndTime, unitId);

            response.sendRedirect("myBookings");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid input.");
            request.getRequestDispatcher("editBooking.jsp").forward(request, response);
        }
    }
}