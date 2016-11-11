/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import java.io.File;
import java.util.ArrayList;
import static java.util.Collections.sort;

/**
 *
 * @author swacisko
 */
public class localGtfsDatabase {

    
    public static void init() {
        stops = GTFSInput.getAllStops();
        routes = GTFSInput.getAllRoutes();
        shapes = GTFSInput.getAllShapes();
        trips = GTFSInput.getAllTrips();
        stoptimes = GTFSInput.getAllStopTimes();
    }

    public static void setGtfsDirPath(String path) {
        gtfsDirPath = path;
    }
    
    public static String write(){
        String s = stops.toString() + routes.toString() + shapes.toString() + trips.toString();
        return s;
    }
    
    
    
    //********************************************************** SECTION WITH EXTRACTING REQUIRED GTFS STRUCTURES
    
    // Shape to jedyna gtfs-struktura, dla ktorej id moze wystepowac w wielu liniach pliku, wiec zwracam wszystkie takie
    // jezeli nie ma elementow o zadanym id, zwracam null
    public static ArrayList< Shape > getAllShapesOfID( String id ){
        ArrayList<Shape> res = new ArrayList<>();
        for( Shape s : shapes ){
            if( s.getShapeId().equals( id ) ){
                res.add(s);
            }
        }
        
        if( res.isEmpty() ) return null;
        else {
            sortShapesBySequence(res);
            return res;            
        }
    }
    
    // zwraca droge o zadanym id, jezeli takiego nie ma, zwraca null
    public static Route getRouteOfID( String id ){
        for( Route r : routes ){
            if( r.getRouteId().equals( id ) ){
                return r;
            }
        }
        return null;
    }
    
    //zawra przystanek o zadanym id, jezeli takiego nie ma, zwraca null
    public static Stop getStopOfID( String id ){
        for( Stop s : stops ){
            if( s.getStopId().equals( id ) ){
                return s;
            }
        }
        return null;
    }
    
    
    public static ArrayList<Stop> getAllStops(){
        return stops;
    }
    
    public static ArrayList<Route> getAllRoutes(){
        return routes;
    }
    
    public static ArrayList<Shape> getAllShapes(){
        return shapes;
    }
    
    public static ArrayList<Trip> getAllTrips(){
        return trips;
    }
    

    private static void sortShapesBySequence( ArrayList<Shape> l ){
        sort( l, new ShapeSequenceComparator() );
    }
    
    
    
    
    

    private static String gtfsDirPath = (new File("").getAbsolutePath()) + "/GTFS/";
    
    
    private static ArrayList<Stop> stops = null;
    public static ArrayList< Route> routes = null;
    private static ArrayList< Shape > shapes = null;
    private static ArrayList< Trip> trips = null;
    private static ArrayList< StopTime> stoptimes = null;
}
