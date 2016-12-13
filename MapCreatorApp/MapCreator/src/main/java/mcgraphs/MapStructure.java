/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgraphs;

import java.awt.Color;
import java.util.ArrayList;

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
    
     

    private Color hoverColor = Color.RED;
    private int ID = -1;
    private Color color = Color.BLACK;
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

    
    
}
