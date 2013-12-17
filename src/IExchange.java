/**
 * @author Tim Rensen
 */
public interface IExchange {
	/**
	 * Updates a pair to the lastest values.
	 * @param pair Pair to be updated.
	 * @return Same Pair object as the parameter.
	 */
	public Pair updatePair(Pair pair);
	
	/**
	 * Determines if this Exchange supports Pair pair.
	 * @param pair Pair to be checked.
	 * @return true iff the Pair is supported.
	 */
	public boolean isValidPair(Pair pair);
	
	/**
	 * Places a new order.
	 * @param pair Pair to use.
	 * @param type Transaction type: "sell" or "buy"
	 * @param rate The rate to buy/sell.
	 * @param amount Amount to buy/sell.
	 * @return true iff the order was successfully placed.
	 */
	public boolean placeOrder(Pair pair, String type, double rate, double amount);
	
	/**
	 * Cancels an order.
	 * @param orderId Order id.
	 * @return true iff the order was successfully canceled.
	 */
	public boolean cancelOrder(int orderId);
	
	/**
	 * Gets all active orders.
	 */
	// TODO change return type (i.e. Order[])
	public void getActiveOrders();
	
	/**
	 * Gets the transaction history.
	 */
	// TODO change return type
	public void getTransactionHistory();
	
	/**
	 * Gets the trade history.
	 */
	// TODO change return type
	public void getTradeHistory(Pair pair);
}
