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
public class Route {

    public Route(Map<String, String> d) {
        data = d;
    }

    public String getRouteId() {
        return data.get("route_id");
    }

    public String getRouteShortName() {
        return data.get("route_short_name");
    }

    public String getRouteLongName() {
        return data.get("route_long_name");
    }

    public String getRouteType() {
        return data.get("route_type");
    }

    private Map<String, String> data = null;
}
