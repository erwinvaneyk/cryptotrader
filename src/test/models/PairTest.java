package test.models;

import static org.junit.Assert.*;
import models.CurrencyType;
import models.Pair;
import models.PairType;

import org.junit.Test;

public class PairTest {

	private Object getExchange() {
		return new Object();
	}
	
	private PairType getPairType() {
		CurrencyType counter = new CurrencyType("usd","dollar","$",2);
		CurrencyType base = new CurrencyType("eur","euro","E",2);
		return new PairType(base,counter); 
	}
	
	/**
	 * TODO: Test cases with working/stub exchange
	 */
	
	@Test
	public void testPair() {
		Pair pair = new Pair(this.getExchange(), this.getPairType());
		assertNotNull(pair);
	}

	@Test
	public void testToString() {
		Pair pair = new Pair(this.getExchange(), this.getPairType());
		assertNotNull(pair.toString());
	}

}
