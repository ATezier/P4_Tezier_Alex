package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static com.parkit.parkingsystem.constants.Fare.PREMIUM_CLIENT;

/**
 * The type Ticket dao test.
 */
@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private static Ticket ticket;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket = new Ticket();
        ticket.setInTime(new Date(System.currentTimeMillis()));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(20);
        dataBasePrepareService.clearDataBaseEntries();
        ticketDAO.saveTicket(ticket);
    }



    @Test
    public void testUpdateTicket() {
        ticket.setOutTime(new Date(System.currentTimeMillis()));
        Assert.assertEquals(true, ticketDAO.updateTicket(ticket));
    }


    @Test
    public void testCheckDiscount(){
        Assert.assertEquals(false, ticketDAO.checkDiscount("ABCDEF"));
    }

    @Test
    public void testApplyDiscount(){
        for(int i = 1 ; i < PREMIUM_CLIENT ; i++){
            ticketDAO.saveTicket(ticket);
        }
        double oldPrice = ticket.getPrice();
        ticketDAO.applyDiscount(ticket);
        Assert.assertEquals(false, (oldPrice == ticket.getPrice()));
    }


    @Test
    public void testSaveTicket() {
        Assert.assertNotNull(ticketDAO.getTicket("ABCDEF"));
    }

}
