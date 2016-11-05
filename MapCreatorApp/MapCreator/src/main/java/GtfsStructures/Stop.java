package GtfsStructures;

import java.util.Map;

/**
 *
 * @author swacisko
 */
public class Stop {
    
    public Stop( Map<String,String> mapa ){
        data = mapa;
    }
    
    public String getStopId(){
        return data.get( "stop_id" );        
    }
    
    public String getStopName(){
        return data.get( "stop_name" );
    }
    
    public String getStopLat(){
        return data.get( "stop_lat" );
    }
    
    public String getStopLon(){
        return data.get( "stop_lon" );
    }
     
    
    
    public void setData( Map<String,String> d ){
        data = d;
    }
    
    
    private Map<String,String> data = null;
    
    
}
