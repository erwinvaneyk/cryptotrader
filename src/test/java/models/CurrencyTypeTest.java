package test.java.models;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import main.models.CurrencyType;

import org.junit.Test;

public class CurrencyTypeTest {

	@Test
	public void getInstanceTestNotNull() throws IOException {
		CurrencyType type = CurrencyType.getInstance("usd");
		assertNotNull(type);
	}
	
	@Test(expected = IOException.class)
	public void getInstanceTestException() throws IOException {
		CurrencyType.getInstance("NUL");
	}
	
	@Test
	public void constructorTest() {
		CurrencyType type = new CurrencyType("usd","dollar","$",2);
		assertNotNull(type);
	}

	@Test(expected = AssertionError.class)
	public void constructorTestBadSign1() {
		new CurrencyType("usd","dollar","$",-1);
	}
	
	@Test(expected = AssertionError.class)
	public void constructorTestBadSign2() {
		new CurrencyType("usd","dollar","$",20);
	}

	@Test(expected = AssertionError.class)
	public void constructorTestBadCode() {
		new CurrencyType("usdfoo","dollar","$",2);
	}
	
	@Test(expected = AssertionError.class)
	public void constructorTestNull1() {
		new CurrencyType(null,"dollar","$",2);
	}
	
	@Test(expected = AssertionError.class)
	public void constructorTestNull2() {
		new CurrencyType("usd",null,"$",2);
	}
	
	@Test(expected = AssertionError.class)
	public void constructorTestNull3() {
		new CurrencyType("usd","dollar",null,2);
	}
	
	@Test
	public void getTest() throws IOException {
		String code = "usd";
		Properties prop = new Properties();
		prop.load(new FileInputStream(CurrencyType.SETTINGS_PATH));
		CurrencyType type = CurrencyType.getInstance(code);
		assertEquals(type.getISOCode(),code.toUpperCase());
		assertEquals(type.getName(),prop.getProperty(code + "_name"));
		assertEquals(type.getSignificance(),Integer.parseInt(prop.getProperty(code + "_sign")));
		assertEquals(type.getSymbol(),prop.getProperty(code + "_symbol"));
	}
}
