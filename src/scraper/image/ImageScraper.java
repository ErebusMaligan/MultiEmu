package scraper.image;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 26, 2015, 11:26:18 PM 
 */
public interface ImageScraper {
	public List<ImageWrapper<URL>> getImages() throws IOException;
}