import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public abstract class Exchange {
	protected String BASE_URL = "undefined";

	protected String HTTPRetriever(String url) throws ExchangeException {
		String result = "";
		try {
			URL u = new URL(url);
			URLConnection connection = u.openConnection();

			// add headers, cookies etc
			configRequest(connection);
		
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

	protected void configRequest(URLConnection connection){
		/**
		 * connection.setDoOutput(true);
		 * OutputStreamWriter out = new OutputStreamWriter(
		 * 			connection.getOutputStream());
		 * out.write("string=" + stringToReverse);
		 * out.close();
		 */
	}
	
	protected static JsonObject getJson(String json) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObj = (JsonObject) jsonParser.parse(json);
		return jsonObj;
	}
}
