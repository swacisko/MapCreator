/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.TitledBorder;
import mcgtfsstructures.MCDatabase;
import mcgtfsstructures.Route;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

/**
 * {@link MRSPanel} is MainRoutesPanel. Here we can select routes we want to highlight on the scheme.
 * @author swacisko
 */
public class MRSPanel extends JPanel {
    
    public MRSPanel(){        
        addComponents();
    }
        
    private void addComponents(){
        setLayout( new GridBagLayout()  );
              
        
        
        ArrayList<Route> routes = MCDatabase.getAllRoutes();
        int row = 3;
        for( Route r : routes ){            
            addJCheckBox(r, row++);            
        }
        
        addHighlightedRoutesTypePanel();
        
    }
    
    private void addJCheckBox( final Route r, int row ){
        String text = "Route " + r.getRouteId();
        text += ". ";
        if( r.getRouteLongName().equals( "" ) == false ){            
            text += "Long name: " + r.getRouteLongName();
        }else{
            text += "Short name: " + r.getRouteShortName();
        }
        JCheckBox box = new JCheckBox( text );
        box.setSelected( (MCSettings.getRouteToHighlightColor( r.getRouteId() ) != null) ); // this way i can check, whether route with r.getRouteID() is in routesToHighlight
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox b = (JCheckBox) e.getSource();
                if( b.isSelected() ){
                    MCSettings.addRouteToHighlight( r.getRouteId() );
                }else{
                    MCSettings.removeRouteToHighlight( r.getRouteId() );
                }
            }
        });
        box.setActionCommand( r.getRouteId() );
        
        mainRouteBoxes.add(box);        
        add( box, new GBC( 0,row,3,1 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH ) );
    }
    
    private void addMainRoutesTypeCheckBox( String name, final int ROUTE_TYPE, JPanel panel ){
        final JCheckBox box = new JCheckBox(name);        
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                for( JCheckBox b : mainRouteBoxes ){
                    Route route = MCDatabase.getRouteOfID( b.getActionCommand() );
                    if( route == null ) continue;
                    int routeType = Integer.parseInt( route.getRouteType() );
                    System.out.println( "I consider box " + b.getActionCommand() + "  routeType = " + routeType + "  ROUTE_TYPE = " + ROUTE_TYPE );
                    if( (1<<routeType) == ROUTE_TYPE ){                        
                        if( box.isSelected() != b.isSelected() ){
                            b.setSelected( box.isSelected() );
                            if( box.isSelected() ){
                                MCSettings.addRouteToHighlight( b.getActionCommand() );
                                System.out.println( "box " + b.getActionCommand() + " should be selected!" );
                            }else{
                                MCSettings.removeRouteToHighlight( b.getActionCommand() );
                            }
                        }
                    }
                }
                
                repaint();
            }
        });
        
        panel.add(box);
    }
    
     private void addHighlightedRoutesTypePanel(){
        JPanel panel = new JPanel();        
        panel.setLayout(new GridLayout(2,4));
        addMainRoutesTypeCheckBox("TRAM", MCSettings.TRAM, panel );
        addMainRoutesTypeCheckBox("BUS", MCSettings.BUS, panel );
        addMainRoutesTypeCheckBox("METRO", MCSettings.METRO, panel );
        addMainRoutesTypeCheckBox("RAIL", MCSettings.RAIL, panel );
        addMainRoutesTypeCheckBox("FERRY", MCSettings.FERRY, panel );
        addMainRoutesTypeCheckBox("CABLE_CAR", MCSettings.CABLE_CAR, panel );
        addMainRoutesTypeCheckBox("GONDOLA", MCSettings.GONDOLA, panel );
        addMainRoutesTypeCheckBox("FUNICULAR", MCSettings.FUNICULAR, panel );
        panel.setBorder( new TitledBorder( BorderFactory.createLineBorder(Color.BLUE, 3, false), "Main routes types" ) );
        add(panel, new GBC( 0,0,3,3 ).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100,100));
    }
     
     private ArrayList<JCheckBox> mainRouteBoxes = new ArrayList<>();
        
}
