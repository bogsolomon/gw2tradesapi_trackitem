package ca.bsolomon.gw2trade.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.bsolomon.gw2trade.dao.TrackedListingChange;
import ca.bsolomon.gw2trade.logic.ConfigReader;
import ca.bsolomon.gw2trade.logic.GW2Connection;
import ca.bsolomon.gw2trade.logic.TrackedItemSwingWorker;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class GW2TradeMainWindow extends JFrame implements WindowListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GW2Connection conn = new GW2Connection();
	private LoginPanel loginPanel;
	private TrackerPanel trackerPanel;
	
	public GW2TradeMainWindow() {
		addWindowListener(this);
		loginPanel = new LoginPanel(this);
		
		if (ConfigReader.readConfig()) {
			if (conn.login(ConfigReader.username, ConfigReader.password)) {
				loggedIn();
			} else {
				loginPanel.setError("Config Login info wrong");
				getContentPane().add(loginPanel);
			} 
		} else {
			getContentPane().add(loginPanel);
		}
	}

	public static void main(String[] args) {
		GW2TradeMainWindow window = new GW2TradeMainWindow();
		window.setPreferredSize(new Dimension(640, 480));
		window.pack();
		window.setVisible(true);
		
		TrackedItemSwingWorker worker = new TrackedItemSwingWorker(window);
		worker.execute();
	}

	public GW2Connection getConn() {
		return conn;
	}

	public void loggedIn() {
		loginPanel.setVisible(false);
		getContentPane().remove(loginPanel);
		
		trackerPanel = new TrackerPanel(this);
		trackerPanel.setVisible(true);
		getContentPane().add(trackerPanel);
	}
	public void windowActivated(WindowEvent e) {
	}
	public void windowClosed(WindowEvent e) {
	}
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent e) {
	}
	public void windowDeiconified(WindowEvent e) {
	}
	public void windowIconified(WindowEvent e) {
	}
	public void windowOpened(WindowEvent e) {
	}

	public void addChange(List<TrackedListingChange> changes) {
		trackerPanel.addChange(changes);
	}
}
