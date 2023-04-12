package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static com.parkit.parkingsystem.constants.Fare.DISCOUNT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * The type Parking data base IT.
 */
@ExtendWith(MockitoExtension.class)
public class TicketDAOIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private static Ticket ticket;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        ticketDAO = new TicketDAO();
        dataBasePrepareService = new DataBasePrepareService();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        ticket = new Ticket();
        ticket.setInTime(new Date());
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(1.0);
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){
        dataBasePrepareService.clearDataBaseEntries();
    }


    /**
     * Test save a ticket.
     */
    @Test
    public void testSaveTicket() {
        assertTrue(ticketDAO.saveTicket(ticket));
    }

    /**
     * Test get ticket
     */
    @Test
    public void testGetTicket() {
        ticketDAO.saveTicket(ticket);
        assertTrue(ticketDAO.getTicket(ticket.getVehicleRegNumber()) != null);
    }

    /**
     * Test update ticket.
     */
    @Test
    public void testUpdateTicket() {
        ticket.setOutTime(new Date());
        assertTrue(ticketDAO.updateTicket(ticket));
    }

    /**
     * Test check discount expected false.
     */
    @Test
    public void testCheckDiscountExpectedFalse() {
        assertFalse(ticketDAO.checkDiscount(ticket.getVehicleRegNumber()));
    }

    /**
     * Test check discount expected true.
     */
    @Test
    public void testCheckDiscountExpectedTrue() {
        ticketDAO.saveTicket(ticket);
        ticketDAO.saveTicket(ticket);
        ticketDAO.saveTicket(ticket);
        assertTrue(ticketDAO.checkDiscount(ticket.getVehicleRegNumber()));
    }

    /**
     * Test apply discount.
     */
    @Test
    public void testApplyDiscount() {
        ticketDAO.saveTicket(ticket);
        ticketDAO.saveTicket(ticket);
        ticketDAO.saveTicket(ticket);
        double oldPrice = ticket.getPrice();
        ticketDAO.applyDiscount(ticket);
        assertEquals(oldPrice*DISCOUNT, ticket.getPrice());
    }

}
