/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mcalgorithms.EdgeContraction;
import mcalgorithms.ForceAlgorithm;
import mcalgorithms.GraphGlueing;
import mcalgorithms.MapGraphCreator;
import mcgraphs.MapGraph;
import mcmapcreator.FileChooser;
import mcmapdrawing.DrawingModule;
import mcmapdrawing.SVG;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

/**
 *
 * @author swacisko
 */
public class MapCreatorPanel extends JPanel {

    public MapCreatorPanel(ManagerFrame parent) {
        parentFrame = parent;
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        setLayout(new GridLayout(5, 1));

        saveMapButton = new JButton("SAVE MAP IN SVG");
        saveMapButton.setBackground(Color.WHITE);
        saveMapButton.setForeground(Color.RED);
        saveMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MapGraph graph = selectedItems.getGraph();
                if (graph == null) {
                    return;
                }
                FileChooser.saveSvgFile(MapCreatorPanel.this);
                while( UsefulFunctions.existsFile( MCSettings.getMapsDirectoryPath() ) ){ // if such file exists, i check, whether user really wants to replace it.
                    int res = JOptionPane.showConfirmDialog( MapCreatorPanel.this, "File with path " + MCSettings.getMapsDirectoryPath() +
                            " already exists. Do you want to continue?");
                    if( res == JOptionPane.YES_OPTION ){
                        break;
                    }else{
                        FileChooser.saveSvgFile(MapCreatorPanel.this);
                    }
                        
                }
                new DrawingModule(new SVG(MCSettings.getINITIAL_SVG_WIDTH(), MCSettings.getINITIAL_SVG_HEIGHT()), selectedItems)
                        .drawGraphOnMap(graph, MCSettings.getSvgFileName());
            }
        });

        basicMapButton = new JButton("Draw basic map");
        basicMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (beginAction() == false) {
                    endAction(null);
                    return;
                }
                MapGraph graph = new MapGraphCreator().createMapGraphFromGtfsDatabase(MCSettings.getDRAWING_ROUTE_TYPE());
                endAction(graph);
            }
        });

        gluedMapButton = new JButton("Draw glued map");
        gluedMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (beginAction() == false) {
                    endAction(null);
                    return;
                }
                MapGraph graph = new MapGraphCreator().createMapGraphFromGtfsDatabase(MCSettings.getDRAWING_ROUTE_TYPE());
                graph = new GraphGlueing(graph).convertGraph();
                endAction(graph);
            }
        });

        contractedMapButton = new JButton("Draw contracted map");
        contractedMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (beginAction() == false) {
                    return;
                }
                MapGraph graph = new MapGraphCreator().createMapGraphFromGtfsDatabase(MCSettings.getDRAWING_ROUTE_TYPE());
                if (graph == null) {
                    System.out.println("\n\nGRAPH == NULL");
                }
                graph = new GraphGlueing(graph).convertGraph();
                graph = new EdgeContraction(graph).convertGraph();
                endAction(graph);
            }
        });

        forcespacedMapButton = new JButton("Draw forcespaced map");
        forcespacedMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (beginAction() == false) {
                    endAction(null);
                    return;
                }
                MapGraph graph = new MapGraphCreator().createMapGraphFromGtfsDatabase(MCSettings.getDRAWING_ROUTE_TYPE());
                graph = new GraphGlueing(graph).convertGraph();
                graph = new EdgeContraction(graph).convertGraph();
                graph = new ForceAlgorithm(graph, new SVG(MCSettings.getINITIAL_SVG_WIDTH(), MCSettings.getINITIAL_SVG_HEIGHT(), "forcespaced")).convertGraph();
                endAction(graph);
            }
        });

        /*centralizedAttractionButton = new JButton( "Centralized Attraction Algorithm" );
         centralizedAttractionButton.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e) {
         System.out.println( "parent.clss = " + getParent().getClass() );                 
         parentFrame.switchToCentralizedAttractionPanel();
         }
         });*/
        add(saveMapButton);
        add(basicMapButton);
        add(gluedMapButton);
        add(contractedMapButton);
        add(forcespacedMapButton);
        // add( centralizedAttractionButton );
    }

    /**
     *
     * @return returns true if user confirmed, he/she deliberately want to draw
     * new scheme, false otherwise.
     */
    private boolean beginAction() {
        disableAllButtons();
        if (selectedItems.getGraph() != null) {
            int res = JOptionPane.showConfirmDialog(this, "Are yout sure you want to create new scheme?", "Please confirm.",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                return true;
            }else{
                return false;
            }
            
        } else {
            return true;
        }
    }

    /**
     * Adds given graph to {@link #selectedItems}. Clears route ends. Enables all buttons. Repaints components.
     * @param graph Graph to be added to {@link #selectedItems}.
     */
    private void endAction(MapGraph graph) {
        if( graph != null ){
            selectedItems.setGraph(graph);
            selectedItems.clearRouteEnds();
        }        
        enableAllButtons();
        repaintAll();
    }

    private void repaintAll() {
        parentFrame.getParentFrame().repaint();
        parentFrame.repaint();
    }

    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }

    public void disableAllButtons() {
        basicMapButton.setEnabled(false);
        gluedMapButton.setEnabled(false);
        contractedMapButton.setEnabled(false);
        forcespacedMapButton.setEnabled(false);
        // centralizedAttractionButton.setEnabled(false);
    }

    public void enableAllButtons() {
        basicMapButton.setEnabled(true);
        gluedMapButton.setEnabled(true);
        contractedMapButton.setEnabled(true);
        forcespacedMapButton.setEnabled(true);
        // centralizedAttractionButton.setEnabled(true);
    }

    public ManagerFrame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(ManagerFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private SelectedItems selectedItems = null;

    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;

    private JButton basicMapButton = null;
    private JButton gluedMapButton = null;
    private JButton contractedMapButton = null;
    private JButton forcespacedMapButton = null;
    private JButton centralizedAttractionButton = null;
    private JButton saveMapButton = null;

    private ManagerFrame parentFrame = null;

}
