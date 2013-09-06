package ca.bsolomon.gw2trade.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JLabel errorLabel;
	private GW2TradeMainWindow mainWindow;
	/**
	 * Create the panel.
	 */
	public LoginPanel(GW2TradeMainWindow mainWindow) {
		this.setLayout(new MigLayout("", "[32px][150px]", "[][][][]"));
		
		JLabel lblUsername = new JLabel("Username:");
		this.add(lblUsername, "cell 0 0,alignx trailing,aligny center");
		
		usernameField = new JTextField();
		this.add(usernameField, "cell 1 0,growx");
		usernameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		this.add(lblPassword, "cell 0 1,alignx trailing,aligny center");
		
		passwordField = new JPasswordField();
		this.add(passwordField, "cell 1 1,growx");
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(this);
		this.add(btnLogin, "cell 1 2,alignx left,aligny top");
		
		this.mainWindow = mainWindow;
		
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		add(errorLabel, "cell 1 3");
	}

	public void actionPerformed(ActionEvent e) {
		String password = new String(passwordField.getPassword());
		String username = usernameField.getText();
		
		if (!mainWindow.getConn().login(username, password)) {
			errorLabel.setText("Can not login. Check info.");
		} else {
			mainWindow.loggedIn();
		}
	}

	public void setError(String string) {
		errorLabel.setText(string);
	}
}
