package mcmapdrawing;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import mcgraphs.MapNode;
import mcgraphs.MapGraph;
import mcgtfsstructures.Stop;
import mcgtfsstructures.Shape;
import mctemplates.Drawable;
import mctemplates.Pair;
import mcgtfsstructures.MCDatabase;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import mcalgorithms.EdgeContraction;
import mcalgorithms.ForceAlgorithm;
import mcalgorithms.GraphGlueing;
import mcalgorithms.MapGraphCreator;
import mcalgorithms.RoutePathCreator;
import mcgraphs.GraphPath;
import mcgraphs.MapEdge;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

public class DrawingModule {

    public DrawingModule(SVG s) {
        svg = s;
        initialSVGFileName = "./DrawingFolder/" + svg.getFileName();        
        createLBCandRUC();
    }

    public void beginSVG() {
        svg.beginSVG();
        svg.addPolylineStyle();
        svg.addCircleStyle();
        svg.addRectangleStyle();
        svg.addEllipseStyle();
    }

    public void endSVG() {
        svg.endSVG();
    }

    private void compareLBCRUC(Pair< Pair<Float, Float>, Pair<Float, Float>> LBCRUC) {
        if (LBCRUC.getST().getST() < LBC.getST()) {
            LBC.setST(LBCRUC.getST().getST());
        }
        if (LBCRUC.getST().getND() < LBC.getND()) {
            LBC.setND(LBCRUC.getST().getND());
        }
        if (LBCRUC.getND().getST() > RUC.getST()) {
            RUC.setST(LBCRUC.getND().getST());
        }
        if (LBCRUC.getND().getND() > RUC.getND()) {
            RUC.setND(LBCRUC.getND().getND());
        }

    }

    private void createLBCandRUC() {
        Pair< Pair<Float, Float>, Pair<Float, Float>> LBCRUC = getLBCandRUC(new ArrayList<Drawable>(MCDatabase.getAllStops())); // TUTAJ NIE MOZE BYC
        LBC = LBCRUC.getST();
        RUC = LBCRUC.getND();

        scaleSVG();
        modifyLBCandRUC();
    }

    private void modifyLBCandRUC() {
        float ratio = 0.02f; // dziwne jest, ze nawet jak zmienie wartosci wzglednie o 1 tysieczna to i tak rysunek sie mocno wygina
        float dW = RUC.getST() - LBC.getST();
        float dH = RUC.getND() - LBC.getND();

        LBC.setST(LBC.getST() - ratio * dW);
        LBC.setND(LBC.getND() - ratio * dH);
        RUC.setST(RUC.getST() + ratio * dW);
        RUC.setND(RUC.getND() + ratio * dH);
    }

    private void createLBCandRUC(MapGraph graph) {
        LBC = new Pair<>(Float.MAX_VALUE, Float.MAX_VALUE);
        RUC = new Pair<>(Float.MIN_VALUE, Float.MIN_VALUE);

        ArrayList<MapNode> nodes = graph.getNodes();
        for (MapNode n : nodes) {
            Pair<Float, Float> p = n.getCoords();
            if (p.getST() < LBC.getST()) {
                LBC.setST(p.getST());
            }

            if (p.getND() < LBC.getND()) {
                LBC.setND(p.getND());
            }

            if (p.getST() > RUC.getST()) {
                RUC.setST(p.getST());
            }

            if (p.getND() > RUC.getND()) {
                RUC.setND(p.getND());
            }
        }

        scaleSVG();
        modifyLBCandRUC();
    }

    /**
     * Function scales SVG. As LBC and RUC are set, this function changes
     * svg.width, so that the proportions of image are adequate
     */
    private void scaleSVG() {
        float dW = RUC.getST() - LBC.getST();
        float dH = RUC.getND() - LBC.getND();

        if (dH <= 0) {
            dH = 0.00001f;
        }

      //  System.out.println( "dW = " + dW + "   dH = " + dH );
        svg.setWidth((int) ((float) svg.getHeight() * (dW / dH)));
        //   System.out.println( "width = " + svg.getWidth() + "   height = " + svg.getHeight() );
    }

