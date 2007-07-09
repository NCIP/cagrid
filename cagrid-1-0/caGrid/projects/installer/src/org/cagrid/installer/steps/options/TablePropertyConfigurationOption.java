/**
 * 
 */
package org.cagrid.installer.steps.options;

import javax.swing.table.TableModel;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class TablePropertyConfigurationOption extends
		AbstractPropertyConfigurationOption {


	private TableModel tableModel;
	
	/**
	 * @param name
	 * @param description
	 */
	public TablePropertyConfigurationOption(String name, String description, TableModel tableModel) {
		this(name, description, tableModel, false);
	}

	/**
	 * @param name
	 * @param description
	 * @param required
	 */
	public TablePropertyConfigurationOption(String name, String description, TableModel tableModel,
			boolean required) {
		super(name, description, required);
		this.tableModel = tableModel;
	}
	
	public boolean isComplete(){
		return true;
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

}
