package ui.image;

import java.io.IOException;

import javax.swing.JDialog;

import scraper.image.ImageScraper;
import scraper.image.ImageWrapper;
import state.MEProps;
import state.MEState;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 13, 2015, 6:08:04 AM 
 */
public class SystemImageSelectionDialog extends ImageSelectionDialog {

	private static final long serialVersionUID = 1L;
	
	public SystemImageSelectionDialog( MEState state, JDialog previous, ImageScraper[] scrapers, ImageWrapper<?> current, String dlFileName ) {
		super( state, previous, scrapers, current, dlFileName );
	}
	
	protected void loadList() {
		model.addElement( current );
		try {
			for ( ImageScraper scraper : scrapers ) {
				scraper.getImages().forEach( i -> model.addElement( i ) );
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		list.setModel( model );
	}

	@Override
	protected String getDownloadPath() {
		return MEProps.SYSTEM_IMAGES_PATH + "/" + dlFileName;
	}
}