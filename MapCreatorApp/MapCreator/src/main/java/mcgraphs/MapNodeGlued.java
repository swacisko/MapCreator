/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgraphs;

import java.util.ArrayList;

/**
 *
 * @author swacisko
 */
public class MapNodeGlued extends MapNode {
    
    public MapNodeGlued(){
        
    }
    
    
    public String getStopIdByIndex( int index ){
        if( index < 0 || index >= containedStopsIds.size() ){
            System.out.println( "Zle indeksowanie w getStopIdByIndex, GluedNode, zwracam null" );
            return null;
        }
        else return containedStopsIds.get(index);
    }
    
    public boolean containsStopOfId( String id ){
        for( String s : containedStopsIds ){
            if( s.equals( "id" ) ) return true;
        }
        return false;
    }
    
    public void addContainedStopId( String id ){
        containedStopsIds.add(id);
    }
    
    public void setContainedStopsIds( ArrayList<String> l ){
        containedStopsIds = l;
    }
    
    public ArrayList<String> getContainedStopsIds(){
        return containedStopsIds;
    }
    
    
    
    private ArrayList<String> containedStopsIds = new ArrayList<>(); // lista id przystankow, ktore sa zawarte w tym wierzcholku mapowym
    
}
