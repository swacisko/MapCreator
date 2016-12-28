package mcmapcreator;

import java.awt.EventQueue;
import javax.swing.JFrame;
import mcgtfsstructures.MCDatabase;
import mcgui.MainFrame;
import mcmapdrawing.SVG;
import mcmapdrawing.DrawingModule;
import mctemplates.MCSettings;

public class Main {

    public static void main(String[] args) {

     //  TEST_SVG t = new TEST_SVG();
     //  t.test();
        
        
                
       // MapGraph g = new MapGraph();
       // g.testGraph();        
        
        /*for( int i=1; i<=15; i++ ){
            MCSettings.addRouteToHighlight( ""+i );
        }*/
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setResizable(true);
                
            }
        });
        
        /*DrawingModule dm = new DrawingModule( new SVG( MCSettings.getINITIAL_SVG_WIDTH(),MCSettings.getINITIAL_SVG_HEIGHT(),"Rysowanie" ) );        
        dm.drawAllMaps();*/
        
    }

}
