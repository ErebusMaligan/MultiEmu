package ui.frame;

import java.awt.BorderLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import joystick.event.JoystickEvent;
import joystick.event.JoystickEventAdapter;
import state.MEState;
import statics.GU;
import ui.util.UIUtils;
import data.UIJoystickMapping;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 17, 2015, 5:13:51 AM 
 */
public class SouthBar extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Map<Integer, JButton> map;
	
	private Stack<Map<Integer, JButton>> prev = new Stack<>();
	
	private JPanel right = new JPanel();
	
	public SouthBar( MEState state ) {
		this.setLayout( new BorderLayout() );
		
		state.getJM().addListener( new JoystickEventAdapter() {
			@Override
			public void buttonReleased( JoystickEvent e ) {
				if ( UIUtils.hasFocusRecursive( SouthBar.this ) ) {
					if ( e.getJoystick().getNum() == UIJoystickMapping.SELECTED_JOYSTICK ) {
						JButton b = map.get( e.getButton() );
						if ( b != null ) {
							b.doClick();
						}
					}
				}
			}
		} );
		this.add( right, BorderLayout.EAST );
		UIUtils.setColorsRecursive( this );
		UIUtils.setColors( this );
	}
	
	public void blank() {
		Map<Integer, JButton> blank = new LinkedHashMap<>();
		JButton b = UIUtils.getButton( "", null, null );
		b.setEnabled( false );
		b.setBorder( null );
		blank.put( -1, b );
		setButtons( blank );
	}
	
	public void clearHistory() {
		prev.clear();
	}
	
	public void setButtons( Map<Integer, JButton> map ) {
		setButtons( map, false );
	}
	
	public void setButtons( Map<Integer, JButton> map, boolean internal ) {
		SwingUtilities.invokeLater( () -> {
			if ( !internal & this.map != null) {
				synchronized( prev ) {
					this.prev.push( this.map );
				}
			}
			right.removeAll();
			this.map = map;
			for ( JButton b : map.values() ) {
				right.add( b );
				GU.spacer( right );
			}
			if ( !map.isEmpty() ) {
				right.remove( right.getComponentCount() - 1 );
			}
			UIUtils.setColorsRecursive( this );
			UIUtils.setColors( this );
			right.revalidate();
			right.requestFocus();
			UIUtils.setTransparentColorsRecursive( this );
		} );
	}
	
	public void back() {
		synchronized ( prev ) {
			if ( !prev.isEmpty() ) {
				setButtons( prev.pop(), true );
			}
		}
	}
}