/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import mctemplates.Drawable;
import mctemplates.Pair;
import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author swacisko
 */
public class Shape extends GtfsStructure implements Drawable{

    
    public Shape(Map<String, String> d) {
        super(d);
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

    @Override
    public Pair<Float, Float> getCoords() {
        if( coords == null){
            float lat = Float.parseFloat( getShapePtLat() );
            float lon = Float.parseFloat( getShapePtLon() );
            coords = new Pair<>( lat,lon );
        }
        
        return coords;
    }
    
    private Pair<Float,Float> coords = null;

}


class ShapeSequenceComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        Shape s1 = (Shape)o1;
        Shape s2 = (Shape)o2;
        
        int f1 = Integer.parseInt(s1.getShapePtSequence() );
        int f2 = Integer.parseInt(s2.getShapePtSequence() );
        
        if( f1 < f2 ) return -1;
        else if( f1 > f2 ) return 1;
        else return 0;        
    }
    
}