    /**
     *
     * @param LBC Left Bottom Corner of our graph
     * @param RUC Right Upper Corner of our graph
     * @param coords Coordinates of a point we want to locate on the map
     * @return returns svg-coordinates corresponding to coords
     */
    private Pair<Integer, Integer> normalizeCoordinates(Pair<Float, Float> LBC, Pair<Float, Float> RUC, Pair<Float, Float> coords) {
        float x = coords.getST();
        float y = coords.getND();

        x -= LBC.getST();
        y -= LBC.getND(); // translacja tak, aby LBC byl w punkcie (0,0)

        float dW = RUC.getST() - LBC.getST();
        float dH = RUC.getND() - LBC.getND();

        if (dW == 0) {
            dW = 0.00001f;
        }
        if (dH == 0) {
            dH = 0.00001f;
        }

        y = dH - y; // zamiana wspolrzednych na wspolrzedne mapowe w svg        

        float W = (float) svg.getWidth();
        float H = (float) svg.getHeight();

        int x2 = Math.round((W * x) / dW);
        int y2 = Math.round((H * y) / dH);

        return new Pair<>(x2, y2);
    }

    // dla zadanej listy struktur zwraca RUC i LBC
    // struktury w liscie structures musza implementowac Drawable
    // zwraca pare <LBC,RUC>
    private Pair< Pair<Float, Float>, Pair<Float, Float>> getLBCandRUC(ArrayList<Drawable> structures) {
        Pair<Float, Float> LBC = new Pair<>(Float.MAX_VALUE, Float.MAX_VALUE);
        Pair<Float, Float> RUC = new Pair<>(Float.MIN_VALUE, Float.MIN_VALUE);

        for (Drawable d : structures) {
            Pair<Float, Float> p = d.getCoords();
            if (p.getST() < LBC.getST()) {
                LBC.setST(p.getST());
            }

            if (p.getND() < LBC.getND()) {
                LBC.setND(p.getND());
            }

            if (p.getST() > RUC.getST()) {
                RUC.setST(p.getST());
            }

            if (p.getND() > RUC.getND()) {
                RUC.setND(p.getND());
            }
        }

        return new Pair<>(LBC, RUC);
    }

    // funkcja tmczasowa - do zmiany, tylko do zaprezentowania dzialania
    public void drawShapesOnMap() {
        Set<String> set = new HashSet<>();

        ArrayList<Shape> allShapes = MCDatabase.getAllShapes();
        ArrayList<Shape> shapeById = null;

        for (Shape s : allShapes) {
            if (!set.contains(s.getShapeId())) {
                set.add(s.getShapeId());
                shapeById = MCDatabase.getAllShapesOfId(s.getShapeId());

                ArrayList<Integer> x = new ArrayList<>();
                ArrayList<Integer> y = new ArrayList<>(); // tego nie powinno byc - bedzie do czasu gdy Asia zrobic funkcje dodawania lini dla par

                for (Shape sh : shapeById) {
                    Pair<Integer, Integer> norm = normalizeCoordinates(LBC, RUC, sh.getCoords());
                    x.add(norm.getST());
                    y.add(norm.getND());
                }

                svg.setPolylineWidth(3);
                svg.setPolylineColor(UsefulFunctions.parseColor(UsefulFunctions.getNextColor()));
                svg.addPolylinePlain(x, y);

                //  System.out.println( "Narysowalem linie dla " + s.getShapeId() );
            }
        }

    }

    public void drawStopsOnMap() {
        ArrayList<Stop> stops = MCDatabase.getAllStops();

        for (Stop s : stops) {
            float x = Float.parseFloat(s.getStopLon());
            float y = Float.parseFloat(s.getStopLat());
            Pair<Integer, Integer> p = normalizeCoordinates(LBC, RUC, new Pair<>(x, y));
            svg.addCircle(p.getST(), p.getND(), 3);
            //   System.out.println( "Dodalem przystanek o id = " + s.getStopId() + "   x = " + p.getST() + "  y = " + p.getND() );
        }
    }

