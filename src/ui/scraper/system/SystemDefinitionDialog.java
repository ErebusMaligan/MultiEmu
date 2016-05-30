package ui.scraper.system;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;

import scraper.gamesdb.image.GamesDBSystemFanartScraper;
import scraper.google.GoogleImageScraper;
import scraper.image.ImageScraper;
import scraper.image.ImageWrapper;
import state.MEState;
import state.MEStrings;
import ui.emudata.system.SimpleSystemDataDisplay;
import ui.file.GFileSelectionMenu;
import ui.generic.GOKCancelDialog;
import ui.generic.IndexedButtonSelectionDialog;
import ui.image.SystemImageSelectionDialog;
import emudata.SystemData;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 9, 2015, 10:29:02 PM 
 */
public class SystemDefinitionDialog extends IndexedButtonSelectionDialog {

	private static final long serialVersionUID = 1L;

	private SystemData sys;
	
	private SimpleSystemDataDisplay ssd;
	
	private boolean edit = false;
	
	public SystemDefinitionDialog( MEState state, JDialog previous, SystemData sys ) {
		super( state, previous, sys );
	}
	
	@Override
	public void init() {
		super.init();
		SimpleSystemDataDisplay.setParentWidth( state );
		if ( additionalParams == null || additionalParams[ 0 ] == null ) {
			sys = new SystemData();
		} else {
			edit = true;
			sys = (SystemData)additionalParams[ 0 ];
		}
		ssd = new SimpleSystemDataDisplay( sys );
		this.add( ssd, BorderLayout.NORTH );
	}
	
	@Override
	protected void setSizing() {
		this.setPreferredSize( new Dimension( state.getFrame().getWidth() / 2, state.getFrame().getHeight() / 2 ) );
	}
	
	@Override
	protected JPanel createButtonPanel() {
		JPanel p = super.createButtonPanel();
		
		p.add( createButton( MEStrings.GET_INFO, e -> {
			SystemSelectionMenu m = new SystemSelectionMenu( state, this );
			showDialog( m, () -> {
				Map<String, String> map = m.getSelection();
				if ( map != null ) {
					sys.setDescription( map.get( SystemData.DESCRIPTION ) );
					sys.setDeveloper( map.get( SystemData.DEVELOPER ) );
					sys.setName( map.get( SystemData.NAME ) );
					sys.setRemoteID( map.get( SystemData.REMOTE_ID ) );
					reload();
				}
			} );
		} ) );
		
		p.add( createButton( MEStrings.SELECT_LAUNCHER_PATH, e -> {
			GFileSelectionMenu m = new GFileSelectionMenu( state, this, false );
			showDialog( m, () -> {
				if ( m.getResult() == GOKCancelDialog.OK && m.getCurrent() != null ) {
					sys.setLauncherPath( m.getCurrent().getAbsolutePath() );
					reload();
				}
			} );
		} ) );
		
		p.add( createButton( MEStrings.SELECT_ROMS_PATH, e -> {
			GFileSelectionMenu m = new GFileSelectionMenu( state, this, true );
			showDialog( m, () -> {
				if ( m.getResult() == GOKCancelDialog.OK && m.getCurrent() != null ) {
					sys.setRomPath( m.getCurrent().getAbsolutePath() );
					reload();
				}
			} );
		} ) );
		
		p.add( createButton( MEStrings.SELECT_CONSOLE_FANART, e -> {
			SystemImageSelectionDialog m = new SystemImageSelectionDialog( state, this, new ImageScraper[] { new GamesDBSystemFanartScraper( sys.getRemoteID() ), new GoogleImageScraper( sys.getName() ) }, 
					new ImageWrapper<File>( sys.getFanPath() == null ? null : new File( sys.getFanPath() ) ), sys.getName() );
			showDialog( m, () -> {
				if ( m.getResult() == GOKCancelDialog.OK ) {
					try {
						File f = m.downloadCurrent();
						sys.setFanPath( f == null ? null : f.getAbsolutePath() );
					} catch ( Exception e1 ) {
						e1.printStackTrace();
					}
					reload();
				}
			} );
		} ) );

		p.add( createButton( MEStrings.DEF_COMPLETE, e -> {
			if ( !edit ) {
				state.getSystemManager().addSystem( sys );
			}
			cancel();
		} ) );

		
		return p;
	}
	
	private void reload() {
		ssd.reload();
	}
}