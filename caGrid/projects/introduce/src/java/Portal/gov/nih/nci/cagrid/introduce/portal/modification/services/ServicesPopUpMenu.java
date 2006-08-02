package gov.nih.nci.cagrid.introduce.portal.modification.services;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.common.portal.PortalUtils;
import gov.nih.nci.cagrid.introduce.IntroduceConstants;
import gov.nih.nci.cagrid.introduce.beans.method.MethodsType;
import gov.nih.nci.cagrid.introduce.beans.resource.ResourcePropertiesListType;
import gov.nih.nci.cagrid.introduce.beans.security.ServiceSecurity;
import gov.nih.nci.cagrid.introduce.beans.service.ServiceType;
import gov.nih.nci.cagrid.introduce.info.SpecificServiceInformation;
import gov.nih.nci.cagrid.introduce.portal.common.IntroduceLookAndFeel;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;

import org.globus.wsrf.encoding.DeserializationException;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;
import org.projectmobius.portal.PortalResourceManager;


public class ServicesPopUpMenu extends JPopupMenu {

	private JMenuItem addResourceMenuItem = null;
	private ServicesTypeTreeNode node;


	/**
	 * This method initializes
	 */
	public ServicesPopUpMenu(ServicesTypeTreeNode node) {
		super();
		this.node = node;
		initialize();
	}


	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.add(getAddResourceMenuItem());

	}


	/**
	 * This method initializes addResourceMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAddResourceMenuItem() {
		if (addResourceMenuItem == null) {
			addResourceMenuItem = new JMenuItem();
			addResourceMenuItem.setText("Add Service Context");
			addResourceMenuItem.setIcon(IntroduceLookAndFeel.getCreateServiceIcon());
			addResourceMenuItem.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);
					ServiceType service = new ServiceType();
					service.setMethods(new MethodsType());
					service.setResourcePropertiesList(new ResourcePropertiesListType());
					service.setResourceFrameworkType(IntroduceConstants.INTRODUCE_BASE_RESOURCE);
					try {
						service.setServiceSecurity((ServiceSecurity) ObjectSerializer.clone(node.getInfo()
							.getServices().getService(0).getServiceSecurity()));
					} catch (SerializationException e1) {
						e1.printStackTrace();
					} catch (DeserializationException e1) {
						e1.printStackTrace();
					}
					ServiceTypeTreeNode newNode = node.addService(service);
					ModifyService comp = new ModifyService(newNode, new SpecificServiceInformation(node.getInfo(),
						service));
					// PortalResourceManager.getInstance().getGridPortal().addGridPortalComponent(
					// new ModifyService(newNode, new
					// SpecificServiceInformation(node.getInfo(),service)));
					comp.setSize(new Dimension(500, 200));
					PortalUtils.centerWindow(comp);
					comp.setVisible(true);

				}

			});
		}
		return addResourceMenuItem;
	}

}
