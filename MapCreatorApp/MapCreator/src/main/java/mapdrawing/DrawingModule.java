package mapdrawing;

import MCTemplates.Drawable;
import MCTemplates.Pair;
import gtfsstructures.*;
import gtfsstructures.localGtfsDatabase;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;

public class DrawingModule {

    public DrawingModule(SVG s) {
        svg = s;
        localGtfsDatabase.init();        
    }
    
    
    private void drawFragment( ArrayList<Shape> shapeById ){
        
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
    public void drawShapes(){
        Set<String> set = new HashSet<String>();        
        svg.beginSVG();
        
        ArrayList<Shape> allShapes = localGtfsDatabase.getAllShapes();
        ArrayList<Shape> shapeById = null;
                
        for( Shape s : allShapes ){            
            if( !set.contains( s.getShapeId() ) ){
                set.add( s.getShapeId() );
                shapeById = localGtfsDatabase.getAllShapesOfID( s.getShapeId() );
                               
                Pair< Pair<Float,Float>, Pair<Float,Float> > LBCRUC = getLBCandRUC( new ArrayList<Drawable>( shapeById ) );
                Pair<Float,Float> LBC = LBCRUC.getST();
                Pair<Float,Float> RUC = LBCRUC.getND();
                
                ArrayList<Integer> x = new ArrayList<>();
                ArrayList<Integer> y = new ArrayList<>(); // tego nie powinno byc - bedzie do czasu gdy Asia zrobic funkcje dodawania lini dla par
                
                
                for( Shape sh :shapeById ){
                    Pair<Integer,Integer> norm = normalizeCoordinates(LBC, RUC, sh.getCoords() );
                    x.add( norm.getST() );
                    y.add( norm.getND() );
                }               
                
                svg.addPolyline(x, y);
                
            }           
        }
        
        
        
        
        svg.endSVG();
    }
    
    
    

    

   

    private SVG svg = null;
   // private localGtfsDatabase database = new localGtfsDatabase(); // tego nie musi tutaj wogole byc poniewaz wszystko w lgdb jest statyczne
    private MapGraph graph = new MapGraph();

    
    
}
