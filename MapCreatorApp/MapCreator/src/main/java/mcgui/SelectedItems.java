/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import mcgraphs.MapGraph;
import mcgraphs.MapNode;

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

    /**
     *
     * @return returns second selected node. Selecting an edge we have to select
     * two nodes. This function return the second one
     */
    public MapNode getSelectedNode2() {
        return selectedNode2;
    }

    public void setSelectedNode2(MapNode selectedNode2) {
        this.selectedNode2 = selectedNode2;
    }

    public MapNode getSelectedEdge() {
        return selectedEdge;
    }

    public void setSelectedEdge(MapNode selectedEdge) {
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
        return isMovableNode;
    }

    public void setMovableNode(boolean moveOption) {
        this.isMovableNode = moveOption;
    }

    private MapGraph graph = null;

    private MapNode selectedNode1 = null;
    private MapNode selectedNode2 = null;
    private MapNode selectedEdge = null;

    /**
     * This value is true if i can move the node
     */
    private boolean isMovableNode = false;

}
