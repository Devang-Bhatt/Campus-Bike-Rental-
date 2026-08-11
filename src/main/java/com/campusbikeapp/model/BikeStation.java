package com.campusbikeapp.model;

public class BikeStation {
    private String name;
    private double lat;
    private double lng;
    private int bikesAvailable;
    private String distance;

    public BikeStation() {}

    public BikeStation(String name, double lat, double lng,
                       int bikesAvailable, String distance) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.bikesAvailable = bikesAvailable;
        this.distance = distance;
    }

    public String getName() { return name; }
    public void setName(String v) { this.name = v; }

    public double getLat() { return lat; }
    public void setLat(double v) { this.lat = v; }

    public double getLng() { return lng; }
    public void setLng(double v) { this.lng = v; }

    public int getBikesAvailable() { return bikesAvailable; }
    public void setBikesAvailable(int v) { this.bikesAvailable = v; }

    public String getDistance() { return distance; }
    public void setDistance(String v) { this.distance = v; }
}
