/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgraphs;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import mctemplates.MCSettings;
import mctemplates.Pair;

/**
 * 
 * @author swacisko
 */
public class MapStructure {

    public MapStructure() {
        ID = MapGraph.getFreeID();
    }

    public void setColor(Color c) {
        color = c;
    }

    public Color getColor() {
        return color;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    @Override
    public boolean equals(Object oth) {
        if (!(oth instanceof MapStructure)) {
            return false;
        }
        MapStructure oth2 = (MapStructure) oth;
        return ID == oth2.ID;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 97 * hash + this.ID;
        return hash;
    }

    @Override
    public String toString() {
        return "ID = " + ID;
    }

    /**
     * 
     * @return return description of the structure - e.g. "edge is contracted"
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return return {@link #structureName} - the name of structure - in case of nodes it returns the name of the most significant place in the vicinity of the node
     */
    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public int getDrawingWidth() {
        return drawingWidth;
    }

    public void setDrawingWidth(int drawingWidth) {
        this.drawingWidth = drawingWidth;
        this.hoverWidth = 1 + 2 * drawingWidth;
    }

    public int getHoverWidth() {
        return hoverWidth;
    }

    public void setHoverWidth(int hoverWidth) {
        this.hoverWidth = hoverWidth;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }
    
    public Pair<Integer, Integer> getTextOffset() {
        return textOffset;
    }

    public void setTextOffset(Pair<Integer, Integer> textOffset) {
        this.textOffset = textOffset;
    }
    
    /**
     * This function changes {@link #textOffset} by adding x to its X coordinate and y to its Y coordinate
     * @param x the value of horizontal offset to add
     * @param y the value of vertical offset to add
     */
    public void updateTextOffset( int x, int y ){
        int X = textOffset.getST();
        int Y = textOffset.getND();
        textOffset.setST( X+x );
        textOffset.setND( Y+y );
    }
    
    
    
    /**
     * 
     * @return returns angle at which node text should be written
     */
    public int getTextAngle() {
        return textAngle;
    }

    public void setTextAngle(int textAngle) {
        this.textAngle = textAngle;
    }

    /**
     * 
     * @return return whether text should be visible
     */
    public boolean isTextVisible() {
        return textVisible;
    }

    public void setTextVisible(boolean textVisilbe) {
        this.textVisible = textVisilbe;
    }
    
    public int getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(int textFontSize) {
        this.textFontSize = textFontSize;
    }
    
    public boolean isTextBold() {
        return textBold;
    }

    public void setTextBold(boolean textBold) {
        this.textBold = textBold;
    }

    public int getTextFormat() {
        return textFormat;
    }

    public void setTextFormat(int textFormat) {
        this.textFormat = textFormat;
    }
    
    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    
    
     

    private Color hoverColor = Color.RED;
    private int ID = -1;
    private Color color = null;
    private String description = "";
    
    /**
     * Structure name is the real name of a stop - e.g. Most Teatralny
     */
    private String structureName = "";
    
    /**
     * This is radius in case of circle or ellipse, width in case of an edge.
     */
    private int drawingWidth = 2;
    private int hoverWidth = 5;

    
    private Pair<Integer,Integer> textOffset = MCSettings.getINITIAL_TEXT_OFFSET();

    private int textAngle = 0;  
    private boolean textVisible = true;
    private int textFontSize = MCSettings.getINITIAL_TEXT_FONT_SIZE();
    private boolean textBold = false;
    private int textFormat = Font.PLAIN;
    private Color textColor = MCSettings.getTEXT_COLOR();

    
    
}
