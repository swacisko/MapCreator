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
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

/**
 * CSPanel is ColorSettingsPanel. We can change default colors of elements in here
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
        
        addLabelAndComboBox( "Node color:",0,graphColors, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setINITIAL_NODE_COLOR( UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        } );
        
        addLabelAndComboBox( "Fill color:",1,graphColors, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setINITIAL_FILL_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        } );
        
        addLabelAndComboBox( "Edge color:",2,graphColors, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setINITIAL_EDGE_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        } );
        
        addLabelAndComboBox( "Text color:",3 ,graphColors, new ActionListener() {
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
        
        addLabelAndComboBox( "Tram color:",0 ,routesTypeColor, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setTRAM_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        });
        
        addLabelAndComboBox( "BUS color:",0 ,routesTypeColor, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setBUS_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        });
        
        addLabelAndComboBox( "METRO color:",0 ,routesTypeColor, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                MCSettings.setMETRO_COLOR(UsefulFunctions.parseColor( (String)combo.getSelectedItem() ) );
            }
        });
        
        addLabelAndComboBox( "Rail color:",0 ,routesTypeColor, new ActionListener() {
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
        int rowNumber = 0;
        for( Map.Entry<String,Color> entry : highlightMap.entrySet() ){            
            final String temp = entry.getKey();
            
            addLabelAndComboBox(temp, rowNumber, highlightedRoutesColor, new ActionListener() {
                
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
        add(scroll, new GBC( 0,10,10,5 ).setAnchor(GBC.EAST).setFill(GBC.BOTH).setWeight(100,100) );
    }
    
    /**
     * Adds label and JComboBox with given actionlistener. 
     * @param name Name of the label
     * @param listener ActionListener for added JComboBox
     * @param row row, in which the box should be added
     * @param panel panel to which label and combo should be added
     */
    private void addLabelAndComboBox( final String name, final int row, final JPanel panel, ActionListener listener ){
        JLabel label = new JLabel(name);
        JComboBox combo = new JComboBox( UsefulFunctions.getColorsAsStrings() );
        combo.addActionListener(listener);
        panel.add( label, new GBC( 0,row,2,1 ).setAnchor(GBC.EAST).setFill(GBC.BOTH).setWeight(100,100) );
        panel.add( combo, new GBC( 2,row,4,1 ).setAnchor(GBC.EAST).setFill(GBC.BOTH).setWeight(100,100) );
    }
    
    
    
    
    
    private int DEFAULT_WIDTH = 100;
    private int DEFAULT_HEIGHT = 100;
    
}
