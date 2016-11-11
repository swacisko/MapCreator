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

    public static String writeDatabase() {
        String s = stops.toString() + routes.toString() + shapes.toString() + trips.toString();
        return s;
    }

    //********************************************************** SECTION WITH EXTRACTING REQUIRED GTFS STRUCTURES
    //   SHAPES
    // Shape to gtfs-struktura, dla ktorej id moze wystepowac w wielu liniach pliku, wiec zwracam wszystkie takie. Zwracam posortowane tak, aby odpowiadaly kolejnosci
    // wystepowania na lini, ktora dany shapes opisuje
    // jezeli nie ma elementow o zadanym id, zwracam null
    public static ArrayList< Shape> getAllShapesOfId(String id) {
        ArrayList<Shape> res = new ArrayList<>();
        for (Shape s : shapes) {
            if (s.getShapeId().equals(id)) {
                res.add(s);
            }
        }

        if (res.isEmpty()) {
            return null;
        } else {
            sortShapesBySequence(res);
            return res;
        }
    }

    public static ArrayList<Shape> getAllShapes() {
        return shapes;
    }

    // sortuje liste shapesow po wartoisc sequence
    private static void sortShapesBySequence(ArrayList<Shape> l) {
        sort(l, new ShapeSequenceComparator());
    }
    //END OF SHAPES SECTION
    //   ROUTES SECTION
    // zwraca droge o zadanym id, jezeli takiego nie ma, zwraca null
    public static Route getRouteOfID(String id) {
        for (Route r : routes) {
            if (r.getRouteId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public static ArrayList<Route> getAllRoutes() {
        return routes;
    }
    // END OF ROUTES SECTION
    //   STOPS SECTION
    //zawra przystanek o zadanym id, jezeli takiego nie ma, zwraca null
    public static Stop getStopOfID(String id) {
        for (Stop s : stops) {
            if (s.getStopId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static ArrayList<Stop> getAllStops() {
        return stops;
    }
    //END OF STOPS SECTION
    //   TRIPS SECTION
    public static ArrayList<Trip> getAllTrips() {
        return trips;
    }

    //END OF TRIPS SECTION
    
    //    STOP_TIMES SECTION
    public static ArrayList<StopTime> getAllStopTimes() {
        return stoptimes;
    }

    public static ArrayList<StopTime> getAllStopTimesOfTripId(String id) {
        ArrayList<StopTime> res = new ArrayList<>();
        for (StopTime s : stoptimes) {
            if (s.getTripId().equals(id)) {
                res.add(s);
            }
        }

        if (res.isEmpty()) {
            return null;
        } else {
            sortStopTimesByTripId(res);
            return res;
        }
    }

    private static void sortStopTimesByTripId( ArrayList<StopTime> l ){
        sort( l, new StopTimeSequenceComparator() );
    }
   //END OF STOP_TIMES SECTION
    private static String gtfsDirPath = (new File("").getAbsolutePath()) + "/GTFS/";

    private static ArrayList<Stop> stops = null;
    public static ArrayList< Route> routes = null;
    private static ArrayList< Shape> shapes = null;
    private static ArrayList< Trip> trips = null;
    private static ArrayList< StopTime> stoptimes = null;
}
