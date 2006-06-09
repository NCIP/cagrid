package gov.nih.nci.cagrid.graph.domainmodelapplication;

import gov.nih.nci.cagrid.metadata.dataservice.DomainModel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

public class DomainModelOutline extends JPanel
{
	public DomainModelOutlineToolBar toolBar;
	public DomainModelOutlineTree    tree;
	
	public static int toolBarHeight = 30;
	
	public DomainModelOutline(DomainModel exp)
	{
	
		this.setLayout(null);
	
		tree = new DomainModelOutlineTree();
		toolBar = new DomainModelOutlineToolBar();
		
		this.add(tree);
		this.add(toolBar);
		
		tree.setDomainModel(exp);
		
		this.addComponentListener(new DomainModelOutlineComponentListener());
	}
	
	public void resizeChildren()
	{
		toolBar.setBounds(0, 0, getWidth(), toolBarHeight);
		tree.setBounds(0, toolBarHeight + 1, getWidth(), getHeight() - toolBarHeight - 1);
		this.validate();
	}
	
	
}

class DomainModelOutlineComponentListener extends ComponentAdapter
{
	public void componentResized(ComponentEvent e)
	{
		DomainModelOutline s = (DomainModelOutline) e.getSource();
		
		s.resizeChildren();
	}
}
