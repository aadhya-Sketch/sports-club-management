package com.sportsclub.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.dao.PaymentDAO;

@WebServlet("/adminRevenue")
public class AdminRevenueServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaymentDAO paymentDAO = new PaymentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("isAdmin") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }
        double revenue = paymentDAO.getTotalRevenue();
        request.setAttribute("revenue", revenue);
        request.getRequestDispatcher("adminRevenue.jsp").forward(request, response);
    }
}