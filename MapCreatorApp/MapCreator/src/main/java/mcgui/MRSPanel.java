/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import mcgtfsstructures.MCDatabase;
import mcgtfsstructures.Route;
import mctemplates.MCSettings;

/**
 * {@link MRSPanel} is MainRoutesDialog. Here we can select routes we want to highlight on the scheme.
 * @author swacisko
 */
public class MRSPanel extends JPanel {
    
    public MRSPanel(){        
        addComponents();
    }
        
    private void addComponents(){
        setLayout( new GridBagLayout()  );
              
        ArrayList<Route> routes = MCDatabase.getAllRoutes();
        int row = 0;
        for( Route r : routes ){            
            addJCheckBox(r, row++);            
        }
        
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
        
        add( box, new GBC( 0,row,3,1 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH ) );
    }
        
}
