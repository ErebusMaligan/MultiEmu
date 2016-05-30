package scraper.image;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 26, 2015, 11:27:49 PM 
 */
public class ImageWrapper<T> {
	
	private T loc = null;
	
	private Image image = null;
	
	public ImageWrapper( T loc ) {
		try {
			this.loc = loc;
			if ( loc != null ) {
				loadImage();
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void loadImage() throws IOException {
		if ( loc instanceof File ) {
			image = ImageIO.read( (File)loc );			
		} else if ( loc instanceof URL ) {
			URL url = (URL)loc;
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0" );
			image = ImageIO.read( connection.getInputStream() );
		}
	}

	/**
	 * @return the url or file
	 */
	public T getLoc() {
		return loc;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
}