package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

/**
 * The type Parking spot.
 */
public class ParkingSpot {
    private int number;
    private ParkingType parkingType;
    private boolean isAvailable;

    /**
     * Instantiates a new Parking spot.
     *
     * @param number      the number
     * @param parkingType the parking type
     * @param isAvailable the is available
     */
    public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
        if (number < 1) throw new IllegalArgumentException("An ID must be greater than 0");
        this.number = number;
        this.parkingType = parkingType;
        this.isAvailable = isAvailable;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return number;
    }

    /**
     * Sets id.
     *
     * @param number the number
     */
    public void setId(int number) {
        if (number < 1) throw new IllegalArgumentException("An ID must be greater than 0");
        this.number = number;
    }

    /**
     * Gets parking type.
     *
     * @return the parking type
     */
    public ParkingType getParkingType() {
        return parkingType;
    }

    /**
     * Sets parking type.
     *
     * @param parkingType the parking type
     */
    public void setParkingType(ParkingType parkingType) {
        if (parkingType == null)throw new IllegalArgumentException("A parkingType is not null.");
        this.parkingType = parkingType;
    }

    /**
     * Is available boolean.
     *
     * @return the boolean
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets available.
     *
     * @param available the available
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}
