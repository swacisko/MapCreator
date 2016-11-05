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
public class Stop {

    public Stop(Map<String, String> d) {
        data = d;
    }

    public String getStopId() {
        return data.get("stop_id");
    }

    public String getStopName() {
        return data.get("stop_name");
    }

    public String getStopLat() {
        return data.get("stop_lat");
    }

    public String getStopLon() {
        return data.get("stop_lon");
    }

    private Map<String, String> data = null;
}
