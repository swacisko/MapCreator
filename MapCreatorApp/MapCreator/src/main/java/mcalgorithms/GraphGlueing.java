/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mctemplates.LongestCommonSubstring;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 *
 *
 */
// ten algorytm skleja nam graf zadany poprzez wszystkie przystanki i polaczenia w graf, w ktorym te same przystanki sa ze soba utozsamiane
public class GraphGlueing{

    public GraphGlueing( MapGraph graph ) {
        this.graph = graph;
    }

    public MapGraph getGluedGraph() {
        
        
        return resGraph;
    }

    public void setGraph(MapGraph graph) {
        this.graph = graph;
    }

    public MapGraph getGraph() {
        return graph;
    }
    
    
    public void testGlueing(){
        ArrayList<MapNode> nodes = graph.getNodes();
        
        for( int i=0; i<nodes.size(); i++ ){
            for(int k=i+1; k<nodes.size(); k++){
                if( similarName( nodes.get(i).getDescription(), nodes.get(k).getDescription() ) ){
                    if( coordinatesRoughlyTheSame( nodes.get(i).getCoords(), nodes.get(k).getCoords() ) ) {
                        System.out.println( "Names   " + nodes.get(i).getDescription()+ "   and   " + nodes.get(k).getDescription()+ "   are similar!"  );
                        System.out.println( "\tFurtheremore, coordinates " + nodes.get(i).getCoords() + "   and   " + nodes.get(k).getCoords() + "  are similar!\n" );
                    }
                }
            }
        }
    }
    
    public void testSimilarNames(){
        ArrayList<MapNode> nodes = graph.getNodes();
        
        for( int i=0; i<nodes.size(); i++ ){
            for(int k=i+1; k<nodes.size(); k++){
                if( similarName( nodes.get(i).getDescription(), nodes.get(k).getDescription() ) ){
                    System.out.println( "Names   " + nodes.get(i).getDescription() + "   and   " + nodes.get(k).getDescription() + "   are similar!" );
                }
            }
        }
        
    }
    
    private boolean similarName( String s1, String s2 ){
        String lcs = LongestCommonSubstring.getLongestCommonSubstring(s1, s2);
        double ratio = (double)lcs.length();
        ratio /= (double) (s1.length() + s2.length());
        ratio *= 2;
        if( ratio >= 0.5 ){
            return true;
        }
        else return false;        
    }
    
    // ta funkcja dziala dobrze tylko dla wspolrzednych rzeczywistych, a nie dla wspolrzednych wierzcholka na mapie
    private boolean coordinatesRoughlyTheSame( Pair<Float,Float> c1, Pair<Float,Float> c2 ){
        float f1x = 10000 * c1.getST();
        float f1y = 10000 * c1.getND();
        
        float f2x = 10000 * c2.getST();
        float f2y = 10000 * c2.getND();
        
        return ( Math.abs( f1x - f2x ) + Math.abs( f1y - f2y ) ) < 1;        
    }

    private MapGraph resGraph = null;
    private MapGraph graph = null;
}
