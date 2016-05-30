package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 16, 2015, 7:41:59 AM 
 */
public class MEProps {
	
	public static Color BACKGROUND = Color.BLACK;
	
	public static Color TRANSPARENT_BG = new Color( BACKGROUND.getRed(), BACKGROUND.getGreen(), BACKGROUND.getBlue(), 96 );
	
	public static Color FULL_TRANSPARENT = new Color( 0, 0, 0, 0 );
	
	public static Color FOREGROUND = Color.RED;
	
	public static Color FOREGROUND_DARKER = FOREGROUND.darker().darker();

	public static Color TRANSPARENT_FG = FOREGROUND;
	
	public static Font BUTTON_FONT = new JLabel().getFont().deriveFont( Font.PLAIN, 14 );
	
	public static Font LARGE_FONT = new JLabel().getFont().deriveFont( Font.PLAIN, 20 );
	
	public static int BUTTON_HEIGHT = 32;
	
	public static int BUTTON_WIDTH = GU.FIELD.width;
	
	public static int BUTTON_EXTRA_WIDE_WIDTH = GU.LONG.width;
	
	public static Image ART_IMAGE = new ImageIcon( "./resources/icons/Emulators.jpg" ).getImage();
//	public static Image ART_IMAGE = new ImageIcon( "./resources/icons/color.jpeg" ).getImage();
	
	public static String IMAGES_PATH = "./resources/images/";
	
	public static String SYSTEM_IMAGES_PATH = IMAGES_PATH + "systems";
	
	public static String DATA_PATH = "./resources/data";
	
	public static void createDirectories() {
		for ( String s : new String[] { IMAGES_PATH, SYSTEM_IMAGES_PATH, DATA_PATH } ) {
			File f = new File( s );
			if ( !f.exists() ) {
				f.mkdirs();
			}
		}
	}
}