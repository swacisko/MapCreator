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
public class Shape {

    public Shape(Map<String, String> d) {
        data = d;
    }

    public String getShapeId() {
        return data.get("shape_id");
    }

    public String getShapePtLat() {
        return data.get("shape_pt_lat");
    }

    public String getShapePtLon() {
        return data.get("shape_pt_lon");
    }

    public String getShapePtSequence() {
        return data.get("shape_pt_sequence");
    }

    private Map<String, String> data = null;

}
