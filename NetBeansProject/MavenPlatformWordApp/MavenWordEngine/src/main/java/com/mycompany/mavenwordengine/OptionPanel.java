package com.mycompany.mavenwordengine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OptionPanel extends JPanel{
    public OptionPanel( InzFrame frame ){
        mainframe = frame;
        
        Toolkit t = Toolkit.getDefaultToolkit();       
        Dimension d = t.getScreenSize();
        setSize(d.width, d.height);
        
        
        setLayout( new GridLayout(2,1) );
    //    addButtons();   
        
    }
    
    private void addButtons(){
        welcomebutton = Buttons.getWelcomeButton();
        add( welcomebutton );
        repaint();
    }
    
    private void drawWelcome( Graphics2D gr ){
        String s = "Welcome to option panel!";
        gr.setFont(  new Font( Font.SANS_SERIF,Font.BOLD, 20 ));
        gr.drawString(s, HEIGHT+100, WIDTH+100 );
    }
    
    @Override
    public void paintComponent( Graphics g ){
        Graphics2D gr = (Graphics2D) g;
        drawWelcome(gr);
        
    }
    
    
    private JButton welcomebutton = null;
    private InzFrame mainframe = null;
    
    
}
