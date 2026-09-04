package com.sportsclub.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.dao.FacilityDAO;
import com.sportsclub.model.Facility;

@WebServlet("/facilities")
public class FacilitiesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FacilityDAO facilityDAO = new FacilityDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("currentMember") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Facility> facilities = facilityDAO.getAllFacilities();
        request.setAttribute("facilities", facilities);
        request.getRequestDispatcher("facilities.jsp").forward(request, response);
    }
}