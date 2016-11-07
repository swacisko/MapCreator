/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gtfsstructures;

import java.util.Map;

/**
 *
 * @author swacisko
 */
public class GtfsStructure {
    
    @Override
    public String toString(){
        return data.toString();
    }
     
    public Map<String,String> getData(){ return data; }
    
    public void setData( Map<String,String> d ){ data = d; }
    
    private Map<String, String> data = null;
    
}
