package ca.bsolomon.gw2trade.logic;

import java.util.ArrayList;
import java.util.List;

import ca.bsolomon.gw2event.api.GW2TradeAPI;
import ca.bsolomon.gw2event.api.dao.TradeItem;
import ca.bsolomon.gw2event.api.dao.TradeListing;

public class GW2Connection {

	private GW2TradeAPI api = new GW2TradeAPI();
	private TradeItem selectedItem;

	public boolean login(String username, String password) {
		return api.login(username, password);
	}

	public List<TradeItem> search(String searchText) {
		return api.searchItems(searchText);
	}

	public void setSelectedItem(TradeItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<TradeListing> getSellListings() {
		if (selectedItem != null) {
			return api.getSellListings(selectedItem);
		}
		
		return new ArrayList<>();
	}
	
	public List<TradeListing> getBuyListings() {
		if (selectedItem != null) {
			return api.getBuyListings(selectedItem);
		}
		
		return new ArrayList<>();
	}

	public TradeItem getSelectedItem() {
		return selectedItem;
	}
}
