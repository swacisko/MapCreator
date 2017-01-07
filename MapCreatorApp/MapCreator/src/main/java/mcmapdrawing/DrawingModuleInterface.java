/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcmapdrawing;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import mcgui.SchemeContructionPanel;

/**
 * Interface of a drawing module - {@link DrawingModule} and {@link SchemeContructionPanel} must implement this interface.
 * @author swacisko
 */
public interface DrawingModuleInterface {
    
    /**
     * 
     * @param p center of the ellipse
     * @param w width of the ellipse
     * @param h height of the ellipse
     */
    public void addEllipse( Point p, int w, int h, int angle ); 
    /**
     * Draws a circle
     * @param p
     * @param radius 
     */
    public void addCircle(Point p, int radius);
    /**
     * Draws a segment between point beg and end.
     * @param beg beginning point of the segment.
     * @param end end point of the segment.
     */
    public void addLine(Point beg, Point end);
    /**
     * Draws a polyline.
     * @param polyline ArrayList of points in the polyline.
     */
    public void addPolyline( ArrayList<Point> polyline );   
    /**
     * Writes given text.
     * @param p left bottom corner of the text
     * @param text text to be written
     * @param fontisze size of font of the text
     * @param angle angle at which text should be written
     */
    public void addText( Point p, String text, int fontisze, int format, int angle );
    
    /**
     * Draws a rectangle.
     * @param p Point specified for the rectangle.
     * @param width width of the rectangle.
     * @param height  height of the rectangle.
     */
    public void addRectangle( Point p, int width, int height, int angle );
    
    /**
     * Sets color of the object we want to draw. I.e. before calling one of the functions: {@link #addCircle(java.awt.Point, int)}, {@link #addEllipse(java.awt.Point, int, int)},
     * {@link #addLine(java.awt.Point, java.awt.Point, int) }, {@link #addPolyline(java.util.ArrayList, int) }, {@link #addText(java.lang.String, java.awt.Point, int) }
     * it sets the color of stroke or font of next objects and/or texts.
     * @param c chosen color
     */
    public void setColor( Color c );
        
    public void setStrokeWidth( int width );
        
    public void setName( String name );
    public String getName();
        
    public void setFill( Color c );
            
    public void setSize( int width, int height );
    
    public int getWidth();
    
    public int getHeight();
    
    public void begin();
    
    public void end();
    
}
