package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import java.util.Date;

import java.sql.*;

public class CustomRequestDAO {
    private DataBaseTestConfig dataBaseTestConfig;

    public CustomRequestDAO() {
        dataBaseTestConfig = new DataBaseTestConfig();
    }

    public int getParkingSlotByVehicleRegNumber(String regNbr) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int parkingSlot = 0;
        try {
            con = dataBaseTestConfig.getConnection();
            ps = con.prepareStatement("select PARKING_NUMBER from ticket where VEHICLE_REG_NUMBER=? and OUT_TIME IS NULL");
            ps.setString(1,regNbr);
            rs = ps.executeQuery();
            if(rs.next()) {
                parkingSlot=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            dataBaseTestConfig.closePreparedStatement(ps);
            dataBaseTestConfig.closeResultSet(rs);
            dataBaseTestConfig.closeConnection(con);
        }
        return parkingSlot;
    }

    public Integer getAvalabilityByVehicleRegNumber(String regNbr) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int parkingSlot = getParkingSlotByVehicleRegNumber(regNbr);
        Integer available = null;
        try {
            con = dataBaseTestConfig.getConnection();
            ps = con.prepareStatement("select AVAILABLE from parking where PARKING_NUMBER=?");
            ps.setInt(1,parkingSlot);
            rs = ps.executeQuery();
            if(rs.next()){
                available = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            dataBaseTestConfig.closePreparedStatement(ps);
            dataBaseTestConfig.closeResultSet(rs);
            dataBaseTestConfig.closeConnection(con);
        }
        return available;
    }

    public void updateTicketInTimeValueFromVehicleRegNumber(String regNbr, Date inTime) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseTestConfig.getConnection();
            ps = con.prepareStatement("update ticket set IN_TIME=? where OUT_TIME is NULL and VEHICLE_REG_NUMBER=?");
            ps.setTimestamp(1,new Timestamp(inTime.getTime()));
            ps.setString(2,regNbr);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            dataBaseTestConfig.closePreparedStatement(ps);
            dataBaseTestConfig.closeConnection(con);
        }
    }

    public Ticket getCurrentTicketByVehicleRegNumber(String regNbr) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Ticket ticket = null;
        try {
            con = dataBaseTestConfig.getConnection();
            ps = con.prepareStatement("select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1");
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(1, regNbr);
            rs = ps.executeQuery();
            if(rs.next()){
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)),false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(2));
                ticket.setVehicleRegNumber(regNbr);
                ticket.setPrice(rs.getDouble(3));
                ticket.setInTime(rs.getTimestamp(4));
                ticket.setOutTime(rs.getTimestamp(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            dataBaseTestConfig.closeResultSet(rs);
            dataBaseTestConfig.closePreparedStatement(ps);
            dataBaseTestConfig.closeConnection(con);
        }
        return ticket;
    }
}
