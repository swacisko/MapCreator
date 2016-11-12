/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import java.util.Comparator;
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


class StopTimeSequenceComparator implements Comparator{
    
    @Override
    public int compare(Object o1, Object o2) {
        StopTime s1 = (StopTime)o1;
        StopTime s2 = (StopTime)o2;
        
        int f1 = Integer.parseInt(s1.getStopSequence());
        int f2 = Integer.parseInt(s2.getStopSequence());
        
        if( f1 < f2 ) return -1;
        else if( f1 > f2 ) return 1;
        else return 0;        
    }
}
