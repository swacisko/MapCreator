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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
        if( e == null ) return;
        String res = "";
        for( String s : e.getContainedForwardStopsIds() ){
            Stop st = MCDatabase.getStopOfID(s);    
            String temp;
            if( st == null ) temp = s;
            else temp = st.getStopName();
            res += temp + "\n";
        }
        
        containedStopsTextArea.setFont( new Font( "Serif",Font.PLAIN, 15 ) );
        containedStopsTextArea.setText(res);        
        textAngleSlider.setValue( e.getTextAngle() );
        textVisibleBox.setSelected( e.isTextVisilbe() );
        textBoldBox.setSelected( e.isTextBold() );
        textSizeSlider.setValue( e.getTextFontSize() );
    }
    
    private void addAllComponents(){
        textBoldBox = new JCheckBox("Bold");
        textBoldBox.addActionListener(this);
        
        textVisibleBox = new JCheckBox( "Visible" );
        textVisibleBox.addActionListener(this);
        
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
        
        add( textVisibleBox,  new GBC( 0,1,6,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( textBoldBox,  new GBC( 6,1,6,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( new JLabel("Move edge text: "), new GBC( 0,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( getTextMoveButton("U",0,-2),  new GBC( 4,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( getTextMoveButton("D",0,2),  new GBC( 6,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
        add( getTextMoveButton("L",-2,0),  new GBC( 8,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100) );
        add( getTextMoveButton("R",2,0),  new GBC( 10,2,2,1 ).setAnchor( GBC.CENTER ).setFill(GBC.BOTH).setWeight(100,100));
        add( textSizeLabel, new GBC( 0,3,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100));
        add( textSizeSlider, new GBC( 4,3,8,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100));
        add( textAngleLabel, new GBC( 0,4,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100));
        add( textAngleSlider, new GBC( 4,4,8,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100));
        
        add( textColorLabel,  new GBC( 0,5,4,1 ).setAnchor( GBC.EAST ).setFill(GBC.BOTH).setWeight(100,100) );
        add( textColorBox,  new GBC( 4,5,8,1 ).setAnchor( GBC.WEST ).setFill(GBC.BOTH).setWeight(100,100) );
                
        add( containedStopsTextArea, new GBC( 0,6,12,7 ).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setWeight(100,100) );
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
            edge.setTextVisilbe( textVisibleBox.isSelected() );
        }else if( source == textBoldBox ){
            edge.setTextBold( textBoldBox.isSelected() );
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
    
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;
}
