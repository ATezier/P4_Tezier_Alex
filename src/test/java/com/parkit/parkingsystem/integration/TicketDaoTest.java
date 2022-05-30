package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.WrongDataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketDaoTest {
    private static Ticket ticket = null;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;


    @Mock
    private static WrongDataBaseTestConfig wrongDataBaseTestConfig = new WrongDataBaseTestConfig();

    @BeforeAll
    private static void setUp() throws Exception {
        ticket = new Ticket();
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = wrongDataBaseTestConfig;
    }


    @Test
    public void testSaveTicket() throws SQLException, ClassNotFoundException {
        when(wrongDataBaseTestConfig.getConnection()).thenThrow(SQLException.class);
        Exception thrown = assertThrows(
                SQLException.class,
                () -> ticketDAO.saveTicket(ticket),
                "Expected doThing() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("Error fetching"));


    }
}
