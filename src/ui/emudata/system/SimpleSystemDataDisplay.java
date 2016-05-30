package ui.emudata.system;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import state.MEProps;
import state.MEState;
import state.MEStrings;
import statics.GU;
import statics.GUIUtils;
import ui.util.UIUtils;
import emudata.SystemData;
import gui.entry.LabelEntry;
import gui.props.variable.StringVariable;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 9, 2015, 11:10:30 PM 
 */
public class SimpleSystemDataDisplay extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static Dimension[] paths;
	
	private SystemData data;
	
	private JTextArea t = new JTextArea();
	
	private StringVariable dev = new StringVariable( "" );
	
	private LabelEntry devE = new LabelEntry( MEStrings.DEVELOPER, dev );
	
	private StringVariable name = new StringVariable( "" );
	
	private LabelEntry nameE = new LabelEntry( MEStrings.SYS_NAME, name );
	
	private StringVariable lp = new StringVariable( "" );
	
	private LabelEntry lpE = new LabelEntry( MEStrings.LAUNCHER_PATH, lp, paths );
	
	public static void setParentWidth( MEState state ) {
		paths = new Dimension[] { GUIUtils.FIELD, new Dimension( state.getFrame().getWidth() / 2 - GUIUtils.FIELD.width - GUIUtils.SPACER.width, GUIUtils.FIELD.height ) };
	}
	
	public SimpleSystemDataDisplay( SystemData data ) {
		this.data = data;
		this.setLayout( new BorderLayout() );
		devE.setFonts( MEProps.LARGE_FONT );
		nameE.setFonts( MEProps.LARGE_FONT );
		lpE.setFonts( MEProps.LARGE_FONT );
		t.setFont( MEProps.LARGE_FONT );
		
		JPanel north = new JPanel( new BorderLayout() );
		north.add( new JScrollPane( t ), BorderLayout.CENTER );
		t.setEditable( false );
		t.setLineWrap( true );
		t.setWrapStyleWord( true );
		t.setRows( 10 );

		JPanel n = new JPanel();
		n.setLayout( new BoxLayout( n, BoxLayout.X_AXIS ) );
		n.add( nameE );
		GU.spacer( n );
		n.add( devE );
		GU.hGlue2( n );
		n.setBorder( BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) );
		north.add( n, BorderLayout.NORTH );
		
		this.add( north, BorderLayout.NORTH );
		
		JPanel south = new JPanel();
		south.setLayout( new BoxLayout( south, BoxLayout.Y_AXIS ) );
		south.add( lpE );
		GU.hGlue2( south );
		
		this.add( south, BorderLayout.SOUTH );
		
//		UIUtils.setColorsRecursive( this );
		UIUtils.setTransparentColorsRecursive( this );
		revalidate();
		reload();
	}
	
	public void reload() {
		t.setText( data.getDescription() );
		dev.fromString( data.getDeveloper() );
		devE.reload();
		name.fromString( data.getName() );
		nameE.reload();
		lp.fromString( data.getLauncherPath() );
		lpE.reload();
	}
}