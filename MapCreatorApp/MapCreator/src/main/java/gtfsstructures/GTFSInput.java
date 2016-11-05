package gtfsstructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GTFSInput {

    public GTFSInput() {
    }

    // this function sets path to the GTFS folder we need to have
    public static void setGtfsDirPath(String path) {
        gtfsDirPath = path;
    }

    public static ArrayList<String> processFile(String filename) {
        try {
            ArrayList<String> list = readFile(filename);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    //
    public static ArrayList<String> readFile(String filename) throws FileNotFoundException, IOException {
        //String path = "/home/swacisko/NetBeansProjects/Inzynierka/GTFS/" + filename;
        String path = (new File("").getAbsolutePath()) + "/GTFS/" + filename;

        System.out.println("path = " + path);

        ArrayList<String> data = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            data.add(line);
            line = br.readLine();
        }

        return data;
    }

    // for given String - whole line in gtfs file - we get the data separated into Strings
    public static ArrayList<String> processFileData(String data) {
        ArrayList<String> list = new ArrayList<>();
        String fragment = "";
        for (int i = 0; i < data.length(); i++) {

            if (data.charAt(i) == ',') {
                if (!fragment.equals("")) {
                    if (fragment.endsWith("\"")) {
                        fragment = fragment.substring(1, fragment.length() - 2);
                    }
                    list.add(fragment);
                    fragment = "";
                } else {
                    list.add("");
                }
            } else {
                fragment = fragment + data.charAt(i);
            }

        }

        if (!fragment.equals("")) {
            if (fragment.endsWith("\"")) { // here i check whether the given data is not in ""
                fragment = fragment.substring(1, fragment.length() - 2);
            }
            list.add(fragment);
        } else {
            list.add("");
        }

        return list;
    }

    public static Map<String, String> getDataMap(ArrayList<String> pattern, ArrayList<String> data) {
        if (pattern.size() != data.size()) {
            System.out.println("pattern.size() != data.size()    in getDataMap, GTFSInput.java");
            return null;
        }

        Map<String, String> mapa = new HashMap<>();
        for (int i = 0; i < pattern.size(); i++) {
            if (!pattern.get(i).equals("")) {
                mapa.put(pattern.get(i), data.get(i));
            }
        }
        return mapa;
    }

    public static ArrayList< Map<String, String>> getDataMaps(String filename) {
        String pth = gtfsDirPath + filename;
        ArrayList<String> lista = processFile(pth);

        ArrayList< Map<String, String>> res = new ArrayList<>();

        ArrayList<String> pattern = null;
        if (lista.size() > 0) {
            pattern = processFileData(lista.get(0));
        }
        for (int i = 1; i < lista.size(); i++) {
            ArrayList<String> singleData = processFileData(lista.get(i));
            Map<String, String> m = getDataMap(pattern, singleData);
            res.add(m);
        }

        return res;
    }

    public static ArrayList< Stop> getAllStops() {
        ArrayList<Stop> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "stops.txt" );
        for( Map<String,String> m : dataMaps ){
            Stop stp = new Stop( m );
            res.add( stp );
        }
        return res;
    }

    public static ArrayList< Route> getAllRoutes() {
        ArrayList< Route> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "routes.txt" );
        for( Map<String,String> m : dataMaps ){
            Route stp = new Route( m );
            res.add( stp );
        }
        return res;
    }

    public static ArrayList< Shape> getAllShapes() {
        ArrayList< Shape> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "shapes.txt" );
        for( Map<String,String> m : dataMaps ){
            Shape stp = new Shape( m );
            res.add( stp );
        }
        return res;
    }

    public static ArrayList< Trip> getAllTrips() {
        ArrayList< Trip> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "trips.txt" );
        for( Map<String,String> m : dataMaps ){
            Trip stp = new Trip( m );
            res.add( stp );
        }
        
        return res;
    }

    public static String gtfsDirPath = (new File("").getAbsolutePath()) + "/GTFS/";

}
