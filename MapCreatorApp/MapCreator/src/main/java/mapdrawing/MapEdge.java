/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdrawing;

import java.awt.Color;
import MCTemplates.*;
/**
 *
 * @author swacisko
 */
public class MapEdge {
    
    
    
    
    
    public void setColor( Color c ){
        color = c;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setEnds( Pair<MapNode,MapNode> p ){
        ends = p;
    }
    public Pair<MapNode,MapNode> getEnds(){
        return ends;
    }
    
    
    
    
    
    
    
    private Color color = null;    
    private Pair<MapNode,MapNode> ends;
    
    
    
}
