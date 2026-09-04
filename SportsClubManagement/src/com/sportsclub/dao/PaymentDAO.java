package com.sportsclub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.sportsclub.db.DBConnection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PaymentDAO {

    // Record a payment for a booking or a membership
	public String recordPayment(int memberId, double amount, String paymentType, int referenceId, String paymentMethod) {
	    String sql = "INSERT INTO payments (member_id, amount, payment_type, reference_id, status, payment_method) " +
	                 "VALUES (?, ?, ?, ?, 'Success', ?)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, memberId);
	        stmt.setDouble(2, amount);
	        stmt.setString(3, paymentType);
	        stmt.setInt(4, referenceId);
	        stmt.setString(5, paymentMethod);

	        int rows = stmt.executeUpdate();
	        return rows > 0 ? "Payment of Rs. " + amount + " via " + paymentMethod + " recorded successfully." 
	                         : "Payment failed. Please try again.";

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Payment failed due to a system error.";
	    }
	}
	public double getTotalRevenue() {
	    String sql = "SELECT SUM(amount) FROM payments WHERE status = 'Success'";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        if (rs.next()) {
	            return rs.getDouble(1);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0.0;
	}
}