import com.google.gson.JsonObject;


public class ExchangeBTCE extends Exchange implements IExchange {
	protected String BASE_URL = "https://btc-e.com/api/2/";
	
	@Override
	public void getInfo() throws ExchangeException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Pair updatePair(Pair pair) throws ExchangeException {
		String html = HTTPRetriever(BASE_URL + "ltc_usd/ticker");
		JsonObject ticker = (JsonObject) getJson(html).get("ticker");
		
		// TODO: update old values in 'pair'
		System.out.println("buy  : " + ticker.get("buy").getAsDouble());
		System.out.println("sell : " + ticker.get("sell"));
		
		return null;
	}

	@Override
	public boolean isValidPair(Pair pair) throws ExchangeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int placeOrder(Pair pair, String type, double rate, double amount)
			throws ExchangeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean cancelOrder(int orderId) throws ExchangeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getActiveOrders() throws ExchangeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTransactionHistory() throws ExchangeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTradeHistory(Pair pair) throws ExchangeException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		ExchangeBTCE ex = new ExchangeBTCE();
		try {
			ex.updatePair(null);
		} catch (ExchangeException e) {
			e.printStackTrace();
		}
	}
}
