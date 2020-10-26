package com.bonial.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LocationResponse {

    @JsonProperty("user-location")
    private String userLocation;

    public LocationResponse() {}

    public LocationResponse(String userLocation, List<Location> locations) {
        this.userLocation = userLocation;
        this.locations = locations;
    }

    private List<Location> locations;

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
