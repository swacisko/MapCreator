/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcmapcreator;

import java.io.File;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import mctemplates.MCSettings;
import mctemplates.UsefulFunctions;

/**
 *
 * @author swacisko
 */
public class FileChooser {
    
    public static void chooseGtfsDirectory( JComponent parent ){
        if( chooser == null ){
            chooser = new JFileChooser( new File(".") );
            File file = new File("");
            if( (file = new File(file.getAbsolutePath()+"/GTFS") ).exists() && file.isDirectory() ){
                chooser.setSelectedFile(file);
            }
        }
        chooser.setToolTipText( "Please select GTFS folder" );
        chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );   
        
        int result = chooser.showOpenDialog(parent);
        if( result == JFileChooser.APPROVE_OPTION ){
            File file = chooser.getSelectedFile();
            MCSettings.setGtfsDirectoryPath( file.getAbsolutePath() + "/" );
            System.out.println( "Directory   " + file.getAbsolutePath() + "   was chosen");
        }        
    }
    
    public static void saveSvgFile( JComponent parent ){
        if( chooser == null ){
            chooser = new JFileChooser( new File(".") );
        }
        chooser.setToolTipText( "Please select svg file" );
        chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );   
        
        int result = chooser.showSaveDialog(parent);
        if( result == JFileChooser.APPROVE_OPTION ){
            File file = chooser.getSelectedFile();
            if( UsefulFunctions.existsFile( file.getAbsolutePath() ) ){
                MCSettings.setSvgFileName("");
            }else{
                //MCSettings.setSvgFileName( file.getName() );
                MCSettings.setSvgFileName( "" );
            }
            MCSettings.setMapsDirectoryPath( file.getAbsolutePath() );
            System.out.println( "Directory   " + file.getAbsolutePath() + "   was chosen");
        }
    }
    
    
    public static JFileChooser chooser = null;
}
