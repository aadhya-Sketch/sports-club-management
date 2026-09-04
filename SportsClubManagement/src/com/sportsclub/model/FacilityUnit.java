package com.sportsclub.model;

public class FacilityUnit {
    private int unitId;
    private int facilityId;
    private String unitName;
    private String status;

    public int getUnitId() { return unitId; }
    public void setUnitId(int unitId) { this.unitId = unitId; }

    public int getFacilityId() { return facilityId; }
    public void setFacilityId(int facilityId) { this.facilityId = facilityId; }

    public String getUnitName() { return unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}