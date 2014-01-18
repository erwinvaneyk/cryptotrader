package main.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Model containing characteristics of a currency.
 * @author Erwin
 *
 * Similar to java.util.Currency, which is not used due to lack of extension to
 * 	non-standard currencies.
 */
public class CurrencyType {
	
	/**
	 * The unique ISO Currency standard code of the currency. (e.g. EUR). If
	 * no currencyType doesn't exist in ISO 4217, use the common used code.
	 * Currency ISO 4217 standard: http://en.wikipedia.org/wiki/ISO_currency_code  
	 */
	private String ISOCode;
	
	/**
	 * The full (lowercase) name of the CurrencyType. (e.g. euro)
	 */
	private String name;
	
	/**
	 * The symbol that should be displayed with amounts. (e.g. 
	 */
	private String symbol;
	
	/**
	 * The significance of amounts that should be displayed. 7.42 EUR has a
	 * significance of 2.
	 */
	private int significance;
	
	/**
	 * Path to the file containing the currency-types. (temporary)
	 * Using: http://docs.oracle.com/javase/6/docs/api/java/util/Properties.html
	 */
	public static String SETTINGS_PATH = "data/currencytypes.cfg"; 
	
	/**
	 * Retrieves the CurrencyType of the ISOCode.
	 * @param ISOCode The unique ISOCode of the relevant CurrencyType 
	 * @return The corresponding CurrencyType to the ISOCode (if available)
	 * @throws IOException Any problems encountered with reading the file.
	 */
	public static CurrencyType getInstance(String ISOCode) throws IOException { 
		Properties prop = new Properties();
		prop.load(new FileInputStream(CurrencyType.SETTINGS_PATH));
		String name = prop.getProperty(ISOCode + "_name");
		if (name == null) throw new IOException("Undefined ISOCode");
		String symbol = prop.getProperty(ISOCode + "_symbol");
		int sign = Integer.parseInt(prop.getProperty(ISOCode + "_sign"));
		return new CurrencyType(ISOCode, name, symbol, sign);
	}
	
	/**
	 * Constructor for the object CurrencyType.
	 */
	public CurrencyType(String code, String name, String symbol, int sign) {
		assert code != null && name != null && symbol != null;
		assert isISOCode(code);
		assert sign >= 0 && sign <= 16;
		this.name = name;
		this.ISOCode = code.toUpperCase();
		this.symbol = symbol;
		this.significance = sign;
	}
	
	/**
	 * Checks if code complies with the requirements of the standard
	 * @param code The code to be checked according to ISO 2417
	 * @return true if the code is compatible with ISO 2417
	 */
	private boolean isISOCode(String code) {
		return code.length() == 3;
	}
	
	public int getSignificance() {
		return significance;
	}
	
	public String getISOCode() {
		return ISOCode;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyType other = (CurrencyType) obj;
		if (ISOCode == null) {
			if (other.ISOCode != null)
				return false;
		} else if (!ISOCode.equals(other.ISOCode))
			return false;
		return true;
	}

	public String toString() {
		return this.getISOCode();
	}
}
