package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Parking service test.
 */
@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;
    private static Ticket ticket;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @Captor
    private static ArgumentCaptor<Ticket> captor;

    @BeforeAll
    private static void setUpAll() {
        ticket = null;
        ticket = new Ticket();
        ticket.setInTime(new Date());
        ticket.setPrice(0);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
    }

    @BeforeEach
    private void setUpPerTest() {
        try {
            lenient().when(inputReaderUtil.readSelection()).thenReturn(1);
            lenient().when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(ticket.getVehicleRegNumber());
            lenient().when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            lenient().when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
            lenient().when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
            lenient().when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up test mock objects");
        }
    }

    @Test
    public void processIncomingVehicleTest() {
        Ticket ticket = null;
        parkingService.processIncomingVehicle();
        verify(ticketDAO).saveTicket(captor.capture());
        ticket = captor.getValue();
        assertTrue(ticket != null);
    }

    /**
     * Process exiting vehicle test.
     */
    @Test
    public void processExitingVehicleTest(){
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }


    /**
     * Fail with invalid vehicle type.
     *
     * @throws Exception the exception
     */
    @Test
    public void failWithInvalidVehicleType() throws Exception {
        when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(0);
        when(inputReaderUtil.readSelection()).thenReturn(1);

        Exception thrown = assertThrows(
                Exception.class,
                () -> parkingService.getNextParkingNumberIfAvailable(),
                "Expected doThing() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("Error fetching parking number"));

    }
}



