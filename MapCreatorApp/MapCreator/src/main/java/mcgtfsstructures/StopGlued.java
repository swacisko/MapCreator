/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgtfsstructures;

import java.util.Map;

/**
 *
 * @author swacisko
 */
public class StopGlued extends Stop {

    public StopGlued(Map<String, String> d) {
        super(d);
    }
    
    
    
    private int gluedNodeId = -1;
    
}
