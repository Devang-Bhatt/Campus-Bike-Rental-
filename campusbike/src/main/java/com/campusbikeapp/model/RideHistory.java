package com.campusbikeapp.model;

/**
 * Same fields as Android RideHistory.java
 */
public class RideHistory {

    private String startPlace;
    private String endPlace;
    private String distance;
    private String price;
    private String date;

    public RideHistory() {}

    public RideHistory(String startPlace, String endPlace,
                       String distance, String price, String date) {
        this.startPlace = startPlace;
        this.endPlace   = endPlace;
        this.distance   = distance;
        this.price      = price;
        this.date       = date;
    }

    // Getters & Setters
    public String getStartPlace()  { return startPlace; }
    public void setStartPlace(String v) { this.startPlace = v; }

    public String getEndPlace()    { return endPlace; }
    public void setEndPlace(String v) { this.endPlace = v; }

    public String getDistance()    { return distance; }
    public void setDistance(String v) { this.distance = v; }

    public String getPrice()       { return price; }
    public void setPrice(String v) { this.price = v; }

    public String getDate()        { return date; }
    public void setDate(String v)  { this.date = v; }
}
