package com.sportsclub.servlet;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Date;
import java.sql.Time;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.dao.BookingDAO;
import com.sportsclub.dao.MembershipDAO;
import com.sportsclub.dao.PaymentDAO;
import com.sportsclub.model.Member;
import com.sportsclub.model.Membership;
import com.sportsclub.util.QRCodeGenerator;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDAO = new BookingDAO();
    private MembershipDAO membershipDAO = new MembershipDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();
    private static final double BOOKING_FEE = 300.00;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("currentMember");
        if (member == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Membership activeMembership = membershipDAO.getActiveMembership(member.getMemberId());
        if (activeMembership == null) {
            request.setAttribute("error", "You need an active membership to book a facility.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        // Carry the unitId along from units.jsp's link, so the form can pre-fill it
        request.setAttribute("unitId", request.getParameter("unitId"));
        request.getRequestDispatcher("book.jsp").forward(request, response);
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
            int unitId = Integer.parseInt(request.getParameter("unitId"));
            Date date = Date.valueOf(request.getParameter("date"));
            Time startTime = Time.valueOf(request.getParameter("startTime") + ":00");
            String paymentMethod = request.getParameter("paymentMethod");

            if (date.toLocalDate().isBefore(java.time.LocalDate.now())) {
                request.setAttribute("error", "Cannot book a slot in the past.");
                request.getRequestDispatcher("book.jsp").forward(request, response);
                return;
            }

            Time endTime = new Time(startTime.getTime() + 3600000); // +1 hour

            int bookingId = bookingDAO.createBookingAndReturnId(
                    member.getMemberId(), unitId, date, startTime, endTime);

            if (bookingId == -1) {
                request.setAttribute("error", "This slot is already booked. Please choose another time.");
                request.getRequestDispatcher("book.jsp").forward(request, response);
                return;
            }

            String paymentResult = paymentDAO.recordPayment(
                    member.getMemberId(), BOOKING_FEE, "Booking", bookingId, paymentMethod);

            request.setAttribute("bookingId", bookingId);
            request.setAttribute("paymentMethod", paymentMethod);
            request.setAttribute("paymentResult", paymentResult);

            // If UPI was chosen, generate the QR and copy it where the browser can reach it
            if ("UPI".equals(paymentMethod)) {
                String destFolder = getServletContext().getRealPath("/qr_codes");
                String fileName = QRCodeGenerator.generateUpiQr(BOOKING_FEE, "Booking Fee", destFolder);
                if (fileName != null) {
                    request.setAttribute("qrImage", "qr_codes/" + fileName);
                } else {
                    request.setAttribute("error", "Could not generate QR code, but booking is confirmed.");
                }
            }
            request.getRequestDispatcher("bookingConfirmation.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid input. Please check the date, time, and unit ID.");
            request.getRequestDispatcher("book.jsp").forward(request, response);
        }
    }
}