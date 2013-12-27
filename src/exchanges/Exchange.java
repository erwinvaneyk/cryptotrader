package exchanges;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public abstract class Exchange {
	protected String BASE_URL = "undefined/";
	
	protected String HTTPRetriever(String url, Map<String, String> args) throws ExchangeException {
		String result = "";
		try {
			URL u = new URL(url);
			URLConnection connection = u.openConnection();

			// add headers, cookies etc
			configRequest(connection, args);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							connection.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				result = result.concat(line);
			}
			in.close();

		} catch(Exception e) {
			throw new ExchangeException(e.getMessage());
		}
		return result;
	}
	
	
	
	protected int getNonce() {
		return (int) new Date().getTime();
	}
	
	public abstract String getName();

	public String toString() {
		return getName();
	}
	
	protected void configRequest(URLConnection connection, Map<String, String> args) throws ExchangeException {}

	
	
	protected static JsonObject getJson(String json) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObj = (JsonObject) jsonParser.parse(json);
		return jsonObj;
	}

	
	
	protected static String hmacDigest(String msg, String keyString, String algo) {
		String digest = null;
		try {
			SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
			Mac mac = Mac.getInstance(algo);
			mac.init(key);

			byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

			StringBuffer hash = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if (hex.length() == 1) {
					hash.append('0');
				}
				hash.append(hex);
			}
			digest = hash.toString();
		} catch (UnsupportedEncodingException e) {
		} catch (InvalidKeyException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		return digest;
	}
}
