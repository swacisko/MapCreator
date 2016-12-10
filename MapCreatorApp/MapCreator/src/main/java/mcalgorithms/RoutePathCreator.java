/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mcgraphs.GraphPath;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mcgtfsstructures.MCDatabase;
import mcgtfsstructures.StopTime;
import mcgtfsstructures.Trip;
import mctemplates.MCConstants;
import mctemplates.Pair;

/**
 *
 * @author swacisko
 */
public class RoutePathCreator {
    public RoutePathCreator( MapGraph graph ){
        this.graph = graph;        
    }
    
    
    public Map< String, ArrayList<GraphPath> > createRoutePaths(){
        ArrayList<String> routes = MCConstants.getRoutesToHighlight();
        
        createStructureMap();
        Map< String,ArrayList<GraphPath> > res = new HashMap<>();
        
        for( String s : routes ){
            res.put( s, getGraphPathsForRoute(s) );
        }
        
        return res;
    }
    
    /**
     * Creates GraphPath object for given route
     * @param routeId
     * @return 
     */
    private ArrayList<GraphPath> getGraphPathsForRoute( String routeId ){
        createMostFrequentPaths(routeId);        
        ArrayList<GraphPath> res = new ArrayList<>();
        for( GraphPath gp : mostFrequentPaths ){
            GraphPath newgp = new GraphPath();
            for( Integer g : gp.getPathSequence() ){
                newgp.addToPath( g );
            }
            res.add( newgp );
        }
        
        return res;
    }
    
    private void createMostFrequentPaths( String routeId ){
        mostFrequentPaths.clear();
        ArrayList<Trip> trips = MCDatabase.getAllTripsOfRouteId(routeId);        
        
        ArrayList< Pair<GraphPath,Integer> > frequencies = new ArrayList<>();
        
        for( Trip t : trips ){
            ArrayList<StopTime> stoptimes = MCDatabase.getAllStopTimesOfTripId(t.getTripId());
            GraphPath p = new GraphPath();
            for( StopTime st : stoptimes ){                
                Integer nodeId = structureMap.get( st.getStopId() );
                if( p.isEmpty() && nodeId != null ){
                    p.addToPath(nodeId );
                }
                else if( nodeId != null ){
                    if( p.getPathEndValue() != nodeId ){
                        p.addToPath( nodeId );
                    }
                }                
            }
                       
            boolean found = false;
            for( Pair<GraphPath,Integer> entry : frequencies ){
                if( p.equals( entry.getST() ) ){
                    entry.setND( entry.getND() +1 );
                    found = true;      
                  //  System.out.println( "found a path equal to " + p );
                }
            }  
            
            if( found == false ){
               // System.out.println( "There is no path equal to " + p + "   adding that path" );
                frequencies.add( new Pair<>( p,1 ) );
            }
        }
        
        int M = 0;
        
        //System.out.println( "createMostFrequentPaths   routeId = " + routeId + "   frequencies:" );
        for( Pair<GraphPath,Integer> entry : frequencies ){
            if( entry.getND() > M ){
                M = entry.getND();
            }   
            
       //     System.out.println( entry.getST() + "  -->  " + entry.getND() );
        }
        
        
        for( Pair<GraphPath,Integer> entry : frequencies ){
            if( entry.getND()== M && (mostFrequentPaths.contains( entry.getST() ) == false) ){
                mostFrequentPaths.add( entry.getST());
            }
        }        
        
      //  System.out.println( "Most frequent paths:\n" + mostFrequentPaths );
    }
    
    private void createStructureMap(){
        structureMap.clear();
        for( MapNode n : graph.getNodes() ){
            for( String s : n.getContainedStopsIds() ){
                structureMap.put( s, n.getID() );
            }
        }
    }
    
    /**
     * For each stop id i store a structure id which contains that stop
     */    
    Map<String, Integer> structureMap = new HashMap<>();
    
    /**
     * This are most frequent paths 
     */
    ArrayList<GraphPath> mostFrequentPaths = new ArrayList<>();
    private MapGraph graph;
}
