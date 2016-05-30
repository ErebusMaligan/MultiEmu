package ui.generic.joystick;

import joystick.event.JoystickEvent;
import joystick.event.JoystickEventAdapter;
import ui.generic.joystick.components.LRSCComponent;
import ui.util.UIUtils;
import data.UIJoystickMapping;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 2, 2015, 10:44:18 PM 
 */
public class LRSCJoystickListener extends JoystickEventAdapter {	
	
	private LRSCComponent lrsc;
	
	public LRSCJoystickListener( LRSCComponent lrsc ) {
		this.lrsc = lrsc;
	}
	
	@Override
	public void buttonReleased( JoystickEvent e ) {
		if ( UIUtils.hasFocusRecursive( lrsc.getFocusContainer() ) ) {
			if ( e.getJoystick().getNum() == UIJoystickMapping.SELECTED_JOYSTICK ) {
				if ( e.getButton() == UIJoystickMapping.OK_BUTTON ) {
					lrsc.select();
				} else if ( e.getButton() == UIJoystickMapping.CANCEL_BUTTON ) {
					lrsc.cancel();
				} else if ( e.getButton() == UIJoystickMapping.LEFT_BUTTON ) {
					lrsc.left();
				} else if ( e.getButton() == UIJoystickMapping.RIGHT_BUTTON ) {
					lrsc.right();
				}
			}
		}
	}
}