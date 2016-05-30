package scraper.image;

import java.awt.Component;
import java.io.File;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import statics.ImageUtils;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Apr 30, 2014, 11:54:49 PM 
 */
public class URLImageCellRenderer implements ListCellRenderer<ImageWrapper<?>> {

	private DefaultListCellRenderer renderer = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent( JList<? extends ImageWrapper<?>> list, ImageWrapper<?> value, int index, boolean isSelected, boolean cellHasFocus ) {
		JLabel c = (JLabel)renderer.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
		Object loc = value.getLoc();
		if ( loc != null ) {
			c.setText( index > 0 ? "Image " + ( index + 1 ) : "Current Image" + ( ( loc instanceof URL ) ? ( (URL)loc ).toString() : ( (File)loc ).getAbsolutePath() ) );
		}
		c.setIcon( new ImageIcon( ImageUtils.getScaledImage( value.getImage(), 200, 150 ) ) );
		return c;
	}
}