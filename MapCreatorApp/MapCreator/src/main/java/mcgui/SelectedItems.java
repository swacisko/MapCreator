/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import mcgraphs.MapEdge;
import mcgraphs.MapGraph;
import mcgraphs.MapNode;
import mcmapdrawing.MapLegend;
import mcmapdrawing.RouteEndGroup;

/**
 * {@link SelectedItems} objects store data of (almost) all non-settings editable elements. (e.g. graph nodes, edges, route ends, selected options in GUI).
 * @author swacisko
 */
public class SelectedItems{

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
    
    /**
     * 
     * @return returns true if {@link #glueNodeEnabled} is set, false otherwise.
     */
     public boolean isGlueNodeEnabled() {
        return glueNodeEnabled;
    }

    public void setGlueNodeEnabled(boolean glueNodeEnabled) {
        this.glueNodeEnabled = glueNodeEnabled;
    }

    public Map<Integer, RouteEndGroup> getRouteEnds() {
        return routeEnds;
    }

    public void setRouteEnds(Map<Integer, RouteEndGroup> routeEnds) {
        this.routeEnds = routeEnds;
    }
    
    /**
     * This function clears {@link #routeEnds} list. Only elements within this list will be drawn as the ends of highlighted routes.
     */
    public void clearRouteEnds(){
        routeEnds.clear();
    }
    
    public Point getAlignmentBeg() {
        return alignmentBeg;
    }

    public void setAlignmentBeg(Point AlignmentBeg) {
        this.alignmentBeg = AlignmentBeg;
    }
    
    public Point getAlignmentEnd() {
        return alignmentEnd;
    }

    public void setAlignmentEnd(Point alignmentEnd) {
        this.alignmentEnd = alignmentEnd;
    }
    
    /**
     * 
     * @return returns {@link #layoutMouseAlignementMode}, 0 is for "do nothing", 1 is for "horizontal alignment", 2 is for "vertical alignment".
     */
    public int getLayoutMouseAlignementMode() {
        return layoutMouseAlignementMode;
    }

    public void setLayoutMouseAlignementMode(int layoutMouseAlignementMode) {
        this.layoutMouseAlignementMode = layoutMouseAlignementMode;
    }
    
    /**
     * 
     * @return return the {@link #legend} element.
     */
    public MapLegend getLegend() {
        return legend;
    }

    public void setLegend(MapLegend legend) {
        this.legend = legend;
    }
    
    private MapGraph graph = null;
    private MapLegend legend = null;
    
    private MapNode selectedNode1 = null;
    private MapEdge selectedEdge = null;

    /**
     * This value is true if i can move the node
     */
    private boolean movableNode = false;
    private boolean edgeSelection = false;
    /**
     * If the value of this variable is set, then user may select node, to which earlier selected node will be glued.
     */
    private boolean glueNodeEnabled = false;

   
    
    private int layoutMouseAlignementMode = 0; 
    private Point alignmentBeg = null;
    private Point alignmentEnd = null;

       

    private Map<Integer, RouteEndGroup> routeEnds = new HashMap<>();

    
}
