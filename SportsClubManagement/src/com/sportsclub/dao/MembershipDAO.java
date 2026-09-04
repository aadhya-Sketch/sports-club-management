package com.sportsclub.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sportsclub.db.DBConnection;
import com.sportsclub.model.Membership;

public class MembershipDAO {

    // Purchase a membership, return the new membership_id (or -1 on failure)
    public int purchaseMembership(int memberId, String membershipType, Date startDate, Date endDate, double totalAmount) {
        String sql = "INSERT INTO memberships (member_id, membership_type, start_date, end_date, total_amount, status) " +
                     "VALUES (?, ?, ?, ?, ?, 'Active')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, memberId);
            stmt.setString(2, membershipType);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            stmt.setDouble(5, totalAmount);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Get a member's currently active membership (if any)
    public Membership getActiveMembership(int memberId) {
    	String sql = "SELECT * FROM memberships WHERE member_id = ? AND status = 'Active' " +
                "AND end_date >= CURDATE() ORDER BY end_date DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Membership m = new Membership();
                    m.setMembershipId(rs.getInt("membership_id"));
                    m.setMemberId(rs.getInt("member_id"));
                    m.setMembershipType(rs.getString("membership_type"));
                    m.setStartDate(rs.getDate("start_date"));
                    m.setEndDate(rs.getDate("end_date"));
                    m.setTotalAmount(rs.getDouble("total_amount"));
                    m.setStatus(rs.getString("status"));
                    return m;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no active membership found
    }
    
    public double getMembershipPrice(String membershipType) {
        return switch (membershipType) {
            case "Monthly" -> 500.00;
            case "Quarterly" -> 1000.00;
            case "Yearly" -> 1500.00;
            default -> -1; // invalid type
        };
    }
}