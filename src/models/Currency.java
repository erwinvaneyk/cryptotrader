package models;

/**
 * Model containing info about the quantity of a currency.
 * Naming conflict with java.util.Currency. The util.Currency will not
 * 	be used due to lacking functionality. So no problems there.
 * @author Erwin
 *
 */
public class Currency {
	
	/**
	 * The amount available in this currency.
	 */
	private double amount;
	
	/**
	 * The currency-type of this currency. (e.g. euro)
	 */
	private CurrencyType type;
	
	/**
	 * Constructor
	 * Should look like: Currency(100,EUR) 
	 * @param amount Amount of the currency.
	 * @param type The type of the currency.
	 */
	public Currency(double amount, CurrencyType type) {
		assert type != null;
		// Should we allow negative amounts?
		this.amount = amount;
		this.type = type;
	}
	
	/**
	 * Rounds the amount to the significance of the CurrencyType.
	 * @return rounded amount
	 */
	public double getRoundedAmount() {
		int s = this.getType().getSignificance();
		return (double)Math.round(this.amount * Math.pow(10,s))/Math.pow(10,s);
	}
	
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public CurrencyType getType() {
		return type;
	}

	public String toString() {
		return this.getRoundedAmount() + " " + this.getType().getISOCode();
	}
	
}
