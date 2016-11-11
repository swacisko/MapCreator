package mcmapcreator;

import mcgtfsstructures.GTFSInput;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import mcmapdrawing.DrawingModule;
import mcmapdrawing.SVG;


public class DrawingPanel extends JPanel{
    public DrawingPanel(){
                
        setLayout( new GridLayout(1,2) );
        
        JPanel leftpanel = new LeftPanel();
        add( leftpanel );
        
        
        textarea = new JTextArea( "Processed information" );
        textarea.setEditable(false);
        textarea.setFont(new Font( Font.DIALOG, Font.PLAIN, 22 ));
        
        scroll = new JScrollPane(textarea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
             
        //add( textarea, BorderLayout.CENTER );
        Border border = BorderFactory.createLineBorder(Color.GREEN.darker().darker(), 5);
        scroll.setBorder(border);

        
        add(scroll);
        
        setBackground(Color.red);
    }
    
    
    
    
    @Override
    public void paintComponent( Graphics g ){
        Graphics2D gr = (Graphics2D) g;
        
        
    }
    
    
    private JTextArea textarea = null;
    private JScrollPane scroll = null;
    
    //************************
    
    class LeftPanel extends JPanel{

           public LeftPanel(){
               setLayout( new GridLayout( 3,1 ) );
               JLabel label = new JLabel( "In the lower text field please enter name of file you want to process", SwingConstants.CENTER);
               label.setFont(new Font("Serif", Font.PLAIN, 20));
               
               
               add( label );
               
               textfield = new JTextField( "stops.txt" );
               textfield.setBackground(Color.LIGHT_GRAY);
               
               textfield.setForeground(Color.red.darker());
               
              // textfield.setForeground(Color.red);
               textfield.setFont( new Font( "SansSerif", Font.PLAIN, 30 ) );
               
               Border border = BorderFactory.createLineBorder(Color.GRAY.darker(), 5);
               textfield.setBorder(border);
               textfield.setHorizontalAlignment(JTextField.CENTER);
                
               
               add(textfield, BorderLayout.CENTER);
               addButton();
               
               
           }
           
           private void addButton(){
               Image image = null;
               try{
                   image = ImageIO.read( new File( "/home/swacisko/NetBeansProjects/Inzynierka/src/inzynierka/Images/bus.png" ) );
               }catch( Exception e ){
                   
               }                     
               button = new JButton( "   Click here to process data from selected gtfs file", new ImageIcon( image ) ); 
               
               button.setVerticalAlignment( SwingConstants.CENTER );
               button.setHorizontalTextPosition(SwingConstants.CENTER);
               button.setVerticalTextPosition( SwingConstants.NORTH );
               
               
               button.setBackground(Color.YELLOW.brighter().brighter());
               button.setFont(new Font( "Serif", Font.PLAIN, 20 ));
               
               button.addActionListener( new ActionListener() {

                   @Override
                   public void actionPerformed(ActionEvent e) {
                       textarea.setText("");
                       
                       ArrayList<String> stops = GTFSInput.processFile( textfield.getText() );
                       if( stops == null ) {
                           textarea.setText( "There is no file with such name!" );
                           return;
                       }
                       
                    //   DrawingModule module = new DrawingModule( new SVG() );
                     //  module.drawStopsFile( textfield.getText() );
                       
                       ArrayList<String> stopdata = null;
                       for( String  s : stops ){
                           stopdata = GTFSInput.processFileData(s);
                           
                           for( String str : stopdata ){
                               textarea.append(str);
                               textarea.append( System.lineSeparator() );
                           }
                           
                           textarea.append( System.lineSeparator() );
                           textarea.append( System.lineSeparator() );
                           //textarea.append( System.lineSeparator() );
                       }
                       
                   }
               } );
               
               
               add(button);
               
           }


           private JTextField textfield = null;
           private JButton button = null;
    };
    
    
    
}
