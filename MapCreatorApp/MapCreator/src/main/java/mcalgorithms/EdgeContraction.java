/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import java.util.Collections;
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
        for( MapNode n : graph.getNodes() ){
            if( (n.countNeighbours() == 2) && n.isContractable() ){
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
            MapNode temp = leftPair.getST(); // ale ustawiam teraz tak kolejnosc, aby ST lewej rawdzi pokazywalo na cos, a ND lewej na wierzcholek node
            leftPair.setST( leftPair.getND() );
            leftPair.setND(temp);
            Collections.reverse( leftEdge.getContainedStopsIds() );
        }
        
        MapNode rightNode = rightPair.getND();
        if( rightNode.getID() == node.getID() ){
            rightNode = rightPair.getST();
            MapNode temp = rightPair.getST();
            rightPair.setST( rightPair.getND() );
            rightPair.setND(temp);
            Collections.reverse( rightEdge.getContainedStopsIds() );
        }        
               
        ArrayList<String> leftEdgeList = leftEdge.getContainedStopsIds();
        ArrayList<String> rightEdgeList = rightEdge.getContainedStopsIds();
        
        ArrayList<String> newList = new ArrayList<>();
        newList.addAll( leftEdgeList );
        newList.addAll( node.getContainedStopsIds() );
        newList.addAll( rightEdgeList );
        
        MapEdge newEdge = new MapEdge();
        newEdge.setColor( leftEdge.getColor() ); // ustawiam na kolor lewej krawedzi
        newEdge.setHoverColor( leftEdge.getHoverColor() );
        newEdge.setDrawingWidth( leftEdge.getDrawingWidth() );
        newEdge.setHoverWidth( leftEdge.getHoverWidth() );
        newEdge.setEnds( new Pair<>( leftNode, rightNode ) );
        newEdge.setContainedStopsIds( newList );
        newEdge.setDescription( "Contracted edge" );
        
        graph.removeMapNodeByID( node.getID() );
        graph.addMapEdge( newEdge );
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
    
}
