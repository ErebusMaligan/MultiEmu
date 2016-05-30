package state;

import icon.DefaultIconLoader;

import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 17, 2015, 5:20:28 AM 
 */
public class UII extends DefaultIconLoader {

	public static String PATH = "./resources/icons/";
	
	public static String OK = "OKButton";
	
	public static String CANCEL = "CancelButton";
	
	public static String MENU = "MenuButton";
	
	public static String LEFT = "Left";
	
	public static String RIGHT = "Right";
	
	public static String UP = "Up";
	
	public static String DOWN = "Down";

	public UII() {
		names = new String[] { OK, CANCEL, MENU, LEFT, RIGHT, UP, DOWN };
		init();
	}
	
	protected void init() {
		icons = new HashMap<String, Icon>();
		for ( String s : names ) {
			icons.put( s, new ImageIcon( PATH + s + extension ) );
		}
	}
}