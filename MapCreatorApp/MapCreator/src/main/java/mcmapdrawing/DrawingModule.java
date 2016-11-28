package mcmapdrawing;

import java.awt.Point;
import mcgraphs.MapNode;
import mcgraphs.MapGraph;
import mcgtfsstructures.Stop;
import mcgtfsstructures.Shape;
import mctemplates.Drawable;
import mctemplates.Pair;
import mcgtfsstructures.localGtfsDatabase;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;
import mcalgorithms.GraphGlueing;
import mcalgorithms.MapGraphCreator;
import mcgraphs.MapEdge;
import mctemplates.MCConstants;
import mctemplates.UsefulFunctions;

public class DrawingModule {

    public DrawingModule(SVG s) {
        svg = s;
        initialSVGFileName = svg.getFileName();
        localGtfsDatabase.init();  
        createLBCandRUC();
        
    }
    
    public void beginSVG(){
        svg.beginSVG();        
        svg.addPolylineStyle();
        svg.addCircleStyle();
        svg.addRectangleStyle();
        svg.addEllipseStyle();
    }
    
    public void endSVG(){
        svg.endSVG();
    }
    
    private void compareLBCRUC( Pair< Pair<Float,Float>, Pair<Float,Float> > LBCRUC ){
        if( LBCRUC.getST().getST() < LBC.getST() ){
            LBC.setST( LBCRUC.getST().getST() );
        }
        if( LBCRUC.getST().getND() < LBC.getND() ){
            LBC.setND( LBCRUC.getST().getND() );
        }
        if( LBCRUC.getND().getST() > RUC.getST() ){
            RUC.setST( LBCRUC.getND().getST() );
        }
        if( LBCRUC.getND().getND() > RUC.getND() ){
            RUC.setND( LBCRUC.getND().getND() );
        }   
        
    }
    
    
    private void createLBCandRUC(){
        Pair< Pair<Float,Float>, Pair<Float,Float> > LBCRUC = getLBCandRUC( new ArrayList<Drawable>( localGtfsDatabase.getAllStops()) ); // TUTAJ NIE MOZE BYC
        LBC = LBCRUC.getST();
        RUC = LBCRUC.getND();
        
        modifyLBCandRUC();
    }
    
    private void modifyLBCandRUC(){
        float ratio = 0.0001f; // dziwne jest, ze nawet jak zmienie wartosci wzglednie o 1 tysieczna to i tak rysunek sie mocno wygina
        LBC.setST( LBC.getST() - ratio*Math.abs( LBC.getST() ) );
        LBC.setND(LBC.getND() - ratio*Math.abs( LBC.getND() ));
        RUC.setST( RUC.getST() + 0.5f*ratio*Math.abs( RUC.getST() ) );
        RUC.setND( RUC.getND() + 0.5f*ratio*Math.abs(RUC.getND()) );
    }
    
    private void createLBCandRUC( MapGraph graph ){
        LBC = new Pair<>( Float.MAX_VALUE, Float.MAX_VALUE );
        RUC = new Pair<>( Float.MIN_VALUE, Float.MIN_VALUE );
        
        ArrayList<MapNode> nodes = graph.getNodes();
        for( MapNode n : nodes ){
            Pair<Float,Float> p = n.getCoords();
            if( p.getST() < LBC.getST() ){
                LBC.setST( p.getST() );
            }
            
            if( p.getND() < LBC.getND() ){
                LBC.setND( p.getND() );
            }
            
            if( p.getST() > RUC.getST() ){
                RUC.setST( p.getST() );
            }
            
            if( p.getND() > RUC.getND() ){
                RUC.setND( p.getND() );
            }            
        } 
        
        modifyLBCandRUC();        
    }
    
    
    
    // jako parametry - LBC - left bottom corner, RUC right upper corner i coords - wspolrzedne do znormalizowania
    // LBC i RUC to wspolrzedne najbardziej wysunietych struktur grafu z malym dodatkiem (np +- 10)
    private Pair<Integer,Integer> normalizeCoordinates( Pair<Float,Float> LBC, Pair<Float,Float> RUC, Pair<Float,Float> coords ){
        float x = coords.getST();
        float y = coords.getND();
        
        x -= LBC.getST();
        y -= LBC.getND(); // translacja tak, aby LBC byl w punkcie (0,0)
        
        float dW = RUC.getST() - LBC.getST();
        float dH = RUC.getND() - LBC.getND();
        
        if( dW == 0 ) dW = 0.00001f;
        if( dH == 0 ) dH = 0.00001f;
        
        y = dH - y; // zamiana wspolrzednych na wspolrzedne mapowe w svg        
        
        float W = (float) svg.getWidth();
        float H = (float) svg.getHeight();
        
        int x2 = Math.round( (W*x) / dW);
        int y2 = Math.round( (W*y) / dH );
             
        return new Pair<>(x2,y2);
    }
    