    // jedna z mapek, ktora tworzy nasz program
    // rysuje na mapie wszystko co jest dane w stops.txt oraz shapes.txt
    // dodaje rowniez podpisy do przystankow czy lini
    public void drawShapeMap() {

        String path = (new File("").getAbsolutePath()) + "/GTFS/" + "shapes.txt";
        if (UsefulFunctions.existsFile(path) == false) {
            System.out.println("No shapes.txt file,   path = " + path);
            return;
        }

        createLBCandRUC();
        svg.setFileName(initialSVGFileName + "_shape_map");
        beginSVG();

        drawShapesOnMap();
        drawStopsOnMap();

        endSVG();
    }

    // rysuje schematyczna mapke - czyli to o co w całym projekcie miało chodzic, ale ze jestesmy ambitni to robimy wieeeeecej
    public void drawSchemeMap() {
        svg.setFileName(initialSVGFileName + "_scheme");
        beginSVG();

        endSVG();
    }

    public void drawDatabaseGraph() {
        System.out.println("Zaczynam tworzyc podstawowy graf z danych GTFS");
        graph = new MapGraphCreator().createMapGraphFromGtfsDatabase(MCSettings.getDRAWING_ROUTE_TYPE());
        System.out.println("Ukonczylem tworzenie grafu z danych GTFS");
        System.out.println("Basic graph ma " + graph.countNodes() + " wierzcholkow i " + graph.countEdges() + " krawedzi");

        System.out.println("Rozpoczynam rysowanie podstawowego grafu");
        drawGraphOnMap(graph, "basic");
        System.out.println("Skonczylem rysowac podstawowy graf\n\n");
    }

    /**
     * Function drawGluedGraph draws glued graph. Earlier drawDatabaseGraph
     * function must be called
     */
    private void drawGluedGraph() {
        System.out.println("Zaczynam procedure sklejania grafu");
        new GraphGlueing(graph).convertGraph();
        System.out.println("Skonczylem procedure sklejania grafu");
        System.out.println("GLUED graph ma " + graph.countNodes() + " wierzcholkow i " + graph.countEdges() + " krawedzi");

        System.out.println("Rozpoczynam rysowanie sklejonego grafu");
        drawGraphOnMap(graph, "glued");
        System.out.println("Skonczylem rysowanie sklejonego grafu\n\n");
    }

    /**
     * Function drawEdgeContractedGraph draws edge-contracted graph. Earlier
     * functions drawDatabaseGraph and drawGluedGraph must be called
     */
    private void drawEdgeContractedGraph() {
        System.out.println("Zaczynam procedure kontrakcji krawedzi");
        new EdgeContraction(graph).convertGraph();
        System.out.println("Skonczylem procedure kontrakcji krawedzi");
        System.out.println("Edgecontracted graph ma " + graph.countNodes() + " wierzcholkow i " + graph.countEdges() + " krawedzi");

        System.out.println("Rozpoczynam rysowanie grafu z kontrakcja krawedzi");
        drawGraphOnMap(graph, "edgecontracted");
        System.out.println("Skonczylem rysowanie grafu z kontrakcja krawedzi\n\n");
    }

    private void drawForceSpacedGraph() {
        System.out.println("Zaczynam algorytm siłowy");
        System.out.println("");
        new ForceAlgorithm(graph, svg).convertGraph();
        System.out.println("Zakonczylem dzialanie algorytmu silowego");
        System.out.println("Force-spaced graph ma " + graph.countNodes() + " wierzcholkow i " + graph.countEdges() + " krawedzi");

        System.out.println("Rozpoczynam rysowanie grafu force-spaced");
        drawGraphOnMap(graph, "force-spaced");
        System.out.println("Skonczylem rysowanie grafu force-spaced\n\n");
    }

