/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gtfsstructures;

import java.util.Map;

/**
 *
 * @author swacisko
 */
public class Route extends GtfsStructure {

    public Route(Map<String, String> d) {
        setData(d);
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
    
}
