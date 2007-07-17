package gov.nih.nci.cagrid.data.ui.table;

import gov.nih.nci.cagrid.common.portal.DocumentChangeAdapter;
import gov.nih.nci.cagrid.common.portal.ErrorDialog;
import gov.nih.nci.cagrid.data.DataServiceConstants;
import gov.nih.nci.cagrid.data.common.ExtensionDataManager;
import gov.nih.nci.cagrid.data.cql.CQLQueryProcessor;
import gov.nih.nci.cagrid.introduce.beans.property.ServiceProperties;
import gov.nih.nci.cagrid.introduce.beans.property.ServicePropertiesProperty;
import gov.nih.nci.cagrid.introduce.common.CommonTools;
import gov.nih.nci.cagrid.introduce.common.ServiceInformation;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;


/**
 * QueryProcessorParametersTable 
 * Table for configuring and displaying query
 * processor parameters
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @created Oct 10, 2006
 * @version $Id: QueryProcessorParametersTable.java,v 1.1 2007/07/12 17:20:52
 *          dervin Exp $
 */
public class QueryProcessorParametersTable extends JTable {
    private ExtensionDataManager extensionDataManager;
    private ServiceInformation serviceInfo;
    private JTextField editorTextField = null;
    private Set<String> propertiesFromEtc = null;

    public QueryProcessorParametersTable(ExtensionDataManager dataManager, ServiceInformation serviceInfo) {
        super(createModel());
        this.extensionDataManager = dataManager;
        this.serviceInfo = serviceInfo;
        setDefaultEditor(Object.class, new DefaultCellEditor(getEditorTextField()));
        try {
            populateProperties();
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorDialog.showErrorDialog("Error populating query processor properties", 
                ex.getMessage(), ex);
        }
    }


    private JTextField getEditorTextField() {
        if (editorTextField == null) {
            editorTextField = new JTextField();
            editorTextField.getDocument().addDocumentListener(new DocumentChangeAdapter() {
                public void documentEdited(DocumentEvent e) {
                    setValueAt(editorTextField.getText(), getSelectedRow(), getSelectedColumn());
                    try {
                        storeProperties();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ErrorDialog.showErrorDialog("Error storing query processor properties!", 
                            ex.getMessage(), ex);
                    }
                }
            });
        }
        return editorTextField;
    }
    
    
    public void populateProperties() throws Exception {
        clearTable();
        // get the selected class
        Class selected = getQueryProcessorClass();
        if (selected != null) {
            // get an instance of the class
            CQLQueryProcessor proc = (CQLQueryProcessor) selected.newInstance();
            // get the default parameters
            Properties defaultProps = proc.getRequiredParameters();
            // get the parameters required to be from etc
            this.propertiesFromEtc = proc.getPropertiesFromEtc();
            // get any existing configured parameters
            Properties configuredProps = new Properties();
            ServiceProperties serviceProps = serviceInfo.getServiceProperties();
            if (serviceProps != null && serviceProps.getProperty() != null) {
                for (ServicePropertiesProperty property : serviceProps.getProperty()) {
                    String rawKey = property.getKey();
                    if (rawKey.startsWith(DataServiceConstants.QUERY_PROCESSOR_CONFIG_PREFIX)) {
                        String key = rawKey.substring(
                            DataServiceConstants.QUERY_PROCESSOR_CONFIG_PREFIX.length());
                        configuredProps.setProperty(key, property.getValue());
                    }
                }
            }
            // update the display of properties
            Enumeration propKeys = defaultProps.keys();
            while (propKeys.hasMoreElements()) {
                String key = (String) propKeys.nextElement();
                String def = defaultProps.getProperty(key);
                String val = null;
                if (configuredProps.containsKey(key)) {
                    // property has been configured
                    val = configuredProps.getProperty(key);
                } else {
                    // fall back to the default
                    val = defaultProps.getProperty(key);
                }
                ((DefaultTableModel) getModel()).addRow(new String[]{key, def, val});
            }
        } else {
            clearTable();
        }
    }


    public void classChanged() {
        // clear the table
        clearTable();
        try {
            populateProperties();
            // commit the displayed properties to the service model
            storeProperties();
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorDialog.showErrorDialog("Error loading query processor", ex.getMessage(), ex);
        }
    }


    public boolean isCellEditable(int row, int column) {
        return column == 2;
    }


    public void clearTable() {
        while (getRowCount() != 0) {
            ((DefaultTableModel) getModel()).removeRow(0);
        }
    }


    public Properties getNonPrefixedConfiguredProperties() {
        Properties props = new Properties();
        for (int i = 0; i < getRowCount(); i++) {
            String key = (String) getValueAt(i, 0);
            String value = (String) getValueAt(i, 2);
            props.put(key, value);
        }
        return props;
    }


    private String getQpClassname() throws Exception {
        return CommonTools.getServicePropertyValue(serviceInfo.getServiceDescriptor(),
            DataServiceConstants.QUERY_PROCESSOR_CLASS_PROPERTY);
    }


    private Class getQueryProcessorClass() throws Exception {
        String className = getQpClassname();
        if ((className != null) && (className.length() != 0)
            && !className.endsWith(DataServiceConstants.QUERY_PROCESSOR_STUB_NAME)) {
            String[] libs = getJarFilenames();
            URL[] urls = new URL[libs.length];
            for (int i = 0; i < libs.length; i++) {
                File libFile = new File(libs[i]);
                urls[i] = libFile.toURL();
            }
            ClassLoader loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
            Class qpClass = loader.loadClass(className);
            return qpClass;
        }
        return null;
    }


    private String[] getJarFilenames() throws Exception {
        String libDir = serviceInfo.getBaseDirectory() + File.separator + "lib";
        String[] jarNames = extensionDataManager.getAdditionalJarNames();
        List namesList = new ArrayList();
        if (jarNames != null) {
            for (String name : jarNames) {
                namesList.add(libDir + File.separator + name);
            }
        }
        String[] namesArray = new String[namesList.size()];
        namesList.toArray(namesArray);
        return namesArray;
    }


    private void storeProperties() throws Exception {
        // set / add service properties to match the information in this table
        for (int i = 0; i < getRowCount(); i++) {
            String key = (String) getValueAt(i, 0);
            String userVal = (String) getValueAt(i, 2);
            String prefixedKey = DataServiceConstants.QUERY_PROCESSOR_CONFIG_PREFIX + key;
            boolean isFromEtc = propertiesFromEtc != null && propertiesFromEtc.contains(key);
            CommonTools.setServiceProperty(serviceInfo.getServiceDescriptor(), 
                prefixedKey, userVal, isFromEtc);
        }
    }


    private static DefaultTableModel createModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Parameter");
        model.addColumn("Default");
        model.addColumn("Current Value");
        return model;
    }
}
