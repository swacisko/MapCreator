/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * This class is the main frame of MapCreator's GUI. Here you can click to choose what do you want to change, select, draw, etc.
 * @author swacisko
 */
public class MCFrame extends JFrame {
    
    public MCFrame(){
        arrangeInitialFrameSettings();        
    }
    
    private void arrangeInitialFrameSettings(){
        setTitle("MapCreator");
        Toolkit t = Toolkit.getDefaultToolkit();
        try {
            //setIconImage(ImageIO.read(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dimension d = t.getScreenSize();
        setSize(d.width, d.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    
    
    
}
