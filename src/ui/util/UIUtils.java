package ui.util;

import gui.tab.SimpleColorTabbedPaneUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import state.MEProps;
import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 27, 2015, 5:58:50 AM 
 */
public class UIUtils {	
	
	public static void setTabUI( JTabbedPane tab ) {
		SimpleColorTabbedPaneUI tabUI = new SimpleColorTabbedPaneUI( MEProps.BACKGROUND, MEProps.FOREGROUND );
		Insets none = new Insets( 0, 0, 0, 0 );	
		tabUI.setContentBorderInsets( none );
		tabUI.setTabAreaInsets( none );
		tab.setUI( tabUI );
	}
	
	public static void setColors( Component c ) {
		c.setBackground( MEProps.BACKGROUND );
		c.setForeground( MEProps.FOREGROUND );
	}
	
	public static void invertColors( Component c ) {
		c.setBackground( MEProps.FOREGROUND );
		c.setForeground( MEProps.BACKGROUND );
	}
	
	public static void setTransparentColors( Component c ) {
		c.setBackground( MEProps.TRANSPARENT_BG );
		c.setForeground( MEProps.TRANSPARENT_FG );
		if ( c instanceof JComponent ) {
			( (JComponent)c ).setOpaque( false );
		}
		if ( c instanceof JPanel ) {
			c.setBackground( MEProps.FULL_TRANSPARENT );
		}
		if ( c instanceof JScrollPane ) {
			c.setBackground( MEProps.FULL_TRANSPARENT );
			( (JScrollPane)c ).getViewport().setBackground( MEProps.FULL_TRANSPARENT );
		}
	}
	
	public static void setColorsRecursive( Container in ) {
		UIUtils.setColors( in );
		for ( Component c : in.getComponents() ) {
			UIUtils.setColors( c );
			if ( c instanceof Container ) {
				setColorsRecursive( (Container)c );
			}
		}
	}
	
	public static void setTransparentColorsRecursive( Container in ) {
		UIUtils.setTransparentColors( in );
		for ( Component c : in.getComponents() ) {
			if ( c instanceof JScrollPane ) {
				UIUtils.setTransparentColors( ((JScrollPane)c).getViewport() );
				( (JScrollPane)c ).setBorder( BorderFactory.createLineBorder(  MEProps.FOREGROUND_DARKER ) );
			}
			UIUtils.setTransparentColors( c );
			if ( c instanceof Container ) {
				setTransparentColorsRecursive( (Container)c );
			}
		}
	}
	
	public static void setColors( Component... c ) {
		for ( Component a : c ) {
			setColors( a );
		}
	}
	
	public static void setTransparentColors( Component... c ) {
		for ( Component a : c ) {
			setTransparentColors( a );
		}
	}
	
	public static List<Component> getComponentsRecursive( Container comp ) {
		List<Component> ret = new ArrayList<>();
		for ( Component c : comp.getComponents() ) {
			ret.add( c );
			if ( c instanceof Container ) {
				ret.addAll( getComponentsRecursive( (Container)c ) );
			}
		}
		return ret;
	}
	
	public static boolean hasFocusRecursive( Container container ) {
		boolean hasFocus = false;
		for ( Component c : getComponentsRecursive( container ) ) {
			hasFocus = c.hasFocus();
			if ( hasFocus ) {
				break;
			}
		}
		return hasFocus;
	}
	
	public static void setButton( JButton b ) {
		b.setFont( MEProps.BUTTON_FONT );
		b.setUI( new GlowButtonUI() );
		b.setBorder( BorderFactory.createLineBorder( MEProps.FOREGROUND_DARKER ) );
		setColors( b );
	}
	
	public static void setLabel( JLabel l ) {
		l.setFont( MEProps.BUTTON_FONT );
		l.setBorder( BorderFactory.createLineBorder( MEProps.FOREGROUND ) );
		setColors( l );
	}
	
	public static JButton getButton( String text, Icon icon, ActionListener l ) {
		JButton b = new JButton( text, icon );
		if ( l != null ) {
			b.addActionListener( l );
		}
		GU.sizes( b, new Dimension( MEProps.BUTTON_WIDTH, MEProps.BUTTON_HEIGHT ) );
		UIUtils.setButton( b );
		return b;
	}
	
	public static Point getCenter( Component c, Component owner) {
		return new Point( owner.getLocation().x + ( owner.getWidth() / 2 ) - c.getWidth() / 2, owner.getLocation().y + ( owner.getHeight() / 2 ) - c.getHeight() / 2 );	
	}
}