package cli;

import java.io.IOException;
import java.util.Scanner;
import models.*;
import org.apache.commons.cli.*;
import exchanges.ExchangeBTCE;

public class CryptotraderCLI {
	private ExchangeBTCE ex;
	
	private static String NAME = "Cryptotrader CLI v0.1";
	
	private boolean keeponrunning = true; // Keep program running
	
	public static void main(String[] args) {
		// Program
		/* 
		 * buy -pair -amount [rate]
		 * sell -pair -amount [rate]
		 * orders //list orders
		 * tick -pair
		 * tradehistory 
		 * balance //list balance contents
		 * 
		 */
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
		ex.getActiveOrders();
	}
}
