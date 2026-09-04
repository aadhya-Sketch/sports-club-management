package com.sportsclub.model;

public class Facility {
    private int facilityId;
    private String facilityName;
    private String facilityType;
    private String description;
    private int noOfUnits;

    public int getFacilityId() { return facilityId; }
    public void setFacilityId(int facilityId) { this.facilityId = facilityId; }

    public String getFacilityName() { return facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }

    public String getFacilityType() { return facilityType; }
    public void setFacilityType(String facilityType) { this.facilityType = facilityType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getNoOfUnits() { return noOfUnits; }
    public void setNoOfUnits(int noOfUnits) { this.noOfUnits = noOfUnits; }
}