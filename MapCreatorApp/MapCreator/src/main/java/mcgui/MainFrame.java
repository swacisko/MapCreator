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
import mcmapdrawing.DrawingModule;

/**
 * This class is the main frame of MapCreator's GUI. Here you can click to choose what do you want to change, select, draw, etc.
 * @author swacisko
 */
public class MainFrame extends JFrame {
    
    public MainFrame(){
        arrangeInitialFrameSettings();        
    }
    
    private void arrangeInitialFrameSettings(){
        setTitle("MapCreator");
        Toolkit t = Toolkit.getDefaultToolkit();
        try {
            //setIconImage(ImageIO.read(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dimension d = t.getScreenSize();
        setSize(d.width, d.height);
        
        setLayout( new GridBagLayout() );
        
        
        
        nodePanel = new SelectedNodePanel();
        nodePanel.setSelectedItems( selectedItems );
        
        schemePanel = new SchemeContructionPanel( (2*d.width/3)-30, d.height - 30 );
        schemePanel.setSelectedItems(selectedItems);
        
        settingsPanel = new SettingsPanel();
        settingsPanel.setSelectedItems(selectedItems);
        
        mapCreatorPanel = new MapCreatorPanel();
        mapCreatorPanel.setSelectedItems(selectedItems);
        
        edgePanel = new SelectedEdgePanel();
        edgePanel.setSelectedItems(selectedItems);
        
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
                System.out.println( "Switched to nodePanel! currentPanel == selectedNodePanel:  " + ( currentPanel == nodePanel ) );
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
        
        centralizedAtractionPanel = new CentralizedAtractionPanel();
        
        currentPanel = mapCreatorPanel;
        
        add( schemePanel, new GBC( 0,0,8,12 ).setAnchor( GBC.CENTER ).setFill( GBC.BOTH ).setWeight(100,100) );
        
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
    
    /**
     * Switches {@link #currentPanel} to {@link #centralizedAtractionPanel}
     */
    public void switchToCentralizedAttractionPanel(){
        switchToPanel(centralizedAtractionPanel);
    }
    
    private JPanel currentPanel = null;
    
    private SelectedItems selectedItems = new SelectedItems();
    private SchemeContructionPanel schemePanel = null;    
    private SelectedNodePanel nodePanel = null;
    private SettingsPanel settingsPanel = null;
    private MapCreatorPanel mapCreatorPanel = null;
    private SelectedEdgePanel edgePanel = null;
    private CentralizedAtractionPanel centralizedAtractionPanel = null;
    
    private JButton mapCreatorButton = null;
    private JButton settingsButton = null;
    private JButton selectNodeButton = null;
    private JButton selectEdgeButton = null;
    
    private DrawingModule module = null;
}
