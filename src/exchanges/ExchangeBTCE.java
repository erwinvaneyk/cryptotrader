package exchanges;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.LogTest;
import models.Currency;
import models.CurrencyType;
import models.Order;
import models.OrderType;
import models.Pair;
import models.PairType;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
public class ExchangeBTCE extends Exchange implements IExchange {
	private static final String SETTINGS_PATH = "data/collectALL.keys";
	private static final String NAME = "BTC-E";
	private static final String SELL = "sell";
	private static final String BUY = "buy";

	protected String BASE_URL = "https://btc-e.com/";

	private String key;
	private String secret;
	protected int nonce;
	
	public ExchangeBTCE() throws IOException {
		super();
		Properties prop = new Properties();
		prop.load(new FileInputStream(ExchangeBTCE.SETTINGS_PATH));
		this.key = prop.getProperty("key");
		this.secret = prop.getProperty("secret");
		this.nonce = Integer.parseInt(prop.getProperty("nonce"));
		if (this.key == null || this.secret == null) throw new IOException("Key(s) invalid");
	}

	
	@Override
	public void getInfo() throws ExchangeException {

		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "getInfo");
		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJsonObject(html));
		System.out.println(html);
	}


	@Override
	public Pair updatePair(Pair pair) throws ExchangeException {
		String url = BASE_URL + 
				"api/2/" +
				getFormatPair(pair) +
				"/ticker";
		
		String html = HTTPRetriever(url, null);
		
		JsonObject ticker = (JsonObject) getJsonObject(html).get("ticker");
		
		if(ticker == null)
			throw new ExchangeException(getJsonObject(html).get("error").toString());

		pair.setBuy(ticker.get("buy").getAsDouble());
		pair.setSell(ticker.get("sell").getAsDouble());
		pair.setLast(ticker.get("last").getAsDouble());
		pair.setVolume(ticker.get("vol").getAsLong());
		pair.setLastUpdated(ticker.get("updated").getAsLong());
		return pair;
	}


	@Override
	public boolean isValidPair(Pair pair) throws ExchangeException {
		return false;
	}


	private int placeOrder(Pair pair, String type, double rate, double amount) throws ExchangeException {
		assert ExchangeBTCE.BUY.equals(type) || ExchangeBTCE.SELL.equals(type);
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "Trade");
		args.put("pair", getFormatPair(pair));
		args.put("type", type);
		args.put("rate", rate + "");
		args.put("amount", amount + "");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJsonObject(html));
		
		JsonObject ret = (JsonObject) getJsonObject(html).get("return");
		
		return ret.get("order_id").getAsInt();
	}
	
	@Override
	public int sellOrder(Pair pair, double rate, double amount)	throws ExchangeException {
		return placeOrder(pair,ExchangeBTCE.SELL,rate,amount);
	}


	@Override
	public int buyOrder(Pair pair, double rate, double amount) throws ExchangeException {
		return placeOrder(pair, ExchangeBTCE.BUY, rate, amount);
	}


	@Override
	public void cancelOrder(int orderId) throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "CancelOrder");
		args.put("order_id", orderId + "");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJsonObject(html));
	}


	@Override
	public Order[] getActiveOrders() throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "ActiveOrders");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		
		try {
			validateRequest(getJsonObject(html));
		} catch(ExchangeException e) {
			if(!"no orders".equals(e.getMessage()))
				throw e;

			return new Order[0];
		}
		
		JsonObject ret = (JsonObject) getJsonObject(html).get("return");
		
		/**
		 * ret (return) looks like:
		 * 	{
		 * 		"order_id":{
		 * 			"pair":"xxx_xxx",
		 * 			"type":"sell/buy",
		 * 			"amount": X,
		 * 			"rate": X,
		 * 			"timestamp_created": X,
		 * 			"status": X (IGNORED)
		 * 		}, ...
		 * 	}
		 */
		
		int size = ret.entrySet().size();
		Order[] result = new Order[size];

		int i = 0;
		Iterator<Entry<String, JsonElement>> it = ret.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<String, JsonElement> entry = it.next();
			// ORDER ID
			int order_id = Integer.parseInt(entry.getKey());

			JsonObject value = (JsonObject) entry.getValue();

			// PAIR
			String[] p = value.get("pair").getAsString().split("_");
			
			CurrencyType base = null, counter = null;
			try {
				base = CurrencyType.getInstance(p[0]);
				counter = CurrencyType.getInstance(p[1]);
			} catch (IOException e) {
				// TODO ExchangeException?
			}
			
			PairType pairType = new PairType(base, counter);
			Pair pair = new Pair(this, pairType);

			// TYPE
			OrderType type = OrderType.BUY;
			if(ExchangeBTCE.SELL.equals(value.get("type").getAsString()))
				type = OrderType.SELL;

			// AMOUNT
			Currency amount = new Currency(value.get("amount").getAsDouble(), base);

			// RATE
			double rate = value.get("rate").getAsDouble();

			// TIMESTAMP
			long timestamp = value.get("timestamp_created").getAsLong();

			result[i++] = new Order(order_id, pair, type, amount, rate, timestamp, false);
		}

		return result;
	}


	@Override
	public void getTransactionHistory() throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "TransHistory");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		
		try {
			validateRequest(getJsonObject(html));
		} catch(ExchangeException e) {
			if(!"no transactions".equals(e.getMessage()))
				throw e;
			
			// return Order[0]
		}
		
		System.out.println(html);
	}
	
	
	/**
	 * Gets the 'count' most recent whether or not personal trades.
	 * @param pair Pair to use.
	 * @param count number of trades to obtain.
	 * @throws ExchangeException
	 */
	public void getRecentTrades(Pair pair, int count) throws ExchangeException {
		String url = BASE_URL + 
				"api/2/" +
				getFormatPair(pair) +
				"/trades/" +
				count;
		
		String html = HTTPRetriever(url, null);
		
		JsonArray trades = getJsonArray(html);
		System.out.println(trades);
	}


	@Override
	public Order[] getTradeHistory() throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "TradeHistory");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		try {
			validateRequest(getJsonObject(html));
		} catch(ExchangeException e) {
			if(!"no trades".equals(e.getMessage()))
				throw e;
			
			return new Order[0];
		}
		
		JsonObject ret = (JsonObject) getJsonObject(html).get("return");
		
		/**
		 *  ret (return) looks like:
		 *  {
		 * 		"trade_id":{
		 * 			"pair":"xxx_xxx",
		 * 			"type":"sell/buy",
		 * 			"amount": X,
		 * 			"rate": X,
		 * 			"order_id": X, (IGNORED)
		 * 			"is_your_order": 0/1, (IGNORED)
		 * 			"timestamp": X
		 * 		}, ...
		 * 	}
		 */


		int size = ret.entrySet().size();
		Order[] result = new Order[size];

		int i = 0;
		Iterator<Entry<String, JsonElement>> it = ret.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<String, JsonElement> entry = it.next();
			// TRADE ID
			int trade_id = Integer.parseInt(entry.getKey());

			JsonObject value = (JsonObject) entry.getValue();
			
			// PAIR
			String[] p = value.get("pair").getAsString().split("_");

			CurrencyType base = null, counter = null;
			try {
				base = CurrencyType.getInstance(p[0]);
				counter = CurrencyType.getInstance(p[1]);
			} catch (IOException e) {
				// TODO ExchangeException?
			}

			PairType pairType = new PairType(base, counter);
			Pair pair = new Pair(this, pairType);

			// TYPE
			OrderType type = OrderType.BUY;
			if(ExchangeBTCE.SELL.equals(value.get("type").getAsString()))
				type = OrderType.SELL;

			// AMOUNT
			Currency amount = new Currency(value.get("amount").getAsDouble(), base);

			// RATE
			double rate = value.get("rate").getAsDouble();

			// TIMESTAMP
			long timestamp = value.get("timestamp").getAsLong();

			result[i++] = new Order(trade_id, pair, type, amount, rate, timestamp, true);
		}
		
		return result;
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
			if(postData.length() > 0)
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
	
	
	private String getFormatPair(Pair pair) {
		return pair.getType().getBase().getISOCode().toLowerCase() + "_" +
			   pair.getType().getCounter().getISOCode().toLowerCase();
	}
	
	protected long getNonce() {
		assert this.nonce >= 0;
		/* (time based nonce)
		int sign = 9; //How many digits the nonce should be
		long time = new Date().getTime();
		long mtime = (long) (Math.floor(time/Math.pow(10,sign))*Math.pow(10, sign));
		*/
		this.nonce++;
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(ExchangeBTCE.SETTINGS_PATH));
			prop.setProperty("nonce", String.valueOf(this.nonce));
			prop.store(new FileOutputStream(ExchangeBTCE.SETTINGS_PATH), "Settings");
		} catch (IOException e) {
			logger.warn("Could not save the updated nonce. (" + this.nonce + ")");
		}
		return this.nonce;
	}


	@Override
	public String getName() {
		return NAME;
	}
}
