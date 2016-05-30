package scraper.url;

import java.io.IOException;
import java.util.List;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 12:31:47 AM 
 */
public interface URLScraper {
	public List<URLNode> getURLs( String input ) throws IOException;
	
	public default List<URLNode> getURLs() throws IOException {  //this is just for shorter syntax, not requiring null to be input if null is desired
		return getURLs( null );
	}
}
