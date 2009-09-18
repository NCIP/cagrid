package org.cagrid.tutorials.photosharing;

import gov.nih.nci.cagrid.common.Runner;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.common.security.ProxyUtil;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.cagrid.demos.photoservicereg.client.PhotoSharingRegistrationClient;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.federation.HostCertificateRecord;
import org.cagrid.gaards.dorian.federation.HostCertificateStatus;
import org.cagrid.gaards.pki.CertUtil;
import org.cagrid.gaards.ui.common.ProgressPanel;
import org.cagrid.gaards.ui.common.TitlePanel;
import org.cagrid.gaards.ui.dorian.DorianHandle;
import org.cagrid.gaards.ui.dorian.ServicesManager;
import org.cagrid.grape.ApplicationComponent;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.utils.ErrorDialog;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class RegistrationWindow extends ApplicationComponent {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JPanel mainPanel = null;

    private JPanel titlePanel = null;

    private ProgressPanel progress = null;

    private JPanel infoPanel = null;

    private JScrollPane jScrollPane = null;

    private HostCertificatesTable hostCertificates = null;

    private JLabel jLabel = null;

    private JTextField gridIdentity = null;

    private JPanel buttonPanel = null;

    private JButton register = null;


    /**
     * This is the default constructor
     */
    public RegistrationWindow() {
        super();
        initialize();
        Runner runner = new Runner() {
            public void execute() {
                try {
                    lookupHostCertificates();
                } catch (Exception e) {
                    ErrorDialog.showError(e);
                }
            }
        };
        try {
            GridApplication.getContext().executeInBackground(runner);
        } catch (Exception t) {
            t.getMessage();
        }
    }


    private void lookupHostCertificates() {
        try {
            getRegister().setEnabled(false);
            getProgress().showProgress("Finding hosts....");
            Thread.sleep(200);
            List<DorianHandle> services = ServicesManager.getInstance().getDorianServices();
            for (int j = 0; j < services.size(); j++) {
                DorianHandle handle = services.get(j);
                GridUserClient client = handle.getUserClient(ProxyUtil.getDefaultProxy());
                List<HostCertificateRecord> records = client.getOwnedHostCertificates();
                for (int i = 0; i < records.size(); i++) {
                    if (records.get(i).getStatus().equals(HostCertificateStatus.Active)) {
                        getHostCertificates().addHostCertificate(records.get(i));
                    }
                }
            }
            getProgress().stopProgress();
            getRegister().setEnabled(true);
        } catch (Exception e) {
            getProgress().stopProgress();
            ErrorDialog.showError(Utils.getExceptionMessage(e), e);
            dispose();

        }

    }


    /**
     * This method initializes this
     */
    private void initialize() {
        this.setSize(500, 300);
        this.setContentPane(getJContentPane());
        this.setTitle("Add Photo");
        this.setFrameIcon(GalleryLookAndFeel.getGallery22x22());
    }


    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getMainPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }


    /**
     * This method initializes mainPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getMainPanel() {
        if (mainPanel == null) {
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.gridx = 0;
            gridBagConstraints13.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints13.gridy = 3;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.BOTH;
            gridBagConstraints1.weighty = 1.0;
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 2;
            gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints1.weightx = 1.0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.weightx = 1.0D;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridy = 1;
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints12.weightx = 1.0D;
            gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints12.anchor = GridBagConstraints.EAST;
            gridBagConstraints12.gridy = 4;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.weightx = 1.0D;
            gridBagConstraints11.gridy = 0;
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.add(getTitlePanel(), gridBagConstraints11);
            mainPanel.add(getProgress(), gridBagConstraints12);
            mainPanel.add(getInfoPanel(), gridBagConstraints);
            mainPanel.add(getJScrollPane(), gridBagConstraints1);
            mainPanel.add(getButtonPanel(), gridBagConstraints13);
        }
        return mainPanel;
    }


    /**
     * This method initializes titlePanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getTitlePanel() {
        if (titlePanel == null) {
            titlePanel = new TitlePanel("Photo Sharing Tutorial Registration",
                "Register for the photo sharing tutorial.");
        }
        return titlePanel;
    }


    /**
     * This method initializes progress
     * 
     * @return javax.swing.JPanel
     */
    private ProgressPanel getProgress() {
        if (progress == null) {
            progress = new ProgressPanel();
        }
        return progress;
    }


    /**
     * This method initializes infoPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getInfoPanel() {
        if (infoPanel == null) {
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.anchor = GridBagConstraints.WEST;
            gridBagConstraints3.gridy = 0;
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridx = 1;
            gridBagConstraints2.gridy = 0;
            gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints2.anchor = GridBagConstraints.WEST;
            gridBagConstraints2.weightx = 1.0;
            jLabel = new JLabel();
            jLabel.setText("Grid Identity");
            infoPanel = new JPanel();
            infoPanel.setLayout(new GridBagLayout());
            infoPanel.add(jLabel, gridBagConstraints3);
            infoPanel.add(getGridIdentity(), gridBagConstraints2);
        }
        return infoPanel;
    }


    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getHostCertificates());
            jScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
                "Select the host you will perform the tutorial on",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, null, org.cagrid.grape.LookAndFeel
                    .getPanelLabelColor()));
        }
        return jScrollPane;
    }


    /**
     * This method initializes hostCertificates
     * 
     * @return javax.swing.JTable
     */
    private HostCertificatesTable getHostCertificates() {
        if (hostCertificates == null) {
            hostCertificates = new HostCertificatesTable();
        }
        return hostCertificates;
    }


    /**
     * This method initializes gridIdentity
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getGridIdentity() {
        if (gridIdentity == null) {
            gridIdentity = new JTextField();
            gridIdentity.setEditable(false);
            try {
                gridIdentity.setText(ProxyUtil.getDefaultProxy().getIdentity());
            } catch (Exception e) {
                gridIdentity.setText("");
            }

        }
        return gridIdentity;
    }


    /**
     * This method initializes buttonPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints4.gridy = 0;
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridBagLayout());
            buttonPanel.add(getRegister(), gridBagConstraints4);
        }
        return buttonPanel;
    }


    /**
     * This method initializes register
     * 
     * @return javax.swing.JButton
     */
    private JButton getRegister() {
        if (register == null) {
            register = new JButton();
            register.setText("Register");
            register.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Runner runner = new Runner() {
                        public void execute() {
                            try {
                                register();
                            } catch (Exception e) {
                                ErrorDialog.showError(e);
                            }
                        }
                    };
                    try {
                        GridApplication.getContext().executeInBackground(runner);
                    } catch (Exception t) {
                        t.getMessage();
                    }
                }
            });
            getRootPane().setDefaultButton(register);
        }
        return register;
    }


    private void register() {
        try {
            getRegister().setEnabled(false);
            getProgress().showProgress("Registering for tutorial....");
            PhotoSharingRegistrationClient client = new PhotoSharingRegistrationClient(
                org.cagrid.tutorials.photosharing.Utils.getRegistrationService(), ProxyUtil.getDefaultProxy());
            client.registerPhotoSharingService(CertUtil.subjectToIdentity(getHostCertificates()
                .getSelectedHostCertificate().getSubject()));
            getProgress().stopProgress();
            getRegister().setEnabled(true);
            dispose();
            GridApplication.getContext().showMessage(
                "Congratulations you have successfully registered for the photo sharing tutorial.");
        } catch (Exception e) {
            getProgress().stopProgress();
            ErrorDialog.showError(Utils.getExceptionMessage(e), e);
            getRegister().setEnabled(true);
        }
    }
}
