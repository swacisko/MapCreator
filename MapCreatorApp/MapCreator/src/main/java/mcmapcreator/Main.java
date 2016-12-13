package mcmapcreator;

import mcgraphs.MapGraph;
import mcgtfsstructures.MCDatabase;
import mcmapdrawing.SVG;
import mcmapdrawing.DrawingModule;
import mctemplates.MCSettings;

public class Main {

    public static void main(String[] args) {

     //  TEST_SVG t = new TEST_SVG();
     //  t.test();
        
        
        
        MCDatabase.init(); // na samym poczatku musze zainicjalizowac baze danych
        
       // MapGraph g = new MapGraph();
       // g.testGraph();        
        
        for( int i=1; i<=15; i++ ){
            MCSettings.addRouteToHighlight( ""+i );
        }
        
        DrawingModule dm = new DrawingModule( new SVG( MCSettings.getINITIAL_SVG_WIDTH(),MCSettings.getINITIAL_SVG_HEIGHT(),"Rysowanie" ) );        
        dm.drawAllMaps();
        
        
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
