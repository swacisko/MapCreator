/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import mcmapdrawing.DrawingModule;
import mcmapdrawing.DrawingModuleInterface;

/**
 *  This class is a panel on which we draw a scheme. 
 * @author swacisko
 */
public class SchemeContructionPanel extends JPanel implements DrawingModuleInterface{

    
    public SchemeContructionPanel( int width, int height ){
         setSize( width, height );  
         
         module = new DrawingModule(this);
         
         setLayout( new FlowLayout());         
         setBorder( BorderFactory.createLineBorder(Color.RED, 3, true) );
         
        // add( new JTextArea(1000,1000), BorderLayout.CENTER );
         /*for( int i=0; i<20; i++ ){
             add( new JButton(""+i) );
         }*/
         
    }
    
    @Override
    public void paintComponent( Graphics g ){
        graphics = (Graphics2D) g;
        module.drawGraphOnMap( selectedItems.getGraph(), "Graph drawing" );
    }
    
    
    
    @Override
    public void addEllipse(Point p, int w, int h) {
        Ellipse2D ellipse = new Ellipse2D.Double(p.x- (w/2) ,p.y -(h/2) ,w,h);
        graphics.setColor( color );
        graphics.draw( ellipse );
        graphics.setColor(fillColor);
        graphics.fill(ellipse);
    }

    @Override
    public void addCircle(Point p, int radius) {
        addEllipse(p, radius,radius);
    }

    @Override
    public void addLine(Point beg, Point end) {
        Line2D line = new Line2D.Double( beg, end );
        graphics.setColor( color );
        graphics.setStroke( new BasicStroke(strokeWidth) );
        graphics.draw( line );
    }

    @Override
    public void addPolyline(ArrayList<Point> polyline) {
        graphics.setColor( color );
        graphics.setStroke( new BasicStroke(strokeWidth) );
        int[] x = new int[ polyline.size() ];
        int[] y = new int[ polyline.size() ];
        for( int i=0; i<polyline.size(); i++ ){
            x[i] = polyline.get(i).x;
            y[i] = polyline.get(i).y;
        }
        graphics.drawPolyline(x,y,polyline.size());
    }

    /**
     * 
     * @param p Beginning of the text - 
     * @param text
     * @param fontsize
     * @param format
     * @param angleWidth 
     */
    @Override
    public void addText(Point p, String text, int fontsize, int format, int angleWidth) {        
        AffineTransform at = new AffineTransform();
        at.setToRotation( 2 * Math.PI * angleWidth / 360f );
        graphics.setTransform(at);
        graphics.setFont( new Font( "Serif",format,fontsize ) );
        graphics.drawString(text,p.x,p.y);
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }
    
    @Override
    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }
    
    @Override
    public void setFill(Color c) {
        fillColor = c;
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
    
    private DrawingModule module = null;
    
    private SelectedItems selectedItems = null;    
    private int DEFAULT_WIDTH = 1500;
    private int DEFAULT_HEIGHT = 1500;
    
    private Color color = null;
    private Color fillColor = null;
    private int textSize = -1;
    private int textAngle = 0;
    private int strokeWidth = 0;
    

    
    
    
    
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
