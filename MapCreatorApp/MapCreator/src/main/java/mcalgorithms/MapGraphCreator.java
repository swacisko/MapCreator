/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import mcgraphs.*;
import mcgtfsstructures.*;
import mctemplates.MCSettings;
import mctemplates.Pair;

/**
 * {@link MapGraphCreator} contains all methods needed to create basic graph from database.
 * @author swacisko
 */
public class MapGraphCreator {

    /**
     * This function checks all conditions, which every route must fulfill to be added to graph. E.g. it may be either a main route, a background route, or meet any
     * other condition that would cause it to be added to graph.
     * @param r route to be added.
     * @return return true, if the given route should be added to graph.
     */
    private boolean addRouteCondition( Route r ){
        int routetype = Integer.parseInt(r.getRouteType());
        if( ( (MCSettings.isFullSchemeBackground() || MCSettings.getRoutesToHighlight().isEmpty()  ) &&  (TRANSPORT_MEASURE & (1 << routetype)) != 0 )||
                (  MCSettings.getRoutesToHighlight().contains( r.getRouteId() ) )  ){
            return true;
        }
        return false;
    }
    
    /**
     * Creates all routes and stops that will be subsequently added to graph.
     */
    private void createConsideredRoutesAndStops() {
        //System.out.println( "Wybieram przystanki i drogi ktore maja zostac dodane do grafu" );
        consideredRoutes.clear();
        consideredStops.clear();
        noncontractableNodes.clear();
        
        for (Route r : MCDatabase.getAllRoutes()) {
            
            if ( addRouteCondition(r) ) {
                consideredRoutes.add(r.getRouteId());
                for (String s : r.getStopIds()) {
                    //  if( consideredStops.contains( MCDatabase.getStopOfID( s ) ) == false ) CNT++;
                    consideredStops.add(MCDatabase.getStopOfID(s));
                }
                
                ArrayList<Trip> trips = MCDatabase.getAllTripsOfRouteId( r.getRouteId());
                if( trips == null ) continue;            
                for( Trip t : trips ){
                    ArrayList<StopTime> l = MCDatabase.getAllStopTimesOfTripId(t.getTripId());
                    noncontractableNodes.add( l.get(0).getStopId() );
                    noncontractableNodes.add( l.get( l.size()-1 ).getStopId() );
                }
            }          
            
        }        

    }

    /**
     * Adds all nodes from {@link #consideredStops} - stops determined in {@link #createConsideredRoutesAndStops() } - to graph.
     * 
     */
    private void addNodesToGraph() {        
        int CNT = 1;
        for (Stop s : consideredStops) {
            //System.out.print( "\rDodaje wierzcholek nr " + (CNT++) );
            MapNode node = new MapNode();
            node.setCoords(s.getCoords());
            node.setStructureName(s.getStopName());
            node.setDescription("Structure represents a STOP");
            if( s.getColor() != null ) node.setColor(s.getColor());
            else node.setColor( MCSettings.getINITIAL_NODE_COLOR() );
            node.addContainedStopsId(s.getStopId());
            if( noncontractableNodes.contains( s.getStopId() ) ){
                node.setContractable( false );
            }
                
            resGraph.addMapNode(node);
        }
        System.out.println();
    }

