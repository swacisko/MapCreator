
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgraphs;

import java.awt.Color;
import java.awt.Font;
import mctemplates.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import mcalgorithms.GraphGlueing;
import mctemplates.Drawable;
import mctemplates.MCSettings;

/**
 *
 * @author swacisko
 */
public class MapNode extends MapStructure implements Drawable {

    public MapNode() {
        super();
        setColor(MCSettings.getINITIAL_NODE_COLOR() );
        setHoverColor(MCSettings.getINITIAL_NODE_HOVER_COLOR() );
        setDrawingWidth(MCSettings.getINITIAL_NODE_WIDTH() );
        setHoverWidth(MCSettings.getINITIAL_NODE_HOVER_WIDTH() );
    }

    public void setCoords(Pair<Float, Float> p) {
        coords = p;
    }

    @Override
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
            Pair<MapNode, MapNode> p = edges.get(index).getEnds();
            if (p.getST().getID() == getID()) {
                return p.getND();
            } else {
                return p.getST();
            }
        }
    }
    
    public boolean hasNeighbourOfId( int id ){
        for( MapEdge e : edges ){
            if( e.hasEndInMapNodeOfID( id ) ) return true;
        }
        return false;
    }
    
    public void removeAllLoops(){
       for( MapEdge e : edges ){
           if( e.getEnds().getST().getID() == e.getEnds().getND().getID() ){
               edges.remove( e );
               removeAllLoops();
               return;               
           }
       }
        
    }
    
    // NIE MA SENSU PISAC FUNKCJI getNeighbourByID, poniewaz gdyby to ID bylo znane, to moznaby wziac tego sasiada prosto z grafu, a nie z 'listy sasiedztwa' wierzcholka
    // funkcja usuwa krawedz, ale nie usuwa tej krawedzi z listy krawdzi sasiada (ta funkcja powinna być wiec wywolywana tylko przez funkcje MapGraph.removeMapEdgeByID
    public void removeMapEdgeByID(int id) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getID() == id) {
                edges.remove(i);
                return;
            }
        }
        System.out.println("Nie ma krawedzi o ID = " + id + " w removeMapEdgeByID w MapNode");
    }
    
    public ArrayList<MapEdge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<MapEdge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        String s = super.toString();
        s += "\tneighbours:\t";
        for( MapNode n : getNeighbours() ){
            s += n.getID() + " ";
        }       
      //  s += "\tcoords:\t" + coords;
        return s;
    }
    
    // funkcja zwraca liczbe krawedzi
    public int countEdges(){
        return edges.size();
    }
    
    /**
     * 
     * @return returns number of neighbours of given node. This number may vary from the number of its edge if graph is a multigraph.
     */
    public int countNeighbours(){
        ArrayList<MapNode> neigh = getNeighbours();
        Set<Integer> diffNeigh = new HashSet<>();
        for( MapNode n : neigh ){
            diffNeigh.add( n.getID() );
        }
        return diffNeigh.size();
    }
    
    /**
     * 
     * @return returns the list of neighbours of given node. Fixed naighbour may occur many times on that list if a graph is a multigraph
     */
    public ArrayList<MapNode> getNeighbours(){
        ArrayList<MapNode> neigh = new ArrayList<>();
        for( MapEdge e : edges ){
            Pair<MapNode,MapNode> ends = e.getEnds();
            if( ends.getST().equals(this) == true ){
                neigh.add( ends.getND() );
            }
            else{
                neigh.add( ends.getST() );
            }
        }
        return neigh;
    }
    
    public boolean isContractable() {
      //  return (countNeighbours() == 2 ) && (containedStopsIds.size() < 4);
        return contractable;
    }

    public void setContractable(boolean contractable) {
        this.contractable = contractable;
    }
    
    public ArrayList<String> getContainedStopsIds() {
        return containedStopsIds;
    }

    public void setContainedStopsIds(ArrayList<String> containedStopIds) {
        this.containedStopsIds = containedStopIds;
    }

    public void addContainedStopsId( String id ){
        containedStopsIds.add(id);
    }
    
    public boolean containsStopOfId( String id ){
        return containedStopsIds.contains( id );
    }
    
    public void removeContainedStopById( String id ){
        containedStopsIds.remove( id );
    }
    
    // zwraca wartosc ladunku danego wierzcholka - czyli liczbe zawartych przez niego przystankow + INITIAL_CHARGE_VALUE
    public float getCharge(){        
        float charge = MCSettings.getINITIAL_NODE_CHARGE();
        //charge += (float) containedStopsIds.size();
        return charge;
    }
    
    public int calculateMapNodeDrawingRadius(){
        return Math.min(10, getDrawingWidth() - 1 + containedStopsIds.size() );
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
        
    
    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    
    /**
     * variable contractable is used to either allow or disable {@link GraphGlueing#glueGraphOld() } function to glue this node with other nodes 
     */
    private boolean contractable = true;         
    private ArrayList<MapEdge> edges = new ArrayList<>(); // to sa krawedzie o jednym z konców w danym wierzcholku
    private Pair<Float, Float> coords = new Pair<>(new Float(0), new Float(0)); // to sa wspolrzedne danego wierzcholka na mapie, PRZED NORMALIZACJA!!! czyli po prostu wspolrzedne z GTFS
    // NORMALIZACJA WSPOLRZEDNYCH BEDZIE NASTEPOWALA TUZ PRZED WYPISYWANIEM GOTOWEJ STRUKTURY GRAFU DO SVG

    private int width = MCSettings.getINITIAL_NODE_WIDTH();
    private int height = MCSettings.getINITIAL_NODE_WIDTH();
    
    private ArrayList<String> containedStopsIds = new ArrayList<>();
    
    
    private Color fillColor = MCSettings.getINITIAL_FILL_COLOR();
    
    
    
   

}
