/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdrawing;

import java.util.ArrayList;

/**
 *
 * @author swacisko
 */
public class MapGraph {

    public MapGraph() {

    }

    public ArrayList<MapNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<MapNode> list) {
        nodes = list;
    }

    public ArrayList<MapEdge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<MapEdge> list) {
        edges = list;
    }
    
    public void addEdge( MapEdge e ){
        edges.add(e);
    }
    
    // jezeli nie ma krawedzi o takim indeksie, to zwracam null
    public MapEdge getMapEdge(int index) {
        if (index >= edges.size()) {
            System.out.println( "Zle indeksowanie w funkcji getMapEdge() w MapGraph, zwracam null" );
            return null;
        } else {
            return edges.get(index);
        }
    }
    
     public void addMapNode(MapNode n) {
        nodes.add(n);
    }

    // jezeli nie ma krawedzi o takim indeksie, to zwracam null
    public MapEdge getMapNode(int index) {
        if (index >= nodes.size()) {
            System.out.println( "Zle indeksowanie w funkcji getMapNode w MapGraph(), zwracam null" );
            return null;
        } else {
            return edges.get(index);
        }
    }

    private ArrayList<MapNode> nodes = null;
    private ArrayList<MapEdge> edges = null;

}
