/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import mcgtfsstructures.MCDatabase;
import mcmapcreator.FileChooser;

/**
 * This class is the main frame of MapCreator's GUI. Here you can click to choose what do you want to change, select, draw, etc.
 * MainFrame consists of {@link SchemeContructionPanel}, and an instance of {@link ManagerFrame} as separate JFrame.
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
        
                        
        schemePanel = new SchemeContructionPanel( d.width - 30, d.height-30, selectedItems );
        schemePanel.setParentFrame(this);
        setLayout( new BorderLayout() );
        JScrollPane scroll = new JScrollPane( schemePanel );
        add( scroll, BorderLayout.CENTER );
        
        setVisible(true);
        selectAndReadGtfs();
               
        managerFrame = new ManagerFrame(this, selectedItems);
        managerFrame.setLocation( new Point( (2*d.width/3), d.height / 10  ) );
        managerFrame.setSize( d.width / 3, d.height/2 );
        managerFrame.setVisible(true);
        managerFrame.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );                
    }
    
    private void selectAndReadGtfs(){
        FileChooser.chooseGtfsDirectory(schemePanel);        
        MCDatabase.init();
    }
    
    public void showManager(){
        managerFrame.setVisible(true);
        managerFrame.toFront();
    }
        
    @Override
    public void repaint(){
        super.repaint();
        managerFrame.repaint();
    }
        
    private SelectedItems selectedItems = new SelectedItems();
    private SchemeContructionPanel schemePanel = null;
    private ManagerFrame managerFrame = null;
    
}
