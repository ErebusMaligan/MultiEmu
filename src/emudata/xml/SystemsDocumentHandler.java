package emudata.xml;

import state.MEProps;
import xml.SimpleFileXMLDocumentHandler;
import emudata.SystemManager;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 10:10:38 PM 
 */
public class SystemsDocumentHandler extends SimpleFileXMLDocumentHandler {
	public SystemsDocumentHandler( SystemManager man ) {
		this.val = man;
		FILE = "Systems";
		ROOT_NODE_NAME = "ROOT";
		EXT = ".xml";
		READABLE = "Emulator Systems Database";
		DIR = MEProps.DATA_PATH;
		init( null, EXT, READABLE + " (" + EXT + ")", DIR, ROOT_NODE_NAME );
	}	
}