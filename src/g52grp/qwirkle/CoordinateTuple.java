package g52grp.qwirkle;
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
	 * Creates a new CoordinateTuple object representing the coordinates (x,y)
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
	 * Returns a hash derived from the x and y values of the object
	 */
	public int hashCode() {
		//I don't know why this works but from research this is the standard way to minimise collisions
		int result = 1;
		int prime1 = 227;
		int prime2 = 631;
		
		result = prime1 * result + x;
		result = prime2 * result + y;
		
		return result;
		
	}
	@Override
	/**
	 * Returns true iff both the x and y values of the two CoordinateTuple objects are the same
	 */
	public boolean equals(Object other) {
		CoordinateTuple otherTuple;
		if (!(other instanceof CoordinateTuple)) {
			return false;
		}
		
		otherTuple = (CoordinateTuple) other;
		
		return this.x == otherTuple.x && this.y == otherTuple.y;
	}
	/**
	 * Creates a new CoordinateTuple object from it's location relative to this CoordinateTuple object
	 * @param dx The x-value of the new coordinates minus the x-value of this object
	 * @param dy The y-value of the new coordinates minus the y-value of this object
	 * @return A new CoordinateTuple object representing the coordinates (this.x + dx, this.y + dy)
	 */
	public CoordinateTuple addXY(int dx, int dy) {
		return new CoordinateTuple(x+dx, y+dy);
	}
	/**
	 * Returns a new CoordinateTuple representing the coordinates directly above this object.
	 * @return The new CoordinateTuple representing the coordinates directly above this object.
	 */
	public CoordinateTuple getCoordinatesAbove() {
		return addXY(0, -1);
	}
	/**
	 * Returns a new CoordinateTuple representing the coordinates directly below this object.
	 * @return The new CoordinateTuple representing the coordinates directly below this object.
	 */
	public CoordinateTuple getCoordinatesBelow() {
		return addXY(0, 1);
	}
	/**
	 * Returns a new CoordinateTuple representing the coordinates directly to the left of this object.
	 * @return The new CoordinateTuple representing the coordinates directly to the left of this object.
	 */
	public CoordinateTuple getCoordinatesLeft() {
		return addXY(-1, 0);
	}
	/**
	 * Returns a new CoordinateTuple representing the coordinates directly to the right of this object.
	 * @return The new CoordinateTuple representing the coordinates directly to the right of this object.
	 */
	public CoordinateTuple getCoordinatesRight() {
		return addXY(1, 0);
	}
}
