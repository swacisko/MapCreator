import java.awt.Point;
import java.util.ArrayList;


public class main {

	/**
	 * @param args
	 */
	static SVG graphic;
	
	//   Stworzenie przykładowego pliku SVG
	public static void main(String[] args) {
		
		ArrayList x = new ArrayList<Integer>();
		ArrayList y = new ArrayList<Integer>();
		x.add(10);
		x.add(80);
		x.add(40);
		x.add(280);
		x.add(370);
		y.add(10);
		y.add(180);
		y.add(140);
		y.add(280);
		y.add(60);
		
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(point(10,10));
		points.add(point(80,180));
		points.add(point(40,140));
		points.add(point(280,280));
		points.add(point(370,60));
		
		graphic = new SVG(800,500,"linia");
		graphic.beginSVG();
		graphic.addPolylineStyle();
		graphic.addCircleStyle();
		graphic.addRectangleStyle();
		graphic.addEllipseStyle();
		graphic.addLine(40, 300, 50, 400);
		graphic.addCirclePlain(40, 300,4);
		graphic.addCirclePlain(50, 400,4);
		graphic.addPolylinePlain(points);
		for (int i=0;i<x.size();i++) graphic.addCirclePlain((int)x.get(i), (int)y.get(i),4);
		graphic.addText(70, 30, "Rozkład jazdy");
		graphic.addRectangleRoundPlain(200, 300, 20, 20, 100, 50);
		graphic.addEllipsePlain(300, 420, 100, 50);
		graphic.endSVG();

	}

	private static Point point(int i, int j) {
		Point a = new Point(i,j);
		return a;
	}

}
