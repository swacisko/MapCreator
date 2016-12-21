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
import mcgraphs.MapEdge;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mcmapdrawing.DrawingModule;
import mcmapdrawing.DrawingModuleInterface;
import mctemplates.Drawable;
import mctemplates.MCSettings;
import mctemplates.Pair;
import mctemplates.UsefulFunctions;

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
         
         addMouseMotionListener( new MouseMotionHandler(this) );
         addMouseListener( new MouseHandler(this) );
         
    }
    
    @Override
    public void paintComponent( Graphics g ){
        graphics = (Graphics2D) g;
        module.drawGraphOnMap( selectedItems.getGraph(), "Graph drawing" );
    }
    
    
    
    @Override
    public void addEllipse(Point p, int w, int h) {
        Ellipse2D ellipse = new Ellipse2D.Double( p.x- (w/2) ,p.y -(h/2) ,w ,h );        
        graphics.setColor(fillColor);
        graphics.fill(ellipse);
        graphics.setColor( color );
        graphics.setStroke( new BasicStroke(strokeWidth) );
        graphics.draw( ellipse );
        
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
        graphics.setColor(color);
        AffineTransform at = new AffineTransform();
        at.setToRotation( 2 * Math.PI * angleWidth / 360f, p.x, p.y );
        graphics.setTransform(at);        
        graphics.setFont( new Font( "Serif",format, (int) ( 0.7f * fontsize * MCSettings.getSvgToSwingFactor() ) ) );
        graphics.drawString(text,p.x,p.y);
        graphics.setTransform(new AffineTransform());
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }
    
    @Override
    public void setStrokeWidth(int width) {
        strokeWidth = (int)( width * MCSettings.getSvgToSwingFactor() );
    }
    
    @Override
    public void setFill(Color c) {
        fillColor = c;
    }

    @Override
    public void begin() {
        // nothing to do here
    }

    @Override
    public void end() {
        // nothing to do here
    }
    
    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    public MainFrame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
    }
    
    private void moveNodeToPosition( Point p ){
        if( selectedItems.isMovableNode() == false ) return;
            MapGraph graph = selectedItems.getGraph();
            MapNode node = selectedItems.getSelectedNode1();
            if( graph == null || node == null ) return;
            Pair< Pair<Float,Float>, Pair<Float,Float> > lbcruc = module.getLBCandRUC( new ArrayList<Drawable>(graph.getNodes()) );
            Pair<Float,Float> LBC = lbcruc.getST();
            Pair<Float,Float> RUC = lbcruc.getND();
            float dW = RUC.getST() - LBC.getST();
            float dH = RUC.getND() - LBC.getND();
            
            float ratio = MCSettings.getLBCRUCModificationFactor(); 
            float width = getWidth()*(1 - ratio*dW);
            float height = getHeight()*(1 - ratio*dH);
            
            
            
           // System.out.println( "LBC = " + LBC + "\nRUC = " + RUC );
           // System.out.println( "dW = " + dW + "   dH = " + dH );
            
           // System.out.println( "Event point = " + p );
            
            
            p.x -= ratio*dW;
            float X = p.x / width;
            p.y += ratio*dH;
            float Y = height - p.y;
            Y /= height;
            
          //  System.out.println( "After transformation point (X,Y) = (" + X + "," + Y + ")" );
          //  System.out.println( "parent: width = " + width + "  height = " + height );
            
            node.setCoords( new Pair<>( LBC.getST() + X*dW, LBC.getND() + Y*dH ) );
            
          //  System.out.println( "denormalized coords = " + node.getCoords() );       
        
    }
    

//******************************************** CLASS FIELDS
    
    /**
     * This is class field to enable functions from {@link DrawingModuleInterface} be called without passing Graphics object as a parameter
     */
    private Graphics2D graphics = null;
    
    private DrawingModule module = null;
    private MainFrame parentFrame = null;

    
    
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
        
        public MouseHandler( JPanel parent ){
            this.parent = parent;
        }
        
        private MapNode getMapNodeOnPosition( Point p ){
            if( selectedItems.getGraph() == null ) return null;
            if( selectedItems.getGraph().getNodes() == null ) return null;
            System.out.println( "szukam wierzcholka" );
            for( MapNode n : selectedItems.getGraph().getNodes() ){
                Pair<Integer,Integer> coords = module.normalizeCoordinates( n.getCoords() );
                float radius = Math.max( n.getWidth(), n.getHeight() );
                Pair<Float,Float> pf = new Pair<>( (float)p.x, (float)p.y );
                float dist = UsefulFunctions.getDistance( new Pair<>( (float)coords.getST(), (float)coords.getND() ), pf );
                if( dist < radius*Math.max( n.getDrawingWidth(),1 ) ) return n;
            }
            
            return null;
        }
        
        @Override
        public void mousePressed(MouseEvent event){
            MapNode n = getMapNodeOnPosition( event.getPoint() );
            //System.out.println( "Point:\t" + event.getPoint() );
            if( n == null ){
                if( selectedItems.isMovableNode() ){
                    moveNodeToPosition( event.getPoint() );
                }
                else{
                    selectedItems.setSelectedNode1(null);
                    //System.out.println( "NULL !" );
                    return;
                }
                
            }
            else{
                //System.out.println( "Map node selected, name = " + n.getStructureName() + "   coords: " + n.getCoords() );
                if( selectedItems.isEdgeSelection() ){
                    if( selectedItems.getSelectedNode1() == null ){
                        selectedItems.setSelectedNode1(n);
                    }else{
                        MapNode n2 = selectedItems.getSelectedNode1();
                        MapEdge e = selectedItems.getGraph().getMapEdgeWithNeighbours( n.getID(), n2.getID() );
                        selectedItems.setSelectedEdge(e);
                    }                    
                }else{
                    selectedItems.setSelectedNode1(n);
                }
                
                
            }
            
            
            getParentFrame().repaint();
        }
        
        @Override
        public void mouseClicked( MouseEvent event ){
            /*MapNode n = getMapNodeOnPosition( event.getPoint() );
            System.out.println( "Point:\t" + event.getPoint() );
            if( n == null ){
                selectedItems.setSelectedNode1(null);
                System.out.println( "NULL !" );
                return;
            }
            else{
                selectedItems.setSelectedNode1(n);
            }
            
            System.out.println( "Map node selected, name = " + n.getStructureName() + "   coords: " + n.getCoords() );
            getParentFrame().repaint();*/
        }
        
        private JPanel parent = null;
        
    }
    
    class MouseMotionHandler implements MouseMotionListener{
        
        public MouseMotionHandler( JPanel parent ){
            this.parent = parent;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            moveNodeToPosition( e.getPoint() );
            
            getParentFrame().repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }
        
        
        private JPanel parent = null;
    }
    
}
