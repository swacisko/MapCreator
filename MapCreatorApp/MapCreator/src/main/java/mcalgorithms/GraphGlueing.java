/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import mcgraphs.MapEdge;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mctemplates.LongestCommonSubstring;
import mctemplates.MCSettings;
import mctemplates.Pair;
import mctemplates.UsefulFunctions;
import org.hibernate.util.StringHelper;

/**
 * This class is responsible for glueing graph - multiple stops in close proximity should be treated as one stop (we don't care whether there are 10 stops around the
 * roundabout, we only want to know that there is any stop). In glueing algorithm there are two phases of glueing. During the first phase stops are glued only
 * if they are in very close proximity - specified by parameter {@link MCSettings#FIRST_GLUEING_DISTANCE_PARAMETER}. This should glue all stops with the same name
 * (e.g if there are two stops on both sides of a street). The second glueing is staged with a bit greater parameter - {@link MCSettings#SECOND_GLUEING_DISTANCE_PARAMTER},
 * therefore all stops within small area should be equated. (e.g. all stops around a roundabout).
 * @author swacisko
 *
 *
 */

public class GraphGlueing {

    /**
     * Class constructor.
     * @param graph 
     */
    public GraphGlueing(MapGraph graph) {
        this.graph = graph;
    }
    
    /**
     * Function used to alternative way of glueing vertices.
     * @return 
     */
    private ArrayList< ArrayList<Integer> > getGlueingList(){
        ArrayList< ArrayList<Integer> > glueingList = new ArrayList<>(); // glueingList.get(i) to lista id wierzcholkow, ktore maja zostac zlepione
        Set<Integer> isGlued = new HashSet<>();
        
        ArrayList<MapNode> nodes = graph.getNodes();
        for( int i=0; i<nodes.size();i++ ){
            
            if( isGlued.contains( i ) ) continue;
            
            ArrayList<Integer> l = new ArrayList<>();
            l.add( nodes.get(i).getID() );
            isGlued.add(i);            
            
            for( int k=i+1; k<nodes.size();k++ ){
                if( isGlued.contains(k) ) continue;
                
                if( canBeGluedTogether( nodes.get(i), nodes.get(k) ) ){
                    l.add( nodes.get(k).getID()  );
                    isGlued.add( k );
                }
            }
            
            glueingList.add(l);
            
        }
        
        return glueingList;
    }
    
    
    /**
     * Function used in alternative way of glueing vertices.
     * @param similarNeighbours
     * @return 
     */
    private ArrayList<Integer> getListOfDifferentNeighbours( ArrayList<Integer> similarNeighbours ){                
        ArrayList<MapEdge> edges = graph.getEdges();        
        Set<Integer> zb = new HashSet<>();
        
        for( MapEdge e : edges ){
            Pair<MapNode,MapNode> p = e.getEnds();
            int id1 = p.getST().getID();
            int id2 = p.getND().getID();
            
            if( similarNeighbours.contains( id1 ) ) zb.add(id2);
            if( similarNeighbours.contains( id2 ) ) zb.add(id1);
                        
        }
        
        ArrayList<Integer> res = new ArrayList<>();
        for( Integer g : zb ){
            res.add(g);
        }
        
        return res;
    }
    
    /**
     * Merges all data from nodes in oldNodesIds into node with gluedNodeId
     * @param gluedNodeId Id of a node, to which we want to glue everything
     * @param oldNodesIds Id's of vertices we want merge
     */
    private void glueAllDataOld( int gluedNodeId, ArrayList<Integer> oldNodesIds ){
        MapNode n = resGraph.getMapNodeByID( gluedNodeId );
        String structurename = graph.getMapNodeByID( oldNodesIds.get(0) ).getStructureName();
        for( Integer g : oldNodesIds ){
            MapNode n2 = graph.getMapNodeByID( g );
            n.setDescription( "Glued node" ); // UWAGA - brany jest tylko ostatni opis pod uwage
            if( n2.getColor() != null ) n.setColor( n2.getColor() ); // tutaj UWAGA - brany jest tylko  ostatni z wystepujacych kolorów!!!
            structurename = LongestCommonSubstring.getLongestCommonSubstring(structurename, n2.getStructureName() );
            n.getContainedStopsIds().addAll( n2.getContainedStopsIds() ); // dodaje wszystkie przystanki, ktore powinienem polaczyc
            n.setCoords( n2.getCoords() );
            n.setContractable( n.isContractable() && n2.isContractable() );
        }
        n.setStructureName( structurename ); 
    }

