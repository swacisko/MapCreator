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
import mctemplates.MCConstants;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */
// TWORZY GRAF Z DANYCH GTFS
public class MapGraphCreator {

    // wybiera wszystkie drogi, ktorych typ jest odpowiedni do tego przekazanego przez TRANSPORT_MEASURE
    private void createConsideredRoutesAndStops() {
        //   int CNT = 0;
        for (Route r : localGtfsDatabase.getAllRoutes()) {
            int routetype = Integer.parseInt(r.getRouteType());
            if ((TRANSPORT_MEASURE & (1 << routetype)) != 0) {
                consideredRoutes.add(r.getRouteId());
                for (String s : r.getStopIds()) {
                    //  if( consideredStops.contains( localGtfsDatabase.getStopOfID( s ) ) == false ) CNT++;
                    consideredStops.add(localGtfsDatabase.getStopOfID(s));
                }
            }
        }

        //   System.out.println( "Dodalem lacznie " + CNT + "  przystankow do narysowania, a wszystkich jest " + localGtfsDatabase.getAllStops().size() );
    }

    private void addNodesToGraph() {
       // for( Stop s : localGtfsDatabase.getAllStops() ) consideredStops.add(s); // TO JEST TYLKO TYMCZASOWE I DO USUNIECIA POZNIEJ!

        for (Stop s : consideredStops) {
            MapNode node = new MapNode();
            node.setCoords(s.getCoords());
            node.setStructureName(s.getStopName());
            node.setDescription("Structure represents a STOP");
            node.setColor(s.getColor());
            node.addContainedStopId(s.getStopId());

            resGraph.addMapNode(node);
        }
    }

    // 
    private void addEdgesToGraph() {
        Set< Pair<Integer, Integer>> edgesAdded = new HashSet<>(); // edgesAdded to zbior par, z ktorych kazda okresla 2 id wierzcholkow, ktore chce polaczyc

        Map<String, Integer> nodeIdReversed = new HashMap<>();  // nodeIdReversed.get(key) to wartosc value taka, ze przystanek o id = key znajduje sie w wierzcholku grafu o id = value

        for (MapNode n : resGraph.getNodes()) {
            for (String s : n.getContainedStopIds()) {
                nodeIdReversed.put(s, n.getID());
            }
        }

        System.out.println("Zaczynam dodawac krawedzie");
        int CNT = 0;

        for (Trip t : localGtfsDatabase.getAllTrips()) {
            if (consideredRoutes.contains(t.getRouteId())) { // jezeli typ danej drogi jet zgodny z TRANSPORT_MEASURE
                int colormode = Integer.parseInt(localGtfsDatabase.getRouteOfID(t.getRouteId()).getRouteType());

                ArrayList<StopTime> l = localGtfsDatabase.getAllStopTimesOfTripId(t.getTripId());
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
                            e.setColor(MCConstants.getCorrespondingColor(colormode));
                            resGraph.addMapEdge(e);

                            System.out.print("\rCNT:" + CNT++);
                        }
                    }
                }
            }
        }

        System.out.println("Skonczylem dodawac krawedzie");
    }

    // funkcja dodaje krawedzie do grafu sortujac topologicznie wszystkie przystanki na danej drodze
    // ALE NA RAZIE NIE DZIALA - PRZYSTANKI W JEDNA I W DRUGA STRONE MAJA ZUPELNIE ROZNE ID, WIEC NIE DA SIE I TAK ROZSADNIE KOLEJNOSCI ODZWIERCIEDLIC
    public void addEdgesToGraphTopoSort() {
        System.out.println( "Zaczynam dodawac krawedzie do poczatkowego grafu" );
        Map<String, Integer> nodeIdReversed = new HashMap<>();  // nodeIdReversed.get(key) to wartosc value taka, ze przystanek o id = key znajduje sie w wierzcholku grafu o id = value

        for (MapNode n : resGraph.getNodes()) {
            for (String s : n.getContainedStopIds()) {
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
            
            ArrayList<Trip> triplist = localGtfsDatabase.getAllTripsOfRouteId(s); // biore wszystkie tripy dla danej drogi route
            int colormode = Integer.parseInt(localGtfsDatabase.getRouteOfID(s).getRouteType());
            
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
                ArrayList<StopTime> stoptimeslist = localGtfsDatabase.getAllStopTimesOfTripId(t.getTripId());
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
                        e.setColor(MCConstants.getCorrespondingColor(colormode));
                        resGraph.addMapEdge(e);
                    }
                }
            }
            

        }

    }

    
    public void testStopsFrequenciesOnRoutes(){
        
        for (String s : consideredRoutes) {
            
            ArrayList<Trip> triplist = localGtfsDatabase.getAllTripsOfRouteId(s); // biore wszystkie tripy dla danej drogi route
            int colormode = Integer.parseInt(localGtfsDatabase.getRouteOfID(s).getRouteType());
                        
            if( triplist == null ){
                System.out.println( "Cos tu jest nie tak, nie ma tripu o id drogi  " + s );
                continue;
            }else{
                System.out.println( "Statsy dla drogi id = " + s );
            }
            
            Map<String,Integer> freq = new HashMap<>();

            for (Trip t : triplist) { // biore wszystkie przystanki dla danej przejazdzki
                ArrayList<StopTime> l = localGtfsDatabase.getAllStopTimesOfTripId(t.getTripId());
                
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
                ArrayList<StopTime> l = localGtfsDatabase.getAllStopTimesOfTripId(t.getTripId());
                
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
    
    public MapGraph createMapGraphFromGtfsDatabase(int TRANSPORT_MEASURE) {
        this.TRANSPORT_MEASURE = TRANSPORT_MEASURE;
        createConsideredRoutesAndStops();

        resGraph = new MapGraph();

        addNodesToGraph();
        addEdgesToGraph();
        //addEdgesToGraphTopoSort();
        testStopsFrequenciesOnRoutes();
        

        return resGraph;
    }

    private int TRANSPORT_MEASURE = 0;

    private MapGraph resGraph = null;

    private Set<String> consideredRoutes = new HashSet<>(); // drogi przechowywane po route.id
    private Set<Stop> consideredStops = new HashSet<>(); // przystanki przechowywane bezposrednio
}
