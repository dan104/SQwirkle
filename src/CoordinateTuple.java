/**
 * 
 * @author Sam
 *
 * The CoordinateTuple class is used to represent the coordinates of a cell on 
 * the board
 */
public class CoordinateTuple {

	/**
	 * The x value of the coordinate
	 */
	private final int x;
	/**
	 * The y value of the coordinate
	 */
	private final int y;
	
	/**
	 * 
	 * @param x The x value of the coordinate
	 * @param y The y value of the coordinate
	 */
	public CoordinateTuple(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
