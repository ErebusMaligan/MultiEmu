package scraper.text;

import java.io.IOException;
import java.util.List;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 12:17:16 AM 
 */
public interface TextScraper {
	public List<String> getText( String input ) throws IOException;
}