package ui.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.ListCellRenderer;

import state.MEState;
import state.MEStrings;
import ui.generic.GListSelectionMenu;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 31, 2015, 9:14:22 PM 
 */
public class GFileSelectionMenu extends GListSelectionMenu<File> {

	private static final long serialVersionUID = 1L;
	
	private File previous = null;
	
	private List<File> lf = null;

	private boolean directoryOnly;

	/**
	 * @param owner
	 * @param state
	 * @param message
	 */
	public GFileSelectionMenu( MEState state, JDialog previous, boolean directoryOnly ) {
		super( state, previous );
		this.directoryOnly = directoryOnly;
		setCurrentSelection( null );
	}
	
	@Override
	protected void setCurrentSelection( File f ) {
		current = f;
		lf = new ArrayList<>();
		previous = f == null ? null : f.getParentFile();
		lf.add( previous );
		if ( f == null ) {
			lf.addAll( Arrays.asList( File.listRoots() ) );
		} else {
			if ( f.isDirectory() ) {
				if ( directoryOnly ) {
					File[] files = f.listFiles();
					if ( files != null ) {
						Arrays.asList( files ).forEach( file -> { 
							if ( file.isDirectory() ) {
								lf.add( file );
							}
						} );
					}
				} else {
					lf.addAll( Arrays.asList( current.listFiles() ) );
				}
			}
		}
		msg.setText( f == null ? MEStrings.ROOT : f.getAbsolutePath() );
		model = new DefaultListModel<>();
		for ( File z : lf ) {
			model.addElement( z );
		}
		list.setModel( model );
		setSelection( 0 );
		this.revalidate();
	}
	
	@Override
	protected ListCellRenderer<File> getRenderer() {
		return new GFileListCellRenderer();
	}
}