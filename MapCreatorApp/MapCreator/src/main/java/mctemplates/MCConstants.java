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
        else return Color.WHITE;
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
    
    
    public static final Color TRAM_COLOR = Color.GREEN;
    public static final Color METRO_COLOR = Color.GRAY;
    public static final Color RAIL_COLOR = Color.BLACK;
    public static final Color BUS_COLOR = Color.BLUE;
    
}
