package ui.menu;

import javax.swing.JPanel;

import emudata.SystemData;
import state.MEState;
import state.MEStrings;
import ui.generic.IndexedButtonSelectionDialog;
import ui.scraper.system.SystemDefinitionDialog;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 20, 2015, 3:37:34 AM 
 */
public class SystemOptionsMenu extends IndexedButtonSelectionDialog {

	private static final long serialVersionUID = 1L;
	
	public SystemOptionsMenu( MEState state, SystemData sys ) {
		super( state, null, sys );
	}
	
	protected JPanel createButtonPanel() {
		JPanel p = super.createButtonPanel();
		p.add( createButton( MEStrings.ADD_SYSTEM, e -> showDialog( new SystemDefinitionDialog( state, this, null ), null ) ) );
		if ( additionalParams != null ) {
			if ( additionalParams[ 0 ] != null ) {
				p.add( createButton( MEStrings.EDIT_SYSTEM, e -> showDialog( new SystemDefinitionDialog( state, this, (SystemData)additionalParams[ 0 ] ), null ) ) );
			}
		}
		return p;
	}
}