package com.sportsclub.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.sportsclub.dao.FacilityDAO;
import com.sportsclub.model.FacilityUnit;

@WebServlet("/units")
public class UnitsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FacilityDAO facilityDAO = new FacilityDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("currentMember") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int facilityId = Integer.parseInt(request.getParameter("facilityId"));
        List<FacilityUnit> units = facilityDAO.getUnitsByFacility(facilityId);
        request.setAttribute("units", units);
        request.getRequestDispatcher("units.jsp").forward(request, response);
    }
}