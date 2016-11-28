/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgraphs;

import java.util.ArrayList;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */
public class MapEdge extends MapStructure {

    public MapEdge() {
        super();
    }

    public void setEnds(Pair<MapNode, MapNode> p) {
        ends = p;
    }

    public Pair<MapNode, MapNode> getEnds() {
        return ends;
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
           
    private Pair<MapNode, MapNode> ends = new Pair<>(null, null);    
    private ArrayList<String> containedStopsIds = new ArrayList<>(); // lista zawierajaca wszystkie id przystankow, ktore zostały pominiete na mapie, w kolejnosci od ends.ST do ends.ND !! kolejnosc bardzi wazna!!!
    // ta lista jest przydatna do wypisywania przystankow, ktore znajduja sie na danym odcinku drogi
    


}
