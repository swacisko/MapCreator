/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcmapdrawing;

import mctemplates.Pair;
import com.sun.management.jmx.Trace;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author swacisko
 */
public class MapGraph {

    public MapGraph() {

    }

    public ArrayList<MapNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<MapNode> list) {
        nodes = list;
    }

    public ArrayList<MapEdge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<MapEdge> list) {
        edges = list;
    }

    // dodaje dana krawedz do grafu
    public void addMapEdge(MapEdge e) {
        edges.add(e);
        Pair<MapNode, MapNode> p = e.getEnds();
        p.getST().addMapEdge(e);
        p.getND().addMapEdge(e);
    }

    // dodaje krawedz, laczaca dwa wierzcholki o ID rownych ID1 oraz ID2
    public void addMapEdge(int id1, int id2) {
        MapEdge e = new MapEdge();
        MapNode n1 = getMapNodeByID(id1);
        MapNode n2 = getMapNodeByID(id2);
        if (n1 == null || n2 == null) {
            System.out.println("Nie ma wierzcholka o ID1 = " + id1 + "  lun id2 = " + id2 + "   --> w addMapEdge,  Nie dodaje krawedzi");
        } else {
            e.setEnds(new Pair<>(n1, n2));
            n1.addMapEdge(e);
            n2.addMapEdge(e);
            edges.add(e);
        }
    }

    // jezeli nie ma krawedzi o takim indeksie, to zwracam null
    public MapEdge getMapEdge(int index) {
        if (index >= edges.size()) {
            System.out.println("Zle indeksowanie w funkcji getMapEdge() w MapGraph, zwracam null");
            return null;
        } else {
            return edges.get(index);
        }
    }

    public MapEdge getMapEdgeByID(int id) {
        for (MapEdge e : edges) {
            if (e.getID() == id) {
                return e;
            }
        }
        System.out.println("Nie ma MapEdge o ID = " + id + "   w funkcji getMapEdgeByID w MapGraph, zwracam null");
        return null;
    }

    public void removeMapEdge(int index) {
        if (index >= edges.size()) {
            System.out.println("Zle indeksowanie w funkcji removeMapEdge() w MapGraph, nic nie usuwam");
            return;
        } else {
            removeMapEdgeByID(edges.get(index).getID());
        }
    }

    public void removeMapEdgeByID(int id) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getID() == id) {
                Pair<MapNode, MapNode> p = edges.get(i).getEnds();
                p.getST().removeMapEdgeByID(id);
                p.getND().removeMapEdgeByID(id);

                edges.remove(i);
                makeFreeID(id);
                return;
            }
        }

        System.out.println("Nie ma MapEdge o ID = " + id + ". Nic nie usuwam");
    }

    public void addMapNode(MapNode n) {
        nodes.add(n);
    }

    // jezeli nie ma krawedzi o takim indeksie, to zwracam null
    public MapNode getMapNode(int index) {
        if (index >= nodes.size()) {
            System.out.println("Zle indeksowanie w funkcji getMapNode w MapGraph(), zwracam null");
            return null;
        } else {
            return nodes.get(index);
        }
    }

    public MapNode getMapNodeByID(int id) {
        for (MapNode n : nodes) {
            if (n.getID() == id) {
                return n;
            }
        }
        System.out.println("Nie ma MapNode o ID = " + id + "   w funkcji getMapNodeByID w MapGraph, zwracam null");
        return null;
    }

    public void removeMapNode(int index) {
        if (index >= nodes.size()) {
            System.out.println("Zle indeksowanie w funkcji removeMapNode w MapGraph(), nic nie usuwam");
            return;
        } else {
            removeMapNodeByID(nodes.get(index).getID());
        }
    }

    public void removeMapNodeByID(int id) {
        for (int i = 0; i < nodes.size(); i++) { // usuwam wierzcholek o danym id z listy wierzcholkow grafu
            if (nodes.get(i).getID() == id) {
                nodes.remove(i);
                makeFreeID(id);
                break;
            }
        }

        ArrayList<MapEdge> copy = new ArrayList<>();
        for (MapEdge e : edges) { // usuwam z listy krawedzi grafu wszystkie krawedzie, ktorych co najmniej jeden koniec jest w wierzcholku o ID=id

            if (e.hasEndInMapNodeOfID(id)) {
                Pair<MapNode, MapNode> p = e.getEnds();
                p.getST().removeMapEdgeByID(e.getID());
                p.getND().removeMapEdgeByID(e.getID());
                makeFreeID(e.getID());
            } else {
                copy.add(e);
            }
        }
        edges = copy;

    }

    @Override
    public String toString() {
        String s = "nodes:\tsize = " + nodes.size() + "\n";
        for (int i = 0; i < nodes.size(); i++) {
            s += nodes.get(i).toString() + "\n";
        }
        s += "\nedges:\tsize = " + edges.size() + "\n";
        for (int i = 0; i < edges.size(); i++) {
            s += edges.get(i).toString() + "\n";
        }
        return s;
    }

    // funkcja pozwala testowac graf - dodawac i usuwac wierzcholki lub krawedzie
    public void testGraph() {

        while (true) {
            System.out.println("1. Dodaj wierzcholek\n2.Usun wierzcholek\n3.Dodaj krawedz\n4.Usun krawedz\n5.Wypisz graf\n9.Wyjdz");
            Scanner in = new Scanner(System.in);
            int ans = in.nextInt();

            switch (ans) {
                case 1: {
                    MapNode n = new MapNode();
                    addMapNode(n);
                    System.out.println("Dodalem wierzcholek o ID = " + n.getID());
                    break;
                }
                case 2: {
                    System.out.println("Podaj ID wierzcholka, ktory chcesz usunac z grafu:");
                    int a = in.nextInt();
                    removeMapNodeByID(a);
                    System.out.println("Wykonalem zadanie usuniecia wierzhcolka o id = " + a);

                    break;
                }
                case 3: {
                    System.out.println("Podaj dwa ID wierzcholkow, ktore maja zostac polaczone:");
                    int a = in.nextInt();
                    int b = in.nextInt();
                    addMapEdge(a, b);
                    System.out.println("Wykonalem prosbe dodania krawedz pomiedzy wierzcholkami o id rownych " + a + " oraz " + b);
                    break;
                }
                case 4: {
                    System.out.println("Podaj id krawedzi, ktora chcesz usunac:");
                    int a = in.nextInt();
                    removeMapEdgeByID(a);
                    System.out.println("Wykonalem operacje usuwania krawedzi z grafu");

                    break;
                }
                case 5: {
                    System.out.println(this);

                    break;
                }
                case 9: {
                    return;
                }
                default: {
                    System.out.println("Wybrales dupny numer. Wybierz inny.");
                }

            }

        }

    }

    //*******************************************************  STATIC BLOCK - ID generators
    public static int getFreeID() {
        int p = 1;
        while (unavailableIds.contains(p)) {
            p++;
        }
        unavailableIds.add(p);
        return p;
    }

    // funkcja usuwa id z unavailableIds
    public static void makeFreeID(int id) {
        unavailableIds.remove(id);
    }

    //******************************************************* END OF STATIC BLOCK
    private static Set<Integer> unavailableIds = new HashSet<>();

    private ArrayList<MapNode> nodes = new ArrayList<>();
    private ArrayList<MapEdge> edges = new ArrayList<>();

}
