package main.models;

import java.util.ArrayList;

import main.exchanges.IExchange;

/**
 * The balance containing the funds and orders of associated with an exchange.
 * @author Erwin
 *
 */
public class Balance {
	/**
	 * The relevant exchange.
	 */
	private IExchange exchange;
	
	/**
	 * List of the funds available on the exchange.
	 */
	private ArrayList<Currency> funds;
	
	/**
	 * List of the active orders on the exchange.
	 */
	private ArrayList<Order> openOrders;

	/**
	 * Constructor
	 * @param exchange The exchange of the balance
	 * @param fillBalance If true, the constructor attempts to fill the balance with info from the exchange.
	 */
	public Balance(IExchange exchange) {
		this.funds = new ArrayList<Currency>();
		this.openOrders = new ArrayList<Order>();
		this.exchange = exchange;
	}

	public Object getExchange() {
		return exchange;
	}

	public ArrayList<Currency> getFunds() {
		return funds;
	}

	public ArrayList<Order> getOpenOrders() {
		return openOrders;
	}

	public void setFunds(ArrayList<Currency> funds) {
		this.funds = funds;
	}

	public void setOpenOrders(ArrayList<Order> openOrders) {
		this.openOrders = openOrders;
	}

	@Override
	public String toString() {
		return "Balance [exchange=" + exchange + ", funds=" + funds
				+ ", openOrders=" + openOrders + "]";
	}
	
	
	
	
}
