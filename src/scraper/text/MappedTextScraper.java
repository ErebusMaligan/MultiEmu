package scraper.text;

import java.io.IOException;
import java.util.Map;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 9, 2015, 6:01:35 AM 
 */
public interface MappedTextScraper {
	public Map<String, String> getText( String input ) throws IOException;
}
