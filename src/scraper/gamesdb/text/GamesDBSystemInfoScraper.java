package scraper.gamesdb.text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import scraper.text.MappedTextScraper;
import emudata.SystemData;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 9, 2015, 6:05:53 AM 
 */
public class GamesDBSystemInfoScraper implements MappedTextScraper {
	
	@Override
	public Map<String, String> getText( String input ) throws IOException {
		Document doc = Jsoup.connect( input ).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").timeout( 5000 ).maxBodySize( 0 ).get();
		Map<String, String> ret = new HashMap<>();
		setData( doc, "Platform", SystemData.NAME, 1, ret );
		setData( doc, "overview", SystemData.DESCRIPTION, 0, ret );
		setData( doc, "developer", SystemData.DEVELOPER, 0, ret );
		return ret;
	}
	
	/**
	 * Convenience method for auto checking bounds and data validity
	 */
	private void setData( Document doc, String search, String tag, int index, Map<String, String> ret ) {
		Elements e = null;
		e = doc.select( search );
		if ( !e.isEmpty() ) {
			if ( e.size() > index ) {
				ret.put( tag, StringEscapeUtils.unescapeHtml4( e.get( index ).text() ) );
			}
		}
	}
}