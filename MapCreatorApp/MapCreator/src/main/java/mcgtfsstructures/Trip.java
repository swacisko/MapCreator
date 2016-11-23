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
public class Trip extends GtfsStructure{

    
    public Trip(Map<String, String> d) {
        super(d);
    }
   
    public String getTripId(){
        return getData().get( "trip_id" );
    }
    
    public String getRouteId(){
        return getData().get( "route_id" );
    }
    
    public String getServiceId(){
        return getData().get( "service_id" );
    }

}
