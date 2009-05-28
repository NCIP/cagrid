/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 *
 */
public class BrowseCommand {
	
	private List<String> browseTypes = new ArrayList<String>();
	
	private String browseType;
	
	public BrowseCommand(){
		browseTypes.add("ALL");
		browseTypes.add("DATASET");
		browseTypes.add("PERSON");
		browseTypes.add("TOOL");
		browseTypes.add("INSTITUTION");
		browseTypes.add("COMMUNITY");
	}

	public List<String> getBrowseTypes() {
		return browseTypes;
	}

	public void setBrowseTypes(List<String> browseTypes) {
		this.browseTypes = browseTypes;
	}

	public String getBrowseType() {
		return browseType;
	}

	public void setBrowseType(String browseType) {
		this.browseType = browseType;
	}
	
	

}
