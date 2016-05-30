package scraper.google;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import scraper.image.ImageScraper;
import scraper.image.ImageWrapper;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 26, 2015, 11:43:31 PM 
 */
public class GoogleImageScraper implements ImageScraper {

	private String input;
	
	public GoogleImageScraper( String input ) {
		this.input = input;
	}
	
	@Override
	public List<ImageWrapper<URL>> getImages() throws IOException {
		Document doc = Jsoup.connect( "https://www.google.com/search?tbm=isch&gws_rd=ssl&q=" + input ).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
				.timeout( 1000 ).maxBodySize( 0 ).get();
		Elements ele = doc.select( "div.rg_di img" );
		List<String> imageURL = new ArrayList<>();
		ele.forEach( e -> {
			Attributes abs = e.attributes();
			String s = abs.get( "data-src" );
			if ( s != null && !s.equals( "" ) ) {
				imageURL.add( s );
			}
		} );
		List<ImageWrapper<URL>> images = new ArrayList<>();
		for ( String i : imageURL ) {
			images.add( new ImageWrapper<URL>( new URL( i ) ) );
		}
		return images;
	}
}