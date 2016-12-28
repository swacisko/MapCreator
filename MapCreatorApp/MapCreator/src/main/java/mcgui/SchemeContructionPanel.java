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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import mcgraphs.MapEdge;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mcmapcreator.FileChooser;
import mcmapdrawing.DrawingModule;
import mcmapdrawing.DrawingModuleInterface;
import mcmapdrawing.RouteEndGroup;
import mcmapdrawing.SVG;
import mctemplates.Drawable;
import mctemplates.MCSettings;
import mctemplates.Pair;
import mctemplates.UsefulFunctions;

/**
 * This class is a panel on which we draw a scheme.
 *
 * @author swacisko
 */
public class SchemeContructionPanel extends JPanel implements DrawingModuleInterface {

    public SchemeContructionPanel(int width, int height, SelectedItems items) {
        setSize(width, height);
        selectedItems = items;
        module = new DrawingModule(this, items);

        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.RED, 3, true));

        addMouseMotionListener(new MouseMotionHandler());
        addMouseListener(new MouseHandler());

        addPopupMenu();

    }

    private void addPopupMenu() {
        popupMenu = new JPopupMenu("Popup menu");
        JMenuItem item = new JMenuItem("Show manager");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getParentFrame().showManager();
            }
        });
        popupMenu.add(item);

        item = new JMenuItem("Save file in svg");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MapGraph graph = selectedItems.getGraph();
                if (graph == null) {
                    return;
                }
                FileChooser.saveSvgFile(SchemeContructionPanel.this);
                new DrawingModule(new SVG(MCSettings.getINITIAL_SVG_WIDTH(), MCSettings.getINITIAL_SVG_HEIGHT()), selectedItems)
                        .drawGraphOnMap(graph, MCSettings.getSvgFileName());
            }
        });
        popupMenu.add(item);

        final JCheckBoxMenuItem moveNodeItem = new JCheckBoxMenuItem("Move node");
        if (selectedItems != null) {
            moveNodeItem.setSelected(selectedItems.isMovableNode());
        }
        moveNodeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedItems.setMovableNode(!selectedItems.isMovableNode());
                moveNodeItem.setSelected(selectedItems.isMovableNode());
            }
        });
        popupMenu.add(moveNodeItem);

        JMenu alignmentMenu = new JMenu("Node alignments");

        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem nullAlignment = new JRadioButtonMenuItem("No alignement");
        nullAlignment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedItems == null) {
                    return;
                }
                selectedItems.setLayoutMouseAlignementMode(0);
            }
        });
        group.add(nullAlignment);

        JRadioButtonMenuItem horizontalAlignment = new JRadioButtonMenuItem("Horizontal alignment");
        horizontalAlignment.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedItems == null) {
                    return;
                }
                selectedItems.setLayoutMouseAlignementMode(1);
            }
        });
        group.add(horizontalAlignment);

        JRadioButtonMenuItem verticalAlignment = new JRadioButtonMenuItem("Vertical alignment");
        verticalAlignment.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedItems == null) {
                    return;
                }
                selectedItems.setLayoutMouseAlignementMode(2);
            }
        });
        group.add(verticalAlignment);

        alignmentMenu.add(nullAlignment);
        alignmentMenu.add(horizontalAlignment);
        alignmentMenu.add(verticalAlignment);

        popupMenu.add(alignmentMenu);

        setComponentPopupMenu(popupMenu);
    }

    @Override
    public void paintComponent(Graphics g) {
        graphics = (Graphics2D) g;
        if (selectedItems == null || selectedItems.getGraph() == null || selectedItems.getGraph().getNodes() == null ||
                selectedItems.getGraph().getNodes().isEmpty()) {
            return;
        }
        module.drawGraphOnMap(selectedItems.getGraph(), "Graph drawing");
    }

    @Override
    public void addEllipse(Point p, int w, int h, int angle) {       
        Ellipse2D ellipse = new Ellipse2D.Double(p.x - (w / 2), p.y - (h / 2), w, h);
        AffineTransform at = new AffineTransform();
        at.setToRotation(2 * Math.PI * angle / 360f, p.x, p.y);
        graphics.setTransform(at);
        if (fillColor != null) {
            graphics.setColor(fillColor);
            graphics.fill(ellipse);
        }
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(strokeWidth));
        graphics.draw(ellipse);
        graphics.setTransform(new AffineTransform());
    }

    @Override
    public void addCircle(Point p, int radius) {
        addEllipse(p, radius, radius,0);
    }

    @Override
    public void addLine(Point beg, Point end) {
        Line2D line = new Line2D.Double(beg, end);
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(strokeWidth));
        graphics.draw(line);
    }

    @Override
    public void addPolyline(ArrayList<Point> polyline) {
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(strokeWidth));
        int[] x = new int[polyline.size()];
        int[] y = new int[polyline.size()];
        for (int i = 0; i < polyline.size(); i++) {
            x[i] = polyline.get(i).x;
            y[i] = polyline.get(i).y;
        }
        graphics.drawPolyline(x, y, polyline.size());
    }
    
    
    /**
     * Draws a rectangle
     *
     * @param p left upper corner of the rectangle
     * @param width width of the rectangle
     * @param height height of the rectangle
     */
    @Override
    public void addRectangle(Point p, int width, int height, int angle) {
        Rectangle rect = new Rectangle(p.x-(width/2), p.y-(height/2), width, height);
        AffineTransform at = new AffineTransform();
        at.setToRotation(2 * Math.PI * angle / 360f, p.x, p.y);
        graphics.setTransform(at);
        if( fillColor != null ){
            graphics.setColor(fillColor);
            graphics.fill(rect);
        }        
        graphics.setStroke(new BasicStroke(strokeWidth));        
        graphics.setColor(color);
        graphics.draw(rect);
        graphics.setTransform(new AffineTransform());
    }

    /**
     *
     * @param p Beginning of the text - baseline of the text.
     * @param text Text to be written
     * @param fontsize fontsize of the text
     * @param format style - bold or italics or plain or ...
     * @param angle angle at which the text should be written
     */
    @Override
    public void addText(Point p, String text, int fontsize, int format, int angle) {
        graphics.setColor(color);
        AffineTransform at = new AffineTransform();
        at.setToRotation(2 * Math.PI * angle / 360f, p.x, p.y);
        graphics.setTransform(at);
        graphics.setFont(new Font("Serif", format, (int) (0.7f * fontsize * MCSettings.getSvgToSwingFactor())));
        graphics.drawString(text, p.x, p.y);
        graphics.setTransform(new AffineTransform());
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void setStrokeWidth(int width) {
        strokeWidth = (int) ( MCSettings.getSvgToSwingFactor() * width);
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

    private void moveNodeToPosition(Point p) {
        if (selectedItems.isMovableNode() == false) {
            return;
        }
        MapGraph graph = selectedItems.getGraph();
        MapNode node = selectedItems.getSelectedNode1();
        if (graph == null || node == null) {
            return;
        }
        Pair< Pair<Float, Float>, Pair<Float, Float>> lbcruc = module.getLBCandRUC(new ArrayList<Drawable>(graph.getNodes()));
        Pair<Float, Float> LBC = lbcruc.getST();
        Pair<Float, Float> RUC = lbcruc.getND();
        float dW = RUC.getST() - LBC.getST();
        float dH = RUC.getND() - LBC.getND();

        float ratio = MCSettings.getLBCRUCModificationFactor();
        float width = getWidth() * (1 - ratio * dW);
        float height = getHeight() * (1 - ratio * dH);

        // System.out.println( "LBC = " + LBC + "\nRUC = " + RUC );
        // System.out.println( "dW = " + dW + "   dH = " + dH );
        // System.out.println( "Event point = " + p );
        p.x -= ratio * dW;
        float X = p.x / width;
        p.y += ratio * dH;
        float Y = height - p.y;
        Y /= height;

        //  System.out.println( "After transformation point (X,Y) = (" + X + "," + Y + ")" );
        //  System.out.println( "parent: width = " + width + "  height = " + height );
        node.setCoords(new Pair<>(LBC.getST() + X * dW, LBC.getND() + Y * dH));

        //  System.out.println( "denormalized coords = " + node.getCoords() );       
    }

    private MapNode getMapNodeOnPosition(Point p) {
        if (selectedItems.getGraph() == null) {
            return null;
        }
        if (selectedItems.getGraph().getNodes() == null) {
            return null;
        }
        //System.out.println( "szukam wierzcholka" );
        for (MapNode n : selectedItems.getGraph().getNodes()) {
            Pair<Integer, Integer> coords = module.normalizeCoordinates(n.getCoords());
            float radius = Math.max(n.getWidth(), n.getHeight());
            Pair<Float, Float> pf = new Pair<>((float) p.x, (float) p.y);
            float dist = UsefulFunctions.getDistance(new Pair<>((float) coords.getST(), (float) coords.getND()), pf);
            if (dist < radius * Math.max(n.getDrawingWidth(), 1)) {
                return n;
            }
        }

        return null;
    }

    private ArrayList<MapNode> getNodesInRectangle(Point abeg, Point aend) {
        ArrayList<MapNode> list = new ArrayList<>();

        Point beg = new Point(abeg);
        Point end = new Point(aend);

        if (selectedItems == null || selectedItems.getGraph() == null) {
            return list;
        }

        if (beg.x > end.x) {
            int temp = beg.x;
            beg.x = end.x;
            end.x = temp;
        }

        if (beg.y > end.y) {
            int temp = beg.y;
            beg.y = end.y;
            end.y = temp;
        }

        for (MapNode n : selectedItems.getGraph().getNodes()) {
            Pair<Integer, Integer> coords = module.normalizeCoordinates(n.getCoords());
            if (coords.getST() >= beg.x && coords.getST() <= end.x && coords.getND() >= beg.y && coords.getND() <= end.y) {
                list.add(n);
            }
        }

        return list;

    }


    public void alignSelectedNodes(ArrayList<MapNode> nodes, int alignment) {
        ArrayList<Pair<Float, Float>> points = new ArrayList<>();
        for (MapNode n : nodes) {
            points.add(n.getCoords());
        }

        Pair<Float, Float> cog = UsefulFunctions.centerOfGravity(points);

        if (alignment == MCSettings.HORIZONTAL_ALIGNMENT) {
            for (MapNode n : nodes) {
                n.getCoords().setND(cog.getND());
            }
        } else if (alignment == MCSettings.VERTICAL_ALIGNMENT) {
            for (MapNode n : nodes) {
                n.getCoords().setST(cog.getST());
            }
        }

    }

//******************************************** CLASS FIELDS
    /**
     * This is class field to enable functions from
     * {@link DrawingModuleInterface} be called without passing Graphics object
     * as a parameter
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
    JPopupMenu popupMenu = null;

    //*********************************************************
    class MouseHandler extends MouseAdapter {

        public MouseHandler() {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            MapNode n = getMapNodeOnPosition(event.getPoint());
            //System.out.println( "Point:\t" + event.getPoint() );
            if (n == null) {
                if (selectedItems.isMovableNode()) {
                    moveNodeToPosition(event.getPoint());
                } else {
                    selectedItems.setSelectedNode1(null);

                    if (selectedItems.getLayoutMouseAlignementMode() != 0) {
                        selectedItems.setAlignmentBeg(event.getPoint());
                    } else {
                        selectedItems.setLayoutMouseAlignementMode(MCSettings.NO_ALIGNMENT);
                        selectedItems.setAlignmentBeg(null);
                        selectedItems.setAlignmentEnd(null);
                    }
                    return;
                }

            } else {
                //System.out.println( "Map node selected, name = " + n.getStructureName() + "   coords: " + n.getCoords() );
                if (selectedItems.isEdgeSelection()) {
                    if (selectedItems.getSelectedNode1() == null) {
                        selectedItems.setSelectedNode1(n);
                    } else {
                        MapNode n2 = selectedItems.getSelectedNode1();
                        MapEdge e = selectedItems.getGraph().getMapEdgeWithNeighbours(n.getID(), n2.getID());
                        selectedItems.setSelectedEdge(e);
                    }
                } else {
                    selectedItems.setSelectedNode1(n);
                }

            }

            getParentFrame().repaint();
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            if (event.getClickCount() >= 3) {
                MapNode node = getMapNodeOnPosition(event.getPoint());
                Map<Integer, RouteEndGroup> routeEnds = selectedItems.getRouteEnds();
                if (node == null || routeEnds == null) {
                    return;
                } else {
                    RouteEndGroup group = routeEnds.get(node.getID());
                    if (group == null) {
                        return;
                    }
                    MyDialog dialog = new MyDialog();
                    dialog.switchToPanel(new RouteEndGroupPanel(group, SchemeContructionPanel.this ), "Route ends");

                    getParentFrame().repaint();
                }
            }

        }

        @Override
        public void mouseReleased(MouseEvent event) {
            if (selectedItems.getLayoutMouseAlignementMode() != 0 && selectedItems.getAlignmentBeg() != null && selectedItems.getAlignmentEnd() != null) {
                ArrayList<MapNode> nodes = getNodesInRectangle(selectedItems.getAlignmentBeg(), selectedItems.getAlignmentEnd());
                alignSelectedNodes(nodes, selectedItems.getLayoutMouseAlignementMode());
            }
                //selectedItems.setLayoutMouseAlignementMode( MCSettings.NO_ALIGNMENT );
                selectedItems.setAlignmentBeg(null);
                selectedItems.setAlignmentEnd(null);                
            
        

            getParentFrame().repaint();
        }

    }

    class MouseMotionHandler implements MouseMotionListener {

        public MouseMotionHandler() {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            moveNodeToPosition(e.getPoint());
            if (selectedItems.getLayoutMouseAlignementMode() != 0 && selectedItems.getAlignmentBeg() != null) {
                selectedItems.setAlignmentEnd(e.getPoint());
            }
            getParentFrame().repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

    }

}
