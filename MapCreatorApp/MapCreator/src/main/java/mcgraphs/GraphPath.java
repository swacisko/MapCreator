/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgraphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * This structure represents a path in the graph. It is jus a sequence of
 * {@link #MapNode} ids, nothing more
 *
 * @author swacisko
 */
public class GraphPath {

    public GraphPath() {
    }

    @Override
    public boolean equals(Object oth) {
        if ((oth instanceof GraphPath) == false) {
            return false;
        }
        GraphPath p = (GraphPath) oth;
        if (pathSequence.size() != p.getPathSequence().size()) {
            return false;
        }

        int L = pathSequence.size();

        boolean identical = true;
        for (int i = 0; i < L; i++) {
            if (pathSequence.get(i).equals(p.getPathSequence().get(L - 1 - i)) == false) {
                identical = false;
                break;
            }
        }
        if (identical) {
            return true;
        }

        Collections.reverse(pathSequence);
        identical = true;
        for (int i = 0; i < L; i++) {
            if (pathSequence.get(i).equals(p.getPathSequence().get(L - 1 - i)) == false) {
                identical = false;
                break;
            }
        }
        if (identical) {
            return true;
        }

        //  System.out.println( "Paths " + toString() + " and " + p + " are not identical" );
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        int VAL = 0;
        for (Integer d : pathSequence) {
            VAL += d;
        }
        hash = 67 * hash + VAL;
        return hash;
    }

    /**
     * This is a list of node ids in graph in the sequence in which they occur
     * on the path. The beginning and end of the path are yet not distinguished.
     */
    private ArrayList<Integer> pathSequence = new ArrayList<>();

    public void addToPath(int id) {
        pathSequence.add(id);
    }

    public ArrayList<Integer> getPathSequence() {
        return pathSequence;
    }

    public void setPathSequence(ArrayList<Integer> pathSequence) {
        this.pathSequence = pathSequence;
    }

    public boolean isEmpty() {
        return pathSequence.size() == 0;
    }

    public int getPathBegValue() {
        if (pathSequence.isEmpty()) {
            return -1;
        }
        return pathSequence.get(0);
    }

    public int getPathEndValue() {
        if (pathSequence.isEmpty()) {
            return -1;
        }
        return pathSequence.get(pathSequence.size() - 1);
    }

    @Override
    public String toString() {
        String s = "";
        for (Integer d : pathSequence) {
            s += d + " ";
        }
        return s;
    }

}
