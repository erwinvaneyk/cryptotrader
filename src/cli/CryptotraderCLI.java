package cli;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import models.*;
import org.apache.commons.cli.*;
import exchanges.ExchangeBTCE;

public class CryptotraderCLI {
	private ExchangeBTCE ex;
	
	private static String NAME = "Cryptotrader CLI v0.1";
	
	private boolean keeponrunning = true; // Keep program running
	
	public static void main(String[] args) {
		CryptotraderCLI ctc = new CryptotraderCLI();
		try {
			System.out.println(CryptotraderCLI.NAME);
			ctc.setup();
			ctc.run();
		} catch (IOException e) {
			System.out.println("[error] " + e.getMessage());
		}
	}
	
	
	public void setup() throws IOException {
		ex  = new ExchangeBTCE();
	}

	public void run() {
		Scanner reader = new Scanner(System.in);
		CommandLineParser parser = new BasicParser();
		while(keeponrunning) {
			System.out.print("> ");
			String input = reader.nextLine();
			String[] args = input.split(" ");
			try {			
				//TODO: Switch method/clean up
				if(args[0].equalsIgnoreCase("tick")) {
					tickAction(parser, args);
				} else if(args[0].equalsIgnoreCase("balance")) {
					balanceAction(parser, args);
				} else if(args[0].equalsIgnoreCase("orders")) {
					activeOrdersAction(parser, args);
				} else if(args[0].equalsIgnoreCase("help")) {
					helpAction(parser, args);
				} else if(args[0].equalsIgnoreCase("buy")) {
					buyAction(parser, args);
				} else if(args[0].equalsIgnoreCase("sell")) {
					sellAction(parser, args);
				} else if(args[0].equalsIgnoreCase("cancel")) {
					cancelAction(parser, args);
				} else if(args[0].equalsIgnoreCase("exit") || args[0].equalsIgnoreCase("quit")) {
					keeponrunning = false;
				} else {
					throw new Exception("unknown command");
				}

			} catch (Exception e) {
				System.out.println("[error] " + e.getMessage());
			}			
		}
		reader.close();
		System.out.println("Application closed.");
	}
	
	private void helpAction(CommandLineParser parser, String[] args) throws Exception {
		Options options = new Options();
		options.addOption("tick", false, "Retrieves the newest data for a specific pair.");
		options.addOption("balance", false, "Fetches the balance of the user on the exchange.");
		options.addOption("orders", false, "Gets the active orders of the user.");
		options.addOption("sell", false, "Sells an amount of the base currency for a specified rate.");
		options.addOption("buy", false, "Buys an amount of the base currency for a specified rate.");
		options.addOption("cancel", false, "Cancels a specific order.");
		options.addOption("help", false, "This.");
		options.addOption("exit", false, "Exit the CLI application. (Same functionality as 'quit')");
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Cryptotrader CLI", options);
	}


	private void tickAction(CommandLineParser parser, String[] args) throws Exception {
		Options options = new Options();
		options.addOption("p", "pair", true, "An (exchange-)supported pair");
		CommandLine line = parser.parse(options, args);
		if(line.hasOption("p")) {
			Pair pair = ex.updatePair(new Pair(ex, new PairType(line.getOptionValue("p"))));
			System.out.println("\t\t" + pair.getType());
			System.out.println("buy:\t\t" + pair.getBuy());
			System.out.println("sell:\t\t" + pair.getSell());
		} else {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("tick", options);
		}
	}
	
	private void balanceAction(CommandLineParser parser, String[] args) throws Exception {
		ex.getInfo();
	}
	
	private void activeOrdersAction(CommandLineParser parser, String[] args) throws Exception {
		System.out.println(Arrays.toString(ex.getActiveOrders()));
	}
	
	private void sellAction(CommandLineParser parser, String[] args) throws Exception {
		// Option setup		
		Options options = new Options();
		options.addOption("p", "pair", true, "An (exchange-)supported pair");
		options.addOption("r", "rate", true, "The rate for the sell order (1 base currency for X counter currency)");
		options.addOption("a", "amount", true, "The amount of the base currency to sell (default: 0.01)");
		CommandLine line = parser.parse(options, args);
		// If no arguments are provided, show help
		if(line.getOptions().length == 1) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("sell", options);
		} else {
			// If pair argument are missing, show error
			if((line.getOptionValue("p") == null) || (line.getOptionValue("r") == null)) 
				throw new Exception("Argument missing");
			else {
				Pair pair = ex.updatePair(new Pair(ex,new PairType(line.getOptionValue("p"))));
				double rate = Double.parseDouble(line.getOptionValue("r"));				
				double amount = Double.parseDouble(line.getOptionValue("a","0.01"));
				if(rate < (pair.getBuy() * 1.1)) throw new Exception("Sell order is too risky.");
				int id = ex.sellOrder(pair, rate, amount);
				System.out.println("Order created! ID: " + id);
			}
		}
	}
	
	private void buyAction(CommandLineParser parser, String[] args) throws Exception {
		// Option setup
		Options options = new Options();
		options.addOption("p", "pair", true, "An (exchange-)supported pair");
		options.addOption("r", "rate", true, "The rate for the buy order (1 base currency for X counter currency)");
		options.addOption("a", "amount", true, "The amount of the base currency to buy (default: 0.01)");
		CommandLine line = parser.parse(options, args);
		// If no arguments are provided, show help
		if(line.getOptions().length == 1) {
			System.out.println("1");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("buy", options);
		} else {
			// If pair argument are missing, show error
			if((line.getOptionValue("p") == null) || (line.getOptionValue("r") == null)) 
				throw new Exception("Argument missing");
			else {
				Pair pair = ex.updatePair(new Pair(ex,new PairType(line.getOptionValue("p"))));
				double rate = Double.parseDouble(line.getOptionValue("r"));				
				double amount = Double.parseDouble(line.getOptionValue("a","0.01"));
				if(rate > (pair.getSell() * 0.9)) throw new Exception("Buy order is too risky.");
				int id = ex.buyOrder(pair, rate, amount);
				System.out.println("Order created! ID: " + id);
			}
		}
	}
	
	private void cancelAction(CommandLineParser parser, String[] args) throws Exception {
		// Option setup
		Options options = new Options();
		options.addOption("id", true, "The id of the order");
		CommandLine line = parser.parse(options, args);
		if(line.hasOption("id")) {
			int id = Integer.parseInt(line.getOptionValue("id"));
			ex.cancelOrder(id);
			System.out.println("Order was succesfully canceled!");
				
		} else {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("tick", options);
		}
	}
}
