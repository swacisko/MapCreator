package mcmapdrawing;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

//TO DO:
//dodawanie klasy (stylu)
//dodawanie części parametrów stylu przy tworzeniu figur
//styl napisów?

//funkcje - pair

public class SVG {

	public SVG () {
            
	}
	
	//konstruktor ustawiający wymiary rysunku
	public SVG (int widthp, int heightp) {
		width = widthp;
		height = heightp;		
	}

	//konstruktor ustawiający nazwę plików
	public SVG (String fileNamep) {
		fileName = fileNamep;
		
	}
		
	//konstruktor ustawiający wymiary rysunku i nazwę plików
	public SVG (int widthp, int heightp, String fileNamep) {
		width = widthp;
		height = heightp;
		fileName = fileNamep;
		
	}
	
	//wszystkie potrzebne rzeczy na początek plików
	public void beginSVG () {            
                try {
			writerSVG = new PrintWriter(fileName + ".svg");
			writerHTML = new PrintWriter(fileName + ".html");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                        return;
		}
		writerSVG.println("<svg width=\"" + width + "\" height=\"" + height +"\">");
		
		writerHTML.println("<!DOCTYPE html>");
		writerHTML.println("<html>");
		writerHTML.println("<body>");
		writerHTML.println();
		writerHTML.println("<svg width=\"" + width + "\" height=\"" + height +"\">");

	}
	
	//wszystkie potrzebne rzeczy na koniec plików
	public void endSVG () {
		writerSVG.println("</svg>");
		writerSVG.close();
		
		writerHTML.println("</svg>");
		writerHTML.println();
		writerHTML.println("</body>");
		writerHTML.println("</html>");
		writerHTML.close();
	}
	
//STYLE
	
