package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * The type Data base prepare service.
 */
public class DataBasePrepareService {

    /**
     * The Data base test config.
     */
    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

    /**
     * Clear data base entries.
     */
    public void clearDataBaseEntries(){
        Connection connection = null;
        PreparedStatement ps = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set parking entries to available
            ps = connection.prepareStatement("update parking set available = true");
            ps.execute();

            //clear ticket entries;
            ps = connection.prepareStatement("truncate table ticket");
            ps.execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closePreparedStatement(ps);
            dataBaseTestConfig.closeConnection(connection);
        }
    }


}