    /**
     * Draws all maps that can be generated
     */
    public void drawAllMaps() {
        drawShapeMap();
        //  drawSchemeMap();

        drawDatabaseGraph(); // UWAGA - to rysowanie musi byc w takiej kolejnosci!
        drawGluedGraph();
        drawEdgeContractedGraph();
        drawForceSpacedGraph();
    }

    private void drawNodeText(MapNode n, Color c) {
        String formerColor = svg.getTextColor();
        svg.setTextColor(UsefulFunctions.parseColor(c));
        String s = n.getStructureName();
        s += ":" + n.getContainedStopsIds().size();
        Point p = UsefulFunctions.convertToPoint(normalizeCoordinates(LBC, RUC, n.getCoords()));
        Pair<Integer,Integer> offset = n.getTextOffset();
        p.x += offset.getST();
        p.y += offset.getND();
        svg.addText(p, s);
        svg.setTextColor(formerColor);
    }

    private void setDrawingNodeParameters(MapNode n) {
        if (n.getDrawingWidth() != 0) {
            svg.setCircleStrokeWidth(n.getDrawingWidth());

            svg.setEllipseStrokeWidth(n.getDrawingWidth());
        }
        if (n.getHoverWidth() != 0) {
            svg.setCircleStrokeWidthHover(n.getHoverWidth());

            svg.setEllipseStrokeWidthHover(n.getHoverWidth());
        }
        if (n.getHoverColor() != null) {
            svg.setCircleFillHover(UsefulFunctions.parseColor(n.getHoverColor()));
            svg.setCircleStrokeColorHover(UsefulFunctions.parseColor(n.getHoverColor()));

            svg.setEllipseColorHover(UsefulFunctions.parseColor(n.getHoverColor()));
            svg.setEllipseStrokeColorHover(UsefulFunctions.parseColor(n.getHoverColor()));
        }
        if (n.getColor() != null) {
            svg.setCircleFill(UsefulFunctions.parseColor(n.getColor()));
            svg.setCircleStrokeColor(UsefulFunctions.parseColor(n.getColor()));

              //  svg.setEllipseColor( UsefulFunctions.parseColor(n.getColor()) );
            //   svg.setEllipseStrokeColor( UsefulFunctions.parseColor(n.getColor()) );
        }
    }

    /**
     * Draws graph nodes
     *
     * @param graph Nodes from this graph will be drawn
     * @return return the same graph to enable chain call
     */
    private MapGraph drawGraphNodesOnMap(MapGraph graph) {
        Set<Integer> insignificantNodes = createInsignificantNodes(graph);
        for (MapNode n : graph.getNodes()) {
            setDrawingNodeParameters(n);

            int dW = MCSettings.getINITIAL_ROUTE_HIGHLIGHT_WIDTH()*getNodeDrawingWidthHighlightCoefficient(n) / 4;
            if (insignificantNodes.contains(n.getID())) {
                dW = 3;
            }

            if ((n.getContainedStopsIds().size() >= 4) || (insignificantNodes.contains(n.getID()) == false)) {
                addEllipse(UsefulFunctions.convertToPoint(normalizeCoordinates(LBC, RUC, n.getCoords())), 3*n.getWidth() +dW, 2*n.getHeight() + dW );
            } else if (n.countEdges() == 1 || (n.countEdges() < 4 && n.isContractable() == false)) {
                addEllipse(UsefulFunctions.convertToPoint(normalizeCoordinates(LBC, RUC, n.getCoords())), n.getWidth() +dW , n.getHeight() + dW );
            } else {
                addEllipse(UsefulFunctions.convertToPoint(normalizeCoordinates(LBC, RUC, n.getCoords())), n.getWidth() + dW, n.getHeight() + dW );
            }
            //drawNodeText(n, MCSettings.getTEXT_COLOR());
        }
        
        return graph;
    }

    

