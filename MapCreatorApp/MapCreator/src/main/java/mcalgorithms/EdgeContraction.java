/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mcgraphs.MapEdge;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */

// chociaz to w zasadzie nie jest kontrakcja krawedzi, tylko usuwanie wierzcholka stopnia 2 :) ale przez to scala sie dwie incydentne krawedzie
public class EdgeContraction {
    public EdgeContraction( MapGraph graph ){
        this.graph = graph;
    }
    
    
    private void createDeg2Vertices(){        
        System.out.print( "Wybieram wierzcholki stopnia 2" );
        deg2Nodes.clear();
        for( MapNode n : graph.getNodes() ){
            if( (n.countEdges() == 2) && n.isContractable() ){
                deg2Nodes.add(n);
            }
        }
        System.out.println();
    }
    
    // usuwa z grafu zadany wierzcholek stopnia 2
    private void removeDeg2NodeFromGraph( MapNode node ){
        ArrayList<MapEdge> edges = node.getEdges();
        if( edges.size() != 2 ){
            System.out.println( "edges.size() != 2 w removeDeg2NodeFromGraph, EdgeContraction" );
            return;
        }
        
        MapEdge leftEdge = edges.get(0); // jedna krawedz wychodzi z 'lewej' strony wierzcholka
        MapEdge rightEdge = edges.get(1); // a druga z prawej strony 
        
        Pair<MapNode,MapNode> leftPair = leftEdge.getEnds();
        Pair<MapNode,MapNode> rightPair = rightEdge.getEnds();
        
        MapNode leftNode = leftPair.getST();
        if( leftNode.getID() == node.getID()){                   
            leftNode = leftPair.getND();
            /*MapNode temp = leftPair.getST(); // ale ustawiam teraz tak kolejnosc, aby ST lewej rawdzi pokazywalo na cos, a ND lewej na wierzcholek node
            leftPair.setST( leftPair.getND() );
            leftPair.setND(temp);
            Collections.reverse( leftEdge.getContainedForwardStopsIds());
            Collections.reverse( leftEdge.getContainedBackwardStopsIds() );*/
            leftEdge.swapEnds();
        }
        
        MapNode rightNode = rightPair.getND();
        if( rightNode.getID() == node.getID() ){
            rightNode = rightPair.getST();
            /*MapNode temp = rightPair.getST();
            rightPair.setST( rightPair.getND() );
            rightPair.setND(temp);
            Collections.reverse( rightEdge.getContainedForwardStopsIds() );
            Collections.reverse( rightEdge.getContainedBackwardStopsIds() );*/
            rightEdge.swapEnds();
        }      
        
        
        MapEdge newEdge = new MapEdge();
               
        ArrayList<String> leftEdgeForwardList = leftEdge.getContainedForwardStopsIds();
        ArrayList<String> rightEdgeForwardList = rightEdge.getContainedForwardStopsIds();        
        ArrayList<String> newForwardList = new ArrayList<>();
        newForwardList.addAll(leftEdgeForwardList ); // najpierw dodaje lewa liste
        newForwardList.addAll( node.getContainedStopsIds() );
        newForwardList.addAll(rightEdgeForwardList ); // pozniej dodaje prawa liste
        newEdge.setContainedForwardStopsIds(newForwardList );
        
        ArrayList<String> leftEdgeBackwardList = leftEdge.getContainedBackwardStopsIds();
        ArrayList<String> rightEdgeBackwardList = rightEdge.getContainedBackwardStopsIds();
        ArrayList<String> newBackwardList = new ArrayList<>();
        newBackwardList.addAll( rightEdgeBackwardList ); // tuta najpierw dodaje prawa liste (bo to backward)
        Collections.reverse( node.getContainedStopsIds() ); // musze odwrocic oczywiscie kolejnosc przystankow w danym wierzcholku
        newBackwardList.addAll( node.getContainedStopsIds() );
        newBackwardList.addAll( leftEdgeBackwardList ); // a pozniej lewa
        newEdge.setContainedBackwardStopsIds(newBackwardList);
        
        newEdge.setColor( leftEdge.getColor() ); // ustawiam na kolor lewej krawedzi
        newEdge.setHoverColor( leftEdge.getHoverColor() );
        newEdge.setDrawingWidth( leftEdge.getDrawingWidth() );
        newEdge.setHoverWidth( leftEdge.getHoverWidth() );
        newEdge.setEnds( new Pair<>( leftNode, rightNode ) );
        
        newEdge.setDescription( "Contracted edge" );
        
        graph.removeMapNodeByID( node.getID() );
        graph.addMapEdge( newEdge );
    }
    
    
    private void createParallelEdges(){
        parallelEdges.clear();
        for( MapEdge e : graph.getEdges() ){
            Pair<MapNode,MapNode> ends = e.getEnds();
            int id1 = ends.getST().getID();
            int id2 = ends.getND().getID();
            
            if( id1 > id2 ){
                int temp = id1;
                id1 = id2;
                id2 = id1;
            }
            
            Pair<Integer,Integer> p = new Pair<>(id1,id2);
            
            if( parallelEdges.containsKey( p ) == false ){
                parallelEdges.put( p, new ArrayList<MapEdge>() );
            }
            
            parallelEdges.get(p).add(e);            
        }        
    }
    