    /**
     * Function adds to graph all edges forming any of the trip with routeId contained in {@link #consideredRoutes}.
     */
    private void addEdgesToGraph() {
        //System.out.println("Zaczynam dodawac krawedzie");
        Set< Pair<Integer, Integer>> edgesAdded = new HashSet<>(); // edgesAdded to zbior par, z ktorych kazda okresla 2 id wierzcholkow, ktore chce polaczyc

        Map<String, Integer> nodeIdReversed = new HashMap<>();  // nodeIdReversed.get(key) to wartosc value taka, ze przystanek o id = key znajduje sie w wierzcholku grafu o id = value

        for (MapNode n : resGraph.getNodes()) {
            for (String s : n.getContainedStopsIds()) {
                nodeIdReversed.put(s, n.getID());
            }
        }
        
        int CNT = 0;
        for (Trip t : MCDatabase.getAllTrips()) {
            if (consideredRoutes.contains(t.getRouteId())) { // jezeli typ danej drogi jet zgodny z TRANSPORT_MEASURE
                int colormode = Integer.parseInt(MCDatabase.getRouteOfID(t.getRouteId()).getRouteType());

                ArrayList<StopTime> l = MCDatabase.getAllStopTimesOfTripId(t.getTripId());
                for (int i = 0; i < l.size(); i++) {
                    if (i > 0) {
                        int id1 = nodeIdReversed.get(l.get(i - 1).getStopId());
                        int id2 = nodeIdReversed.get(l.get(i).getStopId());

                        if (id1 > id2) { // swapuje, aby nie powtarzaly sie krawedzie
                            int temp = id1;
                            id1 = id2;
                            id2 = temp;
                        }
                        if (edgesAdded.contains(new Pair<>(id1, id2)) == false) {
                            edgesAdded.add(new Pair<>(id1, id2));

                            MapEdge e = new MapEdge();
                            MapNode n1 = resGraph.getMapNodeByID(id1);
                            MapNode n2 = resGraph.getMapNodeByID(id2);
                            e.setEnds(new Pair<>(n1, n2));
                            e.setColor(MCSettings.getCorrespondingColor(colormode));
                            resGraph.addMapEdge(e);

                           // System.out.print("\rDodaje krawedz nr: " + CNT++);
                        }
                    }
                }
            }
        }

        //System.out.println("\nSkonczylem dodawac krawedzie");
    }

