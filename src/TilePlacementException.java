/**
 * A TilePlacementException is thrown when a tile is placed on a cell which the rules do not allow
 * eg. when a tile is placed next to another tile of different shape and colour, or when an attempt 
 * is made to place a tile on a cell that already has a tile on it.
 * @author Sam
 *
 */
public class TilePlacementException extends Exception {


	private static final long serialVersionUID = -6030935476854898167L;

	public TilePlacementException(String reason) {
		super(reason);
	}


}
