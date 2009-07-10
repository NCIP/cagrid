package org.cagrid.demo.photosharing.guicomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.demos.photoservicereg.client.PhotoSharingRegistrationClient;
import org.cagrid.demos.photoservicereg.stubs.types.RegistrationException;

public class PhotoSharingRegistrationPanel {

	private PhotoSharingRegistrationClient client;

	public PhotoSharingRegistrationPanel(PhotoSharingRegistrationClient client) {
		this.client = client;
	}
	public JPanel getPanel() {
		JPanel panel = new JPanel();
		String text = "Photo Sharing service host identity: ";
		JLabel label = new JLabel(text);
		JTextField field = new JTextField(50);
		panel.add(label);
		panel.add(field);
		JButton button = new JButton("Register");
		RegistrationAction action = new RegistrationAction(panel, this.client, field);
		button.addActionListener(action);
		panel.add(button);
		return panel;
	}
	
	static class RegistrationAction implements ActionListener {
		
		private JTextField identityField;
		private PhotoSharingRegistrationClient client;
		private JComponent parentComponent;
		
		public RegistrationAction(JComponent parentComponent, PhotoSharingRegistrationClient client, JTextField identityFIeld) {
			this.parentComponent = parentComponent;
			this.client = client;
			this.identityField = identityFIeld;
			
		}
		public void actionPerformed(ActionEvent e) {
			try {
				this.client.registerPhotoSharingService(this.identityField.getText());
				String msg = this.identityField.getText() + " successfully registered";
				String title = "Registration Successful";
				JOptionPane.showMessageDialog(parentComponent, msg, title, JOptionPane.INFORMATION_MESSAGE);
			} catch (RegistrationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws MalformedURIException, RemoteException {
		JFrame frame = new JFrame();
		String serviceURL = "https://tutorials.training.cagrid.org:8444/wsrf/services/cagrid/PhotoSharingRegistration";
		PhotoSharingRegistrationClient client = new PhotoSharingRegistrationClient(serviceURL);
		PhotoSharingRegistrationPanel panel = new PhotoSharingRegistrationPanel(client);
		frame.getContentPane().add(panel.getPanel());
		frame.pack();
		frame.show();
	}
}
