/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mcalgorithms.EdgeContraction;
import mcgraphs.MapNode;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

/**
 *
 * @author swacisko
 */
public class SelectedNodePanel extends JPanel implements ActionListener, ChangeListener {

    public SelectedNodePanel() {
        setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );  
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
        
        add( moveNodeBox, new GBC( 0,0,6,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH) );
        add( removeNodeButton, new GBC( 6,0,6,1 ).setAnchor(GBC.CENTER).setFill(GBC.BOTH) );
        add( contractNodeButton, new GBC( 0,1,6,1 ).setAnchor(GBC.CENTER).setFill(GBC.BOTH)  );
        add( glueNodeButton, new GBC( 6,1,6,1 ).setAnchor(GBC.CENTER).setFill(GBC.BOTH) );       
        
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
        strPanel.setBorder( new TitledBorder( BorderFactory.createLineBorder( Color.BLACK,3 ), "StructureName settings" ) );
        strPanel.setLayout( new GridBagLayout() );
        
        structureNameLabel = new JLabel( "Structure Name:" );
        structureNameTextField = new JTextField();        
        
        structureNameAcceptButton = new JButton( "Accept" );
        structureNameAcceptButton.addActionListener(this);
        
        textBoldBox = new JCheckBox("Bold");
        textBoldBox.addActionListener(this);
        
        textVisibleBox = new JCheckBox( "Visible" );
        textVisibleBox.addActionListener(this);
        
        textSizeSlider = new JSlider( 0, MCSettings.getMAX_TEXT_FONT(),10 );
        textSizeSlider.setPaintTicks(true);
        textSizeSlider.setMajorTickSpacing( 5 );
        textSizeSlider.setPaintLabels(true);
        textSizeSlider.setPaintTrack(true);
        textSizeSlider.addChangeListener(this);
        textSizeLabel = new JLabel( "Text size:" );
        
        textAngleSlider = new JSlider( 0,360,0 );
        textAngleSlider.setPaintTicks(true);
        textAngleSlider.setMajorTickSpacing( 45 );
        textAngleSlider.setPaintLabels(true);
        textAngleSlider.setPaintTrack(true);
        textAngleSlider.addChangeListener(this);
        textAngleLabel = new JLabel( "Text angle:" );
        
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
        
        add(strPanel, new GBC( 0,2,12,5 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
    }

    /**
     * Adds nodeColor ComboBox
     */
    private void addNodeColorBox() {
        nodeColorLabel = new JLabel( "Node color:" );
        Color[] colors = UsefulFunctions.getColors();
        String[] cols = new String[ colors.length ];
        for( int i=0; i<cols.length; i++ ){
            cols[i] = UsefulFunctions.parseColor( colors[i] );
        }
        
        nodeColorBox = new JComboBox( cols );
        nodeColorBox.addActionListener(this);
               
        add( nodeColorLabel, new GBC( 0,7,4,2 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH) );
        add( nodeColorBox, new GBC( 4,7,8,2 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH) );        
    }

    /**
     * Adds node's width and height sliders
     */
    private void addNodeSizesSliders() {
        
        nodeWidthLabel = new JLabel( "Node width:" );
        nodeWidthSlider = new JSlider( 0, MCSettings.getMAX_NODE_WIDTH(), 5 );
        
        nodeWidthSlider.setPaintTicks(true);
        nodeWidthSlider.setMajorTickSpacing(3);
        nodeWidthSlider.setPaintTrack(true);
        nodeWidthSlider.setPaintLabels(true);
        nodeWidthSlider.addChangeListener(this);
        
        add( nodeWidthLabel, new GBC( 0,9,4,2 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100) );
        add( nodeWidthSlider, new GBC( 4,9,8,2 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100) );        
    }

    /**
     * Adds text area to display node's data.
     */
    private void addContainedStopsTextArea(){         
        containedStopsTextArea = new JTextArea("Witam",10,50);
        containedStopsTextArea.setLineWrap(true);
        containedStopsTextArea.setFont( new Font( "Serif",Font.PLAIN, 10 ) );
        containedStopsTextArea.setEnabled(false);
        JScrollPane scroll = new JScrollPane( containedStopsTextArea );
        scroll.setBorder( new TitledBorder( BorderFactory.createLineBorder( Color.BLUE , 3), "Contained stops" ) );
        
        add( scroll, new GBC( 0,11,12,7 ).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setWeight(100,100) );
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
            selectedItems.setMovableNode( moveNodeBox.isSelected() );
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
            selectedItems.setSelectedNode1(null);
        }else if( source == contractNodeButton ){
            MapNode n = selectedItems.getSelectedNode1();
            if( n == null ) return;
            else{
                if( n.countEdges() != 2 || n.countNeighbours() != 2 ){
                    JOptionPane.showMessageDialog(this, "The contracted node must have 2 neighbours and be conbtractable!", "Cannot contract node",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    new EdgeContraction( selectedItems.getGraph() ).removeDeg2NodeFromGraph(n);
                    selectedItems.setSelectedNode1(null);
                }
            }
                
           
        }else if( source == glueNodeButton ){
            
            
        }else if( source == textVisibleBox ){
            MapNode n = selectedItems.getSelectedNode1();
            if( n == null ) return;
            n.setTextVisilbe(textVisibleBox.isSelected());            
        }else if( source == textBoldBox ){
            MapNode n = selectedItems.getSelectedNode1();
            if( n == null ) return;
            int fontformat = n.getTextFormat();
            n.setTextFormat(fontformat ^ Font.BOLD );
        }else if( source == structureNameAcceptButton ){
            MapNode n = selectedItems.getSelectedNode1();
            if( n == null ) return;
            n.setStructureName( structureNameTextField.getText() );
        }else if( source == nodeColorBox ){
            MapNode n = selectedItems.getSelectedNode1();
            if( n == null ) return;
            n.setColor( (Color) nodeColorBox.getSelectedItem() );
        }
        
        repaint();
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

    private JTextArea containedStopsTextArea = null;

    private JCheckBox textVisibleBox = null;
    private JLabel textSizeLabel = null;
    private JSlider textSizeSlider = null;
    private JLabel textAngleLabel = null;
    private JSlider textAngleSlider = null;
    private JCheckBox textBoldBox = null;
    
    
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;
    
}
