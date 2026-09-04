package com.sportsclub.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.dao.MemberDAO;
import com.sportsclub.model.Member;
import com.sportsclub.util.ValidationUtil;

@WebServlet("/verifyOtp")
public class VerifyOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberDAO memberDAO = new MemberDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String enteredOtp = request.getParameter("otp");
        String realOtp = (String) session.getAttribute("pendingOtp");

        if (realOtp == null || !enteredOtp.equals(realOtp)) {
            request.setAttribute("error", "Incorrect verification code.");
            request.getRequestDispatcher("verifyOtp.jsp").forward(request, response);
            return;
        }

        String password = request.getParameter("password");
        if (!ValidationUtil.isStrongPassword(password)) {
            request.setAttribute("error", "Weak password. Needs 6+ chars, uppercase, lowercase, digit, special char.");
            request.getRequestDispatcher("verifyOtp.jsp").forward(request, response);
            return;
        }

        Member member = new Member();
        member.setName((String) session.getAttribute("pendingName"));
        member.setPhoneNumber((String) session.getAttribute("pendingPhone"));
        member.setEmail((String) session.getAttribute("pendingEmail"));
        member.setPassword(ValidationUtil.hashPassword(password));

        boolean success = memberDAO.registerMember(member);

        session.removeAttribute("pendingName");
        session.removeAttribute("pendingPhone");
        session.removeAttribute("pendingEmail");
        session.removeAttribute("pendingOtp");

        if (success) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("error", "Registration failed (email may already be in use).");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}