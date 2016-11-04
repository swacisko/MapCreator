package inzynierka;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class GTFSInput {
    public GTFSInput(){
        
        
    }
    
    public static ArrayList<String> processFile( String filename ){
        
        try{
            ArrayList<String> list = readFile(filename);
            return list;
        }catch(Exception e){
            return null;
        }
        
        
        
    }
    
    public static ArrayList<String> readFile( String filename ) throws FileNotFoundException, IOException{
         String path = "/home/swacisko/NetBeansProjects/Inzynierka/GTFS/" + filename;
         ArrayList<String> data = new ArrayList<>();
        
        BufferedReader br = new BufferedReader(new FileReader( path ));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            data.add(line);
            line = br.readLine();
        }
        
        return data;
    }
    
    
    public static ArrayList<String> readStops(){
        String path = "/home/swacisko/NetBeansProjects/Inzynierka/GTFS/stops.txt";
        ArrayList<String> stops = new ArrayList<>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader( path ));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            while (line != null) {
                stops.add(line);
                line = br.readLine();
            }            
            br.close();
             
        } catch( Exception e ){            
        }
        
        return stops;
    }
    
    // for given String - whole line in gtfs file - we get the data separated into Strings
    public static ArrayList<String> processStopData( String data ){
        ArrayList<String> list = new ArrayList<>();
        String fragment = "";
        for( int i=0; i<data.length(); i++ ){
            
            if( data.charAt(i) == ',' ){
                if( !fragment.equals("") ){
                    list.add(fragment);  
                    fragment = "";
                }
                else{
                    list.add( "--------" );
                }
            }
            else{
                fragment = fragment + data.charAt(i);
            }
            
        }
        
        if( !fragment.equals("") ){
            list.add(fragment);
        }else {
            list.add( "--------" );
        }
        
        return list;
    }
           
    
    
    
    
    
    
}
