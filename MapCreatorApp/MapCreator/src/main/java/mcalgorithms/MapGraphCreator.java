/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import mcgraphs.*;
import mcgtfsstructures.*;
import mcgtfsstructures.localGtfsDatabase;

/**
 *
 * @author swacisko
 */


public class MapGraphCreator {

    public MapGraph createMapGraphFromGtfsDatabase(){
        resGraph = new MapGraph();
        
        ArrayList<Stop> stops = localGtfsDatabase.getAllStops();
        for(Stop s : stops){
            MapNode node = new MapNode();
            node.setCoords( s.getCoords() );
            node.setDescription( s.getStopName() );
            node.setColor( s.getColor() );
            
            resGraph.addMapNode(node);
        }
        
        return resGraph;
    }
    
    
    private MapGraph graph = null;
    private MapGraph resGraph = null;
}
