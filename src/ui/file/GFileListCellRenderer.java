package ui.file;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import state.MEProps;
import ui.util.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 1, 2015, 9:55:26 PM 
 */
public class GFileListCellRenderer implements ListCellRenderer<File>{

	private DefaultListCellRenderer renderer = new DefaultListCellRenderer();
	
	@Override
	public Component getListCellRendererComponent( JList<? extends File> list, File value, int index, boolean isSelected, boolean cellHasFocus ) {
		Component c = renderer.getListCellRendererComponent( list, index == 0 ? ".." : value, index, isSelected, cellHasFocus );
		if ( isSelected ) {
			if ( c instanceof JComponent ) {
				( (JComponent)c ).setOpaque( true );
			}
			UIUtils.invertColors( c );
		} else {
			if ( c instanceof JComponent ) {
				UIUtils.setTransparentColors( c );
			}
			UIUtils.setColors( c );
		}
		c.setFont( MEProps.LARGE_FONT );
		return c;
	}

}