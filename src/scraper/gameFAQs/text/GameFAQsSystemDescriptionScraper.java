package scraper.gameFAQs.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import scraper.text.TextScraper;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 4:46:44 AM 
 */
public class GameFAQsSystemDescriptionScraper implements TextScraper {

	@Override
	public List<String> getText( String input ) throws IOException {
		Document doc = Jsoup.connect( input ).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
				.timeout( 1000 ).maxBodySize( 0 ).get();
		Elements ele = doc.select( ".span8 .desc" );
		List<String> ret = new ArrayList<>();  //no duplicates
		ele.forEach( e -> {
			ret.add( e.html() );
		} );
		
		ele = doc.select( ".span4 .body li:contains(Release)" );
		ele.forEach( e -> {
			ret.add( e.text() );
		} );
		
		ele = doc.select( ".span4 .body li:contains(Also Known As)" );
		ele.forEach( e -> {
			ret.add( e.text() );
		} );
		
		doc = Jsoup.connect( input + "/data" ).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
		.timeout( 1000 ).maxBodySize( 0 ).get();

		ele = doc.select( ".span8 .body a[href]" );
		if ( !ele.isEmpty() ) {
			ret.add( "Developer: " + ele.get( 0 ).text() );
		}
		return ret;
	}
}