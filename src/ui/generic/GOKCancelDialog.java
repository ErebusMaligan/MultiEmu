package ui.generic;

import gui.panel.TransparentPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import state.MEProps;
import state.MEState;
import state.MEStrings;
import state.UII;
import statics.GU;
import ui.generic.joystick.LRSCJoystickListener;
import ui.generic.joystick.components.LRSCComponent;
import ui.util.UIUtils;
import data.UIJoystickMapping;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 16, 2015, 7:38:36 AM 
 */
public class GOKCancelDialog extends IndexedButtonSelectionDialog implements LRSCComponent {

	private static final long serialVersionUID = 1L;
	
	protected String message;
	
	public static final int OK = 1;
	
	public static final int CANCEL = 0;
	
	protected int result;
	
	protected JLabel msg;
	
	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public GOKCancelDialog( MEState state, String message, JDialog previous ) {
		super( state, previous );
		msg.setText( message );
	}
	
	@Override
	protected void init() {  //do not call super.init here, don't want the super class jl
		jl = new LRSCJoystickListener( this );
		msg = new JLabel();
		msg.setFont( MEProps.LARGE_FONT );
		JPanel p = new TransparentPanel();
		p.setLayout( new BoxLayout( p, BoxLayout.X_AXIS ) );
		p.add( msg, BorderLayout.CENTER );
		GU.hGlue2( p );
		this.add( p, BorderLayout.NORTH );
	}

	@Override
	protected void setSizing() {
		this.setPreferredSize( new Dimension( state.getFrame().getWidth() / 3, buttonHeight * 3 ) );
	}
	
	@Override
	protected JPanel createButtonPanel() {
		JPanel p = super.createButtonPanel();
		p.setLayout( new BoxLayout( p, BoxLayout.X_AXIS ) );
		p.add( createButton( MEStrings.OK, e -> {
			new Thread( () -> {
				result = OK;
				super.cancel();
			} ).start();
		} ) );
		p.add( createButton( MEStrings.CANCEL, e -> {
			new Thread( () -> {
				cancel();
			} ).start();
		} ) );
		return p;
	}
	
	@Override
	protected Map<Integer, JButton> createButtonConfig() {
		Map<Integer, JButton> menu = new LinkedHashMap<>();
		menu.put( UIJoystickMapping.LEFT_BUTTON, UIUtils.getButton( MEStrings.SEL_LEFT, state.getUII().getIcon( UII.LEFT ), e -> this.left() ) );
		menu.put( UIJoystickMapping.RIGHT_BUTTON, UIUtils.getButton( MEStrings.SEL_RIGHT, state.getUII().getIcon( UII.RIGHT ), e -> this.right() ) );
		menu.put( UIJoystickMapping.OK_BUTTON, UIUtils.getButton( MEStrings.SELECT, state.getUII().getIcon( UII.OK ), e -> this.select() ) );
		menu.put( UIJoystickMapping.CANCEL_BUTTON, UIUtils.getButton( MEStrings.EXIT_MENU, state.getUII().getIcon( UII.CANCEL ), e -> this.cancel() ) );
		return menu;
	}
	
	@Override
	public void cancel() {
		result = CANCEL;
		super.cancel();
	}
	
	public int getResult() {
		return result;
	}

	@Override
	public void left() {
		up();
	}

	@Override
	public void right() {
		down();
	}
}