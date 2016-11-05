package com.mycompany.mavenwordengine;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class SVG {
	
	SVG () {
		width = 700;
		height = 500;
		fileName = "domyslny.svg";
		try {
			writer = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	SVG (int w, int h, String name) {
		width = w;
		height = h;
		fileName = name;
		try {
			writer = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
        
        public void setSize( int w, int h ){ width = w; height = h; }
	 
	void beginSVG () {
		writer.println("<svg width=\"" + width + "\" height=\"" + height +"\">");
	}
	
	void endSVG () {
		writer.println("</svg>");
		writer.close();
	}
	
	void addLine (int x1, int y1, int x2, int y2) {
		writer.println( "   <line x1=\"" + x1 + "\" y1=\"" + y1 +"\" x2=\"" + x2 + "\" y2=\"" + y2 +"\" style=\"stroke:rgb(255,0,0);stroke-width:2\" />" );
	}
	
	void addPolyline (ArrayList x,ArrayList y) {
		int s = x.size();
		writer.print( "   <polyline points=\"" + x.get(0) + "," + y.get(0) );
		for (int i=1;i<s;i++) writer.print( " " + x.get(i) + "," + y.get(i) );
		writer.print( "\" style=\"fill:none;stroke:black;stroke-width:2\" />\n" );
	}
	
	void addPoint (int x, int y) {
		writer.println( "   <circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"4\" stroke=\"green\" stroke-width=\"2\" fill=\"yellow\" />" );
	}
	
	private PrintWriter writer;
	private int width;
	private int height;
	private String fileName;
}
