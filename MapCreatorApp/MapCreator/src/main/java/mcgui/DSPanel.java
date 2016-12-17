/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import javax.swing.JPanel;

/**
 * DSPanel is DisplaySettingsDialog. We can change here widths of nodes, edges and select background transport measures to be drawn
 * @author swacisko
 */
public class DSPanel extends JPanel {
    
    public DSPanel(){
        setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );
        
    }
    
    
    private int DEFAULT_WIDTH = 100;
    private int DEFAULT_HEIGHT = 100;
}
