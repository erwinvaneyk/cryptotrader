package main.models;

/**
 * Model for holding pair-related information.
 * @author Erwin
 * 
 * For now high and low are not recorded, as this should be handled in
 * 	the client application.
 */
public class Pair {
	
	/**
	 * The type of the pair, which identifies the base and counter currencies. (e.g. EUR_USD)
	 */
	private PairType type;
	
	/**
	 * The exchange the pair is traded on.
	 */
	private Object exchange; // Object is placeholder for exchange-class
	
	/**
	 * The price (in the counter currency) for buying 1 unit of the base currency.
	 */
	private double buy;

	/**
	 * The price (in the counter currency) for selling 1 unit of the base currency. 
	 */
	private double sell;
	
	/**
	 * The price (in the counter currency) of the last trade executed on the exchange.
	 */
	private double last;
	
	/**
	 * The current outstanding volume.
	 * TODO: Unclear what/how volume should be tracker. Fix it.
	 */
	private long volume;
	
	/**
	 * The (server)time of the last update of the values in the specific pair-object. 
	 */
	private long lastUpdated;
	
	/**
	 * Constructor for a Pair-Object
	 * @param exchange The exchange on which this specific pair is being traded.
	 * @param type The type of pair.
	 */
	public Pair(Object exchange, PairType type) {
		this.exchange = exchange;
		this.type = type;
	}

	public PairType getType() {
		return type;
	}

	public Object getExchange() {
		return exchange;
	}

	public double getBuy() {
		return buy;
	}

	public double getSell() {
		return sell;
	}

	public double getLast() {
		return last;
	}

	public long getVolume() {
		return volume;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setBuy(double buy) {
		this.buy = buy;
	}

	public void setSell(double sell) {
		this.sell = sell;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}
	
	/**
	 *  LastUpdated should always be larger than the previous, as updating 
	 *   backwards in time makes no sense at all.
	 */
	public void setLastUpdated(long lastUpdated) {
		assert lastUpdated > this.lastUpdated; 
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "Pair [type=" + type + ", exchange=" + exchange + ", buy=" + buy
				+ ", sell=" + sell + ", last=" + last + ", volume=" + volume
				+ ", lastUpdated=" + lastUpdated + "]";
	}
	
	
}
