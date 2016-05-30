package ui.frame;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 18, 2015, 3:16:26 AM 
 */
public class ArtPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel l;
	
	public ArtPanel( ) {
		l = new JLabel( "" );
		l.setOpaque( true );
		this.setLayout( new BorderLayout() );
		this.add( l, BorderLayout.CENTER );
	}
	
	public void setImage( Image i ) {
		l.setIcon( new ImageIcon( i.getScaledInstance( this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH ) ) );
	}
}