    // scala mi dwie krawedzie w jedna i usuwa druga (ta juz niepotrzebna oczywiscie)
    private void mergeParallelEdges( MapEdge e1, MapEdge e2 ){
        if( e1.getEnds().getST().getID() != e2.getEnds().getST().getID() ){
            e1.swapEnds();
        }
        e1.setContainedBackwardStopsIds( e2.getContainedForwardStopsIds() );        
        graph.removeMapEdgeByID( e2.getID() );     
    }
    
    // moze sie zdarzyc, ze miedzy dwoma przystankami po kontrakcji sa dwie krawedzie - jedna dla przystankow w jedna strone, druga dla przystankow w druga strone
    // w takim przypadku zamieniam te dwie krawedzie na jedna krawedz
    // usuwam krawedzie rownolegle tylko wtedy, gdy sa dokladnie dwie rownolegle, gdy sa trzy lub wiecej to ich nie ruszam, bo to oznacza, ze 
    // zupelnie inna droga (a nie ta sama w dwie strony) mogla zostac sciagnieta do tej krawedzi
    private void removeParallelEdges(){
        System.out.println("Wybieram rownolegle krawedzie");
        createParallelEdges();
        
        int CNT = 1;
        int greaterThan2 = 0;
        for( Map.Entry< Pair<Integer,Integer>, ArrayList<MapEdge> > entry : parallelEdges.entrySet() ){            
            ArrayList<MapEdge> l = entry.getValue();
            if( l.size() == 2  ){
                System.out.print( "\rScalam " + (CNT++) + "-ta pare krawedzi" );
                mergeParallelEdges( l.get(0), l.get(1) );
            }else if( l.size() > 2 ){
                greaterThan2++;
            }         
        }
     //   System.out.println( "\nW grafie jest " + greaterThan2 + " rownoleglych zbiorow krawedzi mocy wiekszej lub rownej 3" ); // dla szczecina jest tylko jedna taka para
        System.out.println();
    }
    
    // po pierwszym scalaniu krawedzi sprawdza, czy listy Forward i Backward sa takie same tylko w odrwoconej kolejnosci
    private void testForwardAndBackwardLists(){
        System.out.println( "\nTestuje poprawnosc forward i backward lists" );
        int CNT = 1;
        int diff = 0;
        for( MapEdge e : graph.getEdges() ){
            System.out.print( "\rSprawdzam krawedz nr" + (CNT++) );
            ArrayList<String> forward = e.getContainedForwardStopsIds();
            ArrayList<String> backward = e.getContainedBackwardStopsIds();
            
            if( forward.size() != backward.size() ){
                System.out.println( "Rozne rozmiary list!   forward.size() = " + forward.size() + "   backwards.size() = " + backward.size() );
                System.out.println( "Forward:\t" + forward );
                System.out.println( "Backward:'t" + backward );                       
                        
                return;
            }
            else{
                if( forward.size() > 0 ) diff++;
                int p = 0;
                while( p < forward.size() ){
                    if( forward.get(p).equals( backward.get( backward.size()-1-p ) ) == false ){
                        System.out.println( "Takie same rozmiary, ale rozne przystanki lub rozna kolejnosc" );
                        
                        System.out.println( "Forward:\t" + forward );
                        System.out.println( "Backward:'t" + backward ); 
                        return;
                    }
                    
                    p++;
                }
            }            
        }
        System.out.println( "Skonczylem sprawdzac krawedzie - zgadza sie!  Bylo dokladnie " + diff + " niepustych list forward sposrod " + graph.countEdges() + " wszystkich krawedzi" );
    }
    
    // nie ma sensu wykonywac wiecej niz 1 'petli' postaci removeDeg2NodeFromGraph()->removeparallelEdges()->removeDeg2NodeFromGraph(), dla szczecina zmniejszy rozmiar grafu o 1 wierzcholek :)
    public MapGraph convertGraph(){
        createDeg2Vertices();
                
        int CNT = 1;
        for( MapNode n : deg2Nodes ){
            System.out.print( "\rDokonuje kontrakcji na wierzcholku nr " + (CNT++) );
            removeDeg2NodeFromGraph(n);
        }
        System.out.println("\nGraph ma teraz " + graph.countNodes() + " wierzcholkow oraz " + graph.countEdges() + " krawedzi");
        
        // testForwardAndBackwardLists();
        // UWAGA - po pierwszej petli dla wszystkich wierzcholkow w listach containedForwardStopsIds i containedBackwardStopsIds powinny byc te same elementy
        // ale w odwroconej kolejnosci.
        
        removeParallelEdges();       
        /*CNT = 1;
        createDeg2Vertices();
        for( MapNode n : deg2Nodes ){
            System.out.print( "\rDokonuje ponownej kontrakcji na wierzcholku nr " + (CNT++) );
            removeDeg2NodeFromGraph(n);
        }        
        System.out.println("\nGraph ma teraz " + graph.countNodes() + " wierzcholkow oraz " + graph.getEdges().countNodes() + " krawedzi");
        */  
        
        return graph;
    }
    
    
    
    
    private MapGraph graph = null;
    
    private ArrayList<MapNode> deg2Nodes = new ArrayList<>(); // lista wszystkich wierzcholkow stopnia 2 w grafie - tylko te wierzcholki usuwamy
    private Map< Pair<Integer,Integer>, ArrayList<MapEdge> > parallelEdges = new HashMap<>();
}
