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
import javax.swing.JPanel;
import mcalgorithms.EdgeContraction;
import mcalgorithms.ForceAlgorithm;
import mcalgorithms.GraphGlueing;
import mcalgorithms.MapGraphCreator;
import mcgraphs.MapGraph;
import mcmapdrawing.SVG;
import mctemplates.MCSettings;

/**
 *
 * @author swacisko
 */
public class MapCreatorPanel extends JPanel{

    public MapCreatorPanel(){
         setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );  
         
         setLayout( new GridLayout(6,1) );
         
         basicMapButton = new JButton( "Draw basic map" );
         basicMapButton.addActionListener( new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 disableAllButtons();
                 MapGraph graph = new MapGraphCreator().createMapGraphFromGtfsDatabase( MCSettings.getDRAWING_ROUTE_TYPE() );
                 selectedItems.setGraph( graph );
                 enableAllButtons();
             }
         } );
         
         gluedMapButton = new JButton( "Draw glued map" );
         gluedMapButton.addActionListener( new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 disableAllButtons();
                 MapGraph graph = new MapGraphCreator().createMapGraphFromGtfsDatabase( MCSettings.getDRAWING_ROUTE_TYPE() );
                 graph = new GraphGlueing(graph).convertGraph();
                 selectedItems.setGraph( graph );
                 enableAllButtons();
             }
         } );
         
         contractedMapButton = new JButton( "Draw contracted map" );
         contractedMapButton.addActionListener( new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 disableAllButtons();
                 MapGraph graph = new MapGraphCreator().createMapGraphFromGtfsDatabase( MCSettings.getDRAWING_ROUTE_TYPE() );
                 if( graph == null ) System.out.println(  "\n\nGRAPH == NULL" );
                 graph = new GraphGlueing(graph).convertGraph();
                 graph = new EdgeContraction(graph).convertGraph();
                 selectedItems.setGraph( graph );
                 enableAllButtons();
             }
         } );
         
         forcespacedMapButton = new JButton( "Draw forcespaced map" );
         forcespacedMapButton.addActionListener( new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 disableAllButtons();
                 MapGraph graph = new MapGraphCreator().createMapGraphFromGtfsDatabase( MCSettings.getDRAWING_ROUTE_TYPE() );
                 graph = new GraphGlueing(graph).convertGraph();
                 graph = new EdgeContraction(graph).convertGraph();
                 graph = new ForceAlgorithm(graph, new SVG( MCSettings.getINITIAL_SVG_WIDTH(), MCSettings.getINITIAL_SVG_HEIGHT(), "forcespaced" ) ).convertGraph();
                 selectedItems.setGraph( graph );
                 enableAllButtons();
             }
         });
         
         centralizedAttractionButton = new JButton( "Centralized Attraction Algorithm" );
         centralizedAttractionButton.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e) {
                 System.out.println( "parent.clss = " + getParent().getClass() );
                 MainFrame frame = (MainFrame) getParent();
                 frame.switchToCentralizedAttractionPanel();
             }
         });
         
         add( new JPanel() );
         add( basicMapButton );
         add( gluedMapButton );
         add( contractedMapButton );
         add( forcespacedMapButton );
         add( centralizedAttractionButton );
    }
    
    private SelectedItems selectedItems = null;

    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    public void disableAllButtons(){
        basicMapButton.setEnabled(false);
        gluedMapButton.setEnabled(false);
        contractedMapButton.setEnabled(false);
        forcespacedMapButton.setEnabled(false);
        centralizedAttractionButton.setEnabled(false);
    }
    
    public void enableAllButtons(){
        basicMapButton.setEnabled(true);
        gluedMapButton.setEnabled(true);
        contractedMapButton.setEnabled(true);
        forcespacedMapButton.setEnabled(true);
        centralizedAttractionButton.setEnabled(true);
    }
    
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;
    
    private JButton basicMapButton = null;
    private JButton gluedMapButton = null;
    private JButton contractedMapButton = null;
    private JButton forcespacedMapButton = null;
    private JButton centralizedAttractionButton = null;
}
