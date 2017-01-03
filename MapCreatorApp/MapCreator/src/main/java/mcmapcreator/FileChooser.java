/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcmapcreator;

import java.io.File;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
            chooser.setDialogTitle( "Please select directory with GTFS data" );
            
            chooser.setToolTipText( "Please select GTFS folder" );
            chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY ); 
        }
          
        File file = new File("");
        if( (file = new File(file.getAbsolutePath() + File.separator + "GTFS" ) ).exists() && file.isDirectory() ){
            chooser.setSelectedFile(file);
        }
        int result = chooser.showOpenDialog(parent);
        if( result == JFileChooser.APPROVE_OPTION ){
            file = chooser.getSelectedFile();
            MCSettings.setGtfsDirectoryPath( file.getAbsolutePath() + File.separator );
            System.out.println( "Directory   " + ( file.getAbsolutePath() + File.separator ) + "   was chosen");
        }else{
            JOptionPane.showMessageDialog(parent, "Please press OK and select proper GTFS directory", "Choose GTFS directory", JOptionPane.WARNING_MESSAGE);
            chooseGtfsDirectory(parent);
        }   
    }
    
    /**
     * Function lets user to select location for the scheme to save. Shows dialog using JFileChooser.
     * @param parent parent component used by {@link JFileChooser#showSaveDialog(java.awt.Component)}.
     * @return returns true if user accepted, false if rejected or closed.
     */
    public static boolean saveSvgFile( JComponent parent ){
        if( chooser == null ){
            chooser = new JFileChooser( new File(".") );
        }
        chooser.setToolTipText( "Please select svg file" );
        chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );   
        chooser.setDialogTitle("Please select svg destination file");
        
        int result = chooser.showSaveDialog(parent);
        if( result == JFileChooser.APPROVE_OPTION ){
            File file = chooser.getSelectedFile();
            if( UsefulFunctions.existsFile( getFilenameWithoutExtension( file.getAbsolutePath() )  ) ){
                int res = JOptionPane.showConfirmDialog(parent, "Chosen file already exists. Do you want to continue?", "File name", JOptionPane.WARNING_MESSAGE);
                if( res != JOptionPane.YES_OPTION ){
                    return false;
                }
            }else{
                MCSettings.setSvgFileName( "" );
            }
            MCSettings.setMapsDirectoryPath( getFilenameWithoutExtension( file.getAbsolutePath() ) );
            System.out.println( "Directory   " + MCSettings.getMapsDirectoryPath() + "   was chosen");
            return true;
        }
        return false;
    }
    
    public static String getFilenameWithoutExtension( String fileName ){
        String noExtension = fileName;
        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            noExtension = fileName.substring(0,i);
        }
        return noExtension;
    }
    
    
    public static JFileChooser chooser = null;
}