    /**
     * Alternative way of glueing nodes.
     */
    private void glueGraphOld(){        
        ArrayList< ArrayList<Integer> > glueingList = getGlueingList(); // glueingList.get(i) to lista id wierzcholkow, ktore maja zostac zlepione
               
     //   System.out.println( "GetGluedGraph:  glueingList:\n" + glueingList );
        for( int i=0; i<glueingList.size(); i++ ){
            MapNode n = new MapNode();
            n.setCoords( graph.getMapNodeByID( glueingList.get(i).get(0) ).getCoords() ); // wspolrzedne pierwszego wierzcholka z listy zlepianych wierzcholkow
            resGraph.addMapNode( n );
        }
        
        for( int i=0; i<glueingList.size(); i++ ){
            glueAllDataOld( resGraph.getMapNode(i).getID(), glueingList.get(i) );
        }
        
      //  System.out.println( "GetGluedGraph:  resGraph.countNodes() = " + resGraph.countNodes() );
        
        Map<Integer,Integer> idInGluedGraph = new HashMap<>(); // idInGluedGraph.get(key) zawiera wartosc value taka, ze wierzcholek o ID key w starym grafie ma miec ID = value w nowym grafie 
        for( int i=0; i< glueingList.size(); i++ ){
            for( Integer g : glueingList.get(i) ){
                idInGluedGraph.put( g, resGraph.getMapNode(i).getID() );
            }
        }
        
        ArrayList< ArrayList<Integer> > differentNeighbours = new ArrayList<>(); // differentNeghbours.get(i) to lista ID wszystkich wierzcholkow, ktore maja byc sasiadem tego zlepionego wierzcholka ,ale UWAGA - wszystkie ID sa w starym grafie, po to jest teraz nam mapa idInGluedGraph
        for( ArrayList<Integer> l : glueingList ){
            differentNeighbours.add( getListOfDifferentNeighbours(l) );
        }
        
      //  System.out.println( "GetGluedGraph: differentNeighbours = " + differentNeighbours  );
        
        Set< Pair<Integer,Integer> > edgesAdded = new HashSet<>(); // zeby sie nie powtarzaly krawedzie
        for( int i=0; i<glueingList.size();i++ ){
            MapNode n = resGraph.getMapNode(i);
            int id1 = n.getID();
            for( Integer g : differentNeighbours.get(i) ){                
                int id2 = idInGluedGraph.get( g );
                
                int a = Math.min( id1,id2 );
                int b = Math.max( id1,id2 );
                
                if( a != b && edgesAdded.contains( new Pair<>(a,b) ) == false ){
                    edgesAdded.add( new Pair<>(a,b) );
                    resGraph.addMapEdge( a,b );
                }
            }
            
            n.removeAllLoops();
        }
        
        
    }
    
    /**
     * Glues node n2 to node n1. Afterwards n2 will be erased from the graph (but it will happen in glueGraph function).
     * @param n1 A node we want to glue with n2. n1 will remain in graph
     * @param n2 Data from node n2 will be merged into data from n1.
     */
    private void glueNodes( MapNode n1, MapNode n2 ){
        ArrayList<MapEdge> edges = n2.getEdges();
        for( MapEdge e : edges ){            
            if( e.hasEndInMapNodeOfID( n1.getID() ) ){ // jezeli ta krawedz ma koniec w n1, to nic nie robie - zostanie ona pozniej usunieta
                continue;
            }
            
            Pair<MapNode,MapNode> ends = e.getEnds();    
            if( ends.getST().getID() == n2.getID() ){
                if( n1.hasNeighbourOfId( ends.getND().getID() ) == false ){
                    n1.addMapEdge(e);
                }else{
                    continue;
                }
                ends.setST( n1 );                
            }
            else {
                if( n1.hasNeighbourOfId( ends.getST().getID() ) == false ){
                    n1.addMapEdge(e);
                }else{
                    continue;
                }
                ends.setND(n1);                 
            }            
        }       
        
        n1.setDescription( "Glued node" );
        if( n1.getColor() == null && n2.getColor() != null ) n1.setColor( n2.getColor() );
        if( glueingTime == 1 ){
            String commonName = LongestCommonSubstring.getLongestCommonSubstring( n1.getStructureName(), n2.getStructureName() );
            n1.setStructureName( commonName );
        }else if( n1.getContainedStopsIds().size() < n2.getContainedStopsIds().size()  ){
               n1.setStructureName( n2.getStructureName() );
        }
        n1.getContainedStopsIds().addAll( n2.getContainedStopsIds() );
        n1.setCoords( UsefulFunctions.centerOfGravity( n1.getCoords() ,n2.getCoords()) );
        n1.setContractable( n1.isContractable() && n2.isContractable() );
        n1.setWidth( n1.getContainedStopsIds().size() );
        n1.setHeight( (int)(2*(float)n1.getContainedStopsIds().size()/3) );
    }
    
