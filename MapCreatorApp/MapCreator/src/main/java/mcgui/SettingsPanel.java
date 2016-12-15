/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import javax.swing.JPanel;

/**
 *
 * @author swacisko
 */
public class SettingsPanel extends JPanel{
    
    public SettingsPanel(){
         setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );  
    }
    
    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    
    private SelectedItems selectedItems = null;  
    
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;
}
