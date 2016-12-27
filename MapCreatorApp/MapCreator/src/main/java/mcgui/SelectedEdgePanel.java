/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mcgraphs.MapEdge;
import mcgtfsstructures.MCDatabase;
import mcgtfsstructures.Stop;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

/**
 *
 * @author swacisko
 */
public class SelectedEdgePanel extends JPanel implements ActionListener, ChangeListener {
    
    public SelectedEdgePanel(){
        setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );   
        setLayout( new GridBagLayout() );
        
        addAllComponents();
    }
    
    @Override
    public void paintComponent( Graphics g ){
        updateComponentData();
        
    }
    
    
    private void updateComponentData(){
        MapEdge e = selectedItems.getSelectedEdge();
        if( e == null ) {
            containedStopsTextArea.setText("");
            textAngleSlider.setValue( textAngleSlider.getMinimum() );
            textVisibleBox.setSelected( false );
            textBoldBox.setSelected( false );
            textSizeSlider.setValue( textSizeSlider.getMinimum() );
            return;
        }
                
        shapeButtonsGroup.setSelected( e.getShape().equals( MCSettings.ELLIPSE ) ? ellipseButton.getModel() : rectangleButton.getModel(),true );
        
        containedStopsTextArea.setFont( new Font( "Serif",Font.PLAIN, 15 ) );
        containedStopsTextArea.setBackground(Color.BLACK);
        String res = e.getEnds().getST().getStructureName() + "  <--->  " + e.getEnds().getND().getStructureName() + "\n\n";
        for( String s : e.getContainedForwardStopsIds() ){
            Stop st = MCDatabase.getStopOfID(s);    
            String temp;
            if( st == null ) temp = s;
            else temp = st.getStopName();
            res += temp + "\n";
        }
        containedStopsTextArea.setText(res);        
        textAngleSlider.setValue( e.getTextAngle() );
        textVisibleBox.setSelected( e.isTextVisible() );
        textBoldBox.setSelected( e.isTextBold() );
        textSizeSlider.setValue( e.getTextFontSize() );
    }
    
    private void addAllComponents(){
        JLabel textStyleLabel = new JLabel( "Text style" );
        textBoldBox = new JCheckBox("Bold");
        textBoldBox.addActionListener(this);
        
        textVisibleBox = new JCheckBox( "Visible" );
        textVisibleBox.addActionListener(this);
        
        JLabel shapeLabel = new JLabel( "Node shape: " );               
        shapeButtonsGroup = new ButtonGroup();
        ellipseButton = new JRadioButton("Ellipse", true); 
        shapeButtonsGroup.add(ellipseButton); 
        rectangleButton = new JRadioButton("Rectangle",false);
        shapeButtonsGroup.add(rectangleButton);              
        ellipseButton.addActionListener(this);
        rectangleButton.addActionListener(this);        
                
        textSizeSlider = new JSlider( 0, MCSettings.getMAX_TEXT_FONT(), MCSettings.getINITIAL_TEXT_FONT_SIZE() );
        textSizeSlider.setPaintTicks(true);
        textSizeSlider.setMajorTickSpacing( 10 );
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
        
        textColorLabel = new JLabel( "Text color" );
        textColorBox = new JComboBox( UsefulFunctions.getColorsAsStrings() );
        textColorBox.addActionListener(this);
        
        containedStopsTextArea = new JTextArea("Hi there!",10,50);
        containedStopsTextArea.setLineWrap(true);
        containedStopsTextArea.setFont( new Font( "Serif",Font.PLAIN, 15 ) );
        containedStopsTextArea.setForeground(Color.BLACK);
        containedStopsTextArea.setEnabled(false);        
        containedStopsTextArea.setBorder( new TitledBorder( BorderFactory.createLineBorder( Color.GREEN.darker(), 3), "Contained stops" ) );
        
        add( textStyleLabel, new GBC( 0,1,4,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( textVisibleBox,  new GBC( 4,1,4,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( textBoldBox,  new GBC( 8,1,4,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add(shapeLabel, new GBC( 0,2,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100)); 
        add(ellipseButton,new GBC( 4,2,4,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
        add(rectangleButton, new GBC( 8,2,4,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
        add( new JLabel("Move edge text: "), new GBC( 0,3,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( getTextMoveButton("U",0,-3),  new GBC( 4,3,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( getTextMoveButton("D",0,3),  new GBC( 6,3,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
        add( getTextMoveButton("L",-3,0),  new GBC( 8,3,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( getTextMoveButton("R",3,0),  new GBC( 10,3,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
        add( textSizeLabel, new GBC( 0,4,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100));
        add( textSizeSlider, new GBC( 4,4,8,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100));
        add( textAngleLabel, new GBC( 0,5,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100));
        add( textAngleSlider, new GBC( 4,5,8,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100));
        
        add( textColorLabel,  new GBC( 0,6,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100) );
        add( textColorBox,  new GBC( 4,6,8,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100) );
                
        add( containedStopsTextArea, new GBC( 0,7,12,7 ).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setWeight(100,100) );
    }
    

    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    
     public ManagerFrame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(ManagerFrame parentFrame) {
        this.parentFrame = parentFrame;
    }
    
     
    private JButton getTextMoveButton( String name, final int xdiff, final int ydiff ){
        JButton button = new JButton(name);
        button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( selectedItems.getSelectedEdge() == null ) return;
                selectedItems.getSelectedEdge().updateTextOffset( xdiff, ydiff );
                getParentFrame().getParentFrame().repaint();
            }
        } );
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        MapEdge edge = selectedItems.getSelectedEdge();
        if( edge == null ) return;
        
        else if( source == textColorBox ){
            edge.setTextColor(UsefulFunctions.parseColor((String)textColorBox.getSelectedItem() ) );
        }else if( source == textVisibleBox ){
            edge.setTextVisible( textVisibleBox.isSelected() );
        }else if( source == textBoldBox ){
            edge.setTextBold( textBoldBox.isSelected() );
        }else if( source == ellipseButton ){
            edge.setShape(MCSettings.ELLIPSE);
        }else if( source == rectangleButton ){
            edge.setShape( MCSettings.RECTANGLE );
        }
        
        getParentFrame().getParentFrame().repaint();
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        MapEdge edge = selectedItems.getSelectedEdge();
        if( edge == null ) return;
        if (source == textSizeSlider ){
            edge.setTextFontSize( textSizeSlider.getValue() );
            
        }else if (source == textAngleSlider ){
            edge.setTextAngle( textAngleSlider.getValue() );
        }
        
        getParentFrame().getParentFrame().repaint();
    }
    
    private ManagerFrame parentFrame = null;
    private SelectedItems selectedItems = null;
   
    private JCheckBox textVisibleBox = null;
    private JLabel textSizeLabel = null;
    private JSlider textSizeSlider = null;
    private JLabel textAngleLabel = null;
    private JSlider textAngleSlider = null;
    private JCheckBox textBoldBox = null;
    private JLabel textColorLabel = null;
    private JComboBox textColorBox = null;
    private JTextArea containedStopsTextArea = null;
    
    ButtonGroup shapeButtonsGroup = null;
    JRadioButton ellipseButton = null;
    JRadioButton rectangleButton = null;
    
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;
}
