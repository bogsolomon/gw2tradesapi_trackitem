package ca.bsolomon.gw2trade.dao;

public class TrackedListingChange {

	private int count;
	private String name;
	private int price;
	
	public TrackedListingChange(int count, String name, int price) {
		super();
		this.count = count;
		this.name = name;
		this.price = price;
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
		return name+" "+count+" "+price;
	}
}
