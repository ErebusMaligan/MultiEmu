package ui.util;

import gui.button.CustomButtonUI;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

import state.MEProps;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 2, 2015, 12:40:12 AM 
 */
public class GlowButtonUI extends CustomButtonUI {
	
	@Override
	protected void paintStandard( Graphics2D g2d, JComponent c ) {
		Rectangle r = new Rectangle( 0, 0, c.getWidth(), c.getHeight() );
		g2d.setColor( c.getBackground() );
		g2d.fill( r );
		drawBorder( g2d, r );
	}
	
	
	@Override
	protected void paintFocus( Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect ) {
		Graphics2D g2d = (Graphics2D)g;
		int i = 3;
		g2d.setStroke( new BasicStroke( i, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, i, new float[] { i }, i ) );
		g2d.setPaint( MEProps.FOREGROUND );
		Rectangle r = b.getVisibleRect();
		drawBorder( g2d, new Rectangle( r.x + 1, r.y + 1, r.width - 1, r.height - 1 ) );
	}
}