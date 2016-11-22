/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctemplates;

import java.awt.Point;

/**
 *
 * @author swacisko
 */
public class UsefulFunctions {
    // konwertuje wspolrzedne zapisane w stringu do inta - np 52.254167 zostanie zapisane jako int 52254167
    public static float convertToFloat(String coord) {
        int p = 0;
        float res = 0;
        
        if (coord.length() > 0 && coord.charAt(0) == '-') {
            p++;
        }
        float dec = 10;
        boolean decimal = false;
        
        while (p < coord.length()) {
            if (coord.charAt(p) == '.') {
                decimal = true;
            } else if( decimal ){
                res += ((int) coord.charAt(p) - '0') / dec;
                dec *= 10;
            }
            else {
                res *= 10;
                res += ((int) coord.charAt(p) - '0');
            }
            p++;
        }
        if (coord.length() > 0 && coord.charAt(0) == '-') {
            res = -res;
        }
        return res;
    }
    
    public static Point convertToPoint( Pair<Integer,Integer> p ){
        return new Point( p.getST(), p.getND() );
    }
    
}
