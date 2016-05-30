package ui.generic.joystick;

import joystick.event.JoystickEvent;
import joystick.event.JoystickEventAdapter;
import ui.generic.joystick.components.UDSCComponent;
import ui.util.UIUtils;
import data.UIJoystickMapping;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 2, 2015, 10:44:18 PM 
 */
public class UDSCJoystickListener extends JoystickEventAdapter {	
	
	private UDSCComponent udsc;
	
	public UDSCJoystickListener( UDSCComponent udsc ) {
		this.udsc = udsc;
	}
	
	@Override
	public void buttonReleased( JoystickEvent e ) {
		if ( UIUtils.hasFocusRecursive( udsc.getFocusContainer() ) ) {
			if ( e.getJoystick().getNum() == UIJoystickMapping.SELECTED_JOYSTICK ) {
				if ( e.getButton() == UIJoystickMapping.OK_BUTTON ) {
					udsc.select();
				} else if ( e.getButton() == UIJoystickMapping.CANCEL_BUTTON ) {
					udsc.cancel();
				} else if ( e.getButton() == UIJoystickMapping.UP_BUTTON ) {
					udsc.up();
				} else if ( e.getButton() == UIJoystickMapping.DOWN_BUTTON ) {
					udsc.down();
				}
			}
		}
	}
}