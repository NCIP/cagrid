package gov.nih.nci.cagrid.data.style;

import gov.nih.nci.cagrid.introduce.beans.extension.ServiceExtensionDescriptionType;
import gov.nih.nci.cagrid.introduce.common.ServiceInformation;

/** 
 *  StyleCodegenPreProcessor
 *  TODO:DOCUMENT ME
 * 
 * @author David Ervin
 * 
 * @created Jul 10, 2007 10:51:15 AM
 * @version $Id: StyleCodegenPreProcessor.java,v 1.1.2.1 2007-08-14 14:42:01 dervin Exp $ 
 */
public interface StyleCodegenPreProcessor {

    public void codegenPreProcessStyle(ServiceExtensionDescriptionType desc, ServiceInformation info) throws Exception;
}
