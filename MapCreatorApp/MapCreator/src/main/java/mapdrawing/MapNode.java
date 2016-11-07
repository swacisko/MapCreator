/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdrawing;

import MCTemplates.Pair;
import java.util.ArrayList;

/**
 *
 * @author swacisko
 */
public class MapNode extends MapStructure {

    public MapNode(int id) {
        super(id);
    }

    public void setCoords(Pair<Float, Float> p) {
        coords = p;
    }

    public Pair<Float, Float> getCoords() {
        return coords;
    }

    public void addMapEdge(MapEdge e) {
        edges.add(e);
    }

    // jezeli nie ma krawedzi o takim indeksie, to zwracam null
    public MapEdge getMapEdge(int index) {
        if (index >= edges.size()) {
            System.out.println( "Zle indeksowanie w funkcji getMapEdge() w MapNode, zwracam null" );
            return null;
        } else {
            return edges.get(index);
        }
    }
    
    
    
    
    

    private ArrayList<MapEdge> edges = null; // to sa krawedzie o jednym z konców w danym wierzcholku
    private Pair<Float, Float> coords = null; // to sa wspolrzedne danego wierzcholka na mapie, PRZED NORMALIZACJA!!!
    // NORMALIZACJA WSPOLRZEDNYCH BEDZIE NASTEPOWALA TUZ PRZED WYPISYWANIEM GOTOWEJ STRUKTURY GRAFU DO SVG

}
