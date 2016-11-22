/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import java.awt.Color;
import mctemplates.Drawable;
import mctemplates.Pair;
import java.util.Map;

/**
 *
 * @author swacisko
 */
public class GtfsStructure implements Drawable {
   
    public GtfsStructure( Map<String,String> d ){
        data = d;
    }
       
    @Override
    public String toString(){
        return data.toString();
    }
     
    public Map<String,String> getData(){ return data; }
    
    public void setData( Map<String,String> d ){ data = d; }
    
    private Map<String, String> data = null;

    @Override
    public Pair<Float, Float> getCoords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    private Color color = null;
}
