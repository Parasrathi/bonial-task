package com.bonial.task.model;

public class Location {

    private String id;
    private String name;
    private String coordinate;
    private Double distance;

    public Location() {}

    public Location(String id, String name, String coordinate, Double distance) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
