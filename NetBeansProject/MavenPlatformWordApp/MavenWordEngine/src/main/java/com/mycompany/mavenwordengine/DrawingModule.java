package com.mycompany.mavenwordengine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawingModule {

    DrawingModule(SVG s) {
        svg = s;

    }

    // konwertuje wspolrzedne zapisane w stringu do inta - np 52.254167 zostanie zapisane jako int 52254167
    public int convertToInt(String coord) {
        int p = 0;
        int res = 0;
        if (coord.length() > 0 && coord.charAt(0) == '-') {
            p++;
        }
        while (p < coord.length()) {
            if (coord.charAt(p) != '.') {
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

    private Point2D.Float normalizeCoordinate(Point2D.Float p, Point2D.Float LUCorner, Point2D.Float RBCorner, int A, int B) {
        int x = (int) (A * ((double) (p.x - LUCorner.x) / (RBCorner.x - LUCorner.x)));
        int y = (int) (B * ((double) (LUCorner.y - p.y) / (LUCorner.y - RBCorner.y)));

        System.out.println("x = " + p.x + "   y = " + p.y + "   I return normalized point (" + x + "," + y + ")");

        return new Point2D.Float(x, y);
    }

    // UWAGA - może nie dzialac dla miast na przelomie 180- stopni szerokosci geograficznej - wtedy narysuje linie przez caly swiat :)
    private void drawStops() {
        Point2D.Float LUCorner = new Point2D.Float(Integer.MAX_VALUE, Integer.MIN_VALUE); // left upper corner
        Point2D.Float RBCorner = new Point2D.Float(Integer.MIN_VALUE, Integer.MAX_VALUE); // right bottom corner

        for (Map<String, String> m : allStops) {
            float x = Float.parseFloat(m.get("stop_lon"));
            float y = Float.parseFloat(m.get("stop_lat"));

            System.out.println("stop at (" + x + "," + y + ")");

            LUCorner.x = Math.min(x, LUCorner.x);
            LUCorner.y = Math.max(y, LUCorner.y);

            RBCorner.x = Math.max(x, RBCorner.x);
            RBCorner.y = Math.min(y, RBCorner.y);
        }

        LUCorner.x *= 0.99;
        LUCorner.y *= 1.01;

        RBCorner.x *= 1.01;
        RBCorner.y *= 0.99;

        //svg.setSize( RBCorner.x - LUCorner.x, LUCorner.y - RBCorner.y );
        int A = 1000, B = 600;
        svg.setSize(A, B);

        svg.beginSVG();

        System.out.println("LUCorner " + LUCorner + "   RBCorner " + RBCorner);

        Point2D.Float P1 = null, P2 = null;
        for (int i = 0; i < allStops.size(); i++) {
            Map<String, String> m = allStops.get(i);
            float x = Float.parseFloat(m.get("stop_lon"));
            float y = Float.parseFloat(m.get("stop_lat"));

            System.out.println("stop at (" + x + "," + y + ")");

            P1 = normalizeCoordinate(new Point2D.Float(x, y), LUCorner, RBCorner, A, B);
            svg.addPoint((int) P1.x, (int) P1.y);
            if (i > 0 && P2 != null) {
                svg.addLine((int) P1.x, (int) P1.y, (int) P2.x, (int) P2.y);
            }
            P2 = P1;
        }

        svg.endSVG();
    }

    public void drawStopsFile(String filename) {
        ArrayList<String> stops = GTFSInput.processFile(filename);
        Map<String, String> mapa = new HashMap<>();

        allStops.clear();

        ArrayList<String> header = GTFSInput.processStopData(stops.get(0));
        ArrayList<String> singleStop;

        for (int i = 1; i < stops.size(); i++) {
            singleStop = GTFSInput.processStopData(stops.get(i));

            if (singleStop.size() != header.size()) {
                System.out.println("BLAD w wielkosci tablic. DrawingModule, drawStopsFile");
                System.out.println("header: ");
                for (String s : header) {
                    System.out.println(s);
                }
                System.out.println("\n" + singleStop + ": ");
                for (String s : singleStop) {
                    System.out.println(s);
                }

            } else {
                for (int j = 0; j < singleStop.size(); j++) { // here i create a map
                    mapa.put(header.get(j), singleStop.get(j));
                }

                allStops.add(new HashMap<>(mapa));
            }
        }

        drawStops();

    }

    private SVG svg = null;
    private ArrayList< Map<String, String>> allStops = new ArrayList<>();
}