    /**
     * Function adds all edges to graph using topological sorting. All stops are sorted topologically. A DAG is created and hence a route is obtained by finding
     * the longest path in that DAG. This however works poorly in general in comparison with graph-glueing method. However there are some special cases in which it
     * is also useful. Another advantage of this method is linear execution time - for large data speeds up the whole process of building a graph. However, since 
     * we don't care for a few seconds, the use of this function is deprecated and should be used only in vindicated instances.
     */
    public void addEdgesToGraphTopoSort() {
        System.out.println( "Zaczynam dodawac krawedzie do poczatkowego grafu" );
        Map<String, Integer> nodeIdReversed = new HashMap<>();  // nodeIdReversed.get(key) to wartosc value taka, ze przystanek o id = key znajduje sie w wierzcholku grafu o id = value

        for (MapNode n : resGraph.getNodes()) {
            for (String s : n.getContainedStopsIds()) {
                nodeIdReversed.put(s, n.getID());
            }
        }

        String BEGID = null; // id przystanku poczatkowego
        String ENDID = null;

        Set< Pair<String, String>> mustBeBefore = new HashSet<>(); // para<s1,s2> wystepuje jezeli sortujac topologicznie przystanek s1 musi byc przed przystnkiem s2
        Set< String> isOnRoute = new HashSet<>(); // id danego przystanku znajduje sie tutaj, jezeli przystanek ten nalezy do aktualnie rozwazanej drogi
        Set< Pair<Integer, Integer>> edgesAdded = new HashSet<>(); // edgesAdded to zbior par, z ktorych kazda okresla 2 id wierzcholkow, ktore chce polaczyc
        
        System.out.println( "Zaczynam sortowanie topologiczne drog. Do posortowania mam " + consideredRoutes.size() + " drog" );
        int CNT = 1;
        
        int CNT10 = 0;
        
        int theresize = 0;
        int backsize = 0;
        
        for (String s : consideredRoutes) {
            System.out.print( "\rZaczynam sortowanie drogi " + s + "   nr " + (CNT++) );
            
            ArrayList<Trip> triplist = MCDatabase.getAllTripsOfRouteId(s); // biore wszystkie tripy dla danej drogi route
            int colormode = Integer.parseInt(MCDatabase.getRouteOfID(s).getRouteType());
            
            mustBeBefore.clear();
            isOnRoute.clear();
            BEGID = null;
            ENDID = null;
            theresize = backsize = 0;
            
            if( triplist == null ){
                System.out.println( "Cos tu jest nie tak, nie ma tripu o id drogi  " + s );
                continue;
            }

            for (Trip t : triplist) { // biore wszystkie przystanki dla danej przejazdzki
                ArrayList<StopTime> stoptimeslist = MCDatabase.getAllStopTimesOfTripId(t.getTripId());
                if (BEGID == null || ( stoptimeslist.get( stoptimeslist.size()-1 ).getStopId().equals( BEGID ) == false && 
                        stoptimeslist.get(0).getStopId().equals( BEGID ) == false ) ){
                    BEGID = stoptimeslist.get(0).getStopId();
                }
                if (stoptimeslist.get(0).getStopId().equals(BEGID) == false) { // w przeciwnym razie
                    ENDID = stoptimeslist.get(0).getStopId();
                    Collections.reverse(stoptimeslist); // odwracam kolejnosc listy   
                    
                    backsize = Math.max( backsize, stoptimeslist.size() );
                }
                else{
                     theresize = Math.max( theresize, stoptimeslist.size() );
                }
                
                for (int i = 0; i < stoptimeslist.size(); i++) {
                    isOnRoute.add(stoptimeslist.get(i).getStopId()); // dany wierzcholek jest na drodze

                    for (int k = i + 1; k < stoptimeslist.size(); k++) {
                        mustBeBefore.add(new Pair<>(stoptimeslist.get(i).getStopId(), stoptimeslist.get(k).getStopId())); // dodaje zaleznosci potrzebne do sortowania topologicznego
                    }
                }
            }
            
            if( theresize != 0 && backsize!= 0 && Math.abs(theresize - backsize) >= 1 ){
                System.out.println( "roznica o wiecej niz 2!  theresize = " + theresize + "  backsize = " + backsize );
            }

            // TERAZ POSORTUJE TOPOLOGICZNIE
            if( CNT == 3 ){
                System.out.println( "\nBEGID = " + BEGID + "   ENDID = " + ENDID );
                System.out.println( "Oto isOnROute przed whilem " + isOnRoute );                
            }
            
            ArrayList<String> sortedStops = new ArrayList<>();

            CNT10 = 0;
            while( isOnRoute.isEmpty() == false ){
                if( CNT10++ > 10000 ){                    
                    if( CNT == 3 ){   
                        System.out.println( "\nNadal tkwie w while w drodze nr " + (CNT-1) );
                        System.out.println( "Oto mustBeBefore " + mustBeBefore );
                        System.out.println( "\nOto isOnRoute:  " + isOnRoute + "\n" );
                        System.out.println( "Oto sortedStops " + sortedStops );
                    }
                    
                    break;
                }
                for (String before : isOnRoute) {
                    boolean canBeNext = true;
                    for (String after : isOnRoute) {
                        if (before.equals(after)) {
                            continue;
                        }

                        if( mustBeBefore.contains( new Pair<>( after,before) ) ){
                            canBeNext = false;
                            break;
                        }
                    }

                    if( canBeNext == true ){
                        if( CNT == 3 ) System.out.println( "\tCan be next = true! Dodaje wierzcholek " + before + " jako nastepny do kolejnosci" );
                        sortedStops.add( before );
                        isOnRoute.remove( before );
                        break;
                    }
                }
            }
            
            for (int i = 0; i < sortedStops.size(); i++) {
                if (i > 0) {
                    int id1 = nodeIdReversed.get(sortedStops.get(i-1));
                    int id2 = nodeIdReversed.get(sortedStops.get(i));

                    if (id1 > id2) { // swapuje, aby nie powtarzaly sie krawedzie
                        int temp = id1;
                        id1 = id2;
                        id2 = temp;
                    }
                    if (edgesAdded.contains(new Pair<>(id1, id2)) == false) {
                        edgesAdded.add(new Pair<>(id1, id2));

                        MapEdge e = new MapEdge();
                        MapNode n1 = resGraph.getMapNodeByID(id1);
                        MapNode n2 = resGraph.getMapNodeByID(id2);
                        e.setEnds(new Pair<>(n1, n2));
                        e.setColor(MCSettings.getCorrespondingColor(colormode));
                        resGraph.addMapEdge(e);
                    }
                }
            }
            

        }

    }

