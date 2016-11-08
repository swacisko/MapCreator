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
public class Stop extends GtfsStructure {

    
    public Stop(Map<String, String> d) {
        setData(d);
    }

    public String getStopId() {
        return getData().get("stop_id");
    }

    public String getStopName() {
        return getData().get("stop_name");
    }

    public String getStopLat() {
        return getData().get("stop_lat");
    }

    public String getStopLon() {
        return getData().get("stop_lon");
    }

}
