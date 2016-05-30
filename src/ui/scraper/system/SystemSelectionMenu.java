package ui.scraper.system;

import java.io.IOException;
import java.util.Map;


import javax.swing.JDialog;
import javax.swing.JPanel;


import emudata.SystemData;
import scraper.gamesdb.text.GamesDBSystemInfoScraper;
import scraper.gamesdb.url.GamesDBAllSystemScraper;
import state.MEState;
import ui.generic.IndexedButtonSelectionDialog;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 9, 2015, 6:18:20 AM 
 */
public class SystemSelectionMenu extends IndexedButtonSelectionDialog {

	private static final long serialVersionUID = 1L;

	private Map<String, String> selection;
	
	public SystemSelectionMenu( MEState state, JDialog previous ) {
		super( state, previous );
	}
	
	protected JPanel createButtonPanel() {
		JPanel p = super.createButtonPanel();
		final GamesDBSystemInfoScraper info = new GamesDBSystemInfoScraper();
		try {
			new GamesDBAllSystemScraper().getURLs().forEach( u -> {
				p.add( createButton( u.getText(), e -> {
					try {
						selection = info.getText( u.getURL().toString() );
						selection.put( SystemData.REMOTE_ID, u.getOptional() );
						cancel();
					} catch ( Exception e1 ) {
						e1.printStackTrace();
					}
				} ) );
			} );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return p;
	}
	
	public Map<String, String> getSelection() {
		return selection;
	}
}