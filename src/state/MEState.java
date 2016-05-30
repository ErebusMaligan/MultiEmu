package state;

import javax.swing.JDialog;

import joystick.JoystickManager;
import ui.MEFrame;
import ui.generic.GOKCancelDialog;
import emudata.SystemManager;
import gui.dialog.OKCancelDialog;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 16, 2015, 7:12:02 AM 
 */
public class MEState {
	
	private JoystickManager jm;
	
	private MEProps props;
	
	private UII uii;
	
	private MEFrame frame;

	public static enum NAV { ROOT, SYSTEM, ENTRY };
	
	private NAV nav = NAV.ROOT;
	
	private SystemManager sys;
	
	public MEState() {
		MEProps.createDirectories();
		sys = new SystemManager();
		jm = new JoystickManager();
		props = new MEProps();
		uii = new UII();
		frame = new MEFrame( this );
		frame.setVisible( true );
		startup();
	}

	public JoystickManager getJM() {
		return jm;
	}
	
	public MEProps getProps() {
		return props;
	}
	
	public UII getUII() {
		return uii;
	}
	
	public MEFrame getFrame() {
		return frame;
	}
	
	private void startup() {
		jm.startPolling();
	}
	
	public NAV getNAV() {
		return nav;
	}
	
	public void setNAV( NAV nav ) {
		this.nav = nav;
	}
	
	public SystemManager getSystemManager() {
		return sys;
	}
	
	public void exit( JDialog previous ) {
		new Thread( () -> {
			previous.setVisible( false );
			GOKCancelDialog d = new GOKCancelDialog( this, MEStrings.CLOSING_APP, previous );
			d.setVisible( true );
			if ( OKCancelDialog.OK == d.getResult() ) {
				previous.dispose();
				jm.stopPolling();
				jm.destroy();
				frame.dispose();
			}
		} ).start();
	}
}