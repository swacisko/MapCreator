/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * @author swacisko
 */

// klasa route opisuje poedyncze linie pliku routes.txt
public class Route extends GtfsStructure {

    
    public Route(Map<String, String> d) {
        super(d);
    }
    
    @Override
    public boolean equals( Object o ){
        if( !(o instanceof Route) ) return false;
        Route r = (Route) o;
        return getRouteId().equals( r.getRouteId() );
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + getRouteId().hashCode();
        return hash;
    }

    public String getRouteId() {
        return getData().get("route_id");
    }

    public String getRouteShortName() {
        return getData().get("route_short_name");
    }

    public String getRouteLongName() {
        return getData().get("route_long_name");
    }

    public String getRouteType() {
        return getData().get("route_type");
    }
    
    public ArrayList<String> getStopIds() {
        return stopIds;
    }

    public void setStopIds(ArrayList<String> stopIds) {
        this.stopIds = stopIds;
    }
    
    
    public void addStopId( String id ){
        stopIds.add(id);
    }
    
    public boolean containsStopOfId( String id ){
        return stopIds.contains(id);
    }
    
    public void removeStopOfId( String id ){
        stopIds.remove( id );
    }
    
    
    private ArrayList<String> stopIds = new ArrayList<>(); // stopIds to lista wszystkich przystankow znajdujacych sie an danej trasie

    
    
}
