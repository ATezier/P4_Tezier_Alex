package com.parkit.parkingsystem.unit;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingSpotDAOTest {

    private static ParkingSpotDAO parkingSpotDAO;

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    public void testGetNextAvailableSlot() {
        assertEquals(false, (parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)==-1));
    }

    @Test
    public void testUpdateParking() {
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
        assertEquals(true, parkingSpotDAO.updateParking(parkingSpot));
    }
}
