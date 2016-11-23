/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctemplates;

/**
 *
 * @author swacisko
 */
public class MCConstants {
    
    
    public static final int TRAM = 1;
    public static final int METRO = (1<<1);
    public static final int RAIL = (1<<2);
    public static final int BUS = (1<<3);
    public static final int FERRY = (1<<4);
    public static final int CABLE_CAR = (1<<5);
    public static final int GONDOLA = (1<<6);
    public static final int FUNICULAR = (1<<7);
    public static final int ALL_TRANSPORT_MEASURES = (1<<8)-1;
    
}
