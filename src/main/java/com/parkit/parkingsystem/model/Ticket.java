package com.parkit.parkingsystem.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * The type Ticket.
 */
public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        if (id < 1) throw new IllegalArgumentException("An ID must be greater than 0");
        this.id = id;
    }

    /**
     * Gets parking spot.
     *
     * @return the parking spot
     */
    public ParkingSpot getParkingSpot() {
        if (this.parkingSpot == null) return null;
        return new ParkingSpot(this.parkingSpot.getId(), this.parkingSpot.getParkingType(), this.parkingSpot.isAvailable());
    }

    /**
     * Sets parking spot.
     *
     * @param parkingSpot the parking spot
     */
    public void setParkingSpot(ParkingSpot parkingSpot) {
        if (parkingSpot == null) this.parkingSpot = null;
        else {
            this.parkingSpot = new ParkingSpot(parkingSpot.getId(), parkingSpot.getParkingType(), parkingSpot.isAvailable());
        }
    }

    /**
     * Gets vehicle reg number.
     *
     * @return the vehicle reg number
     */
    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    /**
     * Sets vehicle reg number.
     *
     * @param vehicleRegNumber the vehicle reg number
     */
    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        if (price < 0.0) throw new IllegalArgumentException("The price must be positive");
        this.price = price;
    }

    /**
     * Gets in time.
     *
     * @return the in time
     */
    public Date getInTime() {
        if (this.inTime == null) return null;
        else {
            return new Date(this.inTime.getTime());
        }
    }

    /**
     * Sets in time.
     *
     * @param inTime the in time
     */
    public void setInTime(Date inTime) {
        if (inTime == null) this.inTime = null;
        else {
            this.inTime = new Date(inTime.getTime());
        }
    }

    /**
     * Gets out time.
     *
     * @return the out time
     */
    public Date getOutTime() {
        if (outTime == null) return null;
        return new Date(outTime.getTime());
    }

    /**
     * Sets out time.
     *
     * @param outTime the out time
     */
    public void setOutTime(Date outTime) {
        if (outTime == null) this.outTime = null;
        else {
            this.outTime = new Date(outTime.getTime());
        }
    }

    /**
     * Truncate price.
     */
    public void truncatePrice() {
        if (this == null) throw new NullPointerException("The ticket shouldn't be null");
        Double truncatedDouble = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
        price = truncatedDouble;
    }
}
