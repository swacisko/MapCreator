/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import javax.swing.JPanel;

/**
 * CSPanel is ColorSettingsDialog. We can change default colors of elements in here
 * @author swacisko
 */
public class CSPanel extends JPanel {
    public CSPanel(){
        setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );
        
    }
    
    
    private int DEFAULT_WIDTH = 100;
    private int DEFAULT_HEIGHT = 100;
    
}
