package com.parkit.parkingsystem;

import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The type Ticket unit test.
 */
public class TicketUnitTest {

    private Ticket ticket;

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    /**
     * Set wrong id.
     */
    @Test
    public void setWrongId(){
        try{
            ticket.setId(-1);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals("An ID must be greater than 0", e.getMessage());
        }
    }

    /**
     * Set negative price.
     */
    @Test
    public void setNegativePrice(){
        try{
            ticket.setPrice(-1);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals("The price must be positive", e.getMessage());
        }
    }
}
