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
public class SelectedEdgePanel extends JPanel {
    
    public SelectedEdgePanel(){
        setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );     
        
    }
    
    
    private SelectedItems selectedItems = null;

    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    
     public ManagerFrame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(ManagerFrame parentFrame) {
        this.parentFrame = parentFrame;
    }
    
    private ManagerFrame parentFrame = null;

   
    
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;
}
