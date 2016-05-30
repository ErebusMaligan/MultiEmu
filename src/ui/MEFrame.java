package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import state.MEProps;
import state.MEState;
import ui.frame.ArtPanel;
import ui.navigation.SystemNavigationDialog;
import ui.util.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 16, 2015, 6:58:03 AM 
 */
public class MEFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private ArtPanel art;
	
	private MEState state;
	
	public MEFrame( MEState state ) {
		this.state = state;
		UIUtils.setColors( this.getContentPane() );
		this.setSize( 800, 600 );
		this.setExtendedState( JFrame.MAXIMIZED_BOTH );
		this.setUndecorated( true );
		this.setLayout( new BorderLayout() );
		art = new ArtPanel();
		this.add( art, BorderLayout.CENTER );
	}
	
	public void setVisible( boolean visible ) {
		super.setVisible( visible );
		SwingUtilities.invokeLater( () -> {
			art.setImage( MEProps.ART_IMAGE );
			SystemNavigationDialog nav = new SystemNavigationDialog( state, null );
			new Thread( () -> {  nav.setVisible( true ); } ).start();
			this.revalidate();
		} );
	}
}