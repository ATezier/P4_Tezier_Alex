package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class ParkingSpotUnitTest {

    private ParkingSpot parkingSpot;

    @BeforeEach
    private void setUpPerTest() {
        parkingSpot = new ParkingSpot(1, ParkingType.CAR,true);
    }

    @SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    @Test
    public void callConstructorWithInvalidId(){
        ParkingSpot parkingSpotTemp = null;
        try{
            parkingSpotTemp = new ParkingSpot(-1,ParkingType.CAR,true);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals("An ID must be greater than 0", e.getMessage());
        }
    }

    @Test
    public void setWrongNumber(){
        try{
            parkingSpot.setId(-1);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals("An ID must be greater than 0", e.getMessage());
        }
    }

    @Test
    public void setUninitialisedParkingType(){
        try{
            parkingSpot.setParkingType(null);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals("A parkingType is not null.", e.getMessage());
        }
    }
}
