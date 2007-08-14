package gov.nih.nci.cagrid.data.ui.browser;

import java.util.EventListener;

/** 
 *  AdditionalJarsChangeListener
 *  Listens for additional jars changed events
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * 
 * @created May 12, 2006 
 * @version $Id: AdditionalJarsChangeListener.java,v 1.1.2.1 2007-08-14 14:42:01 dervin Exp $ 
 */
public interface AdditionalJarsChangeListener extends EventListener {

	public void additionalJarsChanged(AdditionalJarsChangedEvent e);
}
