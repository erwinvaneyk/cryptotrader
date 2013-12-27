package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class LogTest {
	
	public static void main(String[] args) {
	    // print logback's internal status
	    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	    StatusPrinter.print(lc);
	    
	    // Test logger
	    System.out.println(LogTest.class);
		Logger logger = LoggerFactory.getLogger(LogTest.class);
		logger.debug("Hello World!");
		// Test 2 - write to file
		Logger logger2 = LoggerFactory.getLogger("exchanges.io.btce");
		logger2.debug("Hello World!");
	}
}
