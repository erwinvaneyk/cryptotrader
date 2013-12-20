package models;

public class Order {
	/**
	 * The exchange-bound id of the order. 
	 */
	private long id;
	
	/**
	 *
	 */
	private PairType pairType;
	
	private int type; //BUY / SELL
	
	private Currency amount;
	
	private double rate;
	
	private long timestamp;

	public Order(long id, PairType pairType, int type, Currency amount, double rate, long timestamp) {
		this.id = id;
		this.pairType = pairType;
		this.type = type;
		this.amount = amount;
		this.rate = rate;
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public PairType getPairType() {
		return pairType;
	}

	public int getType() {
		return type;
	}

	public Currency getAmount() {
		return amount;
	}

	public double getRate() {
		return rate;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public String toString() {
		return this.getType() + " " + this.getAmount() + " @ " + this.getRate() + " " + this.getPairType();
	}
}
