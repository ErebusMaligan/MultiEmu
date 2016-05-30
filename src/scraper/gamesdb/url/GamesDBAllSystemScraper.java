package scraper.gamesdb.url;

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
 * Created: Aug 9, 2015, 5:19:54 AM 
 */
public class GamesDBAllSystemScraper implements URLScraper {
	
	@Override
	public List<URLNode> getURLs( String input ) throws IOException {
		Document doc = Jsoup.connect( "http://thegamesdb.net/api/GetPlatformsList.php" ).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").timeout( 5000 ).maxBodySize( 0 ).get();
		Elements ele = doc.select( "platform" );
//		String base = doc.select( "basePlatformUrl").text();
		HashSet<URLNode> ret = new HashSet<>();  //no duplicates
		ele.forEach( e -> {
			String id = e.select( "id" ).get( 0 ).text();
			String name = e.select( "name" ).get( 0 ).text();
			ret.add( new URLNode(  "http://thegamesdb.net/api/GetPlatform.php?id=" + id, name, id ) );
		} );
		return new ArrayList<>( ret );
	}
}