	/*dodaje styl danej klasy łamanych
	 * przed wywołaniem należy zmienić porządane parametry (polylineColor, polylineWidth, polylineLinejoin, polylineLinecap, polylineColorHover, polylineWidthHover)
	 * aby użyć tego stylu w jakieś łamanej trzeba użyć do jej stworzenia addPolylineWithClass (ArrayList<Point> points, String className)*/
	public void addPolylineStyleClass (String className) {
		writerSVG.println( "<style>" );
		writerSVG.println("\tpolyline." + className + "{");
		writerSVG.println("\t\tstroke:\t" + polylineColor + ";");
		writerSVG.println("\t\tstroke-width:\t"+ polylineWidth + ";");
		writerSVG.println("\t\tfill:\tnone;");
		writerSVG.println("\t\tstroke-linejoin:\t" + polylineLinejoin +";");
		writerSVG.println("\t\tstroke-linecap:\t" + polylineLinecap + ";");
		writerSVG.println("\t}");
		writerSVG.println("\tpolyline." + className + ":hover{");
		writerSVG.println("\t\tstroke:\t" + polylineColorHover + ";");
		writerSVG.println("\t\tstroke-width:\t" +polylineWidthHover +";");
		writerSVG.println("\t\tfill:none;");
		writerSVG.println("\t\tstroke-linejoin:\t" + polylineLinejoin +";");
		writerSVG.println("\t\tstroke-linecap:\t" + polylineLinecap + ";");
		writerSVG.println("\t}");
		writerSVG.println("</style>");
		writerSVG.println("");
		
		writerHTML.println( "<style>" );
		writerHTML.println("\tpolyline." + className + "{");
		writerHTML.println("\t\tstroke:\t" + polylineColor + ";");
		writerHTML.println("\t\tstroke-width:\t"+ polylineWidth + ";");
		writerHTML.println("\t\tfill:\tnone;");
		writerHTML.println("\t\tstroke-linejoin:\t" + polylineLinejoin +";");
		writerHTML.println("\t\tstroke-linecap:\t" + polylineLinecap + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tpolyline." + className + ":hover{");
		writerHTML.println("\t\tstroke:\t" + polylineColorHover + ";");
		writerHTML.println("\t\tstroke-width:\t" +polylineWidthHover +";");
		writerHTML.println("\t\tfill:none;");
		writerHTML.println("\t\tstroke-linejoin:\t" + polylineLinejoin +";");
		writerHTML.println("\t\tstroke-linecap:\t" + polylineLinecap + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	/*dodaje styl danej klasy kół
	 * przed wywołaniem należy zmienić porządane parametry (circleStrokeColor, circleStrokeWidth, circleFill, circleStrokeColorHover, circleStrokeWidthHover, circleFillHover)*/
	public void addCircleStyleClass (String className) {
		writerSVG.println( "<style>" );
		writerSVG.println("\tcircle." + className + "{");
		writerSVG.println("\t\tstroke:\t" + circleStrokeColor + ";");
		writerSVG.println("\t\tstroke-width:\t"+ circleStrokeWidth + ";");
		writerSVG.println("\t\tfill:\t" + circleFill + ";");
		writerSVG.println("\t}");
		writerSVG.println("\tcircle." + className + ":hover{");
		writerSVG.println("\t\tstroke:\t" + circleStrokeColorHover + ";");
		writerSVG.println("\t\tstroke-width:\t" +circleStrokeWidthHover +";");
		writerSVG.println("\t\tfill:" + circleFillHover + ";");
		writerSVG.println("\t}");
		writerSVG.println("</style>");
		writerSVG.println("");
		
		writerHTML.println( "<style>" );
		writerHTML.println("\tcircle." + className + "{");
		writerHTML.println("\t\tstroke:\t" + circleStrokeColor + ";");
		writerHTML.println("\t\tstroke-width:\t"+ circleStrokeWidth + ";");
		writerHTML.println("\t\tfill:\t" + circleFill + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tcircle." + className + ":hover{");
		writerHTML.println("\t\tstroke:\t" + circleStrokeColorHover + ";");
		writerHTML.println("\t\tstroke-width:\t" +circleStrokeWidthHover +";");
		writerHTML.println("\t\tfill:" + circleFillHover + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	/*dodaje styl danej klasy prostokątów
	 * przed wywołaniem należy zmienić porządane parametry (rectangleStrokeColor, rectangleStrokeWidth, rectangleColor, rectangleStrokeColorHover, rectangleStrokeWidthHover, rectangleColorHover) */
	public void addRectangleStyleClass(String className) {
		writerSVG.println( "<style>" );
		writerSVG.println("\trect." + className + "{");
		writerSVG.println("\t\tstroke:\t" + rectangleStrokeColor + ";");
		writerSVG.println("\t\tstroke-width:\t"+ rectangleStrokeWidth + ";");
		writerSVG.println("\t\tfill:\t" + rectangleColor + ";");
		writerSVG.println("\t}");
		writerSVG.println("\trect." + className + ":hover{");
		writerSVG.println("\t\tstroke:\t" + rectangleStrokeColorHover + ";");
		writerSVG.println("\t\tstroke-width:\t" +rectangleStrokeWidthHover +";");
		writerSVG.println("\t\tfill:" + rectangleColorHover + ";");
		writerSVG.println("\t}");
		writerSVG.println("</style>");
		writerSVG.println("");
		
		writerHTML.println( "<style>" );
		writerHTML.println("\trect." + className + "{");
		writerHTML.println("\t\tstroke:\t" + rectangleStrokeColor + ";");
		writerHTML.println("\t\tstroke-width:\t"+ rectangleStrokeWidth + ";");
		writerHTML.println("\t\tfill:\t" + rectangleColor + ";");
		writerHTML.println("\t}");
		writerHTML.println("\trect." + className + ":hover{");
		writerHTML.println("\t\tstroke:\t" + rectangleStrokeColorHover + ";");
		writerHTML.println("\t\tstroke-width:\t" +rectangleStrokeWidthHover +";");
		writerHTML.println("\t\tfill:" + rectangleColorHover + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}	
	
		
	/*dodaje styl danej klasy elips
	 * przed wywołaniem należy zmienić porządane parametry (ellipseStrokeColor, ellipseStrokeWidth, ellipseColor, ellipseStrokeColorHover, ellipseStrokeWidthHover, ellipseColorHover) */
	public void addEllipseStyleClass (String className) {
		writerSVG.println( "<style>" );
		writerSVG.println("\tellipse." + className + "{");
		writerSVG.println("\t\tstroke:\t" + ellipseStrokeColor + ";");
		writerSVG.println("\t\tstroke-width:\t"+ ellipseStrokeWidth + ";");
		writerSVG.println("\t\tfill:\t" + ellipseColor + ";");
		writerSVG.println("\t}");
		writerSVG.println("\tellipse." + className + ":hover{");
		writerSVG.println("\t\tstroke:\t" + ellipseStrokeColorHover + ";");
		writerSVG.println("\t\tstroke-width:\t" + ellipseStrokeWidthHover +";");
		writerSVG.println("\t\tfill:" + ellipseColorHover + ";");
		writerSVG.println("\t}");
		writerSVG.println("</style>");
		writerSVG.println("");
		
		writerHTML.println( "<style>" );
		writerHTML.println("\tellipse." + className + "{");
		writerHTML.println("\t\tstroke:\t" + ellipseStrokeColor + ";");
		writerHTML.println("\t\tstroke-width:\t"+ ellipseStrokeWidth + ";");
		writerHTML.println("\t\tfill:\t" + ellipseColor + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tellipse." + className + ":hover{");
		writerHTML.println("\t\tstroke:\t" + ellipseStrokeColorHover + ";");
		writerHTML.println("\t\tstroke-width:\t" + ellipseStrokeWidthHover +";");
		writerHTML.println("\t\tfill:" + ellipseColorHover + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
		
//ŁAMANE
	//sprawia, że wszystkie łamane będą miały taki styl
	//nawet te, które mają wewnątrz określony styl
	//chyba, że tutaj nie będzie danego parametru - wtedy liczy się ten z wnętrza definicji łamanej
	public void addPolylineStyle () {
		writerHTML.println( "<style>" );
		writerHTML.println("\tpolyline{");
		writerHTML.println("\t\tstroke:\t" + polylineColor + ";");
		writerHTML.println("\t\tstroke-width:\t"+ polylineWidth + ";");
		writerHTML.println("\t\tfill:\tnone;");
		writerHTML.println("\t\tstroke-linejoin:\t" + polylineLinejoin +";");
		writerHTML.println("\t\tstroke-linecap:\t" + polylineLinecap + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tpolyline:hover{");
		writerHTML.println("\t\tstroke:\t" + polylineColorHover + ";");
		writerHTML.println("\t\tstroke-width:\t" +polylineWidthHover +";");
		writerHTML.println("\t\tfill:none;");
		writerHTML.println("\t\tstroke-linejoin:\t" + polylineLinejoin +";");
		writerHTML.println("\t\tstroke-linecap:\t" + polylineLinecap + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	//dodaje styl łamanych wg parametrów (oprócz tych, które są równe "" (0 dla int))
	public void addPolylineStyle (String polylineColorp, int polylineWidthp, String polylineLinejoinp, String polylineLinecapp, String polylineColorHoverp, int polylineWidthHoverp) {
		writerHTML.println( "<style>" );
		writerHTML.println("\tpolyline{");
		if (polylineColorp!="") writerHTML.println("\t\tstroke:\t" + polylineColorp + ";");
		if (polylineWidthp!=0) writerHTML.println("\t\tstroke-width:\t"+ polylineWidthp + ";");
		writerHTML.println("\t\tfill:\tnone;");
		if (polylineLinejoinp!="") writerHTML.println("\t\tstroke-linejoin:\t" + polylineLinejoinp +";");
		if (polylineLinecapp!="") writerHTML.println("\t\tstroke-linecap:\t" + polylineLinecapp + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tpolyline:hover{");
		if (polylineColorHoverp!="") writerHTML.println("\t\tstroke:\t" + polylineColorHoverp + ";");
		if (polylineWidthHoverp!=0) writerHTML.println("\t\tstroke-width:\t" +polylineWidthHoverp +";");
		writerHTML.println("\t\tfill:none;");
		if (polylineLinejoinp!="") writerHTML.println("\t\tstroke-linejoin:\t" + polylineLinejoinp +";");
		if (polylineLinecapp!="") writerHTML.println("\t\tstroke-linecap:\t" + polylineLinecapp + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	public void addLine (int x1, int y1, int x2, int y2) {
		writerSVG.println( "   <line x1=\"" + x1 + "\" y1=\"" + y1 +"\" x2=\"" + x2 + "\" y2=\"" + y2 +"\" style=\"stroke:rgb(255,0,0);stroke-width:2\" />" );
		writerHTML.println( "   <line x1=\"" + x1 + "\" y1=\"" + y1 +"\" x2=\"" + x2 + "\" y2=\"" + y2 +"\" style=\"stroke:rgb(255,0,0);stroke-width:2\" />" );

	}
	
	//przyjmuje jako parametr listę point (x,y)
	//dodaje łamaną bez stylu do pliku HTML i łamaną z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addPolylinePlain (ArrayList<Point> points) {
		int s = points.size();
		writerSVG.print( "   <polyline points=\"" + points.get(0).getX() + "," + points.get(0).getY() );
		for (int i=1;i<s;i++) writerSVG.print( " " + points.get(i).getX() + "," + points.get(i).getY() );
		writerSVG.print( "\" style=\"stroke:" + polylineColor + "; stroke-width:"+ polylineWidth + "; fill:none; stroke-linejoin:" + polylineLinejoin +"; stroke-linecap:" + polylineLinecap + ";\" />\n" );
		
		writerHTML.print( "   <polyline points=\"" + points.get(0).getX() + "," + points.get(0).getY() );
		for (int i=1;i<s;i++) writerHTML.print( " " + points.get(i).getX() + "," + points.get(i).getY() );
		writerHTML.print( "\" />\n" );
	}
	
	/*dodaje łamaną
	 * przyjmuje jako parametr listę point (x,y) oraz nazwę klasy stylu 
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addPolylineStyleClass (String className)*/
	public void addPolylineWithClass (ArrayList<Point> points, String className) {
		int s = points.size();
		writerSVG.print( "   <polyline class=\"" + className + "\" points=\"" + points.get(0).getX() + "," + points.get(0).getY() );
		for (int i=1;i<s;i++) writerSVG.print( " " + points.get(i).getX() + "," + points.get(i).getY() );
		writerSVG.print( "\"  />\n" );
		
		writerHTML.print( "   <polyline class=\"" + className + "\" points=\"" + points.get(0).getX() + "," + points.get(0).getY() );
		for (int i=1;i<s;i++) writerHTML.print( " " + points.get(i).getX() + "," + points.get(i).getY() );
		writerHTML.print( "\" />\n" );
	}
	
	//dodaje łamaną bez stylu do pliku HTML i łamaną z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addPolylinePlain (ArrayList x,ArrayList y) {
		int s = x.size();
		writerSVG.print( "   <polyline points=\"" + x.get(0) + "," + y.get(0) );
		for (int i=1;i<s;i++) writerSVG.print( " " + x.get(i) + "," + y.get(i) );
		writerSVG.print( "\" style=\"stroke:" + polylineColor + "; stroke-width:"+ polylineWidth + "; fill:none; stroke-linejoin:" + polylineLinejoin +"; stroke-linecap:" + polylineLinecap + ";\" />\n" );
		
		writerHTML.print( "   <polyline points=\"" + x.get(0) + "," + y.get(0) );
		for (int i=1;i<s;i++) writerHTML.print( " " + x.get(i) + "," + y.get(i) );
		writerHTML.print( "\" />\n" );
	}
	
	//jako parametr przyjmuje listę punktów na płaszczyźnie
	//dodaje łamaną ze stylem (czarna łamana)
	public void addPolyline (ArrayList<Point> points) {
		int s = points.size();
		writerSVG.print( "   <polyline points=\"" + points.get(0).getX() + "," + points.get(0).getY() );
		for (int i=1;i<s;i++) writerSVG.print( " " + points.get(i).getX() + "," + points.get(i).getY() );
		writerSVG.print( "\" style=\"fill:none;stroke:black;stroke-width:2\" />\n" );
		
		writerHTML.print( "   <polyline points=\"" + points.get(0).getX() + "," + points.get(0).getY() );
		for (int i=1;i<s;i++) writerHTML.print( " " + points.get(i).getX() + "," + points.get(i).getY() );
		writerHTML.print( "\" style=\"fill:none;stroke:black;stroke-width:2\" />\n" );
	}
	
	//dodaje łamaną ze stylem (czarna łamana)
	public void addPolyline (ArrayList x,ArrayList y) {
		int s = x.size();
		writerSVG.print( "   <polyline points=\"" + x.get(0) + "," + y.get(0) );
		for (int i=1;i<s;i++) writerSVG.print( " " + x.get(i) + "," + y.get(i) );
		writerSVG.print( "\" style=\"fill:none;stroke:black;stroke-width:2\" />\n" );
		
		writerHTML.print( "   <polyline points=\"" + x.get(0) + "," + y.get(0) );
		for (int i=1;i<s;i++) writerHTML.print( " " + x.get(i) + "," + y.get(i) );
		writerHTML.print( "\" style=\"fill:none;stroke:black;stroke-width:2\" />\n" );
	}
	
//KÓŁKA
	//dodaje styl wszystkich kół - z parametrów określonych w klasie SVG
	public void addCircleStyle () {
		writerHTML.println( "<style>" );
		writerHTML.println("\tcircle{");
		writerHTML.println("\t\tstroke:\t" + circleStrokeColor + ";");
		writerHTML.println("\t\tstroke-width:\t"+ circleStrokeWidth + ";");
		writerHTML.println("\t\tfill:\t" + circleFill + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tcircle:hover{");
		writerHTML.println("\t\tstroke:\t" + circleStrokeColorHover + ";");
		writerHTML.println("\t\tstroke-width:\t" +circleStrokeWidthHover +";");
		writerHTML.println("\t\tfill:" + circleFillHover + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	//dodaje styl kółek wg parametrów (oprócz tych, które są równe "" (0 dla int))
	public void addCircleStyle (String circleStrokeColorp, int circleStrokeWidthp, String circleFillp, String circleStrokeColorHoverp, int circleStrokeWidthHoverp, String circleFillHoverp) {
		writerHTML.println( "<style>" );
		writerHTML.println("\tcircle{");
		if (circleStrokeColorp!="") writerHTML.println("\t\tstroke:\t" + circleStrokeColorp + ";");
		if (circleStrokeWidthp!=0) writerHTML.println("\t\tstroke-width:\t"+ circleStrokeWidthp + ";");
		if (circleFillp!="") writerHTML.println("\t\tfill:\t" + circleFillp + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tcircle:hover{");
		if (circleStrokeColorHoverp!="") writerHTML.println("\t\tstroke:\t" + circleStrokeColorHoverp + ";");
		if (circleStrokeWidthHoverp!=0) writerHTML.println("\t\tstroke-width:\t" +circleStrokeWidthHoverp +";");
		if (circleFillHoverp!="") writerHTML.println("\t\tfill:" + circleFillHoverp + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	//parametry: p - środek koła; r - promień
	//dodaje kółko bez stylu do pliku HTML i kółko z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addCirclePlain (Point p, int r) {
		addCirclePlain((int)p.getX(), (int)p.getY(),r);
	}
	
	//dodaje kółko bez stylu do pliku HTML i kółko z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addCirclePlain (int x, int y, int r) {
		writerSVG.println( "   <circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + r + "\" stroke=\"" + circleStrokeColor + "\" stroke-width=\"" + circleStrokeWidth + "\" fill=\"" + circleFill + "\" />" );
		writerHTML.println( "   <circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + r + "\" />" );
	}
	
	//parametry: p - środek koła; r - promień
	//dodaje kółko ze stylem (żółte z zielonym brzegiem)
	public void addCircle (Point p, int r) {
		addCirclePlain((int)p.getX(), (int)p.getY(),r);
	}
	
	//dodaje kółko ze stylem (żółte z zielonym brzegiem)
	public void addCircle (int x, int y, int r) {
		writerSVG.println( "   <circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + r + "\" stroke=\"green\" stroke-width=\"2\" fill=\"yellow\" />" );
		writerHTML.println( "   <circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + r + "\" stroke=\"green\" stroke-width=\"2\" fill=\"yellow\" />" );
	}
	
	/* dodaje kółko
	 * przyjmuje jako parametry współrzędne środka i promień koła oraz nazwę klasy stylu 
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addCircleStyleClass (String className)*/
	public void addCircleWithClass (int x, int y, int r, String className) {
		writerSVG.println( "   <circle class=\"" + className + "\" cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + r + "\" />" );
		writerHTML.println( "   <circle class=\"" + className + "\" cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + r + "\" />" );
	}
	
	/* dodaje kółko
	 * przyjmuje jako parametry współrzędne środka i promień koła oraz nazwę klasy stylu 
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addCircleStyleClass (String className)*/
	public void addCircleWithClass (Point p, int r, String className) {
		addCircleWithClass((int)p.getX(), (int)p.getY(), r, className);
	}
	
//PROSTOKĄTY
	//dodaje styl wszystkich prostokątów - z parametrów określonych w klasie SVG
	public void addRectangleStyle() {
		writerHTML.println( "<style>" );
		writerHTML.println("\trect{");
		writerHTML.println("\t\tstroke:\t" + rectangleStrokeColor + ";");
		writerHTML.println("\t\tstroke-width:\t"+ rectangleStrokeWidth + ";");
		writerHTML.println("\t\tfill:\t" + rectangleColor + ";");
		writerHTML.println("\t}");
		writerHTML.println("\trect:hover{");
		writerHTML.println("\t\tstroke:\t" + rectangleStrokeColorHover + ";");
		writerHTML.println("\t\tstroke-width:\t" +rectangleStrokeWidthHover +";");
		writerHTML.println("\t\tfill:" + rectangleColorHover + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	//dodaje styl prostokątów wg parametrów (oprócz tych, które są równe "" (0 dla int))
	public void addRectangleStyle (String rectangleStrokeColorp, int rectangleStrokeWidthp, String rectangleColorp, String rectangleStrokeColorHoverp, int rectangleStrokeWidthHoverp, String rectangleColorHoverp) {
		writerHTML.println( "<style>" );
		writerHTML.println("\trect{");
		if (rectangleStrokeColorp!="") writerHTML.println("\t\tstroke:\t" + rectangleStrokeColorp + ";");
		if (rectangleStrokeWidthp!=0) writerHTML.println("\t\tstroke-width:\t"+ rectangleStrokeWidthp + ";");
		if (rectangleColorp!="") writerHTML.println("\t\tfill:\t" + rectangleColorp + ";");
		writerHTML.println("\t}");
		writerHTML.println("\trect:hover{");
		if (rectangleStrokeColorHoverp!="") writerHTML.println("\t\tstroke:\t" + rectangleStrokeColorHoverp + ";");
		if (rectangleStrokeWidthHoverp!=0) writerHTML.println("\t\tstroke-width:\t" +rectangleStrokeWidthHoverp +";");
		if (rectangleColorHoverp!="") writerHTML.println("\t\tfill:" + rectangleColorHoverp + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	//p - lewy górny wierzchołek prostokąta
	//dodaje prostokąt bez stylu do pliku HTML i prostokąt z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addRectanglePlain (Point p, int width, int height) {
			addRectanglePlain((int)p.getX(),(int)p.getY(),width,height);
	}
	
	//dodaje prostokąt bez stylu do pliku HTML i prostokąt z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addRectanglePlain (int x, int y, int width, int height) {
		writerSVG.println("   <rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:" + rectangleColor + ";stroke:" + rectangleStrokeColor + ";stroke-width:" + rectangleStrokeWidth + "\" />");
		writerHTML.println("   <rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" />");
	}
	
	//p - lewy górny wierzchołek prostokąta
	//dodaje prostokąt ze stylem (czerwone z czarnym brzegiem)
	public void addRectangle (Point p, int width, int height) {
		addRectangle((int)p.getX(),(int)p.getY(),width,height);
	}
	
	//dodaje prostokąt ze stylem (czerwone z czarnym brzegiem)
	public void addRectangle (int x, int y, int width, int height) {
		writerSVG.println("   <rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:red;stroke:black;stroke-width:5\" />");
		writerHTML.println("   <rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:red;stroke:black;stroke-width:5\" />");
	}
	
	/* dodaje prostokąt
	 * p - lewy górny wierzchołek prostokąta; width,height - długości boków prostokąta; className - nazwa klasy stylu 
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addRectangleStyleClass (String className)*/
	public void addRectangleWithClass (Point p, int width, int height, String className) {
		addRectangleWithClass((int)p.getX(),(int)p.getY(), width, height, className);
	}
	
	/* dodaje prostokąt
	 * x,y - lewy górny wierzchołek prostokąta; width,height - długości boków prostokąta; className - nazwa klasy stylu 
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addRectangleStyleClass (String className)*/
	public void addRectangleWithClass (int x, int y, int width, int height, String className) {
		writerSVG.println("   <rect class=\"" + className + "\" x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" />");
		writerHTML.println("   <rect class=\"" + className + "\" x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" />");
	}
	
	//p - lewy górny wierzchołek prostokąta, rx,ry - jak bardzo zaokrąglone rogi
	//dodaje prostokąt z zaokrąglonymi rogami bez stylu do pliku HTML i prostokąt z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addRectangleRoundPlain (Point p, int rx, int ry, int width, int height) {
		addRectangleRoundPlain((int)p.getX(),(int)p.getY(), rx, ry,width,height);
	}
	
	//dodaje prostokąt z zaokrąglonymi rogami bez stylu do pliku HTML i prostokąt z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addRectangleRoundPlain (int x, int y, int rx, int ry, int width, int height) {
		writerSVG.println("   <rect x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:" + rectangleColor + ";stroke:" + rectangleStrokeColor + ";stroke-width:" + rectangleStrokeWidth + "\" />");
		writerHTML.println("   <rect x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" />");
	}
	
	//p - lewy górny wierzchołek prostokąta, rx,ry - jak bardzo zaokrąglone rogi
	//dodaje prostokąt z zaokrąglonymi rogami ze stylem (czerwone z czarnym brzegiem)
	public void addRectangleRound (Point p, int rx, int ry, int width, int height) {
		addRectangleRound((int)p.getX(),(int)p.getY(), rx, ry,width,height);
	}
	
	//dodaje prostokąt z zaokrąglonymi rogami ze stylem (czerwone z czarnym brzegiem)
	public void addRectangleRound (int x, int y, int rx, int ry, int width, int height) {
		writerSVG.println("   <rect x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:red;stroke:black;stroke-width:5\" />");
		writerHTML.println("   <rect x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:red;stroke:black;stroke-width:5\" />");
	}
	
	/* dodaje prostokąt z zaokrąglonymi rogami
	 * p - lewy górny wierzchołek prostokąta; rx,ry - jak bardzo zaokrąglone rogi; width,height - długości boków prostokąta; className - nazwa klasy stylu 
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addRectangleStyleClass (String className)*/
	public void addRectangleRoundWithClass (Point p, int rx, int ry, int width, int height, String className) {
		addRectangleRoundWithClass((int)p.getX(),(int)p.getY(), rx, ry, width, height, className);
	}
	
	/* dodaje prostokąt z zaokrąglonymi rogami
	 * x,y - lewy górny wierzchołek prostokąta; rx,ry - jak bardzo zaokrąglone rogi; width,height - długości boków prostokąta; className - nazwa klasy stylu 
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addRectangleStyleClass (String className)*/
	public void addRectangleRoundWithClass (int x, int y, int rx, int ry, int width, int height, String className) {
		writerSVG.println("   <rect class=\"" + className + "\" x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" />");
		writerHTML.println("   <rect class=\"" + className + "\" x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" />");
	}
	
	
//ELIPSY
	//dodaje styl wszystkich elips - z parametrów określonych w klasie SVG
	public void addEllipseStyle () {
		writerHTML.println( "<style>" );
		writerHTML.println("\tellipse{");
		writerHTML.println("\t\tstroke:\t" + ellipseStrokeColor + ";");
		writerHTML.println("\t\tstroke-width:\t"+ ellipseStrokeWidth + ";");
		writerHTML.println("\t\tfill:\t" + ellipseColor + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tellipse:hover{");
		writerHTML.println("\t\tstroke:\t" + ellipseStrokeColorHover + ";");
		writerHTML.println("\t\tstroke-width:\t" + ellipseStrokeWidthHover +";");
		writerHTML.println("\t\tfill:" + ellipseColorHover + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	//dodaje styl elips wg parametrów (oprócz tych, które są równe "" (0 dla int))
	public void addEllipseStyle (String ellipseStrokeColorp, int ellipseStrokeWidthp, String ellipseColorp, String ellipseStrokeColorHoverp, int ellipseStrokeWidthHoverp, String ellipseColorHoverp) {
		writerHTML.println( "<style>" );
		writerHTML.println("\tellipse{");
		if (ellipseStrokeColorp!="") writerHTML.println("\t\tstroke:\t" + ellipseStrokeColorp + ";");
		if (ellipseStrokeWidthp!=0) writerHTML.println("\t\tstroke-width:\t"+ ellipseStrokeWidthp + ";");
		if (ellipseColorp!="") writerHTML.println("\t\tfill:\t" + ellipseColorp + ";");
		writerHTML.println("\t}");
		writerHTML.println("\tellipse:hover{");
		if (ellipseStrokeColorHoverp!="") writerHTML.println("\t\tstroke:\t" + ellipseStrokeColorHoverp + ";");
		if (ellipseStrokeWidthHoverp!=0) writerHTML.println("\t\tstroke-width:\t" + ellipseStrokeWidthHoverp +";");
		if (ellipseColorHoverp!="") writerHTML.println("\t\tfill:" + ellipseColorHoverp + ";");
		writerHTML.println("\t}");
		writerHTML.println("</style>");
		writerHTML.println("");
	}
	
	//c - środek elipsy, rx - promień poziomy, ry - promień pionowy
	//dodaje elipsę bez stylu do pliku HTML i elipse z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addEllipsePlain (Point c, int rx, int ry) {
		addEllipsePlain((int)c.getX(), (int)c.getY(), rx, ry);
	}
	
	//dodaje elipsę bez stylu do pliku HTML i elipse z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addEllipsePlain (int cx, int cy, int rx, int ry) {
		writerSVG.println("   <ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" style=\"fill:" + ellipseColor + ";stroke:" + ellipseStrokeColor + ";stroke-width:" + ellipseStrokeWidth + "\" />");
		writerHTML.println("   <ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" />");
	}
	
	//c - środek elipsy, rx - promień poziomy, ry - promień pionowy
	//dodaje elipsę ze stylem (żółta z fioletowym brzegiem)
	public void addEllipse (Point c, int rx, int ry) {
		addEllipse((int)c.getX(), (int)c.getY(), rx, ry);
	}
	
	//dodaje elipsę ze stylem (żółta z fioletowym brzegiem)
	public void addEllipse (int cx, int cy, int rx, int ry) {
		writerSVG.println("   <ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" style=\"fill:yellow;stroke:purple;stroke-width:2\" />");
		writerHTML.println("   <ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" style=\"fill:yellow;stroke:purple;stroke-width:2\" />");
	}
	
	/* dodaje elipsę
	 * cx,cy - środek elipsy, rx - promień poziomy, ry - promień pionowy, className - nazwa klasy stylu
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addEllipseStyleClass (String className)*/
	public void addEllipseWithClass (int cx, int cy, int rx, int ry, String className) {
		writerSVG.println("   <ellipse class=\"" + className + "\" cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" />");
		writerHTML.println("   <ellipse class=\"" + className + "\" cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" />");
	}
	
	/* dodaje elipsę
	 * c - środek elipsy, rx - promień poziomy, ry - promień pionowy, className - nazwa klasy stylu
	 * wcześniej należy dodać styl danej klasy poprzez funkcję:
	 * public void addEllipseStyleClass (String className)*/
	public void addEllipseWithClass (Point c, int rx, int ry, String className) {
		addEllipseWithClass((int)c.getX(), (int)c.getY(), rx, ry, className);
	}
	
	
//NAPISY
	
	//p - lewy dolny wierzchołek prostokąta, w którym znajduje się napis
	//dodaje napis w kolorze z parametrów klasy
	public void addText (Point p, String text) {
		addText((int)p.getX(), (int)p.getY(), text);
	}
	
	//dodaje napis w kolorze z parametrów klasy
	public void addText (int x, int y, String text) {
		writerSVG.println( "   <text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + textColor + "\">" + text + "</text>" );
		writerHTML.println( "   <text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + textColor + "\">" + text + "</text>" );
	}
	
	//p - lewy dolny wierzchołek prostokąta, w którym znajduje się napis
	//dodaje napis
	public void addText (Point p, String text, String color) {
		addText((int)p.getX(), (int)p.getY(), text, color);
	}
	
	//dodaje napis
	public void addText (int x, int y, String text, String color) {
		writerSVG.println( "   <text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + color + "\">" + text + "</text>" );
		writerHTML.println( "   <text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + color + "\">" + text + "</text>" );
	}
	
//OBRAZKI
	
	/* dodaje łącze do grafiki
	 * x,y - współrzędne lewego górnego rogu obrazka
	 * height,width - do jakich rozmiarów obrazek będzie rozciągnięty
	 * link - ścieżka do obrazka (jeżeli w tym samym folderze co plik svg, to może być sama nazwa, np. "bus.jpg") */
	public void addImageLink (int x, int y, int width, int height, String link) {
		writerSVG.println("   <image x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" xlink:href=\"" + link + "\" />");
		writerHTML.println("   <image x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" xlink:href=\"" + link + "\" />");

	}
	
	/* dodaje łącze do grafiki
	 * p - współrzędne lewego górnego rogu obrazka
	 * height,width - do jakich rozmiarów obrazek będzie rozciągnięty
	 * link - ścieżka do obrazka (jeżeli w tym samym folderze co plik svg, to może być sama nazwa, np. "bus.jpg") */
	public void addImageLink (Point p, int width, int height, String link) {
		addImageLink((int)p.getX(), (int)p.getY(), width, height, link);
	}
	
	/* dodaje łącze do grafiki, która jest skalowana
	 * x,y - współrzędne lewego górnego rogu obrazka
	 * height,width - do jakich rozmiarów obrazek będzie rozciągnięty
	 * link - ścieżka do obrazka (jeżeli w tym samym folderze co plik svg, to może być sama nazwa, np. "bus.jpg")
	 * a,b - jak ma być przeskalowany rysunek (np. a = 1.5, b = 1.7 sprawią, że rysunek będzie odpowiednio większy)
	 * a odpowiada za zwiększanie width, b za height */
	public void addImageLink (int x, int y, int width, int height, String link, double a, double b) {
		writerSVG.println("   <image x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" xlink:href=\"" + link + "\" transform = \"scale(" + a + "," + b + ")\" />");
		writerHTML.println("   <image x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" xlink:href=\"" + link + "\" transform = \"scale(" + a + "," + b + ")\" />");

	}
	
	/* dodaje łącze do grafiki, która jest skalowana
	 * p - współrzędne lewego górnego rogu obrazka
	 * height,width - do jakich rozmiarów obrazek będzie rozciągnięty
	 * link - ścieżka do obrazka (jeżeli w tym samym folderze co plik svg, to może być sama nazwa, np. "bus.jpg")
	 * a,b - jak ma być przeskalowany rysunek (np. a = 1.5, b = 1.7 sprawią, że rysunek będzie odpowiednio większy)
	 * a odpowiada za zwiększanie width, b za height */
	public void addImageLink (Point p, int width, int height, String link, double a, double b) {
		addImageLink((int)p.getX(), (int)p.getY(), width, height, link, a, b);
	}
	
	/* dodaje łącze do grafiki, która jest rozciągana do wymiarów pliku svg
	 * link - ścieżka do obrazka (jeżeli w tym samym folderze co plik svg, to może być sama nazwa, np. "bus.jpg") */
	public void addImageLink (String link) {
		writerSVG.println("   <image x=\"0\" y=\"0\" width=\"" + width + "\" height=\"" + height + "\" xlink:href=\"" + link + "\" />");
		writerHTML.println("   <image x=\"0\" y=\"0\" width=\"" + width + "\" height=\"" + height + "\" xlink:href=\"" + link + "\" />");

	}
	
//SET
	
	public void setSize (int widthp, int heightp) {
		setWidth(widthp);
		setHeight(heightp);
	}
	
	public void setWidth (int w) {
		width = w;
	}
	
	public void setHeight (int h) {
		height = h;
	}
	
	public void setFileName (String filename) {
		fileName = filename;
	}
	
	public void setPolylineColor (String color) {
		polylineColor = color;
	}
	
	public void setPolylineColorHover (String colorHover) {
		polylineColorHover = colorHover;
	}
	
	public void setPolylineWidth (int width) {
		polylineWidth = width;
	}
	
	public void setPolylineWidthHover (int widthHover) {
		polylineWidthHover = widthHover;
	}
	
	public void setPolylineLinejoin (String linejoin) {
		polylineLinejoin = linejoin;
	}
	
	public void setPolylineLinecap (String linecap) {
		polylineLinecap = linecap;
	}
	
	public void setCircleStrokeColor (String circleStrokeColorp) {
		circleStrokeColor = circleStrokeColorp;
	}
	
	public void setCircleStrokeColorHover (String circleStrokeColorHoverp) {
		circleStrokeColorHover = circleStrokeColorHoverp;
	}

	public void setCircleStrokeWidth (int circleStrokeWidthp) {
		circleStrokeWidth = circleStrokeWidthp;
	}
	
	public void setCircleStrokeWidthHover (int circleStrokeWidthHoverp) {
		circleStrokeWidthHover = circleStrokeWidthHoverp;
	}

	public void setCircleFill (String circleFillp) {
		circleFill = circleFillp;
	}
	
	public void setCircleFillHover (String circleFillHoverp) {
		circleFillHover = circleFillHoverp;
	}
	
	public void setTextColor (String textColorp) {
		textColor = textColorp;
	}
	
	public void setRectangleColor (String rectangleColorp) {
		rectangleColor = rectangleColorp;
	}
	
	public void setRectangleStrokeWidth (int rectangleStrokeWidthp) {
		rectangleStrokeWidth = rectangleStrokeWidthp;
	}
	
	public void setRectangleStrokeColor (String rectangleStrokeColorp) {
		rectangleStrokeColor = rectangleStrokeColorp;
	}
	
	public void setRectangleColorHover (String rectangleColorHoverp) {
		rectangleColorHover = rectangleColorHoverp;
	}
	
	public void setRectangleStrokeWidthHover (int rectangleStrokeWidthHoverp) {
		rectangleStrokeWidthHover = rectangleStrokeWidthHoverp;
	}
	
	public void setRectangleStrokeColorHover (String rectangleStrokeColorHoverp) {
		rectangleStrokeColorHover = rectangleStrokeColorHoverp;
	}
	
	public void setEllipseColor (String ellipseColorp) {
		ellipseColor = ellipseColorp;
	}

	public void setEllipseStrokeWidth (int ellipseStrokeWidthp) {
		ellipseStrokeWidth = ellipseStrokeWidthp;
	}
	
	public void setEllipseStrokeColor (String ellipseStrokeColorp) {
		ellipseStrokeColor = ellipseStrokeColorp;
	}
	
	public void setEllipseColorHover (String ellipseColorHoverp) {
		ellipseColorHover = ellipseColorHoverp;
	}
	
	public void setEllipseStrokeWidthHover (int ellipseStrokeWidthHoverp) {
		ellipseStrokeWidthHover = ellipseStrokeWidthHoverp;
	}
	
	public void setEllipseStrokeColorHover (String ellipseStrokeColorHoverp) {
		ellipseStrokeColorHover = ellipseStrokeColorHoverp;
	}
	
//GET
	public int getWidth () {
		return width;
	}
	
	public int getHeight () {
		return  height;
	}
	
	public String getFileName () {
		return fileName;
	}	
	
	public String getPolylineColor () {
		return polylineColor;
	}
	
	public String getPolylineColorHover () {
		return polylineColorHover;
	}
	
	public int getPolylineWidth () {
		return polylineWidth;
	}
	
	public int getPolylineWidthHover () {
		return polylineWidthHover;
	}
	
	public String getPolylineLinejoin () {
		return polylineLinejoin;
	}
	
	public String getPolylineLinecap () {
		return polylineLinecap;
	}
	
	public String getCircleStrokeColor () {
		return circleStrokeColor;
	}
	
	public String getCircleStrokeColorHover () {
		return circleStrokeColorHover;
	}
	
	public int getCircleStrokeWidth () {
		return circleStrokeWidth;
	}
	
	public int getCircleStrokeWidthHover () {
		return circleStrokeWidthHover;
	}
	
	public String getCircleFill () {
		return circleFill;
	}
	
	public String getCircleFillHover () {
		return circleFillHover;
	}
	
	public String getTextColor () {
		return textColor;
	}
	
	public String getRectangleColor () {
		return rectangleColor;
	}
	
	public int getRectangleStrokeWidth () {
		return rectangleStrokeWidth;
	}
	
	public String getRectangleStrokeColor () {
		return rectangleStrokeColor;
	}
	
	public String getRectangleColorHover () {
		return rectangleColorHover;
	}
	
	public int getRectangleStrokeWidthHover () {
		return rectangleStrokeWidthHover;
	}
	
	public String getRectangleStrokeColorHover () {
		return rectangleStrokeColorHover;
	}
	
	public String getEllipseColor () {
		return ellipseColor;
	}
	
	public int getEllipseStrokeWidth () {
		return ellipseStrokeWidth;
	}
	
	public String getEllipseStrokeColor () {
		return ellipseStrokeColor;
	}
	
	public String getEllipseColorHover () {
		return ellipseColorHover;
	}
	
	public int getEllipseStrokeWidthHover () {
		return ellipseStrokeWidthHover;
	}
	
	public String getEllipseStrokeColorHover () {
		return ellipseStrokeColorHover;
	}

	
	private PrintWriter writerSVG;
	private PrintWriter writerHTML;
	private int width = 700;
	private int height = 500;
	private String fileName = "domyslny";
	
	private String polylineColor = "rgb(155,150,255)";
	private String polylineColorHover = "rgb(55,70,170)";
	private int polylineWidth = 3;
	private int polylineWidthHover = 6;
	private String polylineLinejoin = "round";
	private String polylineLinecap = "round";
	
	private String circleStrokeColor = "rgb(50,155,50)";
	private String circleStrokeColorHover = "rgb(55,100,55)";
	private int circleStrokeWidth = 4;
	private int circleStrokeWidthHover = 6;
	private String circleFill = "rgb(200,200,0)";
	private String circleFillHover = "rgb(250,250,0)";
	
	private String textColor = "black";
	
	private String rectangleColor = "red";
	private int rectangleStrokeWidth = 3;
	private String rectangleStrokeColor = "black";
	private String rectangleColorHover = "rgb(255,40,40)";
	private int rectangleStrokeWidthHover = 6;
	private String rectangleStrokeColorHover = "rgb(50,50,50)";
	
	private String ellipseColor = "rgb(200,200,0)";
	private int ellipseStrokeWidth = 3;
	private String ellipseStrokeColor = "purple";
	private String ellipseColorHover = "yellow";
	private int ellipseStrokeWidthHover = 6;
	private String ellipseStrokeColorHover = "rgb(170,0,170)";
}
