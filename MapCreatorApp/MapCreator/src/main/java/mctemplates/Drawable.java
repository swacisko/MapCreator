/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctemplates;

/**
 * Interface which enables objects of implementing classes to be drawn on the map.
 * @author swacisko
 */
public interface Drawable {
    
    public Pair<Float,Float> getCoords();   
}
