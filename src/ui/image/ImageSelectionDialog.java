package ui.image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JDialog;
import javax.swing.ListCellRenderer;

import scraper.image.ImageScraper;
import scraper.image.ImageWrapper;
import scraper.image.URLImageCellRenderer;
import state.MEState;
import statics.FileUtils;
import ui.generic.GListSelectionMenu;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 13, 2015, 6:08:04 AM 
 */
public abstract class ImageSelectionDialog extends GListSelectionMenu<ImageWrapper<?>> {

	private static final long serialVersionUID = 1L;

	protected String dlFileName;
	
	protected ImageScraper[] scrapers;
	
	public ImageSelectionDialog( MEState state, JDialog previous, ImageScraper[] scrapers, ImageWrapper<?> current, String dlFileName ) {
		super( state, previous );
		this.current = current;
		this.dlFileName = dlFileName;
		this.scrapers = scrapers;
		loadList();
		setCurrentSelection( current );
		revalidate();
	}
	
	protected abstract void loadList();

	protected abstract String getDownloadPath();
	
	@Override
	protected void setCurrentSelection( ImageWrapper<?> current ) {
		this.current = current;
		//doesn't need to do anything else since it's not a changing/interactive list
	}

	@Override
	protected ListCellRenderer<ImageWrapper<?>> getRenderer() {
		return new URLImageCellRenderer();
	}
	
	public File downloadCurrent() throws IOException {
		File ret = null;
		if ( current != null ) {
			if ( current.getLoc() instanceof URL ) {
				String p = getDownloadPath();
				URL url = (URL)current.getLoc();
				p += "." + FileUtils.getSuffix( url.toString() );
				ret = new File( p );
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0" );
				try ( InputStream s = connection.getInputStream() ) {
					Files.copy( s, ret.toPath(), StandardCopyOption.REPLACE_EXISTING );
					System.out.println( "COPIED TO: " + ret.toPath() );
				}
			} else {
				ret = (File)current.getLoc();
			}
		}
		return ret;
	}
}