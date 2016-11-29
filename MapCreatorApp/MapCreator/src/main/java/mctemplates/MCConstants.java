/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctemplates;

import java.awt.Color;

/**
 *
 * @author swacisko
 */
public class MCConstants {
    
    public static Color getCorrespondingColor( int routetype ){
        if( routetype == 0 ) return TRAM_COLOR;
        else if( routetype == 1 ) return METRO_COLOR;
        else if( routetype == 2 ) return RAIL_COLOR;
        else if( routetype == 3 ) return BUS_COLOR;
        else return Color.ORANGE;
    }
    
    public static final int TRAM = 1;
    public static final int METRO = (1<<1);
    public static final int RAIL = (1<<2);
    public static final int BUS = (1<<3);
    public static final int FERRY = (1<<4);
    public static final int CABLE_CAR = (1<<5);
    public static final int GONDOLA = (1<<6);
    public static final int FUNICULAR = (1<<7);
    public static final int ALL_TRANSPORT_MEASURES = (1<<8)-1;
    
    
    private static Color TRAM_COLOR = Color.YELLOW;
    private static Color METRO_COLOR = Color.GRAY;
    private static Color RAIL_COLOR = Color.YELLOW;
    private static Color BUS_COLOR = Color.BLUE;
    
    public static final float SPRING_COEF = 1;
    public static final float COULOMB_COEF = 1;
    
    public static final float COORDINATES_COMPARISON_PRECISION = 10000; // tego uzywam w GraphGlueing.coordinatestRoughlyTheSame    
    
    private static final Color INITIAL_HOVER_COLOR = Color.RED;
    
    private static Color INITIAL_EDGE_COLOR = Color.BLUE;
    private static Color INITIAL_EDGE_HOVER_COLOR = INITIAL_HOVER_COLOR;
    private static int INITIAL_EDGE_WIDTH = 4;
    private static int INITIAL_EDGE_HOVER_WIDTH = 8;
    
    private static Color INITIAL_NODE_COLOR = Color.GREEN;        
    private static Color INITIAL_NODE_HOVER_COLOR = INITIAL_HOVER_COLOR;
    private static int INITIAL_NODE_WIDTH = 3;
    private static int INITIAL_NODE_HOVER_WIDTH = 6;

    public static int getINITIAL_EDGE_HOVER_WIDTH() {
        return INITIAL_EDGE_HOVER_WIDTH;
    }

    public static void setINITIAL_EDGE_HOVER_WIDTH(int INITIAL_EDGE_HOVER_WIDTH) {
        MCConstants.INITIAL_EDGE_HOVER_WIDTH = INITIAL_EDGE_HOVER_WIDTH;
    }

    public static int getINITIAL_NODE_HOVER_WIDTH() {
        return INITIAL_NODE_HOVER_WIDTH;
    }

    public static void setINITIAL_NODE_HOVER_WIDTH(int INITIAL_NODE_HOVER_WIDTH) {
        MCConstants.INITIAL_NODE_HOVER_WIDTH = INITIAL_NODE_HOVER_WIDTH;
    }

    public static int getINITIAL_EDGE_WIDTH() {
        return INITIAL_EDGE_WIDTH;
    }

    public static void setINITIAL_EDGE_WIDTH(int INITIAL_EDGE_WIDTH) {
        MCConstants.INITIAL_EDGE_WIDTH = INITIAL_EDGE_WIDTH;
    }

    public static int getINITIAL_NODE_WIDTH() {
        return INITIAL_NODE_WIDTH;
    }

    public static void setINITIAL_NODE_WIDTH(int INITIAL_NODE_WIDTH) {
        MCConstants.INITIAL_NODE_WIDTH = INITIAL_NODE_WIDTH;
    }

    public static Color getINITIAL_EDGE_HOVER_COLOR() {
        return INITIAL_EDGE_HOVER_COLOR;
    }

    public static void setINITIAL_EDGE_HOVER_COLOR(Color INITIAL_EDGE_HOVER_COLOR) {
        MCConstants.INITIAL_EDGE_HOVER_COLOR = INITIAL_EDGE_HOVER_COLOR;
    }

    public static Color getINITIAL_NODE_HOVER_COLOR() {
        return INITIAL_NODE_HOVER_COLOR;
    }

    public static void setINITIAL_NODE_HOVER_COLOR(Color INITIAL_NODE_HOVER_COLOR) {
        MCConstants.INITIAL_NODE_HOVER_COLOR = INITIAL_NODE_HOVER_COLOR;
    }

    public static Color getTRAM_COLOR() {
        return TRAM_COLOR;
    }

    public static void setTRAM_COLOR(Color TRAM_COLOR) {
        MCConstants.TRAM_COLOR = TRAM_COLOR;
    }

    public static Color getMETRO_COLOR() {
        return METRO_COLOR;
    }

    public static void setMETRO_COLOR(Color METRO_COLOR) {
        MCConstants.METRO_COLOR = METRO_COLOR;
    }

    public static Color getRAIL_COLOR() {
        return RAIL_COLOR;
    }

    public static void setRAIL_COLOR(Color RAIL_COLOR) {
        MCConstants.RAIL_COLOR = RAIL_COLOR;
    }

    public static Color getBUS_COLOR() {
        return BUS_COLOR;
    }

    public static void setBUS_COLOR(Color BUS_COLOR) {
        MCConstants.BUS_COLOR = BUS_COLOR;
    }

    public static Color getINITIAL_EDGE_COLOR() {
        return INITIAL_EDGE_COLOR;
    }

    public static void setINITIAL_EDGE_COLOR(Color INITIAL_EDGE_COLOR) {
        MCConstants.INITIAL_EDGE_COLOR = INITIAL_EDGE_COLOR;
    }

    public static Color getINITIAL_NODE_COLOR() {
        return INITIAL_NODE_COLOR;
    }

    public static void setINITIAL_NODE_COLOR(Color INITIAL_NODE_COLOR) {
        MCConstants.INITIAL_NODE_COLOR = INITIAL_NODE_COLOR;
    }
    
   
}
