/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.util.HashMap;
import java.util.Map;
import mcgraphs.MapEdge;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mcmapdrawing.RouteEndGroup;

/**
 *
 * @author swacisko
 */
public class SelectedItems {

    public SelectedItems() {

    }

    /**
     *
     * @return return first selected node. To change node's data we have to
     * select it beforehand.
     */
    public MapNode getSelectedNode1() {
        return selectedNode1;
    }

    public void setSelectedNode1(MapNode selectedNode1) {
        this.selectedNode1 = selectedNode1;
    }

    public MapEdge getSelectedEdge() {
        return selectedEdge;
    }

    public void setSelectedEdge(MapEdge selectedEdge) {
        this.selectedEdge = selectedEdge;
    }

    /**
     *
     * @return returns graph we are dealing with.
     */
    public MapGraph getGraph() {
        return graph;
    }

    public void setGraph(MapGraph graph) {
        this.graph = graph;
    }

    /**
     *
     * @return return true if i can move the node
     */
    public boolean isMovableNode() {
        return movableNode;        
    }

    public void setMovableNode(boolean moveOption) {
        this.movableNode = moveOption;
        if( movableNode == true ){
            edgeSelection = false;
        }
    }
    
    /**
     * 
     * @return returns true if i am in {@link SelectedEdgePanel} and want to select edge. Otherwise return false, and i can select nodes
     */
    public boolean isEdgeSelection() {
        return edgeSelection;
    }

    public void setEdgeSelection(boolean edgeSelection) {
        this.edgeSelection = edgeSelection;
        if( edgeSelection == true ){
            movableNode = false;
        }
    }
    
    public boolean isNodeSelection(){
        return !isEdgeSelection();
    }
    

    public Map<Integer, RouteEndGroup> getRouteEnds() {
        return routeEnds;
    }

    public void setRouteEnds(Map<Integer, RouteEndGroup> routeEnds) {
        this.routeEnds = routeEnds;
    }
    
    private MapGraph graph = null;

    private MapNode selectedNode1 = null;
    private MapEdge selectedEdge = null;

    /**
     * This value is true if i can move the node
     */
    private boolean movableNode = false;
    private boolean edgeSelection = false;
    

    private Map<Integer, RouteEndGroup> routeEnds = new HashMap<>();

    
}