    /**
     * Recent version of glueing graph. Glues graph 'inline;, changes the graph structure, does not create a new graph.
     */
    private void glueGraph(){
        ArrayList<MapNode> nodes = graph.getNodes();
        for( int i=0; i<nodes.size(); i++ ){
            for( int k=i+1; k<nodes.size(); k++ ){
                if( canBeGluedTogether(nodes.get(i), nodes.get(k)) ){
                    glueNodes( nodes.get(i), nodes.get(k) );
                    graph.removeMapNode(k);
                    k--;
                }
            }            
        }        
    }
        
    /**
     * Converts {@link GraphGlueing#graph} by glueing its nodes. It reduces the total amount of nodes and edges in graph.
     * @return returns {@link GraphGlueing#resGraph} - graph after glueing procedure.
     */
    public MapGraph convertGraph() {
        if( graph == null ) return null;
        // resGraph = graph;
        CNT = 0;
       // System.out.println( "Przed sklejaniem graf ma " + graph.countNodes() + "  wierzcholkow i " + graph.countEdges() + " krawedzi" );
        
        //resGraph = new MapGraph(); // to jest potrzebne gdy uzywamy funkcji gluegraphold
        //glueGraphOld();   
        
        glueingParameter = MCSettings.getFIRST_GLUEING_DISTANCE_PARAMETER();
        glueGraph();
        glueingTime++;
        glueingParameter = MCSettings.getSECOND_GLUEING_DISTANCE_PARAMTER();
        glueGraph();
        trimNodeStructureNames();
        //transformPolishLetters();
        //System.out.println( "Po sklejaniu graf ma " + graph.countNodes() + "  wierzcholkow i " + graph.countEdges() + " krawedzi" );
        
        return graph;
    }

    /**
     * Function checks, whether two nodes can be glued together during glueing. They can if their mutual distance in Manhattan metrics is nor more than
     * {@link GraphGlueing#glueingParameter}
     * @param n1 First node
     * @param n2 Second node
     * @return returns true if nodes n1 and n2 can be glued together. Otherwise returns false
     */
    private boolean canBeGluedTogether(MapNode n1, MapNode n2) {
        /*boolean b = Math.random() < 0.35; // to jest potrzebne do testowania dla malych grafow
        if( b ){
            System.out.println( "Sklejam wierzcholki " + n1.getID() + " oraz " + n2.getID() );
        }        
        return b;*/
        
        
        if (similarName(n1.getStructureName(), n2.getStructureName(),0.8f)) {
            if (coordinatesRoughlyTheSame(n1.getCoords(), n2.getCoords(), 3*glueingParameter  )) { // bardzo podobne nazwy, wiec i odleglosc dosc duza - ponad kilkadziesiat metrow
                return true;
            }
        } 
        else if (similarName(n1.getStructureName(), n2.getStructureName(),0.4f)) { // cos wspolnego w nazwie maja i wzglednie blisko siebie - kilkadziescia metrow
            if (coordinatesRoughlyTheSame(n1.getCoords(), n2.getCoords(), 2*glueingParameter  )) {
                return true;
            }
        }        
        else if( coordinatesRoughlyTheSame(n1.getCoords(), n2.getCoords(), glueingParameter  ) ){ // rozne nazwy ale tuz obok siebie
            return true;
        }

        return false;
    }

