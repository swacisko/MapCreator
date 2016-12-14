/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mcgraphs.MapNode;
import mctemplates.MCSettings;

/**
 *
 * @author swacisko
 */
public class SelectedNodePanel extends JPanel implements ActionListener, ChangeListener {

    public SelectedNodePanel() {
        setSize( 500,500 );
        setLayout(new GridBagLayout());
        addAllComponents();

    }

    /**
     * Adds all components to this panel. Sliders, textFields,...
     */
    private void addAllComponents() {
        addButtons();
        addStructureName();
        addNodeColorBox();
        addNodeSizesSliders();
        addTextManagers();
        addContainedStopsTextArea();
    }

    /**
     * Adds buttons - moveButton, removeButton,...
     */
    private void addButtons() {
        moveNodeBox = new JCheckBox("Move node", false);
        moveNodeBox.addActionListener(this);

        removeNodeButton = new JButton("Remove");
        removeNodeButton.addActionListener(this);
        
        contractNodeButton = new JButton( "Contract" );
        contractNodeButton.addActionListener(this);
        
        glueNodeButton = new JButton("Glue");
        glueNodeButton.addActionListener(this);
        
        add( moveNodeBox, new GBC( 0,0,6,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( removeNodeButton, new GBC( 6,0,6,1 ).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100,100) );
        add( contractNodeButton, new GBC( 0,1,8,1 ).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100,100)  );
        add( glueNodeButton, new GBC( 8,1,4,1 ).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(100,100) );       
        
    }

    
    private JButton getTextMoveButton( String name, final int xdiff, final int ydiff ){
        JButton button = new JButton(name);
        button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedItems.getSelectedNode1().updateTextOffset( xdiff, ydiff );
            }
        } );
        return button;
    }
    /**
     * Adds structureName text field
     */
    private void addStructureName() {
        JPanel strPanel = new JPanel();
        strPanel.setBorder( new TitledBorder( "StructureName settings" ) );
        strPanel.setLayout( new GridBagLayout() );
        
        structureNameLabel = new JLabel( "Structure Name:" );
        structureNameTextField = new JTextField();        
        
        structureNameAcceptButton = new JButton( "Accept" );
        structureNameAcceptButton.addActionListener(this);
        
        textBoldBox = new JCheckBox("Bold");
        textBoldBox.addActionListener(this);
        
        textVisibleBox = new JCheckBox( "Visible" );
        textVisibleBox.addActionListener(this);
        
        textSizeSlider = new JSlider( 0, MCSettings.getMAX_TEXT_FONT() );
        textSizeSlider.setPaintTicks(true);
        textSizeSlider.setMajorTickSpacing( 5 );
        textSizeSlider.setPaintLabels(true);
        textSizeSlider.setPaintTrack(true);
        textSizeSlider.addChangeListener(this);
        textSizeLabel = new JLabel( "Text size: " );
        
        textAngleSlider = new JSlider( 0,360 );
        textAngleSlider.setPaintTicks(true);
        textAngleSlider.setMajorTickSpacing( 45 );
        textAngleSlider.setPaintLabels(true);
        textAngleSlider.setPaintTrack(true);
        textAngleSlider.addChangeListener(this);
        textAngleLabel = new JLabel( "Text angle: " );
        
        strPanel.add( structureNameLabel, new GBC( 0,0,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100) );
        strPanel.add( structureNameTextField, new GBC( 4,0,6,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100) );
        strPanel.add(structureNameAcceptButton, new GBC( 10,0,2,1 ).setAnchor( GBC.CENTER ).setWeight(100,100) );
        
        strPanel.add( textVisibleBox,  new GBC( 0,1,6,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        strPanel.add( textBoldBox,  new GBC( 6,1,6,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        strPanel.add( new JLabel("Move node text: "), new GBC( 0,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        strPanel.add( getTextMoveButton("U",0,1),  new GBC( 4,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        strPanel.add( getTextMoveButton("D",0,-1),  new GBC( 6,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
        strPanel.add( getTextMoveButton("L",-1,0),  new GBC( 8,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        strPanel.add( getTextMoveButton("R",1,0),  new GBC( 10,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
        strPanel.add( textSizeLabel, new GBC( 0,3,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100));
        strPanel.add( textSizeSlider, new GBC( 4,3,8,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100));
        strPanel.add( textAngleLabel, new GBC( 0,4,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100));
        strPanel.add( textAngleSlider, new GBC( 4,4,8,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100));
        
        add(strPanel, new GBC( 0,2,12,5 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH));
    }

    /**
     * Adds nodeColor ComboBox
     */
    private void addNodeColorBox() {

    }

    /**
     * Adds node's width and height sliders
     */
    private void addNodeSizesSliders() {

    }

    /**
     * Adds text area to display node's data.
     */
    private void addContainedStopsTextArea() {

    }

    /**
     * Adds components to manage text of the node.
     */
    private void addTextManagers() {

    }

    /**
     *
     * @return return selected items. This panel must have an access to selected
     * nodes.
     */
    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == moveNodeBox) {
            JCheckBox b = (JCheckBox) source;
            if (b.isSelected()) {
                selectedItems.setMovableNode(true);
            } else {
                selectedItems.setMovableNode(false);
            }
        } else if (source == removeNodeButton) {
            int confirm = new JOptionPane().showConfirmDialog(this, "Are yout sure yout want to remove that node?","Remove node", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.CANCEL_OPTION || confirm == JOptionPane.CLOSED_OPTION) {
                return;
            }
            MapNode n = selectedItems.getSelectedNode1();
            int id = -1;
            if (n != null) {
                id = n.getID();
            }
            selectedItems.getGraph().removeMapNodeByID(id);
        }else if( source == contractNodeButton ){
            
        }else if( source == glueNodeButton ){
            
        }else if( source == textVisibleBox ){
            
        }else if( source == textBoldBox ){
            
        }else if( source == structureNameAcceptButton ){
            
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        if (source == textSizeSlider ){
            
        }else if (source == textAngleSlider ){
            
        }else if (source == nodeWidthSlider ){
            
        } else if (source == nodeHeightSlider ){
            
        }
    }

    //**************************** CLASS FIELDS
    private SelectedItems selectedItems = null;

    private JCheckBox moveNodeBox = null;
    private JButton removeNodeButton = null;
    private JButton contractNodeButton = null;
    private JButton glueNodeButton = null;

    private JLabel structureNameLabel = null;
    private JTextField structureNameTextField = null;
    private JButton structureNameAcceptButton = null;

    private JLabel nodeColorLabel = null;
    private JComboBox nodeColorBox = null;

    private JLabel nodeWidthLabel = null;
    private JSlider nodeWidthSlider = null;

    private JLabel nodeHeightLabel = null;
    private JSlider nodeHeightSlider = null;

    private JLabel containedStopsLabel = null;
    private JTextArea containedStopsTextArea = null;

    private JCheckBox textVisibleBox = null;
    private JLabel textSizeLabel = null;
    private JSlider textSizeSlider = null;
    private JLabel textAngleLabel = null;
    private JSlider textAngleSlider = null;
    private JCheckBox textBoldBox = null;
    
    
    
    
}
