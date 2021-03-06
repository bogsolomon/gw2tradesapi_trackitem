package ca.bsolomon.gw2trade.dao;

import java.text.DecimalFormat;

public class TrackedListingChange {

	private int count;
	private String name;
	private int price;
	private boolean sale; 
	private boolean added;
	
	private static DecimalFormat myFormatter = new DecimalFormat("##");
	
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
			return count+" "+name+" listed for "+customFormat(price);
		else
			return count+" "+name+" bought for "+customFormat(price);
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
	static public String customFormat(double value) {
		myFormatter.setMinimumIntegerDigits(2);  
		return myFormatter.format((int)value/10000)+"."+myFormatter.format((int)(value/100)%100)+"."+myFormatter.format(value%100);
	}
}
