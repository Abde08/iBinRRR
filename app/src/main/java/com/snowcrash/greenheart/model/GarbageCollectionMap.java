package com.snowcrash.greenheart.model;

import java.util.List;
import java.util.stream.Collectors;

public class GarbageCollectionMap {
    private String State;
    private String City;
    private String Address;
    private String PinCode;
    private String FacilityName;
    private String GPSCoordinates;
    private List<String> Materials;

    public GarbageCollectionMap() {}

    public GarbageCollectionMap(String state, String city, String pinCode, String address, String facilityName, String gpsCoordinates, List<String> materials) {
        this.State = state;
        this.City = city;
        this.PinCode = pinCode;
        this.Address = address;
        this.FacilityName = facilityName;
        this.GPSCoordinates = gpsCoordinates;
        this.Materials = materials;
    }

    public String getState() { return this.State; }
    public String getCity() { return this.City; }
    public String getAddress() { return this.Address; }
    public String getPinCode() { return this.PinCode; }
    public String getFacilityName() { return this.FacilityName; }
    public String getGPSCoordinates() { return this.GPSCoordinates; }
    public List<String> getMaterials() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return this.Materials.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
        }
        return this.Materials;
    }

    public void setState(String state) {
        this.State = state;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setPinCode(String pinCode) {
        this.PinCode = pinCode;
    }

    public void setFacilityName(String facilityName) {
        this.FacilityName = facilityName;
    }

    public void setGPSCoordinates(String gpsCoordinates) {
        this.GPSCoordinates = gpsCoordinates;
    }

    public void setMaterials(List<String> materials) {
        this.Materials = materials;
    }

    public String getFullAddress(){
        return this.FacilityName+"\n"+this.Address+"\n"+this.City+"\n"+this.State+"\n"+this.PinCode+"\nGPS Coordinates: "+this.GPSCoordinates;
    }
}
