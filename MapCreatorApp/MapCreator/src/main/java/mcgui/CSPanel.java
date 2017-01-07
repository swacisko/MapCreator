/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import mcgtfsstructures.MCDatabase;
import mcgtfsstructures.Route;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

/**
 * {@link CSPanel} is ColorSettingsPanel. We can change here default colors of elements.
 * @author swacisko
 */
public class CSPanel extends JPanel {
    public CSPanel(){
        setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );
        
        setLayout( new GridBagLayout() );
        
        addAllComponents();        
    }
    
    /**
     * Adds components that enable user to change initial color settings
     */
    private void addAllComponents(){
        JPanel graphColors = new JPanel();
        graphColors.setBorder( new TitledBorder( BorderFactory.createLineBorder(Color.GREEN, 3, true), "Graph colors" ) );
        graphColors.setLayout(  new GridBagLayout() );
        
        addLabelAndComboBox( "Node color:",0,graphColors,UsefulFunctions.parseColor(MCSettings.getINITIAL_NODE_COLOR()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setINITIAL_NODE_COLOR( UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        } );
        
        addLabelAndComboBox( "Fill color:",1,graphColors,UsefulFunctions.parseColor(MCSettings.getINITIAL_FILL_COLOR()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setINITIAL_FILL_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        } );
        
        addLabelAndComboBox( "Edge color:",2,graphColors, UsefulFunctions.parseColor(MCSettings.getINITIAL_EDGE_COLOR()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setINITIAL_EDGE_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        } );
        
        addLabelAndComboBox( "Text color:",3 ,graphColors,UsefulFunctions.parseColor(MCSettings.getTEXT_COLOR()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setTEXT_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        } );
        
        add( graphColors, new GBC( 0,0,10,5 ).setAnchor(GBC.EAST).setFill(GBC.BOTH).setWeight(100,100) );
                 
        //************************************************************************ ROUTE-TYPE PANEL
         
        JPanel routesTypeColor = new JPanel();
        routesTypeColor.setBorder( new TitledBorder( BorderFactory.createLineBorder(Color.yellow, 3, true), "Route-type colors" ) );
        routesTypeColor.setLayout(  new GridBagLayout() );
        
        addLabelAndComboBox( "Tram color:",0 ,routesTypeColor,UsefulFunctions.parseColor(MCSettings.getTRAM_COLOR()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setTRAM_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        });
        
        addLabelAndComboBox( "BUS color:",1 ,routesTypeColor,UsefulFunctions.parseColor(MCSettings.getBUS_COLOR()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setBUS_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        });
        
        addLabelAndComboBox( "METRO color:",2 ,routesTypeColor, UsefulFunctions.parseColor(MCSettings.getMETRO_COLOR()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setMETRO_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        });
        
        addLabelAndComboBox( "Rail color:",3 ,routesTypeColor, UsefulFunctions.parseColor(MCSettings.getRAIL_COLOR()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setRAIL_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        });
        
        add( routesTypeColor, new GBC( 0,5,10,5 ).setAnchor(GBC.EAST).setFill(GBC.BOTH).setWeight(100,100) );
        
        //************************************************************************ HIGHLIGHTED ROUTES PANEL
        
        JPanel highlightedRoutesColor = new JPanel();
        highlightedRoutesColor.setLayout( new GridBagLayout() );
        
        Map<String,Color> highlightMap = MCSettings.getRouteToHighlightColor();
        Set< String > entrySet = highlightMap.keySet();
        ArrayList<String> entryList = new ArrayList<>( entrySet );
        Collections.sort( entryList );
        
        int rowNumber = 0;
        for( String entry : entryList ){           
            String name = entry;
            Route route = MCDatabase.getRouteOfID(name);
            if( route == null ) continue;
            if( route.getRouteLongName().equals("") == false ){
                 name += ". Long name: " + route.getRouteLongName();
            }else if( route.getRouteShortName().equals("") == false ){
                name += ". Short name: " + route.getRouteShortName();
            }
            final String colorTemp = UsefulFunctions.parseColor( highlightMap.get(entry) );
            final String temp = entry;
            
            addLabelAndComboBox(name, rowNumber, highlightedRoutesColor, colorTemp, new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox combo = (JComboBox) e.getSource();
                    MCSettings.setRouteToHighlightColor( temp, UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
                }
            } );
            
            rowNumber++;            
        }
                
        JScrollPane scroll = new JScrollPane(highlightedRoutesColor);
        scroll.setBorder( new TitledBorder( BorderFactory.createLineBorder(Color.MAGENTA, 3, true), "Highlighted routes colors" ) );
        add(scroll, new GBC( 0,10,10,5 ).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100,100) );
    }
    
    /**
     * Adds label and JComboBox with given actionlistener. 
     * @param name Name of the label
     * @param listener ActionListener for added JComboBox
     * @param row row, in which the box should be added
     * @param panel panel to which label and combo should be added
     */
    private void addLabelAndComboBox( final String name, final int row, final JPanel panel, String selectedItem, ActionListener listener ){
        JLabel label = new JLabel(name);
        JComboBox combo = new JComboBox( UsefulFunctions.getColorsAsStrings() );
        combo.setSelectedItem( selectedItem );
        combo.addActionListener(listener);
        panel.add( label, new GBC( 0,row,2,1 ).setAnchor(GBC.EAST).setFill(GBC.BOTH).setWeight(100,100) );
        panel.add( combo, new GBC( 2,row,4,1 ).setAnchor(GBC.WEST).setFill(GBC.BOTH).setWeight(100,100) );
    }
    
    
    
    
    
    private int DEFAULT_WIDTH = 100;
    private int DEFAULT_HEIGHT = 100;
    
}
