import models.Pair;

/**
 * @author Tim Rensen
 */
public interface IExchange {
	
	public void getInfo() throws ExchangeException;
	
	/**
	 * Updates a pair to the lastest values.
	 * @param pair Pair to be updated.
	 * @return Same Pair object as the parameter.
	 * @throws ExchangeException
	 */
	public Pair updatePair(Pair pair) throws ExchangeException;

	/**
	 * Determines if this Exchange supports Pair pair.
	 * @param pair Pair to be checked.
	 * @return true iff the Pair is supported.
	 * @throws ExchangeException
	 */
	public boolean isValidPair(Pair pair) throws ExchangeException;

	/**
	 * Places a new order.
	 * @param pair Pair to use.
	 * @param type Transaction type: "sell" or "buy"
	 * @param rate The rate to buy/sell.
	 * @param amount Amount to buy/sell.
	 * @return order id.
	 * @throws ExchangeException
	 */
	public int placeOrder(Pair pair, String type, double rate, double amount) throws ExchangeException;

	/**
	 * Cancels an order.
	 * @param orderId Order id.
	 * @return true iff the order was successfully canceled.
	 * @throws ExchangeException
	 */
	public boolean cancelOrder(int orderId) throws ExchangeException;

	/**
	 * Gets all active orders.
	 * @throws ExchangeException
	 */
	// TODO change return type (i.e. Order[])
	public void getActiveOrders() throws ExchangeException;

	/**
	 * Gets the transaction history.
	 * @throws ExchangeException
	 */
	// TODO change return type
	public void getTransactionHistory() throws ExchangeException;

	/**
	 * Gets the trade history.
	 * @throws ExchangeException
	 */
	// TODO change return type
	public void getTradeHistory(Pair pair) throws ExchangeException;
}
