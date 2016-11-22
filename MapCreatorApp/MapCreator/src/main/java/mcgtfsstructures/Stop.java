/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import mctemplates.Drawable;
import mctemplates.Pair;
import java.util.Map;

/**
 *
 * @author swacisko
 */
public class Stop extends GtfsStructure implements Drawable{

    
    public Stop(Map<String, String> d) {
        super(d);
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

    @Override
    public Pair<Float, Float> getCoords() {
        if( coords == null ){
            float lat = Float.parseFloat( getStopLat() );
            float lon = Float.parseFloat( getStopLon() );
            coords = new Pair<>(lat,lon);
        }
        return coords;
    }
    
    public void setCoords(Pair<Float, Float> coords) {
        this.coords = coords;
    }
    
    private Pair<Float,Float> coords = null;

    

}
