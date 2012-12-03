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
	
	@Override
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}
	@Override
	/**
	 * Need to base the hashCode on the x and y values of the coordinates 
	 * so that we can look up cells by their coordinates later
	 */
	public int hashCode() {
		//I don't know why this works but from research this is the standard way to minimise collisions
		int result = 17;
		int prime = 37;
		
		result = prime * result + x;
		result = prime * result + y;
		
		return result;
		
	}
	
}
