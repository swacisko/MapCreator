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
import mctemplates.MCConstants;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 *
 *
 */
// ten algorytm skleja nam graf zadany poprzez wszystkie przystanki i polaczenia w graf, w ktorym te same przystanki sa ze soba utozsamiane
public class GraphGlueing {

    public GraphGlueing(MapGraph graph) {
        this.graph = graph;
    }
    
    // zwraca dwuwymiarowa tablice - kazda lista w tej tablicy zawiera wszystkie ID wierzcholkow ktore maja zostac zlepione w jeden konkretny wierzcholek
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
    
    
    // jako parametr przesylana jest tablica ID wierzcholkow jtore maja zostac zlepione
    // jako wynik zwracana jest lista ID wszystkich sasiadow tychze wierzcholkow
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
    
    // merguje wszystkie potrzebne dane ( description, structureName itp) do nowego wierzcholka
    private void glueAllData( int gluedNodeId, ArrayList<Integer> oldNodesIds ){
        MapNode n = resGraph.getMapNodeByID( gluedNodeId );
        for( Integer g : oldNodesIds ){
            MapNode n2 = graph.getMapNodeByID( g );
            n.setDescription( n2.getDescription() ); // UWAGA - brany jest tylko ostatni opis pod uwage
            if( n2.getColor() != null ) n.setColor( n2.getColor() ); // tutaj UWAGA - brany jest tylko  ostatni z wystepujacych kolorów!!!
            n.setStructureName( n2.getStructureName() ); // UWAGA - brany jest tylko ostatnia nazwa 
            n.getContainedStopIds().addAll( n2.getContainedStopIds() ); // dodaje wszystkie przystanki, ktore powinienem polaczyc
            n.setCoords( n2.getCoords() );
        }
    }

    public void glueGraph(){        
        ArrayList< ArrayList<Integer> > glueingList = getGlueingList(); // glueingList.get(i) to lista id wierzcholkow, ktore maja zostac zlepione
               
     //   System.out.println( "GetGluedGraph:  glueingList:\n" + glueingList );
        for( int i=0; i<glueingList.size(); i++ ){
            MapNode n = new MapNode();
            n.setCoords( graph.getMapNodeByID( glueingList.get(i).get(0) ).getCoords() ); // wspolrzedne pierwszego wierzcholka z listy zlepianych wierzcholkow
            resGraph.addMapNode( n );
        }
        
        for( int i=0; i<glueingList.size(); i++ ){
            glueAllData( resGraph.getMapNode(i).getID(), glueingList.get(i) );
        }
        
      //  System.out.println( "GetGluedGraph:  resGraph.size() = " + resGraph.size() );
        
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
    
    public MapGraph getGluedGraph() {
        if( graph == null ) return null;
        resGraph = graph;
        CNT = 0;
        System.out.println( "Przed sklejaniem graf ma " + graph.size() + "  wierzcholkow" );
        do{
            graph = resGraph;
            resGraph = new MapGraph();
            glueGraph();   
            System.out.println( "Po " + ++CNT +"-tym sklejaniu graf ma " + resGraph.size() + " wierzcholkow" );
        }while( false /*resGraph.size() < graph.size()*/ ); // aby wielokrotnie sklejac graf, moge wywolac ten drugi warunek, ale to chyba nie bedzie mialo zbytnio sensu
        
        return resGraph;
    }

    public void setGraph(MapGraph graph) {
        this.graph = graph;
    }

    public MapGraph getGraph() {
        return graph;
    }

    private boolean canBeGluedTogether(MapNode n1, MapNode n2) {
        /*boolean b = Math.random() < 0.35; // to jest potrzebne do testowania dla malych grafow
        if( b ){
            System.out.println( "Sklejam wierzcholki " + n1.getID() + " oraz " + n2.getID() );
        }        
        return b;*/
        
        
        if (similarName(n1.getStructureName(), n2.getStructureName(),0.8f)) {
            if (coordinatesRoughlyTheSame(n1.getCoords(), n2.getCoords(), 20f  )) { // bardzo podobne nazwy, wiec i odleglosc dosc duza - ponad kilkadziesiat metrow
                return true;
            }
        } 
        else if (similarName(n1.getStructureName(), n2.getStructureName(),0.4f)) { // cos wspolnego w nazwie maja i wzglednie blisko siebie - kilkadziescia metrow
            if (coordinatesRoughlyTheSame(n1.getCoords(), n2.getCoords(), 10f  )) {
                return true;
            }
        }        
        else if( coordinatesRoughlyTheSame(n1.getCoords(), n2.getCoords(), 4f  ) ){ // rozne nazwy ale tuz obok siebie
            return true;
        }

        return false;
    }

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

    // zwraca true jezeli nazwy sa podobne stosunkowo wiecej niz threshold (wartosc threshold to float od 0 do 1, 1 gdy stringi sa identyczne)
    private boolean similarName(String s1, String s2, float threshold) {
        String lcs = LongestCommonSubstring.getLongestCommonSubstring(s1, s2);
        double ratio = (double) lcs.length();
        ratio /= (double) (s1.length() + s2.length());
        ratio *= 2;
        return ratio >= threshold;
    }

    // ta funkcja dziala dobrze tylko dla wspolrzednych rzeczywistych, a nie dla wspolrzednych wierzcholka na mapie
    private boolean coordinatesRoughlyTheSame(Pair<Float, Float> c1, Pair<Float, Float> c2, float threshold) {
        float FACT = MCConstants.COORDINATES_COMPARISON_PRECISION;
        float f1x = FACT * c1.getST();
        float f1y = FACT* c1.getND();

        float f2x = FACT * c2.getST();
        float f2y = FACT * c2.getND();

        return (Math.abs(f1x - f2x) + Math.abs(f1y - f2y)) < threshold;
    }

    private MapGraph resGraph = null;
    private MapGraph graph = null;

    private int CNT = 0;
}
