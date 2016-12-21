/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctemplates;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author swacisko
 */
public class UsefulFunctions {

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
            } else if (decimal) {
                res += ((int) coord.charAt(p) - '0') / dec;
                dec *= 10;
            } else {
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

    public static Point convertToPoint(Pair<Integer, Integer> p) {
        return new Point(p.getST(), p.getND());
    }

    public static Point convertToPointFromFloat(Pair<Float, Float> p) {
        return new Point(Math.round(p.getST()), Math.round(p.getND()));
    }

    public static Pair<Float, Float> parsePairToFloat(Pair<Integer, Integer> p) {
        return new Pair<>((float) p.getST(), (float) p.getND());
    }

    public static Pair<Integer, Integer> parsePairToInteger(Pair<Float, Float> p) {
        int a = Math.round(p.getST());
        int b = Math.round(p.getND());
        return new Pair<>(a, b);
    }

    /**
     *
     * @param c Color to be parsed to String
     * @return returns String corresponding to given color c
     */
    public static String parseColor(Color c) {
        if (c == null) {
            return "";
        } else if (c.equals(Color.RED)) {
            return "red";
        } else if (c.equals(Color.BLUE)) {
            return "blue";
        } else if (c.equals(Color.ORANGE)) {
            return "orange";
        } else if (c.equals(Color.GREEN)) {
            return "green";
        } else if (c.equals(Color.YELLOW)) {
            return "yellow";
        } else if (c.equals(Color.BLACK)) {
            return "black";
        } else if (c.equals(Color.GRAY)) {
            return "gray";
        } else if (c.equals(Color.PINK)) {
            return "pink";
        } else if (c.equals(Color.CYAN)) {
            return "cyan";
        } else if (c.equals(Color.MAGENTA)) {
            return "magenta";
        } else if (c.equals(Color.WHITE)) {
            return "white";
        } else {
            return "" + c.getRGB();
        }
    }
    
    public static Color parseColor( String c ){
        if( c.equalsIgnoreCase( "blue" ) ) return Color.BLUE;
        else if( c.equalsIgnoreCase( "red" ) ) return Color.RED;
        else if( c.equalsIgnoreCase( "yellow" ) ) return Color.YELLOW;
        else if( c.equalsIgnoreCase( "black" ) ) return Color.BLACK;
        else if( c.equalsIgnoreCase( "green" ) ) return Color.GREEN;
        else if( c.equalsIgnoreCase( "orange" ) ) return Color.ORANGE;
        else if( c.equalsIgnoreCase( "pink" ) ) return Color.PINK;
        else if( c.equalsIgnoreCase( "white" ) ) return Color.WHITE;
        else if( c.equalsIgnoreCase( "gray" ) ) return Color.GRAY;
        else if( c.equalsIgnoreCase( "cyan" ) ) return Color.CYAN;
        else if( c.equalsIgnoreCase( "magenta" ) ) return Color.MAGENTA;
        else return null;
    }

    /**
     * Calculates the center of gravity of two points
     *
     * @param p1 First coordinates
     * @param p2 Second coordinates
     * @return Coordinates of the center of gravity
     */
    public static Pair<Float, Float> centerOfGravity(Pair<Float, Float> p1, Pair<Float, Float> p2) {
        float x = p1.getST() + p2.getST();
        x /= 2;
        float y = p1.getND() + p2.getND();
        y /= 2;
        return new Pair<>(x, y);
    }

    /**
     *
     * @return Returns one of 11 random colors
     */
    public static Color getRandomColor() {
        Random rand = new Random();
        return colors[rand.nextInt(colors.length)];
    }

    /**
     * If we want to draw multiple lines on a map, we would better not use two
     * or three colors, but a different color for each of these lines
     *
     * @return Returns next Color in sequence
     */
    public static Color getNextColor() {
        Color c = colors[currentColor];
        currentColor++;
        currentColor %= colors.length;
        return c;
    }

    /**
     * Checks whether a file with given path exists
     *
     * @param path It is the path (preferably exact path) of the file
     * @return returns true if such file exists, otherwise returns false
     */
    public static boolean existsFile(String path) {
        File f = new File(path);
        if (f.exists() == false) {
            System.out.println("File " + path + " does not exist or is a directory");
            return false;
        }
        return true;
    }

    

    /**
     * Normalizes a vector
     *
     * @param vec vector to be normalized
     * @return normalized vector
     */
    public static Pair<Float, Float> getNormalizedVector(Pair<Float, Float> vec) {
        float diffx = vec.getST();
        float diffy = vec.getND();
        float length = (float) Math.sqrt(diffx * diffx + diffy * diffy);

        return new Pair<>(diffx / length, diffy / length);
    }

    /**
     *
     * @param pA coordinates of the node with lower id
     * @param pB coordinates of the node with greater id
     * @return returns a normalized (with length 1) perpendicular vector to
     * given fragment, with constant orientation - either to left or right of
     * fragment [pA-pB]. Return null if pA equals pB
     */
    public static Pair<Float, Float> getNormalizedPerpendicularVector(Pair<Float, Float> pA, Pair<Float, Float> pB) {
        float a = pB.getST() - pA.getST();
        float b = pB.getND() - pA.getND();

        if (new Float(a).equals(0f) && new Float(b).equals(0f)) {
            return null;
        }

        if (new Float(b).equals(0f)) {
            if(a>0) {
                return new Pair<>(0f, 1f);
            } else {
                return new Pair<>(0f, -1f);
            }
        } else if (new Float(a).equals(0f)) {
            if( b>0 ){
                return new Pair<>(-1f, 0f);
            }else{
                return new Pair<>(1f, 0f);
            }
        } else {
            float c = 1f;
            if( b > 0 ) c = -1f;
            float d = (-a*c) / b;
            return getNormalizedVector(new Pair<>(c, d));
        }
    }

    public static float getVectorLength(Pair<Float, Float> vec) {
        float diffx = vec.getST();
        float diffy = vec.getND();
        return (float) Math.sqrt(diffx * diffx + diffy * diffy);
    }
    
    /**
     * Transforms polish letters to their English counterparts in given string
     * @param s String to be transformed
     * @return String with transformed letters
     */
    public static String removePolishLetters( String s ){
        String res = "";
        for( int i=0; i<s.length(); i++ ){
            char t;
            switch( "" + s.charAt(i) ){
                case "ó":{
                    t = 'o';
                    break;
                }
                case "ł":{                    
                    t = 'l';
                    break;
                }
                case "ż":{
                    t = 'z';
                    break;
                }
                case "ź":{
                    t = 'z';
                    break;
                }
                case "ą":{
                    t = 'a';
                    break;
                }
                case "ę":{
                    t = 'e';
                    break;
                }
                case "ń":{
                    t = 'n';
                    break;
                }
                case "ś":{
                    t = 's';
                    break;
                }
                case "ć":{
                    t = 'c';
                    break;
                }
                case "Ó":{
                    t = 'O';
                    break;
                }
                case "Ł":{                    
                    t = 'L';
                    break;
                }
                case "Ż":{
                    t = 'Z';
                    break;
                }
                case "Ź":{
                    t = 'Z';
                    break;
                }
                case "Ą":{
                    t = 'A';
                    break;
                }
                case "Ę":{
                    t = 'E';
                    break;
                }
                case "Ń":{
                    t = 'N';
                    break;
                }
                case "Ś":{
                    t = 'S';
                    break;
                }
                case "Ć":{
                    t = 'C';
                    break;
                }
                default:{
                    t = s.charAt(i);
                }                
            }
            res += t;
        }
        return res;
    }

    private static int currentColor = 0;
    private static Color[] colors = {
        Color.BLACK, Color.BLUE, Color.CYAN, Color.LIGHT_GRAY, Color.GRAY,Color.DARK_GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE, 
        Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
    };
    
    public static float getDistance( Pair<Float,Float> p, Pair<Float,Float> q){
        float dx = p.getST() - q.getST();
        float dy = p.getND() - q.getND();
        return (float)Math.sqrt( dx*dx + dy*dy );
    }

    public static Color[] getColors() {
        return colors;
    }
    
    public static String[] getColorsAsStrings(){
        String[] cols = new String[ colors.length ];
        for( int i=0; i<cols.length; i++ ){
            cols[i] = UsefulFunctions.parseColor( colors[i] );
        }
        return cols;
    }
    
    /**
     * Converts pair to integers
     * @param p pair to be converted
     * @return converted pair
     */
    public static Pair<Integer,Integer> convertToPairInteger( Pair<Float,Float> p ){
        return new Pair<>( Math.round(p.getST()), Math.round( p.getND() ) );
    }
    
}
