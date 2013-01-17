/**
 * 
 * @author Sam
 *
 * The CoordinateTuple class is used to represent the coordinates of a cell on 
 * the board
 * 
 * CoordinateTuple objects are immutable. That is, once they are created their values cannot be altered.
 * This is enforced because the hashCode for the object is calculated using the values of x and y so altering
 * then would alter the hashCode.
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
	@Override
	public boolean equals(Object other) {
		CoordinateTuple otherTuple;
		if (!(other instanceof CoordinateTuple)) {
			return false;
		}
		
		otherTuple = (CoordinateTuple) other;
		
		return this.x == otherTuple.x && this.y == otherTuple.y;
	}
	public CoordinateTuple addXY(int dx, int dy) {
		return new CoordinateTuple(x+dx, y+dy);
	}
	
	public CoordinateTuple getCoordinatesAbove() {
		return addXY(0, -1);
	}
	
	public CoordinateTuple getCoordinatesBelow() {
		return addXY(0, 1);
	}
	
	public CoordinateTuple getCoordinatesLeft() {
		return addXY(-1, 0);
	}
	public CoordinateTuple getCoordinatesRight() {
		return addXY(1, 0);
	}
}
