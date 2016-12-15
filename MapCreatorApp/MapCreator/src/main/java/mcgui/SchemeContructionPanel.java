/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import mcmapdrawing.DrawingModuleInterface;

/**
 *  This class is a panel on which we draw a scheme. 
 * @author swacisko
 */
public class SchemeContructionPanel extends JPanel implements DrawingModuleInterface{

    
    public SchemeContructionPanel( int width, int height ){
         setSize( width, height );  
         
         setLayout( new FlowLayout());
         
        // add( new JTextArea(1000,1000), BorderLayout.CENTER );
         for( int i=0; i<20; i++ ){
             add( new JButton(""+i) );
         }
         
    }
    
    
    
    
    
    @Override
    public void addEllipse(Point p, int w, int h) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCircle(Point p, int radius) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addLine(Point beg, Point end, int width) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPolyline(ArrayList<Point> polyline) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addText(String text, Point p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setColor(Color c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setStrokeWidth(int width) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setFill(Color c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTextSize(int fontsize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void begin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void end() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    
    
    
    
    //******************************************** CLASS FIELDS
    
    /**
     * This is class field to enable functions from {@link DrawingModuleInterface} be called without passing Graphics object as a parameter
     */
    private Graphics2D graphics = null;
    
    private SelectedItems selectedItems = null;    
    private int DEFAULT_WIDTH = 800;
    private int DEFAULT_HEIGHT = 800;

    

    
    
    
    
    //*********************************************************
    
    class MouseHandler extends MouseAdapter{
        
        @Override
        public void mousePressed(MouseEvent event){
            
        }
        
        @Override
        public void mouseClicked( MouseEvent event ){
            
        }
        
    }
    
    class MouseMotionHandler implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }
        
    }
    
}
