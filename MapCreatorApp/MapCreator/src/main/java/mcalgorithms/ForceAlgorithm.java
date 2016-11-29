/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mcgraphs.MapEdge;
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
    
    // jako parametry - LBC - left bottom corner, RUC right upper corner i coords - wspolrzedne do znormalizowania
    // LBC i RUC to wspolrzedne najbardziej wysunietych struktur grafu z malym dodatkiem (np +- 10)
    private Pair<Integer, Integer> normalizeCoordinates(Pair<Float, Float> coords) {
        float x = coords.getST();
        float y = coords.getND();

        x -= LBC.getST();
        y -= LBC.getND(); // translacja tak, aby LBC byl w punkcie (0,0)

        float dW = RUC.getST() - LBC.getST();
        float dH = RUC.getND() - LBC.getND();

        if (dW == 0) {
            dW = 0.00001f;
        }
        if (dH == 0) {
            dH = 0.00001f;
        }

        y = dH - y; // zamiana wspolrzednych na wspolrzedne mapowe w svg        

        float W = (float) svg.getWidth();
        float H = (float) svg.getHeight();

        int x2 = Math.round((W * x) / dW);
        int y2 = Math.round((W * y) / dH);

        return new Pair<>(x2, y2);
    }
    
    // normalizuje wspolrzedne wierzcholkow danego grafu - daje im takie, jak gdyby rysowac je na svg - przez to bede mogl rozsadnie dobierac sily
    private void changeGraphCoordinates(){
        for( MapNode n : graph.getNodes() ){
            Pair<Integer,Integer> normCoords = normalizeCoordinates( n.getCoords() );
            Pair<Float,Float> newCoords = new Pair<>( (float)normCoords.getST(), (float) normCoords.getND() );
            n.setCoords( newCoords );
        }
    }
    
        // glowna funkcja, zwraca dla wyznaczonego grafu graf wynikowy - czyli po zastosowaniu algorytmu siłowego
    public MapGraph convertGraph(){
        createLBCandRUC();
        changeGraphCoordinates();
        
        
        
        return graph;
    }
    
    private Float getForceValue( Pair<Float,Float> force ){
        float diffx = force.getST();
        float diffy = force.getND();
        
        return (float)Math.sqrt( diffx*diffx + diffy*diffy );
    }
    
    private Float getMaxForceValue(){
        return getForceValue( getMaxForce() );
    }
    
    private Pair<Float,Float> getMaxForce(){
        Pair<Float,Float> maxForce = new Pair<>(0f,0f);        
        for( Map.Entry<Integer, Pair<Float,Float>> entry : forces.entrySet() ){
            if( getForceValue( entry.getValue() ) > getForceValue( maxForce ) ){
                maxForce = entry.getValue();
            }
        }
        return maxForce;
    }
    
    // licz i dodaje sily dla zadanej pary wierzcholkow
    private void addCoulombForce( MapNode n1, MapNode n2 ){
        
    }
    
    // liczy i aktualizuje sily miedzy wszystkimi parami wierzcholkow - prawo Coulomba
    private void calculateCoulombForces(){
        ArrayList<MapNode> nodes = graph.getNodes();
        for( int i=0; i<nodes.size(); i++ ){
            for( int k=0; k<nodes.size(); k++ ){
                addCoulombForce( nodes.get(i), nodes.get(k) );
            }
        }
    }
    
    // liczy i dodaje sily dla zadanej krawedzi
    private void addSpringForce( MapEdge e ){
        
    }
    
    // liczy i aktualizuje sily miedzy parami wierzcholkow polaczonych krawedziami - prawo Hooke'a
    private void calculateSpringForces(){
        for( MapEdge e : graph.getEdges() ){
            addSpringForce(e);
        }
    }
    
    private void calculateForces(){
        forces.clear();
        calculateCoulombForces();
        calculateSpringForces();
    }
    
    // oblicza wypadkowe sily dzialajace na wierzcholki w danej turze. Wynik jest w mapie forces
    private void calculateForcesForNode( MapNode node ){
        
        
    }
    
    // liczy srednia dlugosc krawedzi
    private void calculateAverageEdgeLength(){
        averageEdgeLength = 0;
        for( MapEdge e : graph.getEdges() ){
            averageEdgeLength += e.getLength();
        }
        
        averageEdgeLength /= (float) graph.countEdges();        
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
    private Map<Integer, Pair<Float,Float> > forces = new HashMap<>(); // forces to mapa, ktora dla kazdego wierzcholka przechowuje wypadkowy wektor sil dzialajacy na niego w danej chwili

    
}
