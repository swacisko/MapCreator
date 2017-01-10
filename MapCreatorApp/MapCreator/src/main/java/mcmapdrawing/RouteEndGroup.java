/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcmapdrawing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import mctemplates.MCSettings;
import mctemplates.Pair;

/**
 * This class represents a set of routes. Instances of this class are used to store data for each node in the graph about routes with an end in that node.
 * @author swacisko
 */
public class RouteEndGroup {

    public RouteEndGroup(int nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * This node is the end of routes with ids in {@link #routeIds}.
     */
    private int nodeId = -1;

    /**
     * This is the offset of left upper corner of route ends 'diagram'. The
     * offset is relative to {@link #node}.
     */
    private Pair<Integer, Integer> offset = MCSettings.getINITIAL_ROUTE_END_GROUP_OFFSET();
    private int columns = 0;
    private int singleSquareSize = MCSettings.getINITIAL_SINGLE_SQUARE_SIZE();
    private ArrayList<String> routeIds = new ArrayList<>();

    /**
     * Leaves only one route id for each route (there will be no duplicates).
     */
    public void makeUniqueRouteIds(){
        Set<String> zb = new HashSet( routeIds );
        routeIds.clear();
        routeIds.addAll(zb);
    }
    
    /**
     * 
     * @return returns number of different routes with end in node with {@link #nodeId}
     */
    public int getNumnberOfRouteEnds(){
        return routeIds.size();
    }

    public void addRouteEnd(String routeId) {
        routeIds.add(routeId);
    }

    public boolean containsRouteEnd(String routeId) {
        return routeIds.contains(routeId);
    }

    public void removeRouteEnd(String routeId) {
        routeIds.remove(routeId);
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public Pair<Integer, Integer> getOffset() {
        return offset;
    }

    public void setOffset(Pair<Integer, Integer> offset) {
        this.offset = offset;
    }

    /**
     *
     * @return returns number of columns (variable {@link #columns}) if greater than 0 or
     * calculates it, by (int) Math.round( Math.ceil( Math.sqrt( routeIds.size() ) ) ) otherwise.
     */
    public int getColumns() {
        if (columns > 0) {
            return columns;
        } else {
            return (int) Math.round(Math.ceil(Math.sqrt(routeIds.size())));
        }
    }
    
    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getSingleSquareSize() {
        return singleSquareSize;
    }

    public void setSingleSquareSize(int singleSquareSize) {
        this.singleSquareSize = singleSquareSize;
    }

    public ArrayList<String> getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(ArrayList<String> routeIds) {
        this.routeIds = routeIds;
    }

}
