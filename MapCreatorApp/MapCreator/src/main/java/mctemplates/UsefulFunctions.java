/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctemplates;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.Random;

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
    
    public static Point convertToPointFromFloat( Pair<Float,Float> p ){
        return new Point( Math.round(p.getST()), Math.round(p.getND()) );
    }
    
    public static String parseColor( Color c ){
        if( c == null ) return "";
        else if( c.equals( Color.RED ) ) return "red";
        else if( c.equals( Color.BLUE ) ) return "blue";
        else if( c.equals( Color.ORANGE ) ) return "orange";
        else if( c.equals( Color.GREEN ) ) return "green";
        else if( c.equals( Color.YELLOW ) ) return "yellow";
        else if( c.equals( Color.BLACK ) ) return "black";
        else if( c.equals( Color.GRAY ) ) return "gray";
        else if( c.equals( Color.PINK ) ) return "pink";
        else if( c.equals( Color.CYAN ) ) return "cyan";
        else if( c.equals( Color.MAGENTA ) ) return "magenta";
        else if( c.equals( Color.WHITE ) ) return "white";
        else return "";
    }
    
    public static Pair<Float,Float> centerOfGravity( Pair<Float,Float> p1, Pair<Float,Float> p2 ){
        float x = p1.getST() + p2.getST();
        x /= 2;
        float y = p1.getND() + p2.getND();
        y /= 2;
        return new Pair<>(x,y);
    }
    
    
    public static Color getRandomColor(){
        Color[] colors = {
            Color.RED, Color.BLACK, Color.YELLOW, Color.BLUE, Color.GREEN, Color.ORANGE, Color.WHITE, Color.GRAY, Color.MAGENTA, Color.CYAN,
            Color.PINK
        };
        Random rand = new Random();
        return colors[ rand.nextInt( colors.length ) ];       
    }
    
    /**
     * Checks whether a file with given path exists
     * @param path It is the path (preferably exact path) of the file
     * @return returns true if such file exists, otherwise returns false
     */
    public static boolean existsFile( String path ){
        File f = new File(path);
        if( (f.exists()==false) || ( f.isDirectory() ) ) { 
            System.out.println( "File " + path + " does not exist or is a directory" );
            return false;
        }
        return true;
    }
    
}
