package scraper.url;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 12:32:11 AM 
 */
public class URLNode implements Comparable<URLNode> {
	
	private String text;
	
	private URL url;
	
	private String optional;
	
	public URLNode( String urlString, String text ) {
		this( urlString, text, null );
	}
	
	public URLNode( String urlString, String text, String optional ) {
		try {
			url = new URL( urlString );
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		}
		this.text = text;
		this.optional = optional;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the url
	 */
	public URL getURL() {
		return url;
	}
	
	@Override
	public String toString() {
		return text + " : " + url.toString();
	}
	
	public String getOptional() {
		return optional;
	}

	@Override
	public int compareTo( URLNode o ) {
		return o.text.compareToIgnoreCase( text );
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( text == null ) ? 0 : text.hashCode() );
		result = prime * result + ( ( url == null ) ? 0 : url.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		URLNode other = (URLNode)obj;
		if ( text == null ) {
			if ( other.text != null )
				return false;
		} else if ( !text.equals( other.text ) )
			return false;
		if ( url == null ) {
			if ( other.url != null )
				return false;
		} else if ( !url.equals( other.url ) )
			return false;
		return true;
	}
	
	
}