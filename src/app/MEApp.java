package app;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import state.MEState;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 16, 2015, 7:02:25 AM 
 */
public class MEApp {
	
	public static void main( String[] args ) {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
			System.err.println( "Critical JVM Failure!" );
			e.printStackTrace();
		}
		new MEState();
	}
}