    private void setDrawingEdgeParameters(MapEdge e) {
        if (e.getHoverWidth() != 0) {
            svg.setPolylineWidthHover(e.getHoverWidth());
        }
        if (e.getDrawingWidth() != 0) {
            svg.setPolylineWidth(e.getDrawingWidth());
        }
        if (e.getColor() != null) {
            svg.setPolylineColor(UsefulFunctions.parseColor(e.getColor()));
        }
        if (e.getHoverColor() != null) {
            svg.setPolylineColorHover(UsefulFunctions.parseColor(e.getHoverColor()));
        }
    }

    private MapGraph drawGraphEdgesOnMap(MapGraph graph) {
        ArrayList<Point> polyline = new ArrayList<>();

        for (MapEdge e : graph.getEdges()) {
            polyline.clear();
            MapNode n1 = e.getEnds().getST();
            MapNode n2 = e.getEnds().getND();
            polyline.add(UsefulFunctions.convertToPoint(normalizeCoordinates(LBC, RUC, n1.getCoords())));
            polyline.add(UsefulFunctions.convertToPoint(normalizeCoordinates(LBC, RUC, n2.getCoords())));

            setDrawingEdgeParameters(e);

            addPolyline(polyline);

            drawEdgeText(e);
        }
        return graph;
    }

    // rysuje zadany graf do pliku svg
    public MapGraph drawGraphOnMap(MapGraph graph, String svgname) {
        createLBCandRUC(graph);

        svg.setFileName(initialSVGFileName + "_" + svgname);
        beginSVG();
        // svg.addImageLink( "background.jpg" );        

        drawGraphEdgesOnMap(graph);
        drawRoutesToHighlightOnGraph(graph);
        drawGraphNodesOnMap(graph);
        drawTextsOnMap(graph);

        endSVG();
        return graph;
    }
    
    /**
     * Draws text we want to show on map. This includes among the others structure names of nodes and route names of edges. This function is called after 
     * drawing edges and nodes to avoid text being shadowed by other node
     * @param graph 
     */
    private void drawTextsOnMap(MapGraph graph){
        drawEdgeTexts(graph);
        drawNodeTexts(graph);
    }
    
    private void drawEdgeTexts(MapGraph graph){
        
    }
    
    /**
     * Writes every information we want to write, concerned with the edge.
     *
     * @param edge Writes for given MapEdge
     */
    private void drawEdgeText(MapEdge edge) {

    }
    
    private void drawNodeTexts( MapGraph graph ){
        for( MapNode n : graph.getNodes() ){
            drawNodeText(n, MCSettings.getTEXT_COLOR());
        }
    }

    private Set<Integer> createInsignificantNodes(MapGraph graph) {
        Set<Integer> insNodes = new HashSet<>();
        for (MapNode n : graph.getNodes()) {
            insNodes.add(n.getID());
        }
        Map<String, ArrayList<GraphPath>> paths = new RoutePathCreator(graph).createRoutePaths();
        for (Map.Entry<String, ArrayList<GraphPath>> entry : paths.entrySet()) {
            for (GraphPath gp : entry.getValue()) {
                for (Integer d : gp.getPathSequence()) {
                    insNodes.remove(d);
                }
            }
        }
        return insNodes;
    }