    // dla zadanej listy struktur zwraca RUC i LBC
    // struktury w liscie structures musza implementowac Drawable
    // zwraca pare <LBC,RUC>
    private Pair< Pair<Float,Float>, Pair<Float,Float> > getLBCandRUC( ArrayList<Drawable> structures ){
        Pair<Float,Float> LBC = new Pair<>( Float.MAX_VALUE, Float.MAX_VALUE );
        Pair<Float,Float> RUC = new Pair<>( Float.MIN_VALUE, Float.MIN_VALUE );
        
        for( Drawable d : structures ){
            Pair<Float,Float> p = d.getCoords();
            if( p.getST() < LBC.getST() ){
                LBC.setST( p.getST() );
            }
            
            if( p.getND() < LBC.getND() ){
                LBC.setND( p.getND() );
            }
            
            if( p.getST() > RUC.getST() ){
                RUC.setST( p.getST() );
            }
            
            if( p.getND() > RUC.getND() ){
                RUC.setND( p.getND() );
            }            
        }
        
        return new Pair<>( LBC,RUC );        
    }
        
    // funkcja tmczasowa - do zmiany, tylko do zaprezentowania dzialania
    public void drawShapesOnMap(){
        Set<String> set = new HashSet<>();        
        
        ArrayList<Shape> allShapes = localGtfsDatabase.getAllShapes();
        ArrayList<Shape> shapeById = null;
        
               
        for( Shape s : allShapes ){            
            if( !set.contains( s.getShapeId() ) ){
                set.add( s.getShapeId() );
                shapeById = localGtfsDatabase.getAllShapesOfId( s.getShapeId() );
                     
                ArrayList<Integer> x = new ArrayList<>();
                ArrayList<Integer> y = new ArrayList<>(); // tego nie powinno byc - bedzie do czasu gdy Asia zrobic funkcje dodawania lini dla par
                
                for( Shape sh :shapeById ){
                    Pair<Integer,Integer> norm = normalizeCoordinates(LBC, RUC, sh.getCoords() );
                    x.add( norm.getST() );
                    y.add( norm.getND() );
                }               
                
                svg.addPolylinePlain(x, y);
                
             //   System.out.println( "Narysowalem linie dla " + s.getShapeId() );
                                
            }           
        }
        
    }
    
    public void drawStopsOnMap(){
        ArrayList<Stop> stops = localGtfsDatabase.getAllStops();
        
        for( Stop s : stops ){
            float x = Float.parseFloat( s.getStopLat() );
            float y = Float.parseFloat( s.getStopLon() );
            Pair<Integer,Integer> p = normalizeCoordinates(LBC, RUC, new Pair<>(x,y) );
            svg.addCirclePlain(p.getST(), p.getND(),3 );
         //   System.out.println( "Dodalem przystanek o id = " + s.getStopId() + "   x = " + p.getST() + "  y = " + p.getND() );
            
        }
    }
    
