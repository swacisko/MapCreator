package inz.mapcreator;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class InzFrame extends JFrame{
    
    public InzFrame(){
        setTitle("Projekt inżynierski - rysowanie rozkładu jazdy");
        Toolkit t = Toolkit.getDefaultToolkit();
        try {
            //setIconImage(ImageIO.read(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dimension d = t.getScreenSize();
        setSize(d.width, d.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        optionpanel = new OptionPanel( this );        
        //add(optionpanel);
        
        drawingpanel = new DrawingPanel();
        add( drawingpanel );
        
        
    }
    
    
    public Buttons getButtons(){
        return buttons;
    }
    
    public OptionPanel getOptionPanel(){
        return optionpanel;
    }
    
    public DrawingPanel getDrawingPanel(){
        return drawingpanel;
    }
    
    
    
    
    private OptionPanel optionpanel = null;
    private DrawingPanel drawingpanel = null;
    private Buttons buttons = new Buttons();
    
}
