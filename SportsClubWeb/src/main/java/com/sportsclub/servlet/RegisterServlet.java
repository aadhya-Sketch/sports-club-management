package com.sportsclub.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.util.EmailUtil;
import com.sportsclub.util.ValidationUtil;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        if (!ValidationUtil.isValidPhone(phone)) {
            request.setAttribute("error", "Invalid phone number. Enter a valid 10-digit mobile number.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        String otp = EmailUtil.generateOtp();
        boolean sent = EmailUtil.sendOtpEmail(email, otp);
        if (!sent) {
            request.setAttribute("error", "Could not send verification email. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("pendingName", name);
        session.setAttribute("pendingPhone", phone);
        session.setAttribute("pendingEmail", email);
        session.setAttribute("pendingOtp", otp);

        request.getRequestDispatcher("verifyOtp.jsp").forward(request, response);
    }
}