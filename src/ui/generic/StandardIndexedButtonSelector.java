package ui.generic;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 3, 2015, 12:08:57 AM 
 */
public class StandardIndexedButtonSelector {

	protected JButton selected;
	
	protected Container container;
	
	public StandardIndexedButtonSelector( Container container ) {
		this.container = container;
	}
	
	public void setSelected( JButton selected ) {
		this.selected = selected;
	}
	
	public void previous() {
		selected = (JButton)container.getFocusTraversalPolicy().getComponentBefore( container, selected );
		selected.requestFocusInWindow();
		scrollCenter();
	}
	
	public void next() {
		selected = (JButton)container.getFocusTraversalPolicy().getComponentAfter( container, selected );
		selected.requestFocusInWindow();
		scrollCenter();
	}

	public void select() {
		( (JButton)selected ).doClick();
	}	
	
	private void scrollCenter() {
		( (JPanel)selected.getParent() ).scrollRectToVisible( selected.getBounds() );
	}
	
	public JButton getSelected() {
		return selected;
	}
}