    private void drawRoutesToHighlightOnGraph(MapGraph graph) {
        ArrayList<String> routes = MCSettings.getRoutesToHighlight();
        Map<String, ArrayList<GraphPath>> paths = new RoutePathCreator(graph).createRoutePaths();
        createHighlights(paths);

        for (Map.Entry<String, ArrayList<GraphPath>> entry : paths.entrySet()) {
            for (GraphPath gp : entry.getValue()) {
                ArrayList<Point> polyline = new ArrayList<>();
                if (gp.getPathSequence().size() < 2) {
                    continue;
                }

                for (int i = 1; i < gp.getPathSequence().size(); i++) {
                    MapNode nA = graph.getMapNodeByID(gp.getPathSequence().get(i - 1));
                    MapNode nB = graph.getMapNodeByID(gp.getPathSequence().get(i));

                    boolean reversed = false;
                    if (nA.getID() > nB.getID()) {
                        reversed = true;
                        MapNode temp = nA;
                        nA = nB;
                        nB = temp;
                    }

                    Pair<Float, Float> coordsA = UsefulFunctions.parsePairToFloat(normalizeCoordinates(LBC, RUC, nA.getCoords()));
                    Pair<Float, Float> coordsB = UsefulFunctions.parsePairToFloat(normalizeCoordinates(LBC, RUC, nB.getCoords()));

                    if (coordsA == null || coordsB == null) {
                        System.out.println("coordsA = " + coordsA + "\ncoordsB = " + coordsB);
                        System.out.println("nA = " + nA + "\nnB = " + nB);
                        break;
                    }

                    Pair<Float, Float> perpVec = UsefulFunctions.getNormalizedPerpendicularVector(coordsA, coordsB);
                    if (reversed) {
                        perpVec = UsefulFunctions.getNormalizedPerpendicularVector(coordsB, coordsA);
                       // perpVec.setST( -perpVec.getST() );
                        //  perpVec.setND( -perpVec.getND() );
                    }

                    /*if( (UsefulFunctions.getVectorLength(perpVec) < 0.999f) || (UsefulFunctions.getVectorLength(perpVec) > 1.001f) ){
                     System.out.println( "perpVec has length equal to " + UsefulFunctions.getVectorLength(perpVec) );
                     }*/
                    float x = coordsB.getST();
                    float deltax = leftHighlightOffset.get(new Pair<>(nA.getID(), nB.getID())) * perpVec.getST();

                    float y = coordsB.getND();
                    float deltay = leftHighlightOffset.get(new Pair<>(nA.getID(), nB.getID())) * perpVec.getND();

                    if (reversed) {
                        x = coordsA.getST();
                        deltax = rightHighlightOffset.get(new Pair<>(nA.getID(), nB.getID())) * perpVec.getST();
                        y = coordsA.getND();
                        deltay = rightHighlightOffset.get(new Pair<>(nA.getID(), nB.getID())) * perpVec.getND();
                    }

                    float c = coordsA.getST();
                    float d = coordsA.getND();
                    if (reversed) {
                        c = coordsB.getST();
                        d = coordsB.getND();
                    }

                    polyline.add(new Point(Math.round(c + deltax), Math.round(d + deltay)));
                    polyline.add(new Point(Math.round(x + deltax), Math.round(y + deltay)));

                    if (reversed) {
                        float val = rightHighlightOffset.get(new Pair<>(nA.getID(), nB.getID()));
                        val -= MCSettings.getINITIAL_ROUTE_HIGHLIGHT_WIDTH();
                        rightHighlightOffset.remove(new Pair<>(nA.getID(), nB.getID()));
                        rightHighlightOffset.put(new Pair<>(nA.getID(), nB.getID()), val);
                    } else {
                        float val = leftHighlightOffset.get(new Pair<>(nA.getID(), nB.getID()));
                        val -= MCSettings.getINITIAL_ROUTE_HIGHLIGHT_WIDTH();
                        leftHighlightOffset.remove(new Pair<>(nA.getID(), nB.getID()));
                        leftHighlightOffset.put(new Pair<>(nA.getID(), nB.getID()), val);
                    }

                }

                Color c = UsefulFunctions.getNextColor();
                while (c.equals(Color.WHITE) || c.equals(Color.BLACK)) {
                    c = UsefulFunctions.getNextColor();
                }
                svg.setPolylineWidth(MCSettings.getINITIAL_ROUTE_HIGHLIGHT_WIDTH() );
                svg.setPolylineColorHover(UsefulFunctions.parseColor(c));
                svg.setPolylineWidthHover(MCSettings.getINITIAL_ROUTE_HIGHLIGHT_HOVER_WIDTH());
                svg.setPolylineColor(UsefulFunctions.parseColor(c));
                addPolyline(polyline);

            }
        }
    }

