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
import mctemplates.MCConstants;
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
        int y2 = Math.round((H * y) / dH);

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
        System.out.println( "Rozpoczynam algorytm siłowy" );
        for(int i=0; i<2; i++){ // musze to zrobic dwa razy, poniewaz normalizeCoordinates odwraca mi do gory nogami graf
            createLBCandRUC();
            changeGraphCoordinates();
        }
        
        
        CNT = 0;
        //writeNodesCoordinates();
        calculateForces();
        do{
            if( ++CNT % 20 == 0 ) System.out.print( "\rWykonuje w algorytmie silowym iteracje nr " + (CNT) + "\t\tmaxForceValue() = " + getMaxForceValue() );  
            
            applyForcesAndMoveNodes();
         //   System.out.println( "Przesunalem wszystkie wierzcholki" );
          //  writeNodesCoordinates();
            
            calculateForces();
          //  System.out.println( "Oto sily dzialajace na wierzcholki: " + forces );           
        }while( stopCondition() == false );
        System.out.println( "\nSkonczylem konwertowanie grafu, getMaxForceValue() = " + getMaxForceValue() );
                
        return graph;
    }
    
    // zwraca wartosc zadanego wektora siły (jego moduł)
    private Float getForceValue( Pair<Float,Float> force ){
        float diffx = force.getST();
        float diffy = force.getND();
        
        return (float)Math.sqrt( diffx*diffx + diffy*diffy );
    }
    
    // zwraca sile wektora o najwiekszej sile sposrod wektorow sil dzialajacych na wierzcholki
    private Float getMaxForceValue(){
        return getForceValue( getMaxForce() );
    }
    
    // zwraca wektor o najwiekszej sile, sposrod sil dzialajacych na wierzcholki
    private Pair<Float,Float> getMaxForce(){
        Pair<Float,Float> maxForce = new Pair<>(0f,0f);        
        for( Map.Entry<Integer, Pair<Float,Float>> entry : forces.entrySet() ){
            if( getForceValue( entry.getValue() ) > getForceValue( maxForce ) ){
                maxForce = entry.getValue();
            }
        }
        return maxForce;
    }
    
    // oblicza wartosc wektora o poczatku w punkcie beg i koncu w punkcie end
    // nastepnie normalizuje wektor
    private Pair<Float,Float> getNormalizedForceVector( Pair<Float,Float> beg, Pair<Float,Float> end ){
        Pair<Float,Float> vec = new Pair<>( end.getST() - beg.getST(), end.getND() - beg.getND() );
        float veclength = getForceValue( vec );
        if( veclength == 0 ) return new Pair<>(0f,0f);
        vec.setST( vec.getST() / veclength );
        vec.setND( vec.getND() / veclength );
        return vec;        
    }
    
    private Float getDistanceBetweenNodes( MapNode n1, MapNode n2 ){        
        Pair<Float,Float> p1 = n1.getCoords();
        Pair<Float,Float> p2 = n2.getCoords();
                
        float diffx = Math.abs( p1.getST() - p2.getST() );
        float diffy = Math.abs( p1.getND() - p2.getND() );
        
        float res = (float)Math.sqrt( diffx*diffx + diffy*diffy );
        return res;  
    }
    
        
    // licz i dodaje sily dla zadanej pary wierzcholkow
    private void addCoulombForce( MapNode n1, MapNode n2 ){
        float distance = getDistanceBetweenNodes(n1, n2);             
        
        Pair<Float,Float> dir1 = getNormalizedForceVector( n1.getCoords(), n2.getCoords() ); // to sa kierunki, w jakie dzialaja sily na dane wierzcholki
        Pair<Float,Float> dir2 = getNormalizedForceVector( n2.getCoords(), n1.getCoords() ); // to rownie dobrze mogloby byc po prostu  -direction1
        
        float charge1 = n1.getCharge();
        float charge2 = n2.getCharge(); 
        
        float mutualForce = MCConstants.getCOULOMB_COEF(); // sily beda sie odpychaly, poniewa COULOMB_COEF ma WARTOSC UJEMNA
        mutualForce *= charge1*charge2;
        mutualForce /= ( distance*distance );
        
      //  System.out.println( "\nCoulombForce  ID1 = " + n1.getID() + "   ID2 = " + n2.getID() + "\ndir1 = " + dir1 + "    dir2 = " + dir2 + "\ncharge1 = " + charge1 + "    charge2 = " + charge2 
     //               + "   mutualForce = " + mutualForce);
        
        Pair<Float,Float> force1 = new Pair<>( mutualForce * dir1.getST(), mutualForce * dir1.getND() );
        Pair<Float,Float> force2 = new Pair<>( mutualForce * dir2.getST(), mutualForce * dir2.getND() );
        
        if( forces.containsKey( n1.getID() ) == false ){ // jezeli nie ma jeszcze zadnych sil dzialajacych na dany wierzcholek to przyjmuje sile (0,0)
            forces.put( n1.getID(), new Pair<>(0f,0f) );
        }
        if( forces.containsKey( n2.getID() ) == false ){
            forces.put( n2.getID(), new Pair<>(0f,0f) );
        }
        
        Pair<Float,Float> currentForce1 = forces.get( n1.getID() );
        Pair<Float,Float> currentForce2 = forces.get( n2.getID() ); // te sily juz dzialajac na wierzcholek
        
        forces.remove(n1.getID());
        forces.put( n1.getID(), new Pair<>( currentForce1.getST() + force1.getST(), currentForce1.getND() + force1.getND() ) ); // nowa sila, ktora dziala na wierzcholek n1
        
        forces.remove( n2.getID() );
        forces.put( n2.getID(), new Pair<>( currentForce2.getST() + force2.getST(), currentForce2.getND() + force2.getND() ) ); // nowa sila, ktora dziala na wierzcholek n2
    }
    
    // liczy i aktualizuje sily miedzy wszystkimi parami wierzcholkow - prawo Coulomba
    private void calculateCoulombForces(){
        ArrayList<MapNode> nodes = graph.getNodes();
        for( int i=0; i<nodes.size(); i++ ){
            for( int k=i+1; k<nodes.size(); k++ ){
                addCoulombForce( nodes.get(i), nodes.get(k) );
            }
        }
    }
    
    // liczy i dodaje sily dla zadanej krawedzi
    private void addSpringForce( MapEdge e ){        
        Pair<MapNode,MapNode> ends = e.getEnds();
        MapNode n1 = ends.getST();
        MapNode n2 = ends.getND();
        
        Pair<Float,Float> dir1 = getNormalizedForceVector( n1.getCoords(), n2.getCoords() ); // to sa kierunki, w jakie dzialaja sily na dane wierzcholki
        Pair<Float,Float> dir2 = getNormalizedForceVector( n2.getCoords(), n1.getCoords() ); // to rownie dobrze mogloby byc po prostu  -direction1
        
        float deltaX = e.getLength(); // - averageEdgeLength; // jezeli ta wartosc jest dodatnia - to krawedz bedzie chciala sie skrocic - w przeciwnym razie krawedz bedzie chciala sie wydluzyc
        float lengthfactor = 1 + (float) Math.max( e.getContainedForwardStopsIds().size(), e.getContainedBackwardStopsIds().size() );   // krawedziem na ktorych jest wiecej przystankow musza byc dluzsze, wiec dziala na nie wieksza sila     
        if( deltaX < 0 ){
            deltaX *= lengthfactor;
        } else{
            //deltaX /= lengthfactor;
            float wsp = 0.3f;
            deltaX *= ( wsp + (1-wsp)* ( (float)1 / ( (float) lengthfactor ) ) );
        }
        float mutualForce = MCConstants.getSPRING_COEF(); // spring COEF jest dodatnia wartosci;
        mutualForce *= deltaX;
        
    //    System.out.println( "\nSpringForce  ID1 = " + n1.getID() + "   ID2 = " + n2.getID() + "\ndir1 = " + dir1 + "    dir2 = " + dir2 + "   mutualForce = " + mutualForce );
        
        Pair<Float,Float> force1 = new Pair<>( mutualForce * dir1.getST(), mutualForce * dir1.getND() );
        Pair<Float,Float> force2 = new Pair<>( mutualForce * dir2.getST(), mutualForce * dir2.getND() );
        
        if( forces.containsKey( n1.getID() ) == false ){ // jezeli nie ma jeszcze zadnych sil dzialajacych na dany wierzcholek to przyjmuje sile (0,0)
            forces.put( n1.getID(), new Pair<>(0f,0f) );
        }
        if( forces.containsKey( n2.getID() ) == false ){
            forces.put( n2.getID(), new Pair<>(0f,0f) );
        }
        
        Pair<Float,Float> currentForce1 = forces.get( n1.getID() );
        Pair<Float,Float> currentForce2 = forces.get( n2.getID() ); // te sily juz dzialaja na wierzcholek
        
        forces.remove(n1.getID());
        forces.put( n1.getID(), new Pair<>( currentForce1.getST() + force1.getST(), currentForce1.getND() + force1.getND() ) ); // nowa sila, ktora dziala na wierzcholek n1
        
        forces.remove( n2.getID() );
        forces.put( n2.getID(), new Pair<>( currentForce2.getST() + force2.getST(), currentForce2.getND() + force2.getND() ) ); // nowa sila, ktora dziala na wierzcholek n2
    }
    
    // liczy i aktualizuje sily miedzy parami wierzcholkow polaczonych krawedziami - prawo Hooke'a
    private void calculateSpringForces(){
        calculateAverageEdgeLength();
        for( MapEdge e : graph.getEdges() ){
            addSpringForce(e);
        }
    }
    
    private void calculateForces(){
        forces.clear();
        for( MapNode n : graph.getNodes() ){
            forces.put( n.getID(), new Pair<>(0f,0f) );
        }
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
     
    // zwraca true, jezeli algorytm ma przestac dzialac, czyli gdy juz mam przestac liczyc sily i zwrocic otrzymany graf
    // zwraca true, jezeli maksymalna sila wypadkowa dzialajaca na wierzcholki jest nie wieksza niz MCConstants.STOP_THRESHOLD
    private boolean stopCondition(){
        return getMaxForceValue() < MCConstants.getSTOP_THRESHOLD();
    }
    
    private void scaleForcesToUpperBound( float maxForce ){
        float factor = MCConstants.getFORCE_UPPER_BOUND_PER_TURN() / maxForce; // wszystkie sily nalezy pomnozyc przez ta wartosc
        for( Map.Entry<Integer,Pair<Float,Float>> entry : forces.entrySet() ){
            Pair<Float,Float> p = entry.getValue();
            p.setST( factor * p.getST() );
            p.setND( factor * p.getND() );
            entry.setValue(p); // ta linijka prawdopodobnie nie jest wogole potrzebna, poniewaz p jest referencja do pary, ale na wszelki wypadek ja zamieszczam
        }
        if( CNT % 20 == 0 ) System.out.print( "   forces scaled, now getMaxForceValue() = " + getMaxForceValue() );
    }
    
    // funkcja zmienia wspolrzedna grafu tak, aby najwieksza wartosci o jaka sie przesunie wierzcholek byla nie wieksza niz MCConstanst.FORCE_UPPER_BOUND_PER_TURN
    private void applyForcesAndMoveNodes(){
        float maxForce = getMaxForceValue();
        if( maxForce > MCConstants.getFORCE_UPPER_BOUND_PER_TURN() ){
            scaleForcesToUpperBound( maxForce );
        }
        
        for( MapNode n : graph.getNodes() ){
            Pair<Float,Float> force = forces.get( n.getID() );
            if( force == null ){
                System.out.println( "force = null!" );
                continue;
            }
            
            Pair<Float,Float> coords = n.getCoords();
            coords.setST( coords.getST() + force.getST() );
            coords.setND( coords.getND() + force.getND() );            
        }       
    }
    
    
    private void writeNodesCoordinates(){
        System.out.println( "Oto wspolrzedne wierzcholkow:" );
        for( MapNode n : graph.getNodes() ){
            System.out.println( "ID = " + n.getID() + "   coords = " + n.getCoords()  );
        }
        
    }
    
    private MapGraph graph = null;
    private SVG svg = null;
    private Pair<Float,Float> LBC = null;
    private Pair<Float,Float> RUC = null;
    
    private float averageEdgeLength = 1;
    private Map<Integer, Pair<Float,Float> > forces = new HashMap<>(); // forces to mapa, ktora dla kazdego wierzcholka przechowuje wypadkowy wektor sil dzialajacy na niego w danej chwili
    private int CNT = 0;
    
}
