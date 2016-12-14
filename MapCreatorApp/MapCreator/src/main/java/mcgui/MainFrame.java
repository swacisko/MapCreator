/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class is the main frame of MapCreator's GUI. Here you can click to choose what do you want to change, select, draw, etc.
 * @author swacisko
 */
public class MainFrame extends JFrame {
    
    public MainFrame(){
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
        
        setLayout( new GridBagLayout() );
        
        
        
        snp = new SelectedNodePanel();
        add( snp, new GBC( 0,0,1,1).setAnchor( GBC.CENTER ).setFill(GBC.BOTH) );
        
        
        
    }
    
    
    
    SchemeContructionPanel glp = null;
    
    private JPanel snp = null;
}
