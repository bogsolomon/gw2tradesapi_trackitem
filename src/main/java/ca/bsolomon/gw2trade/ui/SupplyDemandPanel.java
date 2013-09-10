package ca.bsolomon.gw2trade.ui;

import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;

import ca.bsolomon.gw2event.api.dao.TradeListing;

public class SupplyDemandPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private JLabel demandLabel;
	private JLabel supplyLabel;
	
	public SupplyDemandPanel() {
		setLayout(new MigLayout("", "[][]", "[][]"));
		
		JLabel lblNewLabel = new JLabel("Supply");
		add(lblNewLabel, "cell 0 0");
		
		supplyLabel = new JLabel("");
		add(supplyLabel, "cell 1 0");
		
		JLabel lblNewLabel_2 = new JLabel("Demand");
		add(lblNewLabel_2, "cell 0 1");
		
		demandLabel = new JLabel("");
		add(demandLabel, "cell 1 1");

	}

	public void clear() {
		demandLabel.setText("");
		supplyLabel.setText("");
	}

	public void setValues(List<TradeListing> sellListing,
			List<TradeListing> buyListing) {
		int sellQuantity = 0;
		
		for (TradeListing listing:sellListing) {
			sellQuantity+=listing.getQuantity();
		}
		supplyLabel.setText(""+sellQuantity);
		
		int buyQuantity = 0;
		
		for (TradeListing listing:buyListing) {
			buyQuantity+=listing.getQuantity();
		}
		demandLabel.setText(""+buyQuantity);
	}

}
