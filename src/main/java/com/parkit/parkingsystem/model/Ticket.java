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
        if (id < 1) throw new IllegalArgumentException("An ID must be greater than 0");
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        if (this.parkingSpot == null) return null;
        return new ParkingSpot(this.parkingSpot.getId(), this.parkingSpot.getParkingType(), this.parkingSpot.isAvailable());
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        if (parkingSpot == null) this.parkingSpot = null;
        else {
            this.parkingSpot = new ParkingSpot(parkingSpot.getId(), parkingSpot.getParkingType(), parkingSpot.isAvailable());
        }
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
        if (price < 0.0) throw new IllegalArgumentException("The price must be positive");
        this.price = price;
    }

    public Date getInTime() {
        if (this.inTime == null) return null;
        else {
            return new Date(this.inTime.getTime());
        }
    }

    public void setInTime(Date inTime) {
        if (inTime == null) this.inTime = null;
        else {
            this.inTime = new Date(inTime.getTime());
        }
    }

    public Date getOutTime() {
        if (outTime == null) return null;
        return new Date(outTime.getTime());
    }

    public void setOutTime(Date outTime) {
        if (outTime == null) this.outTime = null;
        else {
            this.outTime = new Date(outTime.getTime());
        }
    }

    public void truncatePrice() {
        if (this == null) throw new NullPointerException("The ticket shouldn't be null");
        Double truncatedDouble = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
        price = truncatedDouble;
    }
}
