/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author swacisko
 */
public class ManagerFrame extends JFrame {
    
    public ManagerFrame( MainFrame parent, SelectedItems items ){
        parentFrame = parent;
        selectedItems = items;
        
        arrangeInitialFrameSettings();        
    }
    
    
    private void arrangeInitialFrameSettings(){
        setTitle("Map Manager");
        try {
            //setIconImage(ImageIO.read(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setLayout( new GridBagLayout() );   
        
        
        nodePanel = new SelectedNodePanel();
        nodePanel.setSelectedItems( selectedItems );
        nodePanel.setParentFrame(this);
                
        settingsPanel = new SettingsPanel();
        settingsPanel.setSelectedItems(selectedItems);
        
        mapCreatorPanel = new MapCreatorPanel( this );
        mapCreatorPanel.setSelectedItems(selectedItems);
        
        edgePanel = new SelectedEdgePanel();
        edgePanel.setSelectedItems(selectedItems);
        edgePanel.setParentFrame(this);
        
        mapCreatorButton = new JButton( "Map Creator" );
        mapCreatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToMapCreatorPanel();
            }
        });
        
        selectNodeButton = new JButton( "Node Panel" );
        selectNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToSelectedNodePanel();
            }
        });
        
        selectEdgeButton = new JButton( "Edge Panel" );
        selectEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToSelectedEdgePanel();
            }
        });
        
        settingsButton = new JButton( "Settings Panel" );
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToSettingsPanel();
            }
        });
        
       // centralizedAtractionPanel = new CentralizedAtractionPanel();
        
        currentPanel = mapCreatorPanel;
                
        add( mapCreatorButton, new GBC( 8,0,2,1 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH )  );
        add( settingsButton, new GBC( 10,0,2,1 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH )  );
        add( selectNodeButton, new GBC( 8,1,2,1 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH )   );
        add( selectEdgeButton, new GBC( 10,1,2,1 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH ) );
        
        add(currentPanel, new GBC( 8,2,4,10 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH ).setWeight(100,100) );        
        
    }
    
    private void switchToPanel( JPanel panel ){
        remove(currentPanel);
        currentPanel = panel;
        add(currentPanel, new GBC( 8,2,4,10 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH ).setWeight(100,100) );
        revalidate();
        repaint();
    }
    
    /**
     * Switches {@link #currentPanel} to {@link #mapCreatorPanel}
     */
    private void switchToMapCreatorPanel(){
        switchToPanel(mapCreatorPanel);
    }
    
    /**
     * Switches {@link #currentPanel} to {@link #settingsPanel}
     */
    private void switchToSettingsPanel(){
        switchToPanel(settingsPanel);
    }
    
    /**
     * Switches {@link #currentPanel} to {@link #nodePanel}
     */
    private void switchToSelectedNodePanel(){
        switchToPanel(nodePanel);
    }
    
    /**
     * Switches {@link #currentPanel} to {@link #edgePanel}
     */
    private void switchToSelectedEdgePanel(){
        switchToPanel(edgePanel);
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
    
    /**
     * Switches {@link #currentPanel} to {@link #centralizedAtractionPanel}
     */
   /* public void switchToCentralizedAttractionPanel(){
        switchToPanel(centralizedAtractionPanel);
    }*/
    
    private JPanel currentPanel = null;
    
    private SelectedItems selectedItems = null; 
    private SelectedNodePanel nodePanel = null;
    private SettingsPanel settingsPanel = null;
    private MapCreatorPanel mapCreatorPanel = null;
    private SelectedEdgePanel edgePanel = null;
   // private CentralizedAtractionPanel centralizedAtractionPanel = null;
    
    private JButton mapCreatorButton = null;
    private JButton settingsButton = null;
    private JButton selectNodeButton = null;
    private JButton selectEdgeButton = null;
    
    private MainFrame parentFrame = null;

    

    
}
