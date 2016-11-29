package mcmapcreator;

import mcgraphs.MapGraph;
import mcmapdrawing.SVG;
import mcmapdrawing.DrawingModule;
import mctemplates.MCConstants;

public class Main {

    public static void main(String[] args) {

     //  TEST_SVG t = new TEST_SVG();
     //  t.test();
        
        //localGtfsDatabase.init();
        //System.out.println( localGtfsDatabase.write() );
        
        
      //  MapGraph g = new MapGraph();
      //  g.testGraph();
        
        
        DrawingModule dm = new DrawingModule( new SVG( MCConstants.getINITIAL_SVG_WIDTH(),MCConstants.getINITIAL_SVG_HEIGHT(),"Pierwsze rysowanie" ) );        
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
