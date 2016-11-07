/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdrawing;

import MCTemplates.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public void addMapEdge(MapEdge e) {
        edges.add(e);
    }

    // jezeli nie ma krawedzi o takim indeksie, to zwracam null
    public MapEdge getMapEdge(int index) {
        if (index >= edges.size()) {
            System.out.println("Zle indeksowanie w funkcji getMapEdge() w MapGraph, zwracam null");
            return null;
        } else {
            return edges.get(index);
        }
    }

    public MapEdge getMapEdgeByID(int id) {
        for (MapEdge e : edges) {
            if (e.getID() == id) {
                return e;
            }
        }
        System.out.println("Nie ma MapEdge o ID = " + id + "   w funkcji getMapEdgeByID w MapGraph, zwracam null");
        return null;
    }

    public void removeMapEdge(int index) {
        if (index >= edges.size()) {
            System.out.println("Zle indeksowanie w funkcji removeMapEdge() w MapGraph, nic nie usuwam");
            return;
        } else {
            removeMapEdgeByID(edges.get(index).getID());
        }
    }

    public void removeMapEdgeByID(int id) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getID() == id) {
                Pair<MapNode,MapNode> p = edges.get(i).getEnds();
                p.getST().removeMapEdgeByID(id);
                p.getND().removeMapEdgeByID(id);
                
                edges.remove(i);
                makeFreeID(id);
                return;
            }
        }

        System.out.println("Nie ma MapEdge o ID = " + id + ". Nic nie usuwam");
    }

    public void addMapNode(MapNode n) {
        nodes.add(n);
    }

    // jezeli nie ma krawedzi o takim indeksie, to zwracam null
    public MapNode getMapNode(int index) {
        if (index >= nodes.size()) {
            System.out.println("Zle indeksowanie w funkcji getMapNode w MapGraph(), zwracam null");
            return null;
        } else {
            return nodes.get(index);
        }
    }

    public MapNode getMapNodeByID(int id) {
        for (MapNode n : nodes) {
            if (n.getID() == id) {
                return n;
            }
        }
        System.out.println("Nie ma MapNode o ID = " + id + "   w funkcji getMapNodeByID w MapGraph, zwracam null");
        return null;
    }

    public void removeMapNode(int index) {
        if (index >= nodes.size()) {
            System.out.println("Zle indeksowanie w funkcji removeMapNode w MapGraph(), nic nie usuwam");
            return;
        } else {
            removeMapNodeByID( nodes.get(index).getID() );
        }
    }

    public void removeMapNodeByID(int id) {
        for (int i = 0; i < nodes.size(); i++) { // usuwam wierzcholek o danym id z listy wierzcholkow grafu
            if (nodes.get(i).getID() == id) {
                nodes.remove(i);
                makeFreeID(id);
                return;
            }
        }
        
        // czy tutaj sie nie popsuje cos?? czy usuwajac krawedz z edges nie posypie sie petla przelatujaca po wszystkich elementach z edges?
        for( MapEdge e : edges ){ // usuwam z listy krawedzi grafu wszystkie 
            if( e.hasEndInMapNodeOfID(id) ){
                removeMapEdgeByID( e.getID() );
            }
        }
        
        System.out.println("Nie ma MapNode o ID = " + id + ". Nic nie usuwam");
    }

    public static int getFreeID() {
        int p = 1;
        while (unavailableIds.contains(p)) {
            p++;
        }
        unavailableIds.add(p);
        return p;
    }

    // funkcja usuwa id z unavailableIds
    public static void makeFreeID(int id) {
        unavailableIds.remove(id);
    }

    private static Set<Integer> unavailableIds = new HashSet<>();

    private ArrayList<MapNode> nodes = null;
    private ArrayList<MapEdge> edges = null;

}
