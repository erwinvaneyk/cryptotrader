package test.java.models;

import static org.junit.Assert.*;
import main.models.Currency;
import main.models.CurrencyType;

import org.junit.Test;

public class CurrencyTest {

	private CurrencyType getType() {
		return new CurrencyType("usd","dollar","$",2);
	}
	
	private double getEpsilon(Currency cur) {
		return Math.pow(10, -cur.getType().getSignificance()-1);
	}
	
	@Test
	public void testCurrency() {
		Currency cur = new Currency(100, this.getType());
		assertEquals(cur.getAmount(), 100, this.getEpsilon(cur));
		assertEquals(cur.getType(), this.getType());
	}
	
	@Test(expected = AssertionError.class)
	public void testCurrencyNull() {
		new Currency(100, null);	
	}

	@Test
	public void testGetRoundedAmount() {
		Currency cur = new Currency(100.127, this.getType());
		assertEquals(cur.getRoundedAmount(), 100.13, this.getEpsilon(cur));
	}
	
	@Test
	public void testSetAmount() {
		Currency cur = new Currency(100, this.getType());
		cur.setAmount(200);
		assertEquals(cur.getAmount(), 200, this.getEpsilon(cur));
	}

	@Test
	public void testToString() {
		Currency cur = new Currency(100, this.getType());
		assertNotNull(cur.toString());
	}

}
