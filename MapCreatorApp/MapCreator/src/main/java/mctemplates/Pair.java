/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctemplates;

import java.util.Objects;


/**
 *
 * @author swacisko
 */
public class Pair<F, S> {
    private F first; //first member of pair
    private S second; //second member of pair

    
    @Override
    public boolean equals( Object o ){
        if( !(o instanceof Pair) ) return false;
        Pair p = (Pair)o;
        return ( first.equals( p.getST() ) ) && ( second.equals( p.getND() ) );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.first);
        hash = 89 * hash + Objects.hashCode(this.second);
        return hash;
    }
    
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
    
    @Override
    public String toString(){
        return "(" + getST() + "," + getND() + ")";
    }
}