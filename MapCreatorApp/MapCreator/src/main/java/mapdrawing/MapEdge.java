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

    private Pair<MapNode, MapNode> ends;

}
