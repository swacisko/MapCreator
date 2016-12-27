/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgraphs;

import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Collections;
import mctemplates.MCSettings;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */
public class MapEdge extends MapStructure {

    public MapEdge() {
        super();
        setColor(MCSettings.getINITIAL_EDGE_COLOR() );
        setHoverColor(MCSettings.getINITIAL_EDGE_HOVER_COLOR() );
        setDrawingWidth(MCSettings.getINITIAL_EDGE_WIDTH() );
        setHoverWidth(MCSettings.getINITIAL_EDGE_HOVER_WIDTH() );
        setTextFontSize( (3*MCSettings.getINITIAL_TEXT_FONT_SIZE() / 4) );
        setShape(MCSettings.RECTANGLE);
    }

    public void setEnds(Pair<MapNode, MapNode> p) {
        ends = p;
    }

    public Pair<MapNode, MapNode> getEnds() {
        return ends;
    }
    
    // zamienia kolejnosc wierzcholkow ST->ND, a ND->ST
    // odwraca rowniez kolejnosc przystankow na listach Forward i Backward
    public void swapEnds(){
        MapNode temp = ends.getST();
        ends.setST( ends.getND() );
        ends.setND( temp );
        
        ArrayList<String> templist = containedForwardStopsIds; // przy zamianie wierzcholkow NIE ODWRACAM kolejnosci list, lecz musze je SWAPOWAC!
        containedForwardStopsIds = containedBackwardStopsIds;
        containedBackwardStopsIds = templist;        
    }

    // zwraca true, jezeli jeden z koncow krawedzi ma ID = id
    public boolean hasEndInMapNodeOfID(int id) {
        return ((ends.getST().getID() == id) || (ends.getND().getID() == id));
    }

    @Override
    public String toString() {
        String s = super.toString();
        s += "\tends:  ID1 = " + ends.getST().getID() + "   ID2 = " + ends.getND().getID();
        return s;
    }
    
    // funkcja getLength jest przydatna do obliczania dlugosci krawedzi w ForceAlgorithm
    // jako ze kazda krawedz ma swoja dlugosc, wiec jest to funkcja skladowac klasy
    public float getLength(){
        MapNode n1 = ends.getST();
        MapNode n2 = ends.getND();
        Pair<Float,Float> p1 = n1.getCoords();
        Pair<Float,Float> p2 = n2.getCoords();
                
        float diffx = Math.abs( p1.getST() - p2.getST() );
        float diffy = Math.abs( p1.getND() - p2.getND() );
        
        float res = (float)Math.sqrt( diffx*diffx + diffy*diffy );
        return res;        
    }    
    
    public ArrayList<String> getContainedForwardStopsIds() {
        return containedForwardStopsIds;
    }

    public void setContainedForwardStopsIds(ArrayList<String> containedForwardStopsIds) {
        this.containedForwardStopsIds = containedForwardStopsIds;
    }

    public ArrayList<String> getContainedBackwardStopsIds() {
        return containedBackwardStopsIds;
    }

    public void setContainedBackwardStopsIds(ArrayList<String> containedBackwardStopsIds) {
        this.containedBackwardStopsIds = containedBackwardStopsIds;
    }

    public int getContainedStopsWidth() {
        return containedStopsWidth;
    }

    public void setContainedStopsWidth(int containedStopsWidth) {
        this.containedStopsWidth = containedStopsWidth;
    }

    public int getContainedStopsHeight() {
        return containedStopsHeight;
    }

    public void setContainedStopsHeight(int containedStopsHeight) {
        this.containedStopsHeight = containedStopsHeight;
    }
    
    
    /**
     * Functions returns an edge, which, apart from ID, is identical with this
     * @return 
     */
    public MapEdge getMapEdgeCopy(){
        MapEdge e = new MapEdge();
        e.setEnds(ends);
        e.setContainedForwardStopsIds(containedForwardStopsIds);
        e.setContainedBackwardStopsIds(containedBackwardStopsIds);
        return e;
    }
           
    private Pair<MapNode, MapNode> ends = new Pair<>(null, null);    
    private ArrayList<String> containedForwardStopsIds = new ArrayList<>(); // lista zawierajaca wszystkie id przystankow, ktore zostały pominiete na mapie, w kolejnosci od ends.ST do ends.ND !! kolejnosc bardzi wazna!!!
    // ta lista jest przydatna do wypisywania przystankow, ktore znajduja sie na danym odcinku drogi
    // lista Forward to przystanki znajdujace sie na drodze getEnds().getST() -> getEnds().getND()
    private ArrayList<String> containedBackwardStopsIds = new ArrayList<>(); // lista Backward to kolejne przystanki na drodze getEnds().getND() -> getEnds().getST()
    
    private int containedStopsWidth = MCSettings.getCONTAINED_STOPS_WIDTH();
    private int containedStopsHeight = MCSettings.getCONTAINED_STOPS_HEIGHT();
    
    


}
