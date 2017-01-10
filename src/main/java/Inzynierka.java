package inzynierka;

import java.awt.EventQueue;

public class Inzynierka {

    public static void main(String[] args) {
        
       // TEST_SVG t = new TEST_SVG();
       // t.test();
        
        EventQueue.invokeLater(new Runnable(){            
                @Override
                public void run(){
                    InzFrame frame = new InzFrame();
                    frame.setVisible(true);
                    frame.setResizable(true);
                }            
            }
        );
                
                
        
    }
    
}
