package com.sportsclub.servlet;

import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.sportsclub.model.Membership;
import com.sportsclub.dao.MembershipDAO;
import com.sportsclub.dao.PaymentDAO;
import com.sportsclub.model.Member;

@WebServlet("/buyMembership")
public class BuyMembershipServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MembershipDAO membershipDAO = new MembershipDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("currentMember");
        if (member == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Membership activeMembership = membershipDAO.getActiveMembership(member.getMemberId());
        request.setAttribute("activeMembership", activeMembership);

        request.getRequestDispatcher("buyMembership.jsp").forward(request, response);
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
            String type = request.getParameter("membershipType");
            String paymentMethod = request.getParameter("paymentMethod");

            double amount = membershipDAO.getMembershipPrice(type);
            if (amount == -1) {
                request.setAttribute("error", "Invalid membership type.");
                request.getRequestDispatcher("buyMembership.jsp").forward(request, response);
                return;
            }

            Date start = Date.valueOf(request.getParameter("startDate"));
            if (start.toLocalDate().isBefore(java.time.LocalDate.now())) {
                request.setAttribute("error", "Start date cannot be in the past.");
                request.getRequestDispatcher("buyMembership.jsp").forward(request, response);
                return;
            }

            Date end = switch (type) {
                case "Monthly" -> Date.valueOf(start.toLocalDate().plusMonths(1));
                case "Quarterly" -> Date.valueOf(start.toLocalDate().plusMonths(3));
                case "Yearly" -> Date.valueOf(start.toLocalDate().plusYears(1));
                default -> start;
            };

            int membershipId = membershipDAO.purchaseMembership(member.getMemberId(), type, start, end, amount);

            if (membershipId != -1) {
                String paymentResult = paymentDAO.recordPayment(
                        member.getMemberId(), amount, "Membership", membershipId, paymentMethod);
                request.setAttribute("membershipType", type);
                request.setAttribute("amount", amount);
                request.setAttribute("endDate", end);
                request.setAttribute("paymentResult", paymentResult);
                request.getRequestDispatcher("membershipConfirmation.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Membership purchase failed.");
                request.getRequestDispatcher("buyMembership.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid input.");
            request.getRequestDispatcher("buyMembership.jsp").forward(request, response);
        }
    }
}