    /**
     * Function used to test correctness of glueing method. Prints to the standard output all data and details during glueing process to enable programmer to check,
     * whether glueing goes correctly.
     */
    public void testGlueing() {
        ArrayList<MapNode> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            for (int k = i + 1; k < nodes.size(); k++) {
                if (similarName(nodes.get(i).getStructureName(), nodes.get(k).getStructureName(),0.4f)) {
                    System.out.println("Names   " + nodes.get(i).getStructureName() + "   and   " + nodes.get(k).getStructureName() + "   are similar!");
                    if (coordinatesRoughlyTheSame(nodes.get(i).getCoords(), nodes.get(k).getCoords(),2f)) {
                        System.out.println("\tFurtheremore, coordinates " + nodes.get(i).getCoords() + "   and   " + nodes.get(k).getCoords() + "  are similar!");
                        System.out.println("\tCNT = " + CNT);
                        CNT++;
                    }
                    System.out.println();
                }
                else if (coordinatesRoughlyTheSame(nodes.get(i).getCoords(), nodes.get(k).getCoords(),2f)) {
                    System.out.println("Coordinates " + nodes.get(i).getCoords() + "   and   " + nodes.get(k).getCoords() + "  are almost the same!");
                    System.out.println("\tCNT = " + CNT);
                    CNT++;
                }
            }
        }
    }

    /**
     * Function used to test whether algorithm comparing similar names of stops works correctly.
     */
    public void testSimilarNames() {
        ArrayList<MapNode> nodes = graph.getNodes();

        for (int i = 0; i < nodes.size(); i++) {
            for (int k = i + 1; k < nodes.size(); k++) {
                if (similarName(nodes.get(i).getDescription(), nodes.get(k).getDescription(),0.4f)) {
                    System.out.println("Names   " + nodes.get(i).getDescription() + "   and   " + nodes.get(k).getDescription() + "   are similar!");
                }
            }
        }

    }

    /**
     * Function used to check, whether two nodes can be glued together in first glueing. It fins longest common substring of two strings (stop names). 
     * @param s1 First string
     * @param s2 Second string
     * @param threshold Float value in interval [0,1]
     * @return returns true if given strings are similar with respect to threshold value.
     */
    private boolean similarName(String s1, String s2, float threshold) {
        String lcs = LongestCommonSubstring.getLongestCommonSubstring(s1, s2);
        double ratio = (double) lcs.length();
        ratio /= (double) (s1.length() + s2.length());
        ratio *= 2;
        return ratio >= threshold;
    }

    /**
     * Function used to check, whether to nodes can be glued together
     * @param c1 First pair of coordinates
     * @param c2 Second pair of coordinates
     * @param threshold is a float number. The greater, the more nodes will be glued
     * @return If distance between c1 and c2 is lower than threshold then return true. Otherwise returns false
     */
    private boolean coordinatesRoughlyTheSame(Pair<Float, Float> c1, Pair<Float, Float> c2, float threshold) {
        float FACT = MCSettings.COORDINATES_COMPARISON_PRECISION;
        float f1x = FACT * c1.getST();
        float f1y = FACT* c1.getND();

        float f2x = FACT * c2.getST();
        float f2y = FACT * c2.getND();

        return (Math.abs(f1x - f2x) + Math.abs(f1y - f2y)) < threshold;
    }
    
    /**
     * 
     * @return returns {@link #glueingParameter}. 
     */
    public float getGlueingParameter() {
        return glueingParameter;
    }

    public void setGlueingParameter(float glueingParameter) {
        this.glueingParameter = glueingParameter;
    }
    
    /**
     * For given structure name trims it to proper name. E.g. if structure name is "Żeromskiego nż 1", then it will return "Żeromskiego"
     * @param strName Name of a node (or arbitrary string) to be trimmed
     * @return returns trimmed string
     */
    private String trimStructureName( String strName ){
        String res = strName;
        boolean found;
        do{
            found = false;
            for( int i=0; i<=9; i++ ){
                if( res.endsWith( ""+i ) ){
                    res = res.substring( 0, res.lastIndexOf( ""+i ) );
                    found = true;
                }
            }
            
            if( res.lastIndexOf(" nż" ) != -1 ){
                res = res.substring( 0, res.lastIndexOf( " nż" ) );
                found = true;
            }
            
        }while(found);
        return res;
    }
    
    /**
     * Executes {@link #trimStructureName(java.lang.String) } function for each node in {@link #graph}
     */
    private void trimNodeStructureNames(){
        for( MapNode n : graph.getNodes() ){
            n.setStructureName( trimStructureName( n.getStructureName() ) );
        }
    }
    
    /**
     * Transforms polish letters to English ones in structure names of each node in {@link #graph}
     */
    private void transformPolishLetters(){
        for( MapNode n : graph.getNodes() ){
            n.setStructureName( UsefulFunctions.removePolishLetters( n.getStructureName() ) );
        }
    }

    /**
     * We need this only when we use {@link #glueGraphOld() }, which i think is useless now, since {@link #glueGraph() } works better and fast enough.
     */
    private MapGraph resGraph = null; 
    private MapGraph graph = null;
    private float glueingParameter = 10;
    private int glueingTime = 1;
    

    private int CNT = 0;
}
