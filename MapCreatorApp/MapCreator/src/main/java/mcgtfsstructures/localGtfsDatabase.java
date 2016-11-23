/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import java.io.File;
import java.util.ArrayList;
import static java.util.Collections.sort;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author swacisko
 */
public class localGtfsDatabase {

    public static void init() {
        stops = GTFSInput.getAllStops();
        routes = GTFSInput.getAllRoutes(); // getAllStops musi byc pr
        shapes = GTFSInput.getAllShapes();
        trips = GTFSInput.getAllTrips();
        stoptimes = GTFSInput.getAllStopTimes();

        initMaps();

        assignStopsToRoutes();

    }

    private void checkAssignedStopsToRoutes() {
        System.out.println("Oto twoje drogi i ich przystanki");
        for (Route r : routes) {
            System.out.println("Route ID = " + r.getRouteId() + "\nThere are " + r.getStopIds().size() + " stops on that route:");
            for (String s : r.getStopIds()) {
                System.out.print(s + "  |  ");
            }
            System.out.println("\n");
        }
    }

    // tworzy wszystkie mapy (stopsMap, tripsMap,...)
    private static void initMaps() {
        stopsMap = new HashMap<>();
        for (Stop s : stops) {
            stopsMap.put(s.getStopId(), s);
        }

        routesMap = new HashMap<>();
        for (Route r : routes) {
            routesMap.put(r.getRouteId(), r);
        }

        tripsMap = new HashMap<>();
        for (Trip t : trips) {
            tripsMap.put(t.getTripId(), t);
        }

        shapesMap = new HashMap<>();
        for (Shape sh : shapes) {
            if (shapesMap.containsKey(sh.getShapeId()) == false) {
                shapesMap.put(sh.getShapeId(), new ArrayList<Shape>());
            }
            shapesMap.get(sh.getShapeId()).add(sh);
        }

        stoptimesMap = new HashMap<>();
        for (StopTime st : stoptimes) {
            if (stoptimesMap.containsKey(st.getTripId()) == false) {
                stoptimesMap.put( st.getTripId(), new ArrayList<StopTime>() );
            }
            stoptimesMap.get( st.getTripId() ).add( st );
        }
    }

    // do kazdej drogi przypisuje przystanki, ktore znajduja sie na tej drodze
    private static void assignStopsToRoutes() {
        for (StopTime st : stoptimes) {
            String stopid = st.getStopId();
            Trip t = tripsMap.get(st.getTripId());
            Route r = routesMap.get(t.getRouteId());
            if (r.containsStopOfId(stopid) == false) {
                r.addStopId(stopid);
            }
        }
    }

    public static void setGtfsDirPath(String path) {
        gtfsDirPath = path;
    }

    public static String writeDatabase() {
        String s = stops.toString() + routes.toString() + shapes.toString() + trips.toString();
        return s;
    }

    //********************************************************** SECTION WITH EXTRACTING REQUIRED GTFS DATA
    //   SHAPES
    // Shape to gtfs-struktura, dla ktorej id moze wystepowac w wielu liniach pliku, wiec zwracam wszystkie takie. Zwracam posortowane tak, aby odpowiadaly kolejnosci
    // wystepowania na lini, ktora dany shapes opisuje
    // jezeli nie ma elementow o zadanym id, zwracam null
    public static ArrayList< Shape> getAllShapesOfId(String id) {
        /*ArrayList<Shape> res = new ArrayList<>();
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
         }*/

        return shapesMap.get(id);

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
        return routesMap.get(id);
    }

    public static ArrayList<Route> getAllRoutes() {
        return routes;
    }
    // END OF ROUTES SECTION

    //   STOPS SECTION
    //zawra przystanek o zadanym id, jezeli takiego nie ma, zwraca null
    public static Stop getStopOfID(String id) {
        return stopsMap.get(id);
    }

    public static ArrayList<Stop> getAllStops() {
        return stops;
    }
    //END OF STOPS SECTION

    //   TRIPS SECTION
    public static ArrayList<Trip> getAllTrips() {
        return trips;
    }

    public static Trip getTripOfId(String id) {
        return tripsMap.get(id);
    }

    //END OF TRIPS SECTION
    //    STOP_TIMES SECTION
    public static ArrayList<StopTime> getAllStopTimes() {
        return stoptimes;
    }

    // zwraca liste wszystkich StopTimes, dla ktorych trip_id = id. Zwraca w posortowanej kolejnosci - w takiej, w jakiej wystepuje naprawde w trasie
    public static ArrayList<StopTime> getAllStopTimesOfTripId(String id) {
        /*ArrayList<StopTime> res = new ArrayList<>();
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
        }*/
        
        return stoptimesMap.get(id);
    }

    private static void sortStopTimesByTripId(ArrayList<StopTime> l) {
        sort(l, new StopTimeSequenceComparator());
    }
    //END OF STOP_TIMES SECTION

    private static String gtfsDirPath = (new File("").getAbsolutePath()) + "/GTFS/";

    private static ArrayList< Stop> stops = null;
    public static ArrayList< Route> routes = null;
    private static ArrayList< Shape> shapes = null;
    private static ArrayList< Trip> trips = null;
    private static ArrayList< StopTime> stoptimes = null;

    // poniższe mapy przechowuja dane struktur po ich ID - zeby moc zdecydowanie szybciej wyszukiwac potrzebne nam informacje
    private static Map<String, Stop> stopsMap = null; // mapowane po stop.id
    private static Map<String, Route> routesMap = null; // mapowane po route.id
    private static Map<String, Trip> tripsMap = null; // mapowane po trip.id
    private static Map<String, ArrayList<Shape>> shapesMap = null; // mapowane po shape.id
    private static Map<String, ArrayList<StopTime>> stoptimesMap = null; // mapowane po stoptime.getTripId();

}
