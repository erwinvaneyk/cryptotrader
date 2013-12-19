import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
public class ExchangeBTCE extends Exchange implements IExchange {
	protected String BASE_URL = "https://btc-e.com/";

	// TODO config file?
	private String key = "API KEY";
	private String secret = "SECRET KEY";


	@Override
	public void getInfo() throws ExchangeException {

		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "getInfo");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJson(html));
		System.out.println(html);
	}


	@Override
	public Pair updatePair(Pair pair) throws ExchangeException {		
		String html = HTTPRetriever(BASE_URL + "api/2/ltc_usd/ticker", null);		
		JsonObject ticker = (JsonObject) getJson(html).get("ticker");

		// TODO: update old values in 'pair'
		System.out.println("buy  : " + ticker.get("buy"));
		System.out.println("sell : " + ticker.get("sell"));

		return null;
	}


	@Override
	public boolean isValidPair(Pair pair) throws ExchangeException {
		return false;
	}


	@Override
	public int placeOrder(Pair pair, String type, double rate, double amount)
			throws ExchangeException {
		return 0;
	}


	@Override
	public boolean cancelOrder(int orderId) throws ExchangeException {
		return false;
	}


	@Override
	public void getActiveOrders() throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "ActiveOrders");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJson(html));
		System.out.println(html);
	}


	@Override
	public void getTransactionHistory() throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "TransHistory");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJson(html));
		System.out.println(html);
	}


	@Override
	public void getTradeHistory(Pair pair) throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "TradeHistory");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJson(html));
		System.out.println(html);
	}


	protected void validateRequest(JsonObject obj) throws ExchangeException {
		if(obj != null && obj.get("success") != null) {
			if(obj.get("success").getAsInt() == 0)
				throw new ExchangeException(obj.get("error").getAsString());
		} else {
			throw new ExchangeException("BTC-E did not answer in proper format.");
		}
	}
	
	@Override
	protected void configRequest(URLConnection connection, Map<String, String> args) throws ExchangeException {

		if(args != null) {
			args.put("nonce", getNonce() + "");

			String postData = "";
			for(Map.Entry<String, String> entry : args.entrySet()) {
				postData += entry.getKey() + "=" + entry.getValue() + "&";
			}
			// remove last '&'
			if(postData.length() > 0) // <- not necessary
				postData = postData.substring(0, postData.length()-1);

			// generate Sign
			String sign = hmacDigest(postData, secret, "HmacSHA512");

			// add headers
			connection.setRequestProperty("Key", key);
			connection.setRequestProperty("Sign", sign);

			try {
				// send post data
				connection.setDoOutput(true);
				OutputStreamWriter out = new OutputStreamWriter(
						connection.getOutputStream());

				out.write(postData);
				out.close();

			} catch(IOException e) {
				throw new ExchangeException(e.getMessage());
			}
		}
	}


	public static void main(String[] args) {
		ExchangeBTCE ex = new ExchangeBTCE();
		try {
			ex.updatePair(null);
			ex.getInfo();
			ex.getTransactionHistory();
			ex.getTradeHistory(null);
		} catch (ExchangeException e) {
			e.printStackTrace();
		}
	}
}
