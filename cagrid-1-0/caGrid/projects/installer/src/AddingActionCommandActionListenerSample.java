import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class AddingActionCommandActionListenerSample {
  public static void main(String args[]) {
    JFrame frame = new JFrame("Default Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    final JTextField textField = new JTextField();
    frame.add(textField, BorderLayout.NORTH);

    ActionListener actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Command: " + actionEvent.getActionCommand());
      }
    };
    CaretListener caretListener = new CaretListener(){

		public void caretUpdate(CaretEvent evt) {
			System.out.println("Text: " + textField.getText());
		}
    	
    };
    textField.addCaretListener(caretListener);
    textField.setActionCommand("Yo");
    textField.addActionListener(actionListener);

    frame.setSize(250, 150);
    frame.setVisible(true);
  }
}