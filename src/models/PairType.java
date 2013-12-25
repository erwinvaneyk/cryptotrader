package models;

import java.io.IOException;

/**
 * Model for holding a specific currency pair.
 * @author Erwin
 * 
 * Currency pair: http://en.wikipedia.org/wiki/Currency_pair
 */
public class PairType {
	
	/**
	 * The base currency (or transaction currency) is the normalized currency.
	 * EUR/USD -> EUR is the base currency 
	 */
	private CurrencyType base;
	
	/**
	 * The counter currency (or quote currency) is the currency used as a reference.
	 * EUR/USD -> USD is the counter currency.
	 */
	private CurrencyType counter;
	
	/**
	 * Separator between the base and counter currency-types when formatting.
	 */
	public static String SEPARATOR = "_";
	
	/**
	 * Constructor for a Pair-object
	 * @param base The type of currency acting as the base currency.
	 * @param counter The type of currency acting as the counter currency.
	 */
	public PairType(CurrencyType base, CurrencyType counter) {
		assert base != null && counter != null;
		this.base = base;
		this.counter = counter;
	}
	
	/**
	 * Constructor for a Pair-object, using a shortcut, which instantly generates 
	 * 	the CurrencyTypes needed. (May cause redundant objects!) 
	 * @param base The type of currency acting as the base currency.
	 * @param counter The type of currency acting as the counter currency.
	 * @throws IOException If unknown currency has been queried.
	 */
	public PairType(String base, String counter) throws IOException {
		assert base != null && counter != null;
		this.base = CurrencyType.getInstance(base);
		this.counter = CurrencyType.getInstance(counter);
		
	}
	
	/**
	 * Constructor for a Pair-object, using another(!) shortcut, which instantly generates 
	 * 	the CurrencyTypes needed. (May cause redundant objects!) 
	 * @param base The type of currency acting as the base currency.
	 * @param counter The type of currency acting as the counter currency.
	 * @throws IOException If unknown currency has been queried.
	 */	
	public PairType(String composed) throws IOException {
		assert composed != null;
		String[] types = composed.split(PairType.SEPARATOR);
		this.base = CurrencyType.getInstance(types[0].toLowerCase());
		this.counter = CurrencyType.getInstance(types[1].toLowerCase());
		
	}
	
	public CurrencyType getBase() {
		return base;
	}

	public CurrencyType getCounter() {
		return counter;
	}
	
	public String toString() {
		return this.getBase() + PairType.SEPARATOR + this.getCounter();
	}
}
