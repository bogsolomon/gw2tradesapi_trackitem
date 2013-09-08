package ca.bsolomon.gw2trade.ui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import ca.bsolomon.gw2event.api.dao.TradeItem;
import ca.bsolomon.gw2trade.dao.TrackedListingChange;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class TrackerPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	
	private GW2TradeMainWindow mainWindow;
	private JTextField searchTF;
	private JComboBox<TradeItem> itemCB;
	private TrackedSales trackedSales;
	
	public TrackerPanel(GW2TradeMainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setLayout(new MigLayout("", "[grow][grow]", "[][][grow]"));
		
		JLabel lblSearch = new JLabel("Search:");
		add(lblSearch, "cell 0 0,alignx trailing");
		
		searchTF = new JTextField();
		searchTF.addActionListener(this);
		add(searchTF, "cell 1 0,growx");
		searchTF.setColumns(10);
		
		JLabel lblItems = new JLabel("Items:");
		add(lblItems, "cell 0 1,alignx trailing");
		
		itemCB = new JComboBox<>();
		itemCB.addActionListener(this);
		add(itemCB, "cell 1 1,growx");
		
		trackedSales = new TrackedSales();
		add(trackedSales, "cell 0 2 2 1,grow");
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(searchTF)) {
			String searchText = searchTF.getText();
			
			List<TradeItem> items = mainWindow.getConn().search(searchText);
			
			itemCB.setModel(new DefaultComboBoxModel<TradeItem>(items.toArray(new TradeItem[items.size()])));
			mainWindow.getConn().setSelectedItem(items.get(0));
			trackedSales.clear();
		} else if (source.equals(itemCB)) {
			TradeItem selectedItem = (TradeItem)itemCB.getSelectedItem();
			mainWindow.getConn().setSelectedItem(selectedItem);
			trackedSales.clear();
		}
	}

	public void addChange(List<TrackedListingChange> changes) {
		trackedSales.addChange(changes, mainWindow.getConn().getSellListing(), mainWindow.getConn().getBuyListing());
	}
}