    /**
     * Function tests frequencies of stops on routes. 
     */
    public void testStopsFrequenciesOnRoutes(){
        
        for (String s : consideredRoutes) {
            
            ArrayList<Trip> triplist = MCDatabase.getAllTripsOfRouteId(s); // biore wszystkie tripy dla danej drogi route
            int colormode = Integer.parseInt(MCDatabase.getRouteOfID(s).getRouteType());
                        
            if( triplist == null ){
                System.out.println( "Cos tu jest nie tak, nie ma tripu o id drogi  " + s );
                continue;
            }else{
                System.out.println( "Statsy dla drogi id = " + s );
            }
            
            Map<String,Integer> freq = new HashMap<>();

            for (Trip t : triplist) { // biore wszystkie przystanki dla danej przejazdzki
                ArrayList<StopTime> l = MCDatabase.getAllStopTimesOfTripId(t.getTripId());
                
                if( freq.containsKey( l.get( l.size()-1 ).getStopId() ) == false ){
                    freq.put( l.get(l.size()-1).getStopId(), 1 );
                }
                else{
                    int temp = freq.get( l.get( l.size()-1 ).getStopId() );
                    freq.remove( l.get( l.size()-1 ).getStopId() );
                    freq.put( l.get( l.size()-1 ).getStopId(), temp+1 );
                }
                
                if( freq.containsKey( l.get( 0 ).getStopId() ) == false ){
                    freq.put( l.get(0).getStopId(), 1 );
                }
                else{
                    int temp = freq.get( l.get( 0).getStopId() );
                    freq.remove( l.get( 0 ).getStopId() );
                    freq.put( l.get( 0 ).getStopId(), temp+1 );
                }                
            }
            
            System.out.println( "Oto mapa wystepowania poczatkow i koncow:\n" + freq );
            
            int M = 0 , m = 0;
            String BEG="",END="";
            for (Map.Entry<String, Integer> entry : freq.entrySet())
            {                
                if( entry.getValue() >= M ){
                    m = M;
                    END = BEG;
                    BEG = entry.getKey();
                    M = entry.getValue();
                }else if( entry.getValue() >= m ){
                    m = entry.getValue();
                    END = entry.getKey();
                }                
            }     
            
            System.out.println( "Oto najczesciej wystepujace poczatki / konce: (M = "+M+", BEG = " + BEG + ")   (m = " + m + ", END = " + END + ")" );
            
            ArrayList<StopTime> theretrip = new ArrayList<>();
            ArrayList<StopTime> backtrip = new ArrayList<>();
            M = m = 0;
            
            for (Trip t : triplist) { // biore wszystkie przystanki dla danej przejazdzki
                ArrayList<StopTime> l = MCDatabase.getAllStopTimesOfTripId(t.getTripId());
                
                if( l.get(0).getStopId().equals( BEG ) && l.get( l.size()-1 ).getStopId().equals( END ) ){
                    if( l.size() > M ){
                        M = l.size();
                        theretrip = l;
                    }
                    
                }else if( l.get(0).getStopId().equals( END ) && l.get( l.size()-1 ).getStopId().equals( BEG ) ){
                    if( l.size() > m){
                        m = l.size();
                        backtrip = l;
                    }
                }                              
            }
                        
            System.out.println( "Znalazlem najdluzsze przejazdy tam i spowrotem\ntheretrip.size() = " + theretrip.size() + "    backtrip.size() = " + backtrip.size() );
            System.out.println( "Theretrip:\t" );
            for( StopTime st : theretrip ){
                System.out.print( st.getStopId() + "  |  " );
            }
            
            System.out.println( "Backtrip:\t" );
             for( StopTime st : backtrip ){
                System.out.print( st.getStopId() + "  |  " );
            }
            
            
            System.out.println( "\n\n\n" );
        }
        
    }
    
    /**
     * Function creates a graph from data in {@link MCDatabase}. Adds only these routes that meet conditions in 
     * {@link #addRouteCondition(mcgtfsstructures.Route) } function. 
     * @param TRANSPORT_MEASURE this parameter denotes transport measures that should be added to graph as a background of the scheme.
     * @return return created graph.
     */
    public MapGraph createMapGraphFromGtfsDatabase(int TRANSPORT_MEASURE) {
        this.TRANSPORT_MEASURE = TRANSPORT_MEASURE;
        createConsideredRoutesAndStops();

        resGraph = new MapGraph();

        addNodesToGraph();
        addEdgesToGraph();
        //addEdgesToGraphTopoSort();
        //testStopsFrequenciesOnRoutes();
        

        return resGraph;
    }

    private int TRANSPORT_MEASURE = 0;

    private MapGraph resGraph = null;

    private Set<String> consideredRoutes = new HashSet<>(); // drogi przechowywane po route.id
    private Set<Stop> consideredStops = new HashSet<>(); // przystanki przechowywane bezposrednio
    private Set<String> noncontractableNodes = new HashSet<>(); // zawiera id przystankow, ktore sa punktami koncowymi na pewnych tripach - wtedy nie mozna skrocic danych wierzcholkow
}
