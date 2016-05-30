package ui.navigation;

import gui.layout.WrapLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import joystick.event.CompositeJoystickEventAdapter;
import joystick.event.JoystickEvent;
import joystick.event.JoystickEventAdapter;
import joystick.event.JoystickEventListener;
import state.MEProps;
import state.MEState;
import state.MEStrings;
import state.UII;
import statics.GU;
import ui.generic.IndexedButtonSelectionDialog;
import ui.menu.SystemOptionsMenu;
import ui.util.UIUtils;
import data.UIJoystickMapping;



/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 16, 2015, 1:23:30 AM 
 */
public class SystemNavigationDialog extends IndexedButtonSelectionDialog {

	private static final long serialVersionUID = 1L;
	
	public SystemNavigationDialog( MEState state, JDialog previous ) {
		super( state, previous );
	}
	
	@Override
	protected void init() {
		super.init();
		jl = new CompositeJoystickEventAdapter( Arrays.asList( new JoystickEventListener[] { jl, new JoystickEventAdapter() {
			@Override
			public void buttonReleased( JoystickEvent e ) {
				if ( UIUtils.hasFocusRecursive( SystemNavigationDialog.this ) ) {
					if ( e.getJoystick().getNum() == UIJoystickMapping.SELECTED_JOYSTICK ) {
						if ( e.getButton() == UIJoystickMapping.MENU_BUTTON ) {
							showMenu();
						}
					}
				}
			}
		} } ) );
	}
	
	protected void showMenu() {
//		System.out.println(  selector.getSelected().getActionCommand()  );
		new Thread( () -> new SystemOptionsMenu( state, state.getSystemManager().getByRemoteID( selector.getSelected().getActionCommand() ) ).setVisible( true ) ).start();
	}
	
	@Override
	protected void setSizing() {
		this.setPreferredSize( new Dimension( 9 * ( state.getFrame().getWidth() / 10 ), 9 * ( state.getFrame().getHeight() / 10 ) ) );  //the -2 is to account for the glue being added in the constructor after this panel is created
	}
	
	@Override
	protected Map<Integer, JButton> createButtonConfig() {
		Map<Integer, JButton> startup = new LinkedHashMap<>();
		startup.put( UIJoystickMapping.MENU_BUTTON, UIUtils.getButton( MEStrings.MENU, state.getUII().getIcon( UII.MENU ), e -> showMenu() ) );
		startup.put( UIJoystickMapping.OK_BUTTON, UIUtils.getButton( MEStrings.SELECT, state.getUII().getIcon( UII.OK ), e -> selector.getSelected().doClick() ) );
		startup.put( UIJoystickMapping.CANCEL_BUTTON, UIUtils.getButton( MEStrings.EXIT_APP, state.getUII().getIcon( UII.CANCEL ), e -> cancel() ) );
		return startup;
	}
	
	@Override
	protected JComponent createButton( String text, ActionListener l ) {
		JButton b = UIUtils.getButton( text, null, l );
		b.addFocusListener( focus );
		GU.sizes( b, new Dimension( MEProps.BUTTON_EXTRA_WIDE_WIDTH, MEProps.BUTTON_EXTRA_WIDE_WIDTH ) );
		b.setVerticalTextPosition( SwingConstants.BOTTOM );
		b.setHorizontalTextPosition( SwingConstants.CENTER );
		int s = 10;
		b.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( s, s, s, s ), b.getBorder() ) );
		b.setFont( MEProps.LARGE_FONT );
		traversal.addButton( b, count++ );
		return b;
	}
	
	@Override
	protected JPanel createButtonPanel() {
		JPanel p = super.createButtonPanel();
		p.setLayout( new WrapLayout( FlowLayout.LEFT ) );
		reload( p );
		return p;
	}
	
	private void reload( JPanel p ) {
		state.getSystemManager().getSystems().forEach( s -> {
//			for ( int x = 0; x < 20; x++ ) {  //this was just to test scrollbars, remove it later
				JButton b = (JButton)createButton( s.getName(), null );
				Image i = s.getFanart();
				b.setActionCommand( s.getRemoteID() );
				if ( i != null ) {
					b.setIcon( new ImageIcon( i.getScaledInstance( b.getPreferredSize().width, b.getPreferredSize().height * 2 / 3, Image.SCALE_SMOOTH ) ) );
				}
				p.add( b );
//			}
		} );
	}
	
	@Override
	public void cancel() {
		super.cancel();
		state.exit( this );
	}
}