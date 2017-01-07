/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *  {@link MyDialog} is a class used to call JOptionPane dialogs. An instance of this object is created only once, but each time a dialog is shown, panels in this
 * object are being changed.
 * @author swacisko
 */
public class MyDialog extends JFrame implements ActionListener {
        
    public MyDialog(){    
        Toolkit t = Toolkit.getDefaultToolkit();
        try {
            //setIconImage(ImageIO.read(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dimension d = t.getScreenSize();
        setSize(d.width/3, (2*d.height/3));
        
        setLocationByPlatform(true);
        
        setLayout( new BorderLayout() );
        
        scroll = new JScrollPane(panel);
        add( scroll, BorderLayout.CENTER );
        
        okButton = new JButton( "OK" );
        okButton.addActionListener( this );
        add( okButton, BorderLayout.SOUTH );
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }
    
    public void switchToPanel( JPanel panel, String  title ){
        setTitle(title);
        remove(scroll);
        this.panel = panel;
        scroll = new JScrollPane(this.panel);
        add(scroll, BorderLayout.CENTER);
        revalidate();
        setVisible(true);
    }
    
    
    private JScrollPane scroll = null;
    private JPanel panel = null;   
    private JButton okButton = null;

    
    
}
