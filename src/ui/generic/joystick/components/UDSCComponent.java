package ui.generic.joystick.components;

import java.awt.Container;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 2, 2015, 10:41:01 PM 
 */
public interface UDSCComponent {

	public void up();
	
	public void down();
	
	public void select();
	
	public void cancel();
	
	public Container getFocusContainer();
}