package models;

/**
 * The Order-class contains information about a placed or executed order on a exchange
 * @author Erwin
 *
 */
public class Order {
	/**
	 * The exchange-bound id of the order. 
	 */
	private long id;
	
	/**
	 * The pair on which the order is based.
	 */
	private Pair pair;
	
	/**
	 * Buy or sell order?
	 */
	private OrderType type; //BUY / SELL
	
	/**
	 * Amount of Currency to trade.
	 */
	private Currency amount;
	
	/**
	 * The rate at which the order should be executed.
	 */
	private double rate;
	
	/**
	 * The timestamp of the creation of the order.
	 */
	private long timestamp;
	
	/**
	 * Executed?
	 */
	private boolean executed;

	/**
	 * Constructor
	 * @param id The id of the order, often provided by the exchange API.
	 * @param pair The pair related to this order.
	 * @param type Whether this is a BUY or SELL order.
	 * @param amount The amount associated with this order.
	 * @param rate The rate/price at which the order should be executed at.
	 * @param timestamp The time at which the order was placed on the exchange.
	 */
	public Order(long id, Pair pair, OrderType type, Currency amount, double rate, long timestamp, boolean executed) {
		this.id = id;
		this.pair = pair;
		this.type = type;
		this.amount = amount;
		this.rate = rate;
		this.timestamp = timestamp;
		this.executed = executed;
	}

	public long getId() {
		return id;
	}

	public Pair getPair() {
		return pair;
	}

	public OrderType getType() {
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
	
	public boolean isExecuted() {
		return executed;
	}

	public String toString() {
		return "(id: " + this.getId() + ") " + this.getType() + " " + this.getAmount() + " @ " + this.getRate() + " " + this.getPair().getType();
	}
}
