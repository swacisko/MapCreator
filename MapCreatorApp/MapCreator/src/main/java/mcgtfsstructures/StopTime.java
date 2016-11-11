/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import java.util.Map;

/**
 *
 * @author swacisko
 */
public class StopTime extends GtfsStructure {
    
    public StopTime( Map<String,String> d ){
        super(d);
    }
    
    public String getTripId(){
        return getData().get( "trip_id" );
    }
    
    public String getArrivalTime(){
        return getData().get( "arrival_time" );
    }
    
    public String getDepartureTime(){
        return getData().get( "departure_time" );
    }
    
    public String getStopId(){
        return getData().get( "stop_id" );
    }
    
    public String getStopSequence(){
        return getData().get( "stop_sequence" );
    }   
    
}
