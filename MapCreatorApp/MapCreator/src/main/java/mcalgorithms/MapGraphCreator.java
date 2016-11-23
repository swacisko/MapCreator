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
import mcgraphs.*;
import mcgtfsstructures.*;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */

// TWORZY GRAF Z DANYCH GTFS
public class MapGraphCreator {
        
   // wybiera wszystkie drogi, ktorych typ jest odpowiedni do tego przekazanego przez TRANSPORT_MEASURE
    private void createConsideredRoutesAndStops(){
     //   int CNT = 0;
        for( Route r : localGtfsDatabase.getAllRoutes() ){
            int routetype = Integer.parseInt( r.getRouteType() );
            if( (TRANSPORT_MEASURE & ( 1 << routetype )) != 0 ){
                consideredRoutes.add( r );
                for( String s : r.getStopIds() ){
                  //  if( consideredStops.contains( localGtfsDatabase.getStopOfID( s ) ) == false ) CNT++;
                    consideredStops.add( localGtfsDatabase.getStopOfID( s ) );                    
                }
            }            
        }
        
     //   System.out.println( "Dodalem lacznie " + CNT + "  przystankow do narysowania, a wszystkich jest " + localGtfsDatabase.getAllStops().size() );
    }
        
    private void addNodesToGraph(){
       // for( Stop s : localGtfsDatabase.getAllStops() ) consideredStops.add(s); // TO JEST TYLKO TYMCZASOWE I DO USUNIECIA POZNIEJ!
        
        for(Stop s : consideredStops){
            MapNode node = new MapNode();
            node.setCoords( s.getCoords() );
            node.setStructureName(s.getStopName() );
            node.setDescription( "Structure represents a STOP" );
            node.setColor( s.getColor() );
            node.addContainedStopId( s.getStopId() );
            
            resGraph.addMapNode(node);
        }
    }
    
    private void addEdgesToGraph(){
        Set< Pair<Integer,Integer> > edgesToAdd = new HashSet<>(); // edgesToAdd to zbior par, z ktorych kazda okresla 2 id wierzcholkow, ktore chce polaczyc
        
        Map<String,Integer> nodeIdReversed = new HashMap<>();  // nodeIdReversed.get(key) to wartosc value taka, ze przystanek o id = key znajduje sie w wierzcholku grafu o id = value
            
        
        
        for( Trip t : localGtfsDatabase.getAllTrips() ){
            ArrayList<StopTime> l = localGtfsDatabase.getAllStopTimesOfTripId( t.getTripId() );
            for( int i=0; i<l.size(); i++ ){
                
            }
            
        }
        
    }

    public MapGraph createMapGraphFromGtfsDatabase( int TRANSPORT_MEASURE ){
        this.TRANSPORT_MEASURE = TRANSPORT_MEASURE;
        createConsideredRoutesAndStops();
        
        resGraph = new MapGraph();       
        
        addNodesToGraph();
        addEdgesToGraph();
        
        
        
        return resGraph;
    }
    
    
    private int TRANSPORT_MEASURE = 0;
    
    private MapGraph resGraph = null;
    
    private Set<Route> consideredRoutes = new HashSet<>();
    private Set<Stop> consideredStops = new HashSet<>();
}