    /**
     * Creates {@link #highlights} map. For each pair of nodes we determine the
     * number of highlighted routes between them
     *
     * @param paths
     */
    private void createHighlights(Map<String, ArrayList<GraphPath>> paths) {
        highlights.clear();
        leftHighlightOffset.clear();
        rightHighlightOffset.clear();
        for (Map.Entry<String, ArrayList<GraphPath>> entry : paths.entrySet()) {
            for (GraphPath gp : entry.getValue()) {
                for (int i = 1; i < gp.getPathSequence().size(); i++) {
                    int a = gp.getPathSequence().get(i);
                    int b = gp.getPathSequence().get(i - 1);
                    if (a > b) {
                        int temp = a;
                        a = b;
                        b = temp;
                    }
                    Pair<Integer, Integer> p = new Pair<>(a, b);

                    if (highlights.containsKey(p) == false) {
                        highlights.put(p, 0);
                    }

                    int val = highlights.get(p);
                    highlights.remove(p);
                    highlights.put(p, val + 1);
                }
            }
        }

        for (Map.Entry< Pair<Integer, Integer>, Integer> entry : highlights.entrySet()) {
            int offset = entry.getValue();
            float val;
            if (offset % 2 == 0) {
                offset /= 2;
                val = MCSettings.getINITIAL_ROUTE_HIGHLIGHT_WIDTH() * offset;
                val -= ((float) MCSettings.getINITIAL_ROUTE_HIGHLIGHT_WIDTH()) / 2;
                leftHighlightOffset.put(entry.getKey(), val);
                rightHighlightOffset.put(entry.getKey(), val);
            } else {
                offset /= 2;
                val = MCSettings.getINITIAL_ROUTE_HIGHLIGHT_WIDTH() * offset;
                leftHighlightOffset.put(entry.getKey(), val);
                rightHighlightOffset.put(entry.getKey(), val);
            }

        }
    }

    /**
     * For given map node we check what is the maximal number of highlighted
     * routes that visit this node
     *
     * @param n We check for the given node
     * @return returns the maximum number of highlighted routes between node n
     * and one of its neighbours
     */
    private int getNodeDrawingWidthHighlightCoefficient(MapNode n) {
        int M = 0;
        for (MapNode neigh : n.getNeighbours()) {
            int a = n.getID();
            int b = neigh.getID();
            int val = (highlights.get(new Pair<>(a < b ? a : b, a < b ? b : a)) == null) ? 0 : highlights.get(new Pair<>(a < b ? a : b, a < b ? b : a));
            if (val > M) {
                M = val;
            }
        }
        return M;
    }

    private void addPolyline(ArrayList<Point> list) {
        svg.addPolylinePlain(list);
    }

    private void addCircle(int x, int y, int r) {
        svg.addCirclePlain(x, y, r);
    }

    private void addCircle(Point p, int r) {
        addCircle((int) p.getX(), (int) p.getY(), r);
    }

    private void addEllipse(int x, int y, int width, int height) {
        svg.addEllipsePlain(x, y, width, height);
    }

    private void addEllipse(Point p, int width, int height) {
        DrawingModule.this.addEllipse((int) p.getX(), (int) p.getY(), width, height);
    }

    private String initialSVGFileName = MCSettings.getMapsDirectoryPath();
    private SVG svg = null;
    private MapGraph graph = new MapGraph();

    private Pair<Float, Float> RUC = null;
    private Pair<Float, Float> LBC = null;

    /**
     * {@link #highlights} is a map in which for each pair of nodes in a graph
     * there is number of routes between them to be highlighted
     */
    private Map< Pair<Integer, Integer>, Integer> highlights = new HashMap<>();
    private Map< Pair<Integer, Integer>, Float> leftHighlightOffset = new HashMap<>();
    private Map< Pair<Integer, Integer>, Float> rightHighlightOffset = new HashMap<>();

}
