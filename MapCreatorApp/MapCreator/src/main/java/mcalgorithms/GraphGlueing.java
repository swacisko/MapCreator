/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcalgorithms;

import mcgraphs.MapGraph;

/**
 *
 * @author swacisko
 *
 *
 */
// ten algorytm skleja nam graf zadany poprzez wszystkie przystanki i polaczenia w graf, w ktorym te same przystanki sa ze soba utozsamiane
public class GraphGlueing {

    public GraphGlueing() {

    }

    public MapGraph getGluedGraph(MapGraph graph) {
        this.graph = graph;

        return resGraph;

    }

    public void setGraph(MapGraph graph) {
        this.graph = graph;
    }

    public void setResGraph(MapGraph resGraph) {
        this.resGraph = resGraph;
    }

    public MapGraph getGraph() {
        return graph;
    }

    public MapGraph getResGraph() {
        return resGraph;
    }

    private MapGraph resGraph = null;
    private MapGraph graph = null;
}
