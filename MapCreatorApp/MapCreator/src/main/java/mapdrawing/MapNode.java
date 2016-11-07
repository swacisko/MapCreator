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

    public MapNode() {
        super();
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
            System.out.println("Zle indeksowanie w funkcji getMapEdge() w MapNode, zwracam null");
            return null;
        } else {
            return edges.get(index);
        }
    }    
    
    // NIE MA SENSU PISAC FUNKCJI getMapEdgeByID, poniewaz gdyby to ID bylo znane, to moznaby wziac ta krawedz prosto z grafu, a nie z listy krawedzi incydentnych

    public MapNode getNeighbour(int index) {
        if (index >= edges.size()) {
            System.out.println("Zle indeksowanie w getNieghbour w MapNode, zwracam null");
            return null;
        } else {
            Pair<MapNode,MapNode> p = edges.get(index).getEnds();
            if( p.getST().getID() == getID() ){
                return p.getND();
            }
            else return p.getST();
        }
    }
    
    // NIE MA SENSU PISAC FUNKCJI getNeighbourByID, poniewaz gdyby to ID bylo znane, to moznaby wziac tego sasiada prosto z grafu, a nie z 'listy sasiedztwa' wierzcholka

    private ArrayList<MapEdge> edges = null; // to sa krawedzie o jednym z konców w danym wierzcholku
    private Pair<Float, Float> coords = null; // to sa wspolrzedne danego wierzcholka na mapie, PRZED NORMALIZACJA!!! czyli po prostu wspolrzedne z GTFS
    // NORMALIZACJA WSPOLRZEDNYCH BEDZIE NASTEPOWALA TUZ PRZED WYPISYWANIEM GOTOWEJ STRUKTURY GRAFU DO SVG

}
