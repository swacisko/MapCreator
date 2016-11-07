/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdrawing;

import MCTemplates.Pair;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author swacisko
 */
public class MapNode {
    
    public MapNode(  ){
        
    }
    
    
    
    
    public void setColor( Color c ){
        color = c;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setCoords( Pair<Float,Float> p ){
        coords = p;
    }
    
    public Pair<Float,Float> getCoords(){
        return coords;
    }
    
    public void addMapEdge( MapEdge e ){
        edges.add(e);
    }
    
    // jezeli nie ma krawedzi o takim indeksie, to zwracam null
    public MapEdge getMapEdge( int index ){
        if( index >= edges.size() ) return null;
        else return edges.get(index);
    }
    
    
    
    
    
    
    private Color color = null;
    
    private ArrayList<MapEdge> edges = null;
    
    private Pair<Float,Float> coords = null;
    
}
