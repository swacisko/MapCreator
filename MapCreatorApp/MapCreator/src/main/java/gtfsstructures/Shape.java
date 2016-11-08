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
public class Shape extends GtfsStructure {

    
    public Shape(Map<String, String> d) {
        setData(d);
    }

    public String getShapeId() {
        return getData().get("shape_id");
    }

    public String getShapePtLat() {
        return getData().get("shape_pt_lat");
    }

    public String getShapePtLon() {
        return getData().get("shape_pt_lon");
    }

    public String getShapePtSequence() {
        return getData().get("shape_pt_sequence");
    }


}
