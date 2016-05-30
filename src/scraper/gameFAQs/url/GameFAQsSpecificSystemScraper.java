package scraper.gameFAQs.url;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import scraper.url.URLNode;
import scraper.url.URLScraper;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 1:32:44 AM 
 */
public class GameFAQsSpecificSystemScraper implements URLScraper {
	
	@Override
	public List<URLNode> getURLs( String input ) throws IOException {
		Document doc = Jsoup.connect( input ).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
				.timeout( 1000 ).maxBodySize( 0 ).get();
		Elements ele = doc.select( ".span8 a[href]:contains(HOME)" );
		List<URLNode> ret = new ArrayList<>();  //no duplicates
		ele.forEach( e -> {
			ret.add( new URLNode( e.attr( "abs:href" ), e.text() ) );
		} );
		return ret;
	}
}