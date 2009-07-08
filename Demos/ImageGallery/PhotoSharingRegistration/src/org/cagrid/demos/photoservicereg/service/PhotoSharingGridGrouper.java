package org.cagrid.demos.photoservicereg.service;

import edu.internet2.middleware.grouper.GrouperRuntimeException;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouper;

public class PhotoSharingGridGrouper extends GridGrouper {

	public PhotoSharingGridGrouper(String serviceURI, boolean preferAnonymous) {
		super(serviceURI, null);
		try {
			this.getClient().setAnonymousPrefered(preferAnonymous);
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
			throw new GrouperRuntimeException(Utils.getExceptionMessage(e));
		}
	}
}
