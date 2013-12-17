package models;

/**
 * Model containing characteristics of a currency.
 * Similar to java.util.Currency, which is not used due to lack of extension to
 * 	non-standard currencies.
 * @author Erwin
 *
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
	 * Retrieves the CurrencyType of the ISOCode.
	 * @param ISOCode The unique ISOCode of the relevant CurrencyType 
	 * @return The corresponding CurrencyType to the ISOCode (if available)
	 */
	public static CurrencyType getInstance(String ISOCode) { 
		//TODO: import from settings/database (or differentiate classes?)
		return null;
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
	
	public String ToString() {
		return this.getName() + "(" + this.getISOCode() + ")";
	}
}
