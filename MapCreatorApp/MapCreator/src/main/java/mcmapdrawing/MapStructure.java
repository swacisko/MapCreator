/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcmapdrawing;

import java.awt.Color;

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

    public boolean equals(MapStructure oth) {
        return ID == oth.ID;
    }
    
    @Override
    public String toString(){
        return "ID = " + ID + "    color = " + color;
    }

    private int ID = -1;

    private Color color = null;

}
