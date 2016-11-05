package com.mycompany.mavenwordengine;

import java.awt.EventQueue;

public class Inzynierka {

    public static void main(String[] args) {
        
       // TEST_SVG t = new TEST_SVG();
       // t.test();
        
       /* EventQueue.invokeLater(new Runnable(){            
                @Override
                public void run(){
                    InzFrame frame = new InzFrame();
                    frame.setVisible(true);
                    frame.setResizable(true);
                }            
            }
        );*/
        
        try{
            System.out.println( "SIEMANO, coś tu jest nie tak" );
                GtfsReaderExampleMain r = new GtfsReaderExampleMain();
                r.TEST();
        }	
        catch( Exception e ){
		e.printStackTrace();
        }       
                
        
    }
    
}
