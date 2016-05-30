package ui.generic;

import gui.panel.TransparentPanel;
import gui.scroll.TransparentScrollPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import joystick.event.JoystickEventListener;
import state.MEProps;
import state.MEState;
import state.MEStrings;
import state.UII;
import statics.GU;
import ui.frame.SouthBar;
import ui.generic.joystick.UDSCJoystickListener;
import ui.generic.joystick.components.UDSCComponent;
import ui.util.UIUtils;
import data.UIJoystickMapping;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 9, 2015, 6:21:22 AM 
 */
public abstract class IndexedButtonSelectionDialog extends JDialog implements UDSCComponent {

	private static final long serialVersionUID = 1L;

	protected IndexedButtonTraversalPolicy traversal = new IndexedButtonTraversalPolicy();
	
	protected int count = 0;
	
	protected JoystickEventListener jl;
	
	protected StandardIndexedButtonSelector selector = new StandardIndexedButtonSelector( this );
	
	protected MEState state;
	
	protected Map<Integer, JButton> buttonConfig;
	
	protected int spacer = 2;
	
	protected int buttonHeight = ( MEProps.BUTTON_HEIGHT + ( 2 * spacer ) + 10 );  //i don't know where the extra +10 comes from... but it is required
	
	protected JPanel button;
	
	protected SouthBar bar;
	
	protected JDialog previous;
	
	protected Component previouslySelected = null;
	
	protected Object[] additionalParams;
	
	protected FocusListener focus = new FocusListener() {	
		@Override
		public void focusLost( FocusEvent e ) { previouslySelected = e.getComponent();  }
		
		@Override
		public void focusGained( FocusEvent e ) {}
	};
	
	public IndexedButtonSelectionDialog( MEState state, JDialog previous, Object... additionalParams ) {
		super( state.getFrame(), null, true );
		this.additionalParams = additionalParams;
		this.previous = previous;
		this.state = state;
		this.buttonConfig = createButtonConfig();
		this.setLayout( new BorderLayout() );
		this.setUndecorated( true );
		this.getRootPane().setBorder( BorderFactory.createLineBorder( MEProps.FOREGROUND_DARKER ) );
		this.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosed( WindowEvent e ) {
				state.getJM().removeListener( jl );
			}
		} );
		this.setFocusTraversalPolicy( traversal );
		init();
		
		button = createButtonPanel();
		GU.vGlue2( button );
		this.add( new TransparentScrollPane( button ), BorderLayout.CENTER );
		bar = new SouthBar( state );
		bar.setButtons( createButtonConfig() );;
		this.add( bar, BorderLayout.SOUTH );
		selector.setSelected( traversal.getButtonForIndex( count ) );
		setSizing();
		
		//one of these methods must be called
//		this.pack();
		this.setSize( this.getPreferredSize() );
		//
		
		this.setLocation( UIUtils.getCenter( this, state.getFrame() ) );
		UIUtils.setTransparentColorsRecursive( this );

	}
	
	protected void init() {
		jl = new UDSCJoystickListener( this );
		//do anything else that needs to be inserted in the middle of the super constructor before other methods are called
	}
	
	protected void setSizing() {
		int max = 10;
		this.setPreferredSize( new Dimension( state.getFrame().getWidth() / 3, button.getComponentCount() - 2 > max ? ( max + 1 ) * buttonHeight : ( button.getComponentCount() -2 + 1 ) * buttonHeight ) );  //the -2 is to account for the glue being added in the constructor after this panel is created
	}
	
	protected Map<Integer, JButton> createButtonConfig() {
		Map<Integer, JButton> menu = new LinkedHashMap<>();
		menu.put( UIJoystickMapping.UP_BUTTON, UIUtils.getButton( MEStrings.SEL_UP, state.getUII().getIcon( UII.UP ), e -> this.up() ) );
		menu.put( UIJoystickMapping.DOWN_BUTTON, UIUtils.getButton( MEStrings.SEL_DOWN, state.getUII().getIcon( UII.DOWN ), e -> this.down() ) );
		menu.put( UIJoystickMapping.OK_BUTTON, UIUtils.getButton( MEStrings.SELECT, state.getUII().getIcon( UII.OK ), e -> this.select() ) );
		menu.put( UIJoystickMapping.CANCEL_BUTTON, UIUtils.getButton( MEStrings.EXIT_MENU, state.getUII().getIcon( UII.CANCEL ), e -> this.cancel() ) );
		return menu;
	}
	
	protected JPanel createButtonPanel() {
		JPanel p = new TransparentPanel();
		p.setLayout( new BoxLayout( p, BoxLayout.Y_AXIS ) );
//		p.setBorder( BorderFactory.createLineBorder( MEProps.FOREGROUND ) );
		return p;
	}
	
	protected JComponent createButton( String text, ActionListener l ) {
		JButton b = UIUtils.getButton( text, null, l );
		b.addFocusListener( focus );
		GU.sizes( b, new Dimension( MEProps.BUTTON_EXTRA_WIDE_WIDTH, MEProps.BUTTON_HEIGHT ) );
		JPanel g = new TransparentPanel();
		g.add( b );
		g.setBorder( BorderFactory.createEmptyBorder( spacer, spacer, spacer, spacer ) );
		traversal.addButton( b, count++ );
		return g;
	}
	
	protected boolean hasFocusOrLast( Container c ) {
		return UIUtils.hasFocusRecursive( c ) || ( UIUtils.hasFocusRecursive( bar ) && UIUtils.getComponentsRecursive( c ).contains( previouslySelected ) );
	}
	
	@Override
	public void setVisible( boolean visible ) {
		if ( visible ) {
			state.getJM().addListener( jl );
		}
		super.setVisible( visible );
	}
	
	@Override
	public void up() {
		selector.previous();
	}
	
	@Override
	public void down() {
		selector.next();
	}
	
	@Override
	public void cancel() {
		this.dispose();
		if ( previous != null ) {
			SwingUtilities.invokeLater( () -> previous.setVisible( true ) );  //needs to be invoke later, otherwise the listeners never get added right, i think a deadlock issue on joystickmanager
		}
	}
	
	@Override
	public void select() {
		selector.select();
	}
	
	@Override
	public Container getFocusContainer() {
		return this;
	}
	
	protected void showDialog( JDialog dialog, PostDialogProcess process ) {
		new Thread( () -> {
			this.dispose();
			dialog.setVisible( true );
			if ( process != null ) {
				process.process();
			}
		} ).start();
	}
}