/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import mcmapdrawing.DrawingModule;

/**
 * This class is the main frame of MapCreator's GUI. Here you can click to choose what do you want to change, select, draw, etc.
 * @author swacisko
 */
public class MainFrame extends JFrame {
    
    public MainFrame(){
        setTitle("Map Creator");
        Toolkit t = Toolkit.getDefaultToolkit();
        try {
            //setIconImage(ImageIO.read(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dimension d = t.getScreenSize();
        setSize(d.width, d.height);
        setResizable(false);
                
        schemePanel = new SchemeContructionPanel( d.width - 30, d.height-350 );
        schemePanel.setSelectedItems(selectedItems);
        setLayout( new BorderLayout() );
        add( schemePanel, BorderLayout.CENTER );
        
        managerFrame = new ManagerFrame(this, selectedItems);
        managerFrame.setVisible(true);
        managerFrame.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
        
    }
        
    private SelectedItems selectedItems = new SelectedItems();
    private SchemeContructionPanel schemePanel = null;
    private DrawingModule module = null;
    private ManagerFrame managerFrame = null;
}
