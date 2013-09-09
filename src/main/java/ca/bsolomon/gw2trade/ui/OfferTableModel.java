package ca.bsolomon.gw2trade.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ca.bsolomon.gw2event.api.dao.TradeListing;

public class OfferTableModel extends AbstractTableModel {

	protected int pageSize = 20;

	protected int pageOffset = 0;

	private static DecimalFormat myFormatter = new DecimalFormat("##");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] columnNames = new String[] { "Price", "Quantity",
			"Listings" };

	private List<TradeListing> offers = new ArrayList<>();

	@Override
	public int getRowCount() {
		int remainingSize = offers.size() - pageSize*pageOffset;

		return Math.min(pageSize, remainingSize);
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TradeListing offer = offers.get(rowIndex+(pageOffset*pageSize));

		switch (columnIndex) {
		case 0:
			return customFormat(offer.getUnit_price());
		case 1:
			return offer.getQuantity();
		case 2:
			return offer.getListings();
		default:
			return null;
		}
	}

	public List<TradeListing> getOffers() {
		return offers;
	}

	public void setOffers(List<TradeListing> offers) {
		this.offers = offers;
	}

	// Update the page offset and fire a data changed (all rows).
	public void pageDown() {
		if (pageOffset < getPageCount() - 1) {
			pageOffset++;
			fireTableDataChanged();
		}
	}

	// Update the page offset and fire a data changed (all rows).
	public void pageUp() {
		if (pageOffset > 0) {
			pageOffset--;
			fireTableDataChanged();
		}
	}

	public int getPageCount() {
		return (int) Math.ceil((double) offers.size() / pageSize);
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	
	public static String customFormat(double value) {
		myFormatter.setMinimumIntegerDigits(2);  
		return myFormatter.format((int)value/10000)+"."+myFormatter.format((int)(value/100)%100)+"."+myFormatter.format(value%100);
	}
}
