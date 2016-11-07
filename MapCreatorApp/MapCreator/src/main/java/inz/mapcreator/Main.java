package inz.mapcreator;

import gtfsstructures.localGtfsDatabase;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        
       // TEST_SVG t = new TEST_SVG();
       // t.test();
        
        
        localGtfsDatabase.init();
        
        System.out.println( localGtfsDatabase.write() );
        
        
        /*EventQueue.invokeLater(new Runnable(){            
                @Override
                public void run(){
                    InzFrame frame = new InzFrame();
                    frame.setVisible(true);
                    frame.setResizable(true);
                }            
            }
        );*/
                
                
        
    }
    
}
