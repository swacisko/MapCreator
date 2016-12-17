/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import mcgtfsstructures.MCDatabase;
import mcgtfsstructures.Route;
import mctemplates.MCSettings;

/**
 * {@link MRSDialog} is MainRoutesDialog. Here we can select routes we want to highlight on the scheme.
 * @author swacisko
 */
public class MRSDialog extends JDialog {
    
    public MRSDialog( Frame parent, String title ){
        super( parent, title, false );
                
        addComponents();
    }
    
    
    private void addComponents(){
        panel = new JPanel();
        panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS)  );
        
        ArrayList<Route> routes = MCDatabase.getAllRoutes();
        for( Route r : routes ){            
            addJCheckBox(r);            
        }
         
        
    }
    
    private void addJCheckBox( final Route r ){
        String text = r.getRouteId();
        text += ". ";
        if( r.getRouteShortName().equals( "" ) == false ){
            text += r.getRouteShortName();
        }else{
            text += r.getRouteLongName();
        }
        JCheckBox box = new JCheckBox( text );
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
        
        add( box );
    }
    
    
    
    
    public JPanel panel = null;
    
}
