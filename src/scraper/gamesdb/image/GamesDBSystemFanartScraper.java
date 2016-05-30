package scraper.gamesdb.image;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import scraper.image.ImageScraper;
import scraper.image.ImageWrapper;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 9, 2015, 6:05:53 AM 
 */
public class GamesDBSystemFanartScraper implements ImageScraper {

	private String id;
	
	public GamesDBSystemFanartScraper( String id ) {
		this.id = id;
	}
	
	@Override
	public List<ImageWrapper<URL>> getImages() throws IOException {
		Document doc = Jsoup.connect( "http://thegamesdb.net/api/GetPlatform.php?id=" + id ).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").timeout( 5000 ).maxBodySize( 0 ).get();
		Elements ele = doc.select( "baseimgurl" );
		String path = ele.get( 0 ).text();
		ele = doc.select( "original" );
		List<ImageWrapper<URL>> ret = new ArrayList<>();
		ele.forEach( e -> {
			try {
				ret.add( new ImageWrapper<URL>( new URL( path + e.text() ) ) );
			} catch ( Exception e1 ) {
				e1.printStackTrace();
			}
		} );
		return ret;
	}
}