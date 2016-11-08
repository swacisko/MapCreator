/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdrawing;

import MCTemplates.*;

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
    public boolean hasEndInMapNodeOfID( int id ){
        return (ends.getST().getID() == id) || (ends.getND().getID() == id);
    }
    
    @Override
    public String toString(){
        String s = super.toString();
        s += "\tends:  ID1 = " + ends.getST().getID() + "   ID2 = " + ends.getND().getID();
        return s;
    }

    private Pair<MapNode, MapNode> ends;

}
