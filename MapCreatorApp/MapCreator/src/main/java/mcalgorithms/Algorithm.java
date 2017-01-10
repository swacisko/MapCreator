/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import mcgraphs.MapGraph;

/**
 * {@link Algorithm} is an interface which must be implemented by any class with task to perform any kind of algorithm on the MapGraph.
 * @author swacisko
 */
public interface Algorithm {
    
    public MapGraph convertGraph( MapGraph graph );
    
}
