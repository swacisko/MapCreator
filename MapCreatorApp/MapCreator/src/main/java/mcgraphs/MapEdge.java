/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgraphs;

import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Collections;
import mctemplates.MCConstants;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */
public class MapEdge extends MapStructure {

    public MapEdge() {
        super();
        setColor( MCConstants.getINITIAL_EDGE_COLOR() );
        setHoverColor( MCConstants.getINITIAL_EDGE_HOVER_COLOR() );
        setDrawingWidth( MCConstants.getINITIAL_EDGE_WIDTH() );
        setHoverWidth( MCConstants.getINITIAL_EDGE_HOVER_WIDTH() );
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
    
    
    //UWAGA - TO JESZCZE NIE DZIALA!
    public float getLength(){
        return -1;
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
    
    
           
    private Pair<MapNode, MapNode> ends = new Pair<>(null, null);    
    private ArrayList<String> containedForwardStopsIds = new ArrayList<>(); // lista zawierajaca wszystkie id przystankow, ktore zostały pominiete na mapie, w kolejnosci od ends.ST do ends.ND !! kolejnosc bardzi wazna!!!
    // ta lista jest przydatna do wypisywania przystankow, ktore znajduja sie na danym odcinku drogi
    // lista Forward to przystanki znajdujace sie na drodze getEnds().getST() -> getEnds().getND()
    private ArrayList<String> containedBackwardStopsIds = new ArrayList<>(); // lista Backward to kolejne przystanki na drodze getEnds().getND() -> getEnds().getST()

    


}
