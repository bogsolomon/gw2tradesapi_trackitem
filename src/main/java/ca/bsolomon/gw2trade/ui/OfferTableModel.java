package ca.bsolomon.gw2trade.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ca.bsolomon.gw2event.api.dao.TradeListing;

public class OfferTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] columnNames = new String[] {
		    "Price", "Quantity", "Listings"
		  };
	
	private List<TradeListing> offers = new ArrayList<>();
	
	@Override
	public int getRowCount() {
		return offers.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int col) { return columnNames[col]; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TradeListing offer = offers.get(rowIndex);
		
		switch(columnIndex) {
		case 0: return offer.getUnit_price();
		case 1: return offer.getQuantity();
		case 2: return offer.getListings();
		default: return null;
		}
	}

	public List<TradeListing> getOffers() {
		return offers;
	}

	public void setOffers(List<TradeListing> offers) {
		this.offers = offers;
	}

}
