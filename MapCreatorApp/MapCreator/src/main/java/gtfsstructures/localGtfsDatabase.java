/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gtfsstructures;

import java.io.File;
import java.util.ArrayList;

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
    }

    public static void setGtfsDirPath(String path) {
        gtfsDirPath = path;
    }
    
    public static String write(){
        String s = stops.toString() + routes.toString() + shapes.toString() + trips.toString();
        return s;
    }

    private static String gtfsDirPath = (new File("").getAbsolutePath()) + "/GTFS/";

    private static ArrayList< Stop> stops = null;
    public static ArrayList< Route> routes = null;
    private static ArrayList< Shape> shapes = null;
    private static ArrayList< Trip> trips = null;
}
