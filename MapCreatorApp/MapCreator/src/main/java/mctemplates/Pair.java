/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctemplates;

/**
 *
 * @author swacisko
 */
public class Pair<F, S> {
    private F first; //first member of pair
    private S second; //second member of pair

    
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setST(F first) {
        this.first = first;
    }

    public void setND(S second) {
        this.second = second;
    }

    public F getST() {
        return first;
    }

    public S getND() {
        return second;
    }
}