package com.parkit.parkingsystem.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id<1)throw new IllegalArgumentException("An ID must be greater than 0");
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price<0.0)throw new IllegalArgumentException("The price must be positive");
        this.price = price;
    }

    public Date getInTime() {
        if(this.inTime==null)return null;
        else{
            return new Date(this.inTime.getTime());
        }
    }

    public void setInTime(Date inTime) {
        if(inTime==null)this.inTime=null;
        else{
            this.inTime=new Date(inTime.getTime());
        }
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public void truncatePrice(){
        Double truncatedDouble = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
        price =truncatedDouble;
    }
}
