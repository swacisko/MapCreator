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
        
        
        
    }
    
    // moze sie zdarzyc, ze miedzy dwoma przystankami po kontrakcji sa dwie krawedzie - jedna dla przystankow w jedna strone, druga dla przystankow w druga strone
    // w takim przypadku zamieniam te dwie krawedzie na jedna krawedz
    // usuwam krawedzie rownolegle tylko wtedy, gdy sa dokladnie dwie rownolegle, gdy sa trzy lub wiecej to ich nie ruszam, bo to oznacza, ze 
    // zupelnie inna droga (a nie ta sama w dwie strony) mogla zostac sciagnieta do tej krawedzi
    private void removeParallelEdges(){
        createParallelEdges();
        
        for( Map.Entry< Pair<Integer,Integer>, ArrayList<MapEdge> > entry : parallelEdges.entrySet() ){
            ArrayList<MapEdge> l = entry.getValue();
            if( l.size() == 2  ){
                mergeParallelEdges( l.get(0), l.get(1) );
            }            
        }        
    }
    
    public MapGraph convertGraph(){
        createDeg2Vertices();
        
        int CNT = 1;
        for( MapNode n : deg2Nodes ){
            System.out.print( "\rDokonuje kontrakcji na wierzcholku nr " + (CNT++) );
            removeDeg2NodeFromGraph(n);
        }
        System.out.println();
        
        return graph;
    }
    
    
    
    
    private MapGraph graph = null;
    
    private ArrayList<MapNode> deg2Nodes = new ArrayList<>(); // lista wszystkich wierzcholkow stopnia 2 w grafie - tylko te wierzcholki usuwamy
    private Map< Pair<Integer,Integer>, ArrayList<MapEdge> > parallelEdges = new HashMap<>();
}
