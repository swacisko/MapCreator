/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * GUI panel with options to modify settings.
 * @author swacisko
 */
public class SettingsPanel extends JPanel{
    
    public SettingsPanel(){
         setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );  
         
         setLayout( new GridLayout(5,1) );
         
         add( new JPanel() );
         addColorSettingsButton();
         addDisplaySettingsButton();
         addMainRouteSettingsButton();  
         addAlgorithmParametersButton();
    }
    
    private void addAlgorithmParametersButton(){
        /*algorithmParametersButton = new JButton("Algorithm parameters");
        algorithmParametersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMyDialog( new APPanel(), "Algorithm parameters" );
            }
        }); 
        add( algorithmParametersButton );   */
        addSettingsPanelButton(algorithmParametersButton, "Algorithm parameters", new APPanel() );
    }
    
    private void addMainRouteSettingsButton(){
        /*mainRoutesSettingsButton = new JButton( "Main routes settings" );
        mainRoutesSettingsButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 showMyDialog( new MRSPanel(), "Main routes settings" );
             }
         }); 
         add( mainRoutesSettingsButton );*/
         addSettingsPanelButton(mainRoutesSettingsButton, "Main routes settings", new MRSPanel() );
    }
    
    private void addDisplaySettingsButton(){
        /*displaySettingsButton = new JButton( "Display settings" );
        displaySettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMyDialog( new DSPanel(), "Display Settings" );
            }
        }); 
        add( displaySettingsButton );*/
        addSettingsPanelButton(displaySettingsButton, "Display settings", new DSPanel() );
    }
    
    private void addColorSettingsButton(){
        /*colorSettingButton = new JButton( "Color settings" );
         colorSettingButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 showMyDialog( new CSPanel(), "Color settings" );
             }
         }); 
         add( colorSettingButton );*/
        addSettingsPanelButton(colorSettingButton, "Color settings", new CSPanel() );
    }
    
    private void addSettingsPanelButton( JButton button, final String name, final JPanel panel ){
        button = new JButton( name );
        button.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 showMyDialog( panel, name );
             }
         });
         add( button );
    }
    
    private void showMyDialog( JPanel panel, String title ){       
        if( myDialog == null ){
            myDialog = new MyDialog();
        }
        
        myDialog.switchToPanel( panel, title );
        
        
    }
    
    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    
    private SelectedItems selectedItems = null;  
    
    
    private MyDialog myDialog = null;
    
    private JButton colorSettingButton = null;    
    private JButton mainRoutesSettingsButton = null;
    private JButton displaySettingsButton = null;
    private JButton algorithmParametersButton = null;
    
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;
}
