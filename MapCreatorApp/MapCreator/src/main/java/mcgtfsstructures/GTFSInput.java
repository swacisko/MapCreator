package mcgtfsstructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

public class GTFSInput {

    public GTFSInput() {
    }
    

    // this function sets path to the GTFS folder we need to have
    public static void setGtfsDirPath(String path) {
        gtfsDirPath = path;
    }

    /**
     * This is just invoking {@link #readFile(java.lang.String) } function. To be honest, {@link #processFile(java.lang.String) } function is completely dispensible.
     * @param filename
     * @return 
     */
    public static ArrayList<String> processFile(String filename) {
        try {            
            ArrayList<String> list = readFile(filename);
            return list;
        } catch (Exception e) {
          //  e.printStackTrace();
            System.out.println( "Wyjatek przy wczytywaniu z pliku " + filename );
            return new ArrayList<>();
        }
    }

    /**
     * This function reads line after line from file with given filename (filename must be a name of a gtfs file, e.g. routes.txt).
     * @param filename name of the file. E.g. routes.txt, stops.txt, stoptimes.txt.
     * @return returns an ArrayList of strings, each string describing a single line in given file.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static ArrayList<String> readFile(String filename) throws FileNotFoundException, IOException {
        String path = MCSettings.getGtfsDirectoryPath() + filename;

        if( UsefulFunctions.existsFile(path) == false ) { 
            return new ArrayList<>();
        }

        ArrayList<String> data = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();

        while (line != null) {
            data.add(line);
            line = br.readLine();
        }

        return data;
    }

    /**
     * processFileData function is responsible for dividing single lines read in {@link #readFile(java.lang.String)} function into separate strings.
     * E.g. following line
     * route_id, route_short_name, "route_long_name",,
     * will be divided into 5 strings. All quotation marks will be ignored.
     * @param data It is the single line of a file to be divided into.
     * @return 
     */
    public static ArrayList<String> processFileData(String data) {
        ArrayList<String> list = new ArrayList<>();
        String fragment = "";
        boolean openParenthesis = false;
        for (int i = 0; i < data.length(); i++) {

            if ( (data.charAt(i) == ',') && (openParenthesis == false) ) {
                if (!fragment.equals("")) {
                    if (fragment.endsWith("\"")) {
                    //    System.out.println( "fragment = " + fragment );
                        fragment = fragment.substring(1, fragment.length() - 1);
                     //   System.out.println( "\tnow fragment = " + fragment );
                    }
                    list.add(fragment);
                    fragment = "";
                } else {
                    list.add("");
                }
            } else {
                if( data.charAt(i) == '\"' ){
                    openParenthesis = !openParenthesis;
                }
                fragment += data.charAt(i);
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

    /**
     * {@link #getDataMap(java.util.ArrayList, java.util.ArrayList) } function converts single lines of gtfs file into maps. After calling that function each string
     * in line of the file (e.g. shapes.txt) will be associated in a map with it's 'descriptor' - e.g. "Sobieskiego nż 11" will be associated with descriptor "stop_id".
     * @param pattern pattern is the first line of given gtfs file, separated into different entities. According to it, the data in the file will be mapped.
     * @param data data represents a single (separated into strings) line in gtfs file.
     * @return 
     */
    public static Map<String, String> getDataMap(ArrayList<String> pattern, ArrayList<String> data) {
        if (pattern.size() != data.size()) {
            System.out.println("pattern.size() != data.size()    in getDataMap, GTFSInput.java");
            return new HashMap<>();
        }

        Map<String, String> mapa = new HashMap<>();
        for (int i = 0; i < pattern.size(); i++) {
            if ( !pattern.get(i).equals("") ) {
                mapa.put(pattern.get(i).intern(), data.get(i).intern());
            }
        }
        return mapa;
    }

    /**
     * 
     * @param filename
     * @return 
     */
    public static ArrayList< Map<String, String>> getDataMaps(String filename) {       
        ArrayList<String> lista = processFile(filename);
        
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

    /**
     * Function reads and converts all data in stops.txt file.
     * @return return converted data as an array list of Stop objects.
     */
    public static ArrayList< Stop> getAllStops() {
        System.out.print( "Wczytuje dane z pliku stops.txt... " );
        ArrayList<Stop> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "stops.txt" );
        for( Map<String,String> m : dataMaps ){
            Stop stp = new Stop( m );
            res.add( stp );
        }        
        System.out.println( "Dane wczytane!" );
        return res;
    }

    /**
     * Function reads and converts all data in routes.txt file.
     * @return return converted data as an array list of Route objects.
     */
    public static ArrayList< Route> getAllRoutes() {
        System.out.print( "Wczytuje dane z pliku routest.txt... " );
        ArrayList< Route> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "routes.txt" );
        for( Map<String,String> m : dataMaps ){
            Route stp = new Route( m );
            res.add( stp );
        }
        System.out.println( "Dane wczytane!" );
        return res;
    }

    /**
     * Function reads and converts all data in shapes.txt file.
     * @return return converted data as an array list of Shape objects..
     */
    public static ArrayList< Shape > getAllShapes() {
        System.out.print( "Wczytuje dane z pliku shapes.txt... " );
        ArrayList< Shape> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "shapes.txt" );
        
        for( Map<String,String> m : dataMaps ){
            Shape stp = new Shape( m );
            res.add( stp );
        }
        System.out.println( "Dane wczytane!" );
        return res;
    }

    /**
     * Function reads and converts all data in trips.txt file.
     * @return return converted data as an array list of Trip objects..
     */
    public static ArrayList< Trip> getAllTrips() {
        System.out.print( "Wczytuje dane z pliku trips.txt... " );
        ArrayList< Trip> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "trips.txt" );
        for( Map<String,String> m : dataMaps ){
            Trip stp = new Trip( m );
            res.add( stp );
        }
        System.out.println( "Dane wczytane!" );
        return res;
    }

    /**
     * Function reads and converts all data in stoptimes.txt file.
     * @return return converted data as an array list of StopTime objects.
     */
    public static ArrayList< StopTime> getAllStopTimes(){
        System.out.print( "Wczytuje dane z pliku stoptimes.txt... " );
        ArrayList< StopTime> res = new ArrayList<>();
        ArrayList< Map<String,String> > dataMaps = getDataMaps( "stop_times.txt" );
        for( Map<String,String> m : dataMaps ){
            StopTime stp = new StopTime( m );
            res.add( stp );
        }
        System.out.println( "Dane wczytane!" );
        return res;
    }
    
    
    
    public static String gtfsDirPath = MCSettings.getGtfsDirectoryPath();

}
