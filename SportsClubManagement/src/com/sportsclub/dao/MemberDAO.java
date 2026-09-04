package com.sportsclub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sportsclub.db.DBConnection;
import com.sportsclub.model.Member;

public class MemberDAO {

    // Register a new member
    public boolean registerMember(Member member) {
        String sql = "INSERT INTO members (name, phone_number, email, password, join_date, status) " +
                     "VALUES (?, ?, ?, ?, CURDATE(), 'Active')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, member.getName());
            stmt.setString(2, member.getPhoneNumber());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getPassword());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login: check email + password match
    public Member login(String email, String password) {
        String sql = "SELECT * FROM members WHERE email = ?"; // no longer filtering by password here

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");

                    // Compare the typed password against the stored hash
                    if (com.sportsclub.util.ValidationUtil.checkPassword(password, storedHash)) {
                        Member member = new Member();
                        member.setMemberId(rs.getInt("member_id"));
                        member.setName(rs.getString("name"));
                        member.setPhoneNumber(rs.getString("phone_number"));
                        member.setEmail(rs.getString("email"));
                        member.setStatus(rs.getString("status"));
                        return member;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // wrong email, or wrong password, or no such member
    }
    // Get a member by ID
    public Member getMemberById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setMemberId(rs.getInt("member_id"));
                    member.setName(rs.getString("name"));
                    member.setPhoneNumber(rs.getString("phone_number"));
                    member.setEmail(rs.getString("email"));
                    member.setStatus(rs.getString("status"));
                    return member;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}