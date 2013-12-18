package test.models;

import static org.junit.Assert.*;
import models.CurrencyType;
import models.PairType;

import org.junit.Test;

public class PairTypeTest {

	private CurrencyType getType1() {
		return new CurrencyType("usd","dollar","$",2);
	}

	private CurrencyType getType2() {
		return new CurrencyType("eur","euro","E",2);
	}
	
	@Test
	public void testPairType() {
		PairType ptype = new PairType(this.getType1(), this.getType2());
		assertEquals(ptype.getBase(), this.getType1());
		assertEquals(ptype.getCounter(), this.getType2());
	}
	
	@Test(expected = AssertionError.class)
	public void testPairTypeNull1() {
		new PairType(null, this.getType2());
	}

	@Test(expected = AssertionError.class)
	public void testPairTypeNull2() {
		new PairType(this.getType1(), null);
	}
	
	@Test
	public void testToString() {
		PairType ptype = new PairType(this.getType1(), this.getType2());
		assertNotNull(ptype.toString());
	}

}
