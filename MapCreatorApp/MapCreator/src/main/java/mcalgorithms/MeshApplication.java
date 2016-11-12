/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import mcgraphs.MapGraph;
import mcmapdrawing.SVG;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */
public class MeshApplication {
    
    public MeshApplication( MapGraph g, SVG s ){
        graph = g;
        svg = s;
    }
    
    
    // glowna funkcja
    public MapGraph convertGraph(){
        
        
        
        return null;
    }
    
    // naklada na dany rysunek grafu (graph) wirtualna siatke, do ktorej bedzie przylepial poszczegolne elementy grafu - krawedzie, wierzcholki, przystanki itp
    // rows i columns to parametr okreslajacy liczbe poziomych oraz pionowych lini (krata wymiaru rows*columns)
    private void applyMesh1( int rows, int columns ){
        
        
    }
    
    // to samo co mesh1, tylko, ze sa jeszcze krawedzie 'po przekatnych' :)
    private void applyMesh2( int rows, int columns ){
               
        
    }
    
    // znajduje najblizsza krawedz na nalozonej siatce, do ktorej powinien sie przylepic
    private void findNearestEdge( Pair<Float,Float> point ){
        
        
    }
    
    
    
    
    
    private MapGraph graph = null;
    private SVG svg = null;
    private MapGraph resGraph = null; // to jest graf wynikowy - latwiej trzymac jako skladowa klasy niz przesylac do wzsystkich funkcji jako parametry
}
