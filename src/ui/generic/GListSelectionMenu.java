package ui.generic;

import gui.event.MouseClickedListener;
import gui.panel.TransparentPanel;
import gui.scroll.TransparentScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import joystick.event.CompositeJoystickEventAdapter;
import joystick.event.HoldDelayJoystickEventListener;
import joystick.event.JoystickEvent;
import joystick.event.JoystickEventAdapter;
import state.MEState;
import state.MEStrings;
import state.UII;
import ui.generic.joystick.UDLRSCJoystickListener;
import ui.generic.joystick.components.UDLRSCComponent;
import ui.util.UIUtils;
import data.UIJoystickMapping;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 31, 2015, 9:14:22 PM 
 */
public abstract class GListSelectionMenu<T> extends GOKCancelDialog implements UDLRSCComponent {

	private static final long serialVersionUID = 1L;
	
	protected T current = null;
	
	protected JList<T> list;

	protected DefaultListModel<T> model = new DefaultListModel<>();
	
	protected JPanel listPanel;
	
	protected HoldDelayJoystickEventListener hold;
	
	/**
	 * @param owner
	 * @param state
	 * @param message
	 */
	public GListSelectionMenu( MEState state, JDialog previous ) {
		super( state, null, previous );
	}
	
	@Override
	protected void init() {
		super.init();
		this.add( createWestContent(), BorderLayout.WEST );
		
		hold = new HoldDelayJoystickEventListener( new JoystickEventAdapter() {
			@Override
			public void buttonHeld( JoystickEvent e ) {
				if ( UIUtils.hasFocusRecursive( listPanel ) ) {
					if ( e.getButton() == UIJoystickMapping.UP_BUTTON ) {
						joyUp();
					} else if ( e.getButton() == UIJoystickMapping.DOWN_BUTTON ) {
						joyDown();
					}
				}
			}
		} );
		
		jl = new CompositeJoystickEventAdapter( Arrays.asList( new JoystickEventAdapter[] { hold, new UDLRSCJoystickListener( this ) } ) );
	}
	
	private void joyUp() {
		setSelection( list.getSelectedIndex() == -1 ? 0 : list.getSelectedIndex() == 0 ? list.getModel().getSize() - 1 : list.getSelectedIndex() - 1 );
	}
	
	private void joyDown() {
		setSelection( list.getSelectedIndex() == -1 || list.getSelectedIndex() == list.getModel().getSize() - 1 ? 0 : list.getSelectedIndex() + 1 );
	}
	
	protected void setSelection( int index ) {
		list.setSelectedIndex( index );
		SwingUtilities.invokeLater( () -> list.ensureIndexIsVisible( index ) );
		this.revalidate();
	}

	protected JPanel createWestContent() {
		listPanel = new TransparentPanel( new BorderLayout() );
		list = new JList<>();
		list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		list.setCellRenderer( getRenderer() );
		list.addMouseListener( (MouseClickedListener)( e ) -> {
				if ( e.getClickCount() == 2 ) {
					setCurrentSelection( list.getSelectedValue() );
				}
			}
		);
		list.addFocusListener( focus );
		JScrollPane scroll = new TransparentScrollPane( list );
		scroll.setPreferredSize( new Dimension( 500, 300 )  );
		listPanel.add( scroll, BorderLayout.CENTER );
		return listPanel;
	}
	
	public T getCurrent() {
		return current;
	}
	
	@Override
	protected void setSizing() {
		this.setPreferredSize( new Dimension( state.getFrame().getWidth() / 2, state.getFrame().getHeight() / 2 ) );
	}
	
	protected abstract void setCurrentSelection( T selected );
	
	protected abstract ListCellRenderer<T> getRenderer();
	
	@Override
	protected JPanel createButtonPanel() {
		JPanel p = super.createButtonPanel();
		p.setLayout( new BoxLayout( p, BoxLayout.Y_AXIS ) );
		return p;
	}
	
	@Override
	public void cancel() {
		result = CANCEL;
		hold.resetHoldCount();
		super.cancel();
	}
	
	public int getResult() {
		return result;
	}

	@Override
	public void left() {
		list.requestFocusInWindow();
		hold.resetHoldCount();
	}
	
	@Override
	public void select() {
		if ( hasFocusOrLast( listPanel ) ) {
			setCurrentSelection( list.getSelectedValue() );
		} else if ( hasFocusOrLast( button ) ) {
			selector.select();
		}
		hold.resetHoldCount();
	}

	@Override
	public void right() {
		JButton b = (JButton)traversal.getFirstComponent( this );
		selector.setSelected( b );
		b.requestFocusInWindow();
		hold.resetHoldCount();
	}
	
	@Override
	public void up() {
		if ( hasFocusOrLast( listPanel ) ) {
			joyUp();
		} else if ( hasFocusOrLast( button ) ) {
			selector.previous();
		}
		hold.resetHoldCount();
	}
	
	@Override
	public void down() {
		if ( hasFocusOrLast( listPanel ) ) {
			joyDown();
		} else if ( hasFocusOrLast( button ) ) {
			selector.next();
		}
		hold.resetHoldCount();
	}
	
	@Override
	protected Map<Integer, JButton> createButtonConfig() {
		Map<Integer, JButton> menu = new LinkedHashMap<>();
		menu.put( UIJoystickMapping.LEFT_BUTTON, UIUtils.getButton( MEStrings.LEFT_PANEL, state.getUII().getIcon( UII.LEFT ), e -> this.left() ) );
		menu.put( UIJoystickMapping.RIGHT_BUTTON, UIUtils.getButton( MEStrings.RIGHT_PANEL, state.getUII().getIcon( UII.RIGHT ), e -> this.right() ) );
		menu.put( UIJoystickMapping.UP_BUTTON, UIUtils.getButton( MEStrings.SEL_UP, state.getUII().getIcon( UII.UP ), e -> this.up() ) );
		menu.put( UIJoystickMapping.DOWN_BUTTON, UIUtils.getButton( MEStrings.SEL_DOWN, state.getUII().getIcon( UII.DOWN ), e -> this.down() ) );
		menu.put( UIJoystickMapping.OK_BUTTON, UIUtils.getButton( MEStrings.SELECT, state.getUII().getIcon( UII.OK ), e -> this.select() ) );
		menu.put( UIJoystickMapping.CANCEL_BUTTON, UIUtils.getButton( MEStrings.EXIT_MENU, state.getUII().getIcon( UII.CANCEL ), e -> this.cancel() ) );
		return menu;
	}
	
	@Override
	public void setVisible( boolean visible ) {
		SwingUtilities.invokeLater( () -> list.requestFocusInWindow() );
		super.setVisible( visible );
	}
}