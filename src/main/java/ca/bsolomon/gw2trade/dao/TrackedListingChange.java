package ca.bsolomon.gw2trade.dao;

public class TrackedListingChange {

	private int count;
	private String name;
	private int price;
	private boolean sale; 
	private boolean added;
	
	public TrackedListingChange(int count, String name, int price, boolean sale, boolean added) {
		super();
		this.count = count;
		this.name = name;
		this.price = price;
		this.sale = sale;
		this.added = added;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String toString() {
		if (added)
			return count+" "+name+" listed for "+price/10000+"."+(price/100)%100+"."+price%100;
		else
			return count+" "+name+" bought for "+price;
	}

	public boolean isSale() {
		return sale;
	}

	public void setSale(boolean sale) {
		this.sale = sale;
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}
}
