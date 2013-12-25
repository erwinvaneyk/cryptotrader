package exchanges;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import models.CurrencyType;
import models.Pair;
import models.PairType;

import com.google.gson.JsonObject;
public class ExchangeBTCE extends Exchange implements IExchange {
	private static final String SETTINGS_PATH = "data/collectALL.keys";

	protected String BASE_URL = "https://btc-e.com/";

	private String key;
	private String secret;

	
	public ExchangeBTCE() throws IOException {
		
		name = "BTC-E";
		
		Properties prop = new Properties();
		prop.load(new FileInputStream(ExchangeBTCE.SETTINGS_PATH));
		this.key = prop.getProperty("key");
		this.secret = prop.getProperty("secret");
		if (this.key == null || this.secret == null) throw new IOException("Key(s) invalid");
	}

	
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
		String url = BASE_URL + 
				"api/2/" +
				getFormatPair(pair) +
				"/ticker";
		
		String html = HTTPRetriever(url, null);
		
		JsonObject ticker = (JsonObject) getJson(html).get("ticker");
		
		if(ticker == null)
			throw new ExchangeException(getJson(html).get("error").toString());

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


	@Override
	public int placeOrder(Pair pair, String type, double rate, double amount)
			throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "Trade");
		args.put("pair", getFormatPair(pair));
		args.put("type", type);
		args.put("rate", rate + "");
		args.put("amount", amount + "");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJson(html));
		System.out.println(html);
		
		return 0;
	}


	@Override
	public boolean cancelOrder(int orderId) throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "CancelOrder");
		args.put("order_id", orderId + "");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		validateRequest(getJson(html));
		System.out.println(html);
		
		return false;
	}


	@Override
	public void getActiveOrders() throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "ActiveOrders");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		
		try {
			validateRequest(getJson(html));
		} catch(ExchangeException e) {
			if(!"no orders".equals(e.getMessage()))
				throw e;
		}
		System.out.println(html);
	}


	@Override
	public void getTransactionHistory() throws ExchangeException {
		Map<String, String> args = new HashMap<String, String>();
		args.put("method", "TransHistory");

		String html = HTTPRetriever(BASE_URL + "tapi", args);
		
		try {
			validateRequest(getJson(html));
		} catch(ExchangeException e) {
			if(!"no transactions".equals(e.getMessage()))
				throw e;
		}
		
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
	
	
	private String getFormatPair(Pair pair) {
		return pair.getType().getBase().getISOCode().toLowerCase() + "_" +
			   pair.getType().getCounter().getISOCode().toLowerCase();
	}

	
	public static void main(String[] args) throws IOException {
		ExchangeBTCE ex  = new ExchangeBTCE();
		CurrencyType ltc = CurrencyType.getInstance("ltc");
		CurrencyType usd = CurrencyType.getInstance("usd");
		PairType ltc_usd = new PairType(ltc, usd);
		Pair pair = new Pair(ex, ltc_usd);
		
		try {
			ex.updatePair(pair);
			ex.getInfo();
			ex.getTransactionHistory();
			ex.getActiveOrders();
			/*ex.cancelOrder(order_id);
			ex.placeOrder(null, "sell", 199, 0.1);
			ex.placeOrder(pair, "sell", 199, 0.1);*/
		} catch (ExchangeException e) {
			e.printStackTrace();
		}
	}
}
