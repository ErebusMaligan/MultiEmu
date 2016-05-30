package ui.generic;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 1, 2015, 11:41:02 PM 
 */
public class IndexedButtonTraversalPolicy extends FocusTraversalPolicy {
	
	private Map<JButton, Integer> buttons = new LinkedHashMap<>();
	
	@Override
	public Component getComponentAfter( Container aContainer, Component aComponent ) {
		return getButtonForIndex( intCheck( buttons.get( (JButton)aComponent ) + 1 ) );
	}

	@Override
	public Component getComponentBefore( Container aContainer, Component aComponent ) {
		return getButtonForIndex( intCheck( buttons.get( (JButton)aComponent ) - 1 ) );
	}

	@Override
	public Component getFirstComponent( Container aContainer ) {
		return getButtonForIndex( intCheck( 0 ) );
	}

	@Override
	public Component getLastComponent( Container aContainer ) {
		return getButtonForIndex( intCheck( -1 ) );  //should auto roll around to last value
	}

	@Override
	public Component getDefaultComponent( Container aContainer ) {
		return getFirstComponent( aContainer );
	}
	
	private int intCheck( int i ) {
		int ret = i;
		if ( i == buttons.size() ) {
			ret = 0;
		} else if ( i < 0 ) {
			ret = buttons.size() - 1;
		}
		return ret;
	}
	
	public JButton getButtonForIndex( int i ) {
		JButton ret = null;
		if ( buttons.keySet().iterator().hasNext() ) {
			ret = buttons.keySet().iterator().next();
			for ( JButton b : buttons.keySet() ) {
				if ( buttons.get( b ).equals( i ) ) {
					ret = b;
					break;
				}
			};
		}
		return ret;
	}
	
	public void addButton( JButton b, int i ) {
		buttons.put( b, i );
	}
}