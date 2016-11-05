package mapdrawing;

import java.util.ArrayList;


public class TEST_SVG {

	/**
	 * @param args
	 */
	static SVG graphic;
	
	//   Stworzenie przykładowego pliku SVG
	public void test() {
		
		ArrayList x = new ArrayList<Integer>();
		ArrayList y = new ArrayList<Integer>();
		x.add(10);
		x.add(80);
		x.add(400);
		x.add(280);
		x.add(370);
		y.add(10);
		y.add(1800);
		y.add(140);
		y.add(280);
		y.add(60);
		
		graphic = new SVG(800,500,"linia.svg");
		graphic.beginSVG();
		graphic.addLine(40, 300, 50, 400);
		graphic.addPoint(40, 300);
		graphic.addPoint(50, 400);
		graphic.addPolyline(x, y);
		for (int i=0;i<x.size();i++) graphic.addPoint((int)x.get(i), (int)y.get(i));
		graphic.endSVG();

	}

}
