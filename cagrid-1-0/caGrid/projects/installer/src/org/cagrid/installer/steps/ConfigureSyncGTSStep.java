/**
 * 
 */
package org.cagrid.installer.steps;

import gov.nih.nci.cagrid.common.Utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.util.InstallerUtils;
import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ConfigureSyncGTSStep extends PanelWizardStep implements
		ActionListener {

	private static final Log logger = LogFactory
			.getLog(ConfigureSyncGTSStep.class);
	
	private static final String SYNC_GTS_NS = "http://cagrid.nci.nih.gov/12/SyncGTS";
	private static final String SYNC_GTS_NS_PREFIX = "sync";
	private static final String GTS_NS = "http://cagrid.nci.nih.gov/8/gts";
	private static final String GTS_NS_PREFIX = "gts";

	private CaGridInstallerModel model;

	private JTextField gtsServiceURIField;

	private JTextField expirationHoursField;

	private JTextField expirationMinutesField;

	private JTextField expirationSecondsField;

	private JCheckBox performAuthzField;

	private JTextField gtsIdentField;

	private JCheckBox deleteInvalidField;

	private JTextField nextSyncField;

	private JButton tafCmdAdd;

	private JButton tafCmdDelete;

	private DefaultTableModel tafTableModel;

	private JTable tafTable;

	private JButton ecCmdAdd;

	private JButton ecCmdDelete;

	private DefaultTableModel ecTableModel;

	private JTable ecTable;

	private XPathFactory xpFact;

	/**
	 * 
	 */
	public ConfigureSyncGTSStep() {

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConfigureSyncGTSStep(String name, String summary) {
		super(name, summary);

	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public ConfigureSyncGTSStep(String name, String summary, Icon icon) {
		super(name, summary, icon);
	}

	public void init(WizardModel m) {

		if (!(m instanceof CaGridInstallerModel)) {
			throw new IllegalStateException(
					"This step requires a StatefulWizardModel instance.");
		}
		this.model = (CaGridInstallerModel) m;

		this.xpFact = XPathFactory.newInstance();

		setLayout(new GridBagLayout());

		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridBagLayout());
		add(optionsPanel, getGridBagConstraints(0, 0));

		// Add gtsServiceURI field
		String gtsServiceURI = getProperty(this.model.getState(),
				Constants.SYNC_GTS_GTS_URI,
				"https://cagrid02.bmi.ohio-state.edu:8442/wsrf/services/cagrid/GTS");
		this.gtsServiceURIField = new JTextField(gtsServiceURI);
		addRequiredListener(this.gtsServiceURIField);
		JLabel gtsServiceURILabel = new JLabel(this.model
				.getMessage("sync.gts.gts.uri"));
		addOption(optionsPanel, gtsServiceURILabel, this.gtsServiceURIField, 0);

		// Expiration hours
		String hours = getProperty(this.model.getState(),
				Constants.SYNC_GTS_EXPIRATION_HOURS, "1");
		this.expirationHoursField = new JTextField(hours);
		addRequiredListener(this.expirationHoursField);
		JLabel expirationHoursLabel = new JLabel(this.model
				.getMessage("sync.gts.expiration.hours"));
		addOption(optionsPanel, expirationHoursLabel,
				this.expirationHoursField, 1);

		String minutes = getProperty(this.model.getState(),
				Constants.SYNC_GTS_EXPIRATION_MINUTES, "0");
		this.expirationMinutesField = new JTextField(minutes);
		addRequiredListener(this.expirationMinutesField);
		JLabel expirationMinutesLabel = new JLabel(this.model
				.getMessage("sync.gts.expiration.minutes"));
		addOption(optionsPanel, expirationMinutesLabel,
				this.expirationMinutesField, 2);

		String seconds = getProperty(this.model.getState(),
				Constants.SYNC_GTS_EXPIRATION_SECONDS, "0");
		this.expirationSecondsField = new JTextField(seconds);
		addRequiredListener(this.expirationSecondsField);
		JLabel expirationSecondsLabel = new JLabel(this.model
				.getMessage("sync.gts.expiration.seconds"));
		addOption(optionsPanel, expirationSecondsLabel,
				this.expirationSecondsField, 3);

		String performAuthz = getProperty(this.model.getState(),
				Constants.SYNC_GTS_PERFORM_AUTHZ, "true");
		this.performAuthzField = new JCheckBox();
		this.performAuthzField.setSelected("true".equals(performAuthz));
		JLabel performAuthzLabel = new JLabel(this.model
				.getMessage("sync.gts.perform.authz"));
		addOption(optionsPanel, performAuthzLabel, this.performAuthzField, 4);
		this.performAuthzField.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				ConfigureSyncGTSStep.this.checkComplete();
				boolean selected = evt.getStateChange() == ItemEvent.SELECTED;
				ConfigureSyncGTSStep.this.gtsIdentField.setEnabled(selected);
			}

		});

		String gtsIdent = getProperty(this.model.getState(),
				Constants.SYNC_GTS_GTS_IDENT,
				"/O=OSU/OU=BMI/OU=caGrid/OU=Trust Fabric/CN=host/cagrid02.bmi.ohio-state.edu");
		this.gtsIdentField = new JTextField(gtsIdent);
		JLabel gtsIdentLabel = new JLabel(this.model
				.getMessage("sync.gts.gts.ident"));
		addOption(optionsPanel, gtsIdentLabel, this.gtsIdentField, 5);
		this.gtsIdentField.setEnabled(this.performAuthzField.isSelected());

		String deleteInvalid = getProperty(this.model.getState(),
				Constants.SYNC_GTS_DELETE_INVALID, "false");
		this.deleteInvalidField = new JCheckBox();
		this.deleteInvalidField.setSelected("true".equals(deleteInvalid));
		JLabel deleteInvalidLabel = new JLabel(this.model
				.getMessage("sync.gts.delete.invalid"));
		addOption(optionsPanel, deleteInvalidLabel, this.deleteInvalidField, 6);
		this.deleteInvalidField.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				ConfigureSyncGTSStep.this.checkComplete();
			}

		});

		String nextSync = getProperty(this.model.getState(),
				Constants.SYNC_GTS_NEXT_SYNC, "600");
		this.nextSyncField = new JTextField(nextSync);
		addRequiredListener(this.nextSyncField);
		JLabel nextSyncLabel = new JLabel(this.model
				.getMessage("sync.gts.next.sync"));
		addOption(optionsPanel, nextSyncLabel, this.nextSyncField, 7);

		JPanel trustedAuthFilterPanel = new JPanel();
		trustedAuthFilterPanel.setLayout(new BorderLayout());
		trustedAuthFilterPanel.setPreferredSize(new Dimension(500, 125));
		add(trustedAuthFilterPanel, getGridBagConstraints(0, 1));

		JPanel buttonPanel = new JPanel();
		this.tafCmdAdd = new JButton(this.model.getMessage("add"));
		this.tafCmdAdd.addActionListener(this);
		this.tafCmdDelete = new JButton(this.model.getMessage("delete"));
		this.tafCmdDelete.addActionListener(this);
		buttonPanel.add(this.tafCmdAdd);
		buttonPanel.add(this.tafCmdDelete);

		String[] colNames = new String[] { "Name", "CertificateDN",
				"TrustLevels", "Lifetime", "Status", "IsAuthority",
				"AuthorityGTS", "SourceGTS" };
		this.tafTableModel = new DefaultTableModel(new Object[0][0], colNames);
		this.tafTable = new JTable(this.tafTableModel);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component renderer = super.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);
				setBorder(BorderFactory.createEtchedBorder());
				return renderer;
			}
		};
		for (int i = 0; i < 8; i++) {
			TableColumn col = this.tafTable.getColumnModel().getColumn(i);
			col.setCellRenderer(r);
		}
		JComboBox lifetimeChoices = new JComboBox();
		lifetimeChoices.addItem("----");
		lifetimeChoices.addItem("Valid");
		lifetimeChoices.addItem("Expired");
		this.tafTable.getColumnModel().getColumn(3).setCellEditor(
				new DefaultCellEditor(lifetimeChoices));

		JComboBox statusChoices = new JComboBox();
		statusChoices.addItem("----");
		statusChoices.addItem("Trusted");
		statusChoices.addItem("Suspended");
		this.tafTable.getColumnModel().getColumn(4).setCellEditor(
				new DefaultCellEditor(statusChoices));

		JComboBox isAuthChoices = new JComboBox();
		isAuthChoices.addItem("----");
		isAuthChoices.addItem("true");
		isAuthChoices.addItem("false");
		this.tafTable.getColumnModel().getColumn(5).setCellEditor(
				new DefaultCellEditor(isAuthChoices));

		this.tafTable
				.setPreferredScrollableViewportSize(new Dimension(400, 100));
		JScrollPane scrollPane = new JScrollPane(tafTable);
		trustedAuthFilterPanel.add(BorderLayout.NORTH, new JLabel(this.model
				.getMessage("sync.gts.auth.filter")));
		trustedAuthFilterPanel.add(BorderLayout.CENTER, scrollPane);
		trustedAuthFilterPanel.add(BorderLayout.SOUTH, buttonPanel);

		JPanel excludedCAsPanel = new JPanel();
		excludedCAsPanel.setLayout(new BorderLayout());
		excludedCAsPanel.setPreferredSize(new Dimension(500, 125));
		add(excludedCAsPanel, getGridBagConstraints(0, 2));

		JPanel ecButtonPanel = new JPanel();
		this.ecCmdAdd = new JButton(this.model.getMessage("add"));
		this.ecCmdAdd.addActionListener(this);
		this.ecCmdDelete = new JButton(this.model.getMessage("delete"));
		this.ecCmdDelete.addActionListener(this);
		ecButtonPanel.add(this.ecCmdAdd);
		ecButtonPanel.add(this.ecCmdDelete);

		String[] ecColNames = new String[] { "ExcludedCAs" };
		// Object[][] ecData = new Object[1][1];
		// ecData[0][0] = "O=OSU,OU=BMI,OU=caGrid,OU=Trust Fabric,CN=caGrid
		// Trust Fabric CA";
		this.ecTableModel = new DefaultTableModel(new Object[0][0], ecColNames);
		this.ecTable = new JTable(this.ecTableModel);
		this.ecTable.getColumnModel().getColumn(0).setCellRenderer(r);
		this.ecTable
				.setPreferredScrollableViewportSize(new Dimension(400, 100));
		excludedCAsPanel.add(BorderLayout.NORTH, new JLabel(this.model
				.getMessage("sync.gts.excluded.cas")));
		excludedCAsPanel
				.add(BorderLayout.CENTER, new JScrollPane(this.ecTable));
		excludedCAsPanel.add(BorderLayout.SOUTH, ecButtonPanel);

	}

	private void addOption(JPanel panel, JLabel label, Component field, int y) {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = y;
		panel.add(label, gridBagConstraints1);

		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.fill = GridBagConstraints.BOTH;
		gridBagConstraints2.gridy = y;
		gridBagConstraints2.weightx = 1;
		gridBagConstraints2.insets = new Insets(2, 5, 2, 2);
		panel.add(field, gridBagConstraints2);

	}

	private void addRequiredListener(JTextField field) {
		field.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent evt) {
				ConfigureSyncGTSStep.this.checkComplete();
			}

		});
	}

	private String getProperty(Map state, String propName, String defaultValue) {
		String value = (String) state.get(propName);
		if (value == null || value.trim().length() == 0) {
			value = defaultValue;
		}
		return value;
	}

	protected void checkComplete() {
		if (!isEmpty(this.gtsServiceURIField)
				&& !isEmpty(this.expirationHoursField)
				&& !isEmpty(this.expirationMinutesField)
				&& !isEmpty(this.expirationSecondsField)
				&& !isEmpty(this.nextSyncField)) {
			setComplete(true);
		} else {
			setComplete(false);
		}
	}

	private boolean isEmpty(JTextField field) {
		return field.getText() == null || field.getText().trim().length() == 0;
	}

	private Object getGridBagConstraints(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.gridy = y;
		return gbc;
	}

	public void prepare() {
		try {
			// Read in sync-description.xml
			String fileName = getSyncDescriptionFileName();

			logger.info("Looking for " + fileName);

			File syncDescriptionFile = new File(fileName);
			if (syncDescriptionFile.exists()) {

				logger.info("Parsing " + fileName);

				DocumentBuilderFactory fact = DocumentBuilderFactory
						.newInstance();
				fact.setValidating(false);
				fact.setNamespaceAware(true);
				DocumentBuilder builder = fact.newDocumentBuilder();
				Document doc = builder.parse(syncDescriptionFile);
				Element root = doc.getDocumentElement();

				// Get the first SyncDescriptor element
				Element syncDescEl = (Element) xpFact
						.newXPath()
						.compile(
								"/*[local-name()='SyncDescription']/*[local-name()='SyncDescriptor'][1]")
						.evaluate(root, XPathConstants.NODE);
				if (syncDescEl != null) {

					logger.debug("Found SyncDescriptor");

					// Get gtsServiceURI
					String gtsServiceURI = getChildElementText(syncDescEl,
							"gtsServiceURI");
					logger.debug("Setting gtsServiceURI to '" + gtsServiceURI
							+ "'");
					this.gtsServiceURIField.setText(gtsServiceURI);

					// Get Expiration
					Element expirationEl = (Element) xpFact.newXPath().compile(
							"./*[local-name()='Expiration']").evaluate(
							syncDescEl, XPathConstants.NODE);
					if (expirationEl != null) {
						String hours = getValue(expirationEl
								.getAttribute("hours"), "1");
						String minutes = getValue(expirationEl
								.getAttribute("minutes"), "0");
						String seconds = getValue(expirationEl
								.getAttribute("seconds"), "0");
						logger.debug("Setting Expiration to hours=" + hours
								+ ", minutes=" + minutes + ", seconds="
								+ seconds);
						this.expirationHoursField.setText(hours);
						this.expirationMinutesField.setText(minutes);
						this.expirationSecondsField.setText(seconds);
					}

					// Get TrustedAuthorityFilter elements
					NodeList filters = (NodeList) xpFact.newXPath().compile(
							"./*[local-name()='TrustedAuthorityFilter']")
							.evaluate(syncDescEl, XPathConstants.NODESET);
					logger.debug("Found " + filters.getLength()
							+ " TrustedAuthorityFilter elments.");
					for (int i = 0; i < filters.getLength(); i++) {
						Element filter = (Element) filters.item(i);
						String name = getValue(getChildElementText(filter,
								"Name"), "");
						String certDN = getValue(getChildElementText(filter,
								"CertificateDN"), "");

						// Concat trust levels
						NodeList levels = (NodeList) xpFact
								.newXPath()
								.compile(
										"./*[local-name()='TrustLevels']/*[local-name()='TrustLevel']")
								.evaluate(filter, XPathConstants.NODESET);
						logger.debug("Found " + levels.getLength()
								+ " trust levels");
						StringBuilder sb = new StringBuilder();
						for (int j = 0; j < levels.getLength(); j++) {
							Element level = (Element) levels.item(j);
							sb.append(level.getTextContent());
							if (j + 1 < levels.getLength()) {
								sb.append(",");
							}
						}
						String trustLevels = sb.toString();

						String lifetime = getValue(getChildElementText(filter,
								"Lifetime"), "----");

						String status = getValue(getChildElementText(filter,
								"Status"), "----");

						String isAuth = getValue(getChildElementText(filter,
								"IsAuthority"), "----");

						String authGTS = getValue(getChildElementText(filter,
								"AuthorityGTS"), "");

						String sourceGTS = getValue(getChildElementText(filter,
								"SourceGTS"), "");

						String[] trustedAuthFilter = new String[] { name,
								certDN, trustLevels, lifetime, status, isAuth,
								authGTS, sourceGTS };

						logger.debug("Adding trusted authority filter: Name="
								+ name + ", CertificateDN=" + certDN
								+ ", TrustLevels=" + trustLevels
								+ ", Lifetime=" + lifetime + ", Status="
								+ status + ", IsAuthority=" + isAuth
								+ ", AuthorityGTS=" + authGTS + ", sourceGTS="
								+ sourceGTS);
						this.tafTableModel.addRow(trustedAuthFilter);
					}
				}// done adding SyncDescriptor elements

				// Get ExcludedCAs
				NodeList caSubjEls = (NodeList) xpFact
						.newXPath()
						.compile(
								"/*[local-name()='SyncDescription']/*[local-name()='ExcludedCAs']/*[local-name()='CASubject']")
						.evaluate(root, XPathConstants.NODESET);
				logger.debug("Found " + caSubjEls.getLength()
						+ " excluded CAs.");
				for (int i = 0; i < caSubjEls.getLength(); i++) {
					Element caSubjEl = (Element) caSubjEls.item(i);
					String text = caSubjEl.getTextContent();
					if (text != null && text.trim().length() > 0) {
						String[] excludedCARow = new String[] { text };
						logger.debug("Adding excluded CA: " + excludedCARow);
						this.ecTableModel.addRow(excludedCARow);
					}
				}

				String deleteInvalidFiles = getValue(getChildElementText(root,
						"DeleteInvalidFiles"), "true");
				logger.debug("Setting DeleteInvalidFiles = "
						+ deleteInvalidFiles);
				this.deleteInvalidField.setSelected("true"
						.equals(deleteInvalidFiles));

				String nextSync = getValue(
						getChildElementText(root, "NextSync"), "600");
				logger.debug("Setting NextSync = " + nextSync);
				this.nextSyncField.setText(nextSync);

			}
			checkComplete();
		} catch (Exception ex) {
			throw new RuntimeException("Error preparing editor: "
					+ ex.getMessage(), ex);
		}
	}

	private String getSyncDescriptionFileName() {
		return InstallerUtils.getServiceDestDir(this.model.getState())
				+ "/syncgts/ext/resources/sync-description.xml";
	}

	private String getChildElementText(Element parentEl, String childName) {
		String text = null;
		try {
			Element childEl = (Element) this.xpFact.newXPath().compile(
					"./*[local-name()='" + childName + "']").evaluate(parentEl,
					XPathConstants.NODE);
			if (childEl != null) {
				text = childEl.getTextContent();
			}
		} catch (Exception ex) {
			throw new RuntimeException("Error getting child element text: "
					+ ex.getMessage(), ex);
		}
		return text;
	}

	private String getValue(String value, String defaultValue) {
		return value == null ? defaultValue : value;
	}

	public void applyState() throws InvalidStateException {
		try {
//			File syncDescFile = new File(getSyncDescriptionFileName());
//			if (syncDescFile.exists()) {
//				Utils.copyFile(syncDescFile, new File(syncDescFile
//						.getAbsolutePath()
//						+ "-bak"));
//				syncDescFile.delete();
//			}
//			
//			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
//			fact.setValidating(false);
//			fact.setNamespaceAware(true);
//			DocumentBuilder builder = fact.newDocumentBuilder();
//			Document doc = builder.newDocument();
//			
//			Element root = doc.createElementNS(SYNC_GTS_NS, SYNC_GTS_NS_PREFIX + ":SyncDescription");
//
//			
//			//Write to file
//			String xml = toString(doc);
//			FileWriter w = new FileWriter(syncDescFile);
//			w.write(xml);
//			w.flush();
//			w.close();
		} catch (Exception ex) {
			throw new InvalidStateException(
					"Error configuring sync-description.xml file: "
							+ ex.getMessage(), ex);
		}
	}

	private static String toString(Node node) throws Exception {
		StringWriter w = new StringWriter();
		Source s = new DOMSource(node);
		Result r = new StreamResult(w);
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty("omit-xml-declaration", "yes");
		t.setOutputProperty("indent", "yes");
		t.transform(s, r);
		return w.getBuffer().toString();
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == this.tafCmdAdd) {
			this.tafTableModel.addRow(new String[8]);
		} else if (source == this.tafCmdDelete) {
			this.tafTableModel.removeRow(this.tafTable.getSelectedRow());
		} else if (source == this.ecCmdAdd) {
			this.ecTableModel.addRow(new String[8]);
		} else if (source == this.ecCmdDelete) {
			this.ecTableModel.removeRow(this.ecTable.getSelectedRow());
		}
	}
}
