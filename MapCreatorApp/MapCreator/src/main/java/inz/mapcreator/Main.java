package inz.mapcreator;

import mapdrawing.*;

public class Main {

    public static void main(String[] args) {

     //  TEST_SVG t = new TEST_SVG();
     //  t.test();
        
        //localGtfsDatabase.init();
        //System.out.println( localGtfsDatabase.write() );
        
        /*
        MapGraph g = new MapGraph();
        g.testGraph();
        */
        
        
        DrawingModule dm = new DrawingModule( new SVG( 2000,2000,"Pierwsze rysowanie" ) );
        dm.beginSVG();
        dm.drawAll();
        dm.endSVG();
        
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
