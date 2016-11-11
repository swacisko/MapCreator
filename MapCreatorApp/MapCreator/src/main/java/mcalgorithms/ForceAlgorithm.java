/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import mcmapdrawing.MapGraph;
import mcmapdrawing.SVG;

/**
 *
 * @author swacisko
 */
public class ForceAlgorithm {
    
    public ForceAlgorithm( MapGraph g, SVG s ){
        graph = g;
        svg = s;
    }
    
    
    
    
    // glowna funkcja, zwraca dla wyznaczonego grafu graf wynikowy - czyli po zastosowaniu algorytmu siłowego
    public MapGraph convertGraph(){
        
        return resGraph;
    }
    
    
    
    
    
    
    private MapGraph graph = null;
    private SVG svg = null;
    private MapGraph resGraph = null;// to jest graf wynikowy - latwiej trzymac jako skladowa klasy niz przesylac do wzsystkich funkcji jako parametry

}
