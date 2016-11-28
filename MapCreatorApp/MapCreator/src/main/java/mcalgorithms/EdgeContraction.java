/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;

/**
 *
 * @author swacisko
 */
public class EdgeContraction {
    public EdgeContraction( MapGraph graph ){
        this.graph = graph;
    }
    
    
    
    public MapGraph convertGraph(){
        
        
        
        
        return graph;
    }
    
    
    
    
    private MapGraph graph = null;
    
    private ArrayList<MapNode> deg2Vertices = new ArrayList<>(); // lista wszystkich wierzcholkow stopnia 2 w grafie - tylko te wierzcholki usuwamy
    
}
