package ui.generic.joystick.components;

import java.awt.Container;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 12, 2015, 12:11:23 AM 
 */
public interface LRSCComponent {
	
	public void left();
	
	public void right();
	
	public void select();
	
	public void cancel();
	
	public Container getFocusContainer();

}