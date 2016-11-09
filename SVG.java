import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

//TO DO:
//dodawanie klasy (stylu)
//dodawanie części parametrów stylu przy tworzeniu figur
//styl napisów?

//funkcje - pair
//dodanie do html wyblakłego obrazka mpk
public class SVG {

	public SVG () {
		try {
			writerSVG = new PrintWriter(fileName + ".svg");
			writerHTML = new PrintWriter(fileName + ".html");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//konstruktor ustawiający wymiary rysunku
	public SVG (int widthp, int heightp) {
		width = widthp;
		height = heightp;
		try {
			writerSVG = new PrintWriter(fileName + ".svg");
			writerHTML = new PrintWriter(fileName + ".html");		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
				
			e.printStackTrace();
		}
	}

	//konstruktor ustawiający nazwę plików
	public SVG (String fileNamep) {
		fileName = fileNamep;
		try {
			writerSVG = new PrintWriter(fileName + ".svg");
			writerHTML = new PrintWriter(fileName + ".html");		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
		
	//konstruktor ustawiający wymiary rysunku i nazwę plików
	public SVG (int widthp, int heightp, String fileNamep) {
		width = widthp;
		height = heightp;
		fileName = fileNamep;
		try {
			writerSVG = new PrintWriter(fileName + ".svg");
			writerHTML = new PrintWriter(fileName + ".html");		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	//wszystkie potrzebne rzeczy na początek plików
	public void beginSVG () {
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
	
	//dodaje prostokąt bez stylu do pliku HTML i prostokąt z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addRectanglePlain (int x, int y, int width, int height) {
		writerSVG.println("   <rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:" + rectangleColor + ";stroke:" + rectangleStrokeColor + ";stroke-width:" + rectangleStrokeWidth + "\" />");
		writerHTML.println("   <rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" />");
	}
	
	//dodaje prostokąt ze stylem (czerwone z czarnym brzegiem)
	public void addRectangle (int x, int y, int width, int height) {
		writerSVG.println("   <rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:red;stroke:black;stroke-width:5\" />");
		writerHTML.println("   <rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:red;stroke:black;stroke-width:5\" />");
	}
	
	//dodaje prostokąt z zaokrąglonymi rogami bez stylu do pliku HTML i prostokąt z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addRectangleRoundPlain (int x, int y, int rx, int ry, int width, int height) {
		writerSVG.println("   <rect x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:" + rectangleColor + ";stroke:" + rectangleStrokeColor + ";stroke-width:" + rectangleStrokeWidth + "\" />");
		writerHTML.println("   <rect x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" />");
	}
	
	//dodaje prostokąt z zaokrąglonymi rogami ze stylem (czerwone z czarnym brzegiem)
	public void addRectangleRound (int x, int y, int rx, int ry, int width, int height) {
		writerSVG.println("   <rect x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:red;stroke:black;stroke-width:5\" />");
		writerHTML.println("   <rect x=\"" + x + "\" y=\"" + y + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:red;stroke:black;stroke-width:5\" />");
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
	
	//dodaje elipsę bez stylu do pliku HTML i elipse z "domyślnym" (ustawionym w parametrach klasy) stylem do pliku SVG
	public void addEllipsePlain (int cx, int cy, int rx, int ry) {
		writerSVG.println("   <ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" style=\"fill:" + ellipseColor + ";stroke:" + ellipseStrokeColor + ";stroke-width:" + ellipseStrokeWidth + "\" />");
		writerHTML.println("   <ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" />");
	}
	
	//dodaje elipsę ze stylem (żółta z fioletowym brzegiem)
	public void addEllipse (int cx, int cy, int rx, int ry) {
		writerSVG.println("   <ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" style=\"fill:yellow;stroke:purple;stroke-width:2\" />");
		writerHTML.println("   <ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry + "\" style=\"fill:yellow;stroke:purple;stroke-width:2\" />");
	}
	
//NAPISY
	//dodaje napis w kolorze z parametrów klasy
	public void addText (int x, int y, String text) {
		writerSVG.println( "   <text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + textColor + "\">" + text + "</text>" );
		writerHTML.println( "   <text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + textColor + "\">" + text + "</text>" );
	}
	
	//dodaje napis
	public void addText (int x, int y, String text, String color) {
		writerSVG.println( "   <text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + color + "\">" + text + "</text>" );
		writerHTML.println( "   <text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + color + "\">" + text + "</text>" );
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