    // jedna z mapek, ktora tworzy nasz program
    // rysuje na mapie wszystko co jest dane w stops.txt oraz shapes.txt
    // dodaje rowniez podpisy do przystankow czy lini
    public void drawShapeMap(){
        createLBCandRUC();
        
        svg.setFileName( initialSVGFileName + "_shape_map" );        
        beginSVG();
        
        drawShapesOnMap(); 
        drawStopsOnMap();    
        
        endSVG();
    }
    
    
    // rysuje schematyczna mapke - czyli to o co w całym projekcie miało chodzic, ale ze jestesmy ambitni to robimy wieeeeecej
    public void drawSchemeMap(){
        svg.setFileName( initialSVGFileName + "_scheme" );
        beginSVG();
        
        endSVG();
    }
    
    
    // rysuje wszystkie mapy, jakie tylko moge wygenerowac :)
    public void drawAllMaps(){
        drawShapeMap();
      //  drawSchemeMap();
        
        graph = new MapGraphCreator().createMapGraphFromGtfsDatabase( MCConstants.ALL_TRANSPORT_MEASURES );
        drawGraphOnMap( graph, "graph_not_glued");
        System.out.println( "Not glued graph ma " + graph.size() + " wierzcholkow i " + graph.getEdges().size() + " krawedzi" );
                
        //new GraphGlueing(graph).testGlueing();
        MapGraph glGraph = new GraphGlueing(graph).getGluedGraph();
        System.out.println( "GLUED graph ma " + glGraph.size() + " wierzcholkow i " + glGraph.getEdges().size() + " krawedzi" );
        drawGraphOnMap( glGraph, "graph_glued");
    }
    
        
    private void drawGraphNodesOnMap( MapGraph graph ){
        
        for( MapNode n : graph.getNodes() ){
            if( n.getDrawingWidth() != 0 ) svg.setCircleStrokeWidth( n.getDrawingWidth() );
            if( n.getHoverColor() != null ) {
                svg.setCircleFillHover( UsefulFunctions.parseColor( n.getHoverColor() ));
                svg.setCircleStrokeColorHover( UsefulFunctions.parseColor( n.getHoverColor() ) );
            }
            if( n.getColor() != null ) {
                svg.setCircleFill( UsefulFunctions.parseColor( n.getColor()) );
                svg.setCircleStrokeColor( UsefulFunctions.parseColor( n.getColor()) );
            }
            
            int drawingWidth = n.getDrawingWidth() - 1 + n.getContainedStopIds().size();     
            
            if( n.getContainedStopIds().size() >= 4 ){
                svg.setTextColor( "red" );
                String s = "";
                for( String sadd : n.getContainedStopIds() ) s += localGtfsDatabase.getStopOfID(sadd).getStopName() + " -- ";
                svg.addText( UsefulFunctions.convertToPoint( normalizeCoordinates(LBC, RUC, n.getCoords() ) ), s );
                svg.setTextColor( "black" );
                
                svg.addEllipsePlain(UsefulFunctions.convertToPoint( normalizeCoordinates(LBC, RUC, n.getCoords() ) ), 3*drawingWidth/2, drawingWidth );
            }
            else if(n.getEdges().size() == 1){
                String s = "";
                for( String sadd : n.getContainedStopIds() ) s += localGtfsDatabase.getStopOfID(sadd).getStopName() + " -- ";
                svg.addText( UsefulFunctions.convertToPoint( normalizeCoordinates(LBC, RUC, n.getCoords() ) ), s );
                svg.addCircle( UsefulFunctions.convertToPoint( normalizeCoordinates(LBC, RUC, n.getCoords() ) ), drawingWidth );
            }
            else{
                svg.addCirclePlain( UsefulFunctions.convertToPoint( normalizeCoordinates(LBC, RUC, n.getCoords() ) ) , drawingWidth);
            }
        }
    }
    
    // JESZCZE NIE GOTOWE
    private void drawGraphEdgesOnMap( MapGraph graph ){
        ArrayList<Point> polyline = new ArrayList<>();
        
        int CNT = 0;
        for( MapEdge e : graph.getEdges() ){
            polyline.clear();
            MapNode n1 = e.getEnds().getST();
            MapNode n2 = e.getEnds().getND();
            polyline.add( UsefulFunctions.convertToPoint( normalizeCoordinates(LBC, RUC, n1.getCoords() ) ) );
            polyline.add( UsefulFunctions.convertToPoint( normalizeCoordinates(LBC, RUC, n2.getCoords() ) ) );
            
            if( e.getHoverWidth() != 0 ) svg.setPolylineWidthHover( e.getHoverWidth() );
            if( e.getDrawingWidth() != 0 ) svg.setPolylineWidth( e.getDrawingWidth() );
            if( e.getColor() != null ) svg.setPolylineColor( UsefulFunctions.parseColor(e.getColor() ));
            if( e.getHoverColor() != null ) svg.setPolylineColorHover( UsefulFunctions.parseColor(e.getHoverColor()) );
            svg.addPolylinePlain(polyline);       
           // svg.addLine( polyline.get(0).x, polyline.get(0).y, polyline.get(1).x, polyline.get(1).y );
            CNT++;
        }
        
        System.out.println( "Dodalem " + CNT + " krawedzi do pliku svg" );
    }
    
    // rysuje zadany graf do pliku svg
    public void drawGraphOnMap( MapGraph graph, String svgname ){
        createLBCandRUC(graph);
        
        svg.setFileName( initialSVGFileName + "_" + svgname );       
        beginSVG();
       // svg.addImageLink( "background.jpg" );        
                
        drawGraphEdgesOnMap(graph);
        drawGraphNodesOnMap(graph);        
        
        endSVG();
    }

   
    private String initialSVGFileName = "";
    private SVG svg = null;
    private MapGraph graph = new MapGraph();
    
    private Pair<Float,Float> RUC = null; 
    private Pair<Float,Float> LBC = null;
    
    
}
