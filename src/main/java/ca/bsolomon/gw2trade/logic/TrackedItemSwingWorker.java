package ca.bsolomon.gw2trade.logic;

import java.util.List;

import javax.swing.SwingWorker;

import ca.bsolomon.gw2event.api.dao.TradeItem;
import ca.bsolomon.gw2event.api.dao.TradeListing;
import ca.bsolomon.gw2trade.dao.TrackedListingChange;
import ca.bsolomon.gw2trade.ui.GW2TradeMainWindow;

public class TrackedItemSwingWorker extends SwingWorker<Void, TrackedListingChange> {

	private GW2TradeMainWindow mainWindow;
	private List<TradeListing> lastSellListing;
	private List<TradeListing> lastBuyListing;
	private TradeItem item;
	
	public TrackedItemSwingWorker(GW2TradeMainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		while (!isCancelled()) {
			if (mainWindow.getConn().getSelectedItem() != null && !mainWindow.getConn().getSelectedItem().equals(item)) {
				item = mainWindow.getConn().getSelectedItem();
				lastSellListing = null;
				lastBuyListing = null;
			}
			
			List<TradeListing> sellListing = mainWindow.getConn().getSellListings();
			List<TradeListing> buyListing = mainWindow.getConn().getBuyListings();	
			
			if (lastSellListing != null) {
				int index = sellListing.indexOf(lastSellListing.get(0));
				
				if (index != -1) {
					for (int i=0;i<index;i++) {
						TradeListing listing = sellListing.get(0);
						
						System.out.println("Added "+listing.getQuantity()+" - "+listing.getUnit_price());
						publish(new TrackedListingChange(listing.getQuantity(), item.getName(), listing.getUnit_price()));
					}
					
					int diff = sellListing.get(index).getQuantity() - lastSellListing.get(0).getQuantity();
					
					if (diff > 0) {
						System.out.println("Added "+diff+" - "+lastSellListing.get(0).getUnit_price());
						publish(new TrackedListingChange(diff, item.getName(), lastSellListing.get(0).getUnit_price()));
					}
				} else {
					
				}
			}
			
			lastSellListing = sellListing;
			
			Thread.sleep(5000);
		}
		
		return null;
	}
	
	@Override
	protected void process(List<TrackedListingChange> changes) {
		mainWindow.addChange(changes);
	}
}
