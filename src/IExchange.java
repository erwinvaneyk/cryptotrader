import java.util.List;

public interface IExchange {
	/**
	 * A list containing all supported Pairs
	 */
	//private List<Pair> supportedPairs;
	
	public Pair updatePair(Pair pair);
}
