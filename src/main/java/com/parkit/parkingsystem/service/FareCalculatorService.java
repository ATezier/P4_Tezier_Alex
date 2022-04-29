package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long durationMinutes = (ticket.getOutTime().getTime() - ticket.getInTime().getTime()) / 60000;

        //TODO: Some tests are failing here. Need to check if this logic is correct
        System.out.println(ticket.getInTime()+" to "+ticket.getOutTime());
        System.out.println("DurationMillis : "+durationMinutes);

        if(durationMinutes > 30){
            long durationHour=durationMinutes/60;
            durationMinutes%=60;
            double finalDuration = durationMinutes/60.0;
            finalDuration+=durationHour;
            switch (ticket.getParkingSpot().getParkingType()){
                case CAR: {
                    ticket.setPrice(finalDuration * Fare.CAR_RATE_PER_HOUR);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(finalDuration * Fare.BIKE_RATE_PER_HOUR);
                    break;
                }
                default: throw new IllegalArgumentException("Unkown Parking Type");
            }
        }
        else
        {
            ticket.setPrice(0);
        }
    }
}