/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GtfsStructures;

import java.util.Map;

/**
 *
 * @author swacisko
 */
public class Trip {
    public Trip( Map<String,String> d ){
        data = d;
        
    }
    
    public String getRouteId(){
        return data.get("route_id");        
    }
    
    public String getServiveId(){
        return data.get("service_id");
    }
    
    public String getTripId(){
        return data.get("trip_id");
    }
    
    public String getShapeId(){
        return data.get("shape_id");
    }
    
    
    
    
    private Map<String,String> data = null;
    
}
