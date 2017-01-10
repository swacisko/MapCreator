/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mctemplates.MCSettings;
import mctemplates.Pair;

/**
 * DSPanel is DisplaySettingsDialog. We can change here widths of nodes, edges and select background transport measures to be drawn
 * @author swacisko
 */
public class DSPanel extends JPanel {
    
    public DSPanel(){
        setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );
        
        setLayout( new GridLayout(4,1) );
        
        addAllComponents();
    }
    
    /**
     * Adds components that enable user to change initial color settings
     */
    private void addAllComponents(){
               
        addBackgroundRoutesPanel();
        addTextOptionsPanel();
        addDrawingSizesPanel();
        addDrawingModuleOptionsPanel();
        addRouteEndsOptions();
    }
    
    private void addDrawingModuleOptionsPanel(){
        
        
    }
    
    private void addBackgroundRoutesPanel(){
        JPanel panel = new JPanel();        
        panel.setLayout(new GridLayout(2,4));
        addBackgroungRouteCheckBox("TRAM", MCSettings.TRAM, panel );
        addBackgroungRouteCheckBox("BUS", MCSettings.BUS, panel );
        addBackgroungRouteCheckBox("METRO", MCSettings.METRO, panel );
        addBackgroungRouteCheckBox("RAIL", MCSettings.RAIL, panel );
        addBackgroungRouteCheckBox("FERRY", MCSettings.FERRY, panel );
        addBackgroungRouteCheckBox("CABLE_CAR", MCSettings.CABLE_CAR, panel );
        addBackgroungRouteCheckBox("GONDOLA", MCSettings.GONDOLA, panel );
        addBackgroungRouteCheckBox("FUNICULAR", MCSettings.FUNICULAR, panel );
        panel.setBorder( new TitledBorder( BorderFactory.createLineBorder(Color.BLUE, 3, false), "Background routes" ) );
        add(panel);
    }
    
    private void addTextOptionsPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        panel.setBorder( new TitledBorder( BorderFactory.createLineBorder(Color.BLUE, 3, false), "Text options" ) );
        
        JSlider slider = getJSlider( 0, MCSettings.getMAX_TEXT_FONT(), MCSettings.getINITIAL_TEXT_FONT_SIZE(),10,  new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setINITIAL_TEXT_FONT_SIZE( slider.getValue() );
            }
        } );        
        addLabelAndSlider("Initial text font size: ", slider, panel );
                
        slider = getJSlider( -Math.abs(MCSettings.getMAX_TEXT_OFFSET().getST()) , Math.abs(MCSettings.getMAX_TEXT_OFFSET().getST()), MCSettings.getINITIAL_TEXT_OFFSET().getST(),25,  new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                Pair<Integer,Integer> offset = new Pair( slider.getValue(), MCSettings.getINITIAL_TEXT_OFFSET().getND());
                MCSettings.setINITIAL_TEXT_OFFSET( offset );
            }
        } ); 
        addLabelAndSlider("Initial text offset X: ", slider, panel);
        
        slider = getJSlider( -Math.abs(MCSettings.getMAX_TEXT_OFFSET().getND()), Math.abs(MCSettings.getMAX_TEXT_OFFSET().getND()), MCSettings.getINITIAL_TEXT_OFFSET().getND(),25, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                Pair<Integer,Integer> offset = new Pair( slider.getValue(), MCSettings.getINITIAL_TEXT_OFFSET().getND());
                MCSettings.setINITIAL_TEXT_OFFSET( offset );
            }
        } ); 
        addLabelAndSlider("Initial text offset Y: ", slider, panel);
        
        JCheckBox box = new JCheckBox( "Draw background texts" );
        box.setSelected( MCSettings.isDrawBackgroundTexts() );
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox box = (JCheckBox) e.getSource();
                MCSettings.setDrawBackgroundTexts( box.isSelected() );
            }
        });
        panel.add( box );
        
        box = new JCheckBox( "Draw contained stops texts" );
        box.setSelected( MCSettings.isDrawContainedStopsTexts());
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox box = (JCheckBox) e.getSource();
                MCSettings.setDrawContainedStopsTexts(box.isSelected() );
            }
        });
        panel.add( box );        
        add( panel );
    }
    
    private void addDrawingSizesPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,1));
        panel.setBorder( new TitledBorder( BorderFactory.createLineBorder(Color.BLUE, 3, false), "Drawing sizes" ) );
        
        JSlider slider = getJSlider( 0, MCSettings.getMAX_NODE_WIDTH(), MCSettings.getINITIAL_NODE_WIDTH(),10,  new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setINITIAL_NODE_WIDTH( slider.getValue() );
            }
        } ); 
        addLabelAndSlider("Initial node width: ", slider, panel);
        
        slider = getJSlider( 0, MCSettings.getMAX_NODE_HEIGHT(), MCSettings.getINITIAL_NODE_HEIGHT(),10,  new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setINITIAL_NODE_HEIGHT(slider.getValue() );
            }
        } ); 
        addLabelAndSlider("Initial node height: ", slider, panel);
        
        slider = getJSlider( 0, MCSettings.getMAX_EDGE_WIDTH(), MCSettings.getINITIAL_EDGE_WIDTH(),10,  new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setINITIAL_EDGE_WIDTH(slider.getValue() );
            }
        } ); 
        addLabelAndSlider("Initial edge width: ", slider, panel);
        
        slider = getJSlider( 0, MCSettings.getMAX_NODE_WIDTH(), MCSettings.getINITIAL_ROUTE_HIGHLIGHT_WIDTH(),10,  new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setINITIAL_ROUTE_HIGHLIGHT_WIDTH(slider.getValue() );
            }
        } ); 
        addLabelAndSlider("Initial route highlight width: ", slider, panel);
        
        slider = getJSlider( 0, MCSettings.getMAX_NODE_WIDTH(), MCSettings.getCONTAINED_STOPS_WIDTH(),10, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setCONTAINED_STOPS_WIDTH(slider.getValue() );
            }
        } ); 
        addLabelAndSlider("Contained stops width: ", slider, panel);
        
        slider = getJSlider( 0, MCSettings.getMAX_NODE_WIDTH(), MCSettings.getCONTAINED_STOPS_HEIGHT(),10, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setCONTAINED_STOPS_HEIGHT(slider.getValue() );
            }
        } ); 
        addLabelAndSlider("Contained stops height: ", slider, panel);
        
        add(panel);
    }
    
    private void addRouteEndsOptions(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));
        panel.setBorder( new TitledBorder( BorderFactory.createLineBorder(Color.BLUE, 3, false), "Route ends options" ) );
        
        JSlider slider = getJSlider( 0, MCSettings.getMAX_NODE_HEIGHT(), MCSettings.getINITIAL_SINGLE_SQUARE_SIZE(),10,  new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                MCSettings.setINITIAL_SINGLE_SQUARE_SIZE(slider.getValue() );
            }
        } );        
        addLabelAndSlider("Single square size: ", slider, panel );
                
        slider = getJSlider( -2*Math.abs(MCSettings.getMAX_TEXT_OFFSET().getST()) , 2*Math.abs(MCSettings.getMAX_TEXT_OFFSET().getST()), MCSettings.getINITIAL_ROUTE_END_GROUP_OFFSET().getST(),50,  new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                Pair<Integer,Integer> offset = new Pair( slider.getValue(), MCSettings.getINITIAL_ROUTE_END_GROUP_OFFSET().getND());
                MCSettings.setINITIAL_ROUTE_END_GROUP_OFFSET( offset );
            }
        } ); 
        addLabelAndSlider("Initial route ends legend offset X: ", slider, panel);
        
        slider = getJSlider( -2*Math.abs(MCSettings.getMAX_TEXT_OFFSET().getND()), 2*Math.abs(MCSettings.getMAX_TEXT_OFFSET().getND()), MCSettings.getINITIAL_ROUTE_END_GROUP_OFFSET().getND(),50, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                Pair<Integer,Integer> offset = new Pair( MCSettings.getINITIAL_ROUTE_END_GROUP_OFFSET().getST(), slider.getValue() );
                MCSettings.setINITIAL_ROUTE_END_GROUP_OFFSET( offset );
            }
        } ); 
        addLabelAndSlider("Initial route ends legend offset Y: ", slider, panel);        
               
        add( panel );
        
    }
    
    /**
     * Adds check box, that enables user to select route types to be drawn (e.g. all TRAM routes should be highlighted).
     * This function is used in {@link #addBackgroundRoutesPanel() }
     * @param name title of the box
     * @param ROUTE_TYPE integer corresponding to given route type, taken from {@link MCSettings}.
     * @param panel panel, to which the box should be added.
     */
    private void addBackgroungRouteCheckBox( String name, final int ROUTE_TYPE, JPanel panel ){
        JCheckBox box = new JCheckBox(name);
        box.setSelected( (MCSettings.getDRAWING_ROUTE_TYPE() & ROUTE_TYPE) != 0 );
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MCSettings.setDRAWING_ROUTE_TYPE( MCSettings.getDRAWING_ROUTE_TYPE() | ROUTE_TYPE );
                JCheckBox box = (JCheckBox) e.getSource();
                if( box.isSelected() == false ){
                    MCSettings.setDRAWING_ROUTE_TYPE( MCSettings.getDRAWING_ROUTE_TYPE() ^ ROUTE_TYPE );
                }                
            }
        });
        
        panel.add(box);
    }
    
    
    /**
     * Creates JSlider object with specified parameters. Invokes methods: setPaintTicks(), setPaintTrack(), setPaintLabels()m setMajorTickSpacing()
     * @param min minimal value of slider scope
     * @param max maximal value of slider scope
     * @param value initial value of the slider
     * @param listener change listener for the slider
     * @return returns constructed slider
     */
    private JSlider getJSlider( int min, int max, int value, int tickSpacing, ChangeListener listener ){
        JSlider slider = new JSlider(min,max,value);
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing( tickSpacing );
        //slider.setSnapToTicks(true);
        slider.addChangeListener(listener);
        return slider;
    }
    
    /**
     * Adds slider labeled with given name. Adds it to temporary panel, then adds temporary panel to panel sent as parameter
     * @param name name of the slider
     * @param slider slider to add
     * @param panel panel to which add the slider
     * @param row row of the GBC layout
     */
    private void addLabelAndSlider( String name, JSlider slider, JPanel panel ){
        JPanel temp = new JPanel();
        temp.setLayout( new GridBagLayout() );
        JLabel label = new JLabel(name);        
        temp.add( label, new GBC(0,0,2,1).setAnchor(GBC.EAST).setFill(GBC.BOTH).setWeight(100,100) );
        temp.add(slider,new GBC(2,0,6,1).setAnchor(GBC.WEST).setFill(GBC.BOTH).setWeight(100,100));      
        panel.add(temp);
    }
    
    
    
    
    private int DEFAULT_WIDTH = 100;
    private int DEFAULT_HEIGHT = 100;
}
