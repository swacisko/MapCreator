/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author swacisko
 */
public class SettingsPanel extends JPanel{
    
    public SettingsPanel(){
         setSize( DEFAULT_WIDTH, DEFAULT_HEIGHT );  
         
         colorSettingButton = new JButton();
         colorSettingButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 
             }
         });
         
         
         
         mainRoutesSettingsButton = new JButton();
         
         
    }
    
    
    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SelectedItems selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    
    private SelectedItems selectedItems = null;  
    
    
    
    
    
    private JButton colorSettingButton = null;
    private JButton mainRoutesSettingsButton = null;
    
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 600;
}
