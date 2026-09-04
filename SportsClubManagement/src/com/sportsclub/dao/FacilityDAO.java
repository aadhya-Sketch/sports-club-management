package com.sportsclub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.sportsclub.db.DBConnection;
import com.sportsclub.model.Facility;
import com.sportsclub.model.FacilityUnit;

public class FacilityDAO {

    // Get all facilities
    public List<Facility> getAllFacilities() {
        List<Facility> facilities = new ArrayList<>();
        String sql = "SELECT * FROM facilities";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Facility facility = new Facility();
                facility.setFacilityId(rs.getInt("facility_id"));
                facility.setFacilityName(rs.getString("facility_name"));
                facility.setFacilityType(rs.getString("facility_type"));
                facility.setDescription(rs.getString("description"));
                facility.setNoOfUnits(rs.getInt("no_of_units"));
                facilities.add(facility);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facilities;
    }

    // Get all units belonging to a specific facility
    public List<FacilityUnit> getUnitsByFacility(int facilityId) {
        List<FacilityUnit> units = new ArrayList<>();
        String sql = "SELECT * FROM facilities_units WHERE facility_id = ? AND status = 'Active'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, facilityId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FacilityUnit unit = new FacilityUnit();
                    unit.setUnitId(rs.getInt("unit_id"));
                    unit.setFacilityId(rs.getInt("facility_id"));
                    unit.setUnitName(rs.getString("unit_name"));
                    unit.setStatus(rs.getString("status"));
                    units.add(unit);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }
}