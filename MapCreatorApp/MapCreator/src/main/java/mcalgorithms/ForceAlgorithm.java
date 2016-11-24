/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mcmapdrawing.SVG;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */
public class ForceAlgorithm {
    
    // uwaga - modyfikuje zadany graf, nie tworzy nowej kopii!!!!
    public ForceAlgorithm( MapGraph g, SVG s ){
        graph = g;
        svg = s;
    }
    
    private void createLBCandRUC(){
        LBC = new Pair<>( Float.MAX_VALUE, Float.MAX_VALUE );
        RUC = new Pair<>( Float.MIN_VALUE, Float.MIN_VALUE );
        
        for( MapNode n : graph.getNodes() ){
            Pair<Float,Float> p = n.getCoords();
            if( p.getST() < LBC.getST() ){
                LBC.setST( p.getST() );
            }
            
            if( p.getND() < LBC.getND() ){
                LBC.setND( p.getND() );
            }
            
            if( p.getST() > RUC.getST() ){
                RUC.setST( p.getST() );
            }
            
            if( p.getND() > RUC.getND() ){
                RUC.setND( p.getND() );
            }            
        }                
    }
    
    
        // glowna funkcja, zwraca dla wyznaczonego grafu graf wynikowy - czyli po zastosowaniu algorytmu siłowego
    public MapGraph convertGraph(){
        createLBCandRUC();
        
        
        
        
        return graph;
    }
    
    private void calculateForces(){
        
        
    }
    
    private void calculateForcesForNode( MapNode node ){
        
        
    }
    
    private void calculateAverageEdgeLength(){
        
        
    }
    
    
    public float getAverageEdgeLength() {
        return averageEdgeLength;
    }

    public void setAverageEdgeLength(float averageEdgeLength) {
        this.averageEdgeLength = averageEdgeLength;
    }
    
    
    
    
    private MapGraph graph = null;
    private SVG svg = null;
    private Pair<Float,Float> LBC = null;
    private Pair<Float,Float> RUC = null;
    
    private float averageEdgeLength = 1;

    
}
