package com.sportsclub.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import com.sportsclub.db.DBConnection;
import java.util.ArrayList;
import java.util.List;
import com.sportsclub.model.Booking;
import java.sql.Statement;

public class BookingDAO {

    // 1. Check if a unit is free at a given date/time
    public boolean isAvailable(int unitId, Date bookingDate, Time startTime) {
        String sql = "SELECT COUNT(*) FROM bookings " +
                     "WHERE unit_id = ? AND booking_date = ? AND start_time = ? " +
                     "AND booking_status = 'Confirmed'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, unitId);
            stmt.setDate(2, bookingDate);
            stmt.setTime(3, startTime);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // true = no conflicting booking found
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // fail safe: if something went wrong, don't assume it's available
    }

    // 2. Create a booking (only if available)
    public String createBooking(int memberId, int unitId, Date bookingDate, Time startTime, Time endTime) {
        if (!isAvailable(unitId, bookingDate, startTime)) {
            return "This slot is already booked. Please choose another time.";
        }

        String sql = "INSERT INTO bookings (member_id, unit_id, booking_date, start_time, end_time, booking_status) " +
                     "VALUES (?, ?, ?, ?, ?, 'Confirmed')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, unitId);
            stmt.setDate(3, bookingDate);
            stmt.setTime(4, startTime);
            stmt.setTime(5, endTime);

            int rows = stmt.executeUpdate();
            return rows > 0 ? "Booking confirmed!" : "Booking failed. Please try again.";

        } catch (SQLException e) {
            e.printStackTrace();
            // Most likely cause here: the UNIQUE constraint from Step 4 caught a
            // race condition that slipped past isAvailable() — see note below.
            return "Booking failed: slot may have just been taken.";
        }
    }

    // 3. Cancel a booking
    public String cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET booking_status = 'Cancelled', cancelled_at = ? " +
                     "WHERE booking_id = ? AND booking_status = 'Confirmed'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, bookingId);

            int rows = stmt.executeUpdate();
            return rows > 0 ? "Booking cancelled." : "Booking not found or already cancelled.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Cancellation failed due to a system error.";
        }
    }
    
    // Same as createBooking, but also returns the new booking_id
    public int createBookingAndReturnId(int memberId, int unitId, Date bookingDate, Time startTime, Time endTime) {
        if (!isAvailable(unitId, bookingDate, startTime)) {
            return -1; // -1 = slot unavailable
        }

        String sql = "INSERT INTO bookings (member_id, unit_id, booking_date, start_time, end_time, booking_status) " +
                     "VALUES (?, ?, ?, ?, ?, 'Confirmed')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, unitId);
            stmt.setDate(3, bookingDate);
            stmt.setTime(4, startTime);
            stmt.setTime(5, endTime);

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
    
    public List<Booking> getBookingsByMember(int memberId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE member_id = ? ORDER BY booking_date DESC, start_time DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getInt("booking_id"));
                    b.setMemberId(rs.getInt("member_id"));
                    b.setUnitId(rs.getInt("unit_id"));
                    b.setBookingDate(rs.getDate("booking_date"));
                    b.setStartTime(rs.getTime("start_time"));
                    b.setEndTime(rs.getTime("end_time"));
                    b.setBookingStatus(rs.getString("booking_status"));
                    b.setCreatedAt(rs.getTimestamp("created_at"));
                    b.setCancelledAt(rs.getTimestamp("cancelled_at"));
                    bookings.add(b);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY booking_date DESC, start_time DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setMemberId(rs.getInt("member_id"));
                b.setUnitId(rs.getInt("unit_id"));
                b.setBookingDate(rs.getDate("booking_date"));
                b.setStartTime(rs.getTime("start_time"));
                b.setEndTime(rs.getTime("end_time"));
                b.setBookingStatus(rs.getString("booking_status"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                b.setCancelledAt(rs.getTimestamp("cancelled_at"));
                bookings.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}