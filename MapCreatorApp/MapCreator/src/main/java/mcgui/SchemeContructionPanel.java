/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;
import mcmapdrawing.DrawingModuleInterface;

/**
 *  This class is a panel on which we draw a scheme. 
 * @author swacisko
 */
public class SchemeContructionPanel extends JPanel implements DrawingModuleInterface{

    
    public SchemeContructionPanel(){
        
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
    public void addPolyline(ArrayList<Point> polyline, int width) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addText(String text, Point p, int size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setColor(Color c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    
    //******************************************** CLASS FIELDS
    
    /**
     * This is class field to enable functions from {@link DrawingModuleInterface} be called without passing Graphics object as a parameter
     */
    private Graphics2D graphics = null;
    
        
}
