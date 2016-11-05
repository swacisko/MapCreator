package inz.mapcreator;

import javax.swing.JButton;



public class Buttons {    
    
    public static JButton getWelcomeButton(){
        JButton b = new JButton( "OPTION PANEL" );
        return b;
    }
    
    public void setMainFrame( InzFrame f ){
        mainframe = f;
    }
    
    public InzFrame getMainFrame(){  return mainframe; }
    
    private InzFrame mainframe = null;
}
