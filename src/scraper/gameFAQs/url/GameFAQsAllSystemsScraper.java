package scraper.gameFAQs.url;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
 * Created: Jul 27, 2015, 12:38:17 AM 
 */
public class GameFAQsAllSystemsScraper implements URLScraper {
	@Override
	public List<URLNode> getURLs( String input ) throws IOException {
		Document doc = Jsoup.connect( "http://www.gamefaqs.com/systems.html" ).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
				.timeout( 1000 ).maxBodySize( 0 ).get();
		Elements ele = doc.select( ".span8 a[href]:not(:has(img))" );
		HashSet<URLNode> ret = new HashSet<>();  //no duplicates
		ele.forEach( e -> {
			ret.add( new URLNode( e.attr( "abs:href" ), e.text() ) );
		} );
		return new ArrayList<>( ret );
	}
}