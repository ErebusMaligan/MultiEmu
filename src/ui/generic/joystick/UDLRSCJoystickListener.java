package ui.generic.joystick;

import joystick.event.JoystickEvent;
import joystick.event.JoystickEventAdapter;
import ui.generic.joystick.components.UDLRSCComponent;
import ui.util.UIUtils;
import data.UIJoystickMapping;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 2, 2015, 10:44:18 PM 
 */
public class UDLRSCJoystickListener extends JoystickEventAdapter {	
	
	private UDLRSCComponent udlrsc;
	
	public UDLRSCJoystickListener( UDLRSCComponent udlrsc ) {
		this.udlrsc = udlrsc;
	}
	
	@Override
	public void buttonReleased( JoystickEvent e ) {
		if ( UIUtils.hasFocusRecursive( udlrsc.getFocusContainer() ) ) {
			if ( e.getJoystick().getNum() == UIJoystickMapping.SELECTED_JOYSTICK ) {
				if ( e.getButton() == UIJoystickMapping.OK_BUTTON ) {
					udlrsc.select();
				} else if ( e.getButton() == UIJoystickMapping.CANCEL_BUTTON ) {
					udlrsc.cancel();
				} else if ( e.getButton() == UIJoystickMapping.UP_BUTTON ) {
					udlrsc.up();
				} else if ( e.getButton() == UIJoystickMapping.DOWN_BUTTON ) {
					udlrsc.down();
				} else if ( e.getButton() == UIJoystickMapping.LEFT_BUTTON ) {
					udlrsc.left();
				} else if ( e.getButton() == UIJoystickMapping.RIGHT_BUTTON ) {
					udlrsc.right();
				}
			}
		}
	}
}