package emudata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import xml.XMLExpansion;
import xml.XMLValues;
import emudata.xml.SystemsDocumentHandler;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 10:16:40 PM 
 */
public class SystemManager implements XMLValues {

	private static final String SYSTEMS = "SYSTEMS";
	
	private List<SystemData> systems = new ArrayList<>();
	
	private SystemsDocumentHandler handler = new SystemsDocumentHandler( this );
	
	public SystemManager() {
		handler.loadDoc();
	}
	
	public void addSystem( SystemData data ) {
		systems.add( data );
		handler.createDoc();
	}
	
	public void removeSystem( SystemData data ) {
		systems.remove( data );
		handler.createDoc();
	}
	
	public SystemData getByRemoteID( String remoteID ) {
		Optional<SystemData> ret = systems.stream().filter( s -> s.getRemoteID().equals( remoteID ) ).findFirst();
		return ret.isPresent() ? ret.get() : null;
	}
	
	public List<SystemData> getSystems() {
		return Collections.unmodifiableList( systems );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<XMLValues> getChildNodes() {
		return ( ( List<XMLValues> )( (Object)systems ) );
	}

	@Override
	public void loadParamsFromXMLValues( XMLExpansion ex ) {
		XMLExpansion s = ex.getChild( SYSTEMS );
		for ( XMLExpansion e : s.getChildren() ) {
			SystemData d = new SystemData();
			d.loadParamsFromXMLValues( e );
			systems.add( d );
		}
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() { 
		Map<String, Map<String, String[]>> ret = new HashMap<>();
		Map<String, String[]> m = new HashMap<>();
		ret.put( SYSTEMS, m );
		return ret;
	}
}