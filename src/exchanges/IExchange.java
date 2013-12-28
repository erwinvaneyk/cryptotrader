package exchanges;
import models.Order;
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
	 * Cancels an order.
	 * @param orderId Order id.
	 * @return true iff the order was successfully canceled.
	 * @throws ExchangeException
	 */
	public void cancelOrder(int orderId) throws ExchangeException;

	/**
	 * Gets all active orders.
	 * @throws ExchangeException
	 */
	public Order[] getActiveOrders() throws ExchangeException;

	/**
	 * Gets the transaction history.
	 * @throws ExchangeException
	 */
	// TODO change return type
	public void getTransactionHistory() throws ExchangeException;

	/**
	 * Gets the trade history.
	 * @return 
	 * @throws ExchangeException
	 */
	public Order[] getTradeHistory() throws ExchangeException;
	
	/**
	 * Places a new Sell order.
	 * @param pair Pair to use.
	 * @param rate The rate to buy/sell.
	 * @param amount Amount to buy/sell.
	 * @return order id.
	 * @throws ExchangeException
	 */
	public int sellOrder(Pair pair,  double rate, double amount) throws ExchangeException;
	
	/**
	 * Places a new Buy order.
	 * @param pair Pair to use.
	 * @param rate The rate to buy/sell.
	 * @param amount Amount to buy/sell.
	 * @return order id.
	 * @throws ExchangeException
	 */
	public int buyOrder(Pair pair,  double rate, double amount) throws ExchangeException;
}
