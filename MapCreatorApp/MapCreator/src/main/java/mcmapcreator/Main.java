package mcmapcreator;

import mcgraphs.MapGraph;
import mcgtfsstructures.MCDatabase;
import mcmapdrawing.SVG;
import mcmapdrawing.DrawingModule;
import mctemplates.MCConstants;

public class Main {

    public static void main(String[] args) {

     //  TEST_SVG t = new TEST_SVG();
     //  t.test();
        
        
        
        MCDatabase.init(); // na samym poczatku musze zainicjalizowac baze danych
        
       // MapGraph g = new MapGraph();
       // g.testGraph();        
        
        MCConstants.addRouteToHighlight( "1" );
        MCConstants.addRouteToHighlight( "101" );
        MCConstants.addRouteToHighlight( "106" );
        MCConstants.addRouteToHighlight( "521" );
        MCConstants.addRouteToHighlight( "74" );
        MCConstants.addRouteToHighlight( "121" );
        MCConstants.addRouteToHighlight( "74" );
        MCConstants.addRouteToHighlight( "A" );
        MCConstants.addRouteToHighlight( "E" );
        
        DrawingModule dm = new DrawingModule( new SVG( MCConstants.getINITIAL_SVG_WIDTH(),MCConstants.getINITIAL_SVG_HEIGHT(),"Rysowanie" ) );        
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
