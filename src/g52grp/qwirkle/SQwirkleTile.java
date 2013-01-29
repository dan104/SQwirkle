package g52grp.qwirkle;
import java.util.ArrayList;


public class SQwirkleTile {
	
	/**
	 * The shape of the tile
	 */
	private final Shape shape;
	/**
	 * The colour of the tile
	 */
	private final Colour colour;
	
	/**
	 * Creates a new SQwirkleTile with the colour and shape given as parameters
	 * 
	 * @param colour the tile's colour
	 * @param shape the tile's shape
	 */
	public SQwirkleTile(Colour colour, Shape shape) {
		this.colour = colour;
		this.shape = shape;
	}

	public Shape getShape() {
		return shape;
	}

	public Colour getColour() {
		return colour;
	}
	
	/**
	 * Calculates all the tiles that can successfully be placed in the same line as this tile
	 * 
	 * @return an ArrayList of all the tiles that can be places in the same line as this tile
	 */
	public ArrayList<SQwirkleTile> getCompatibleTiles() {
		ArrayList<SQwirkleTile> compatibleTiles = new ArrayList<SQwirkleTile>(10);
		SQwirkleTile tile;
		
		//get all tiles with the same colour
		for (Shape s: Shape.values()) {
			if (s != shape) {
				tile = new SQwirkleTile(colour, s);
				compatibleTiles.add(tile);
			}
		}
		
		//get all the tiles with the same shape
		for (Colour c: Colour.values()) {
			if (c != colour) {
				tile = new SQwirkleTile(c, shape);
				compatibleTiles.add(tile);
			}
		}
		
		return compatibleTiles;
	}
	
	/**
	 * Compares this to another object and returns true only if both the colour and the shape
	 * of the two tiles match
	 */
	@Override
	public boolean equals(Object other) {
		SQwirkleTile otherTile;
		
		//check that other is a SQwirkleTile
		if (!(other instanceof SQwirkleTile))
			return false;
		
		//cast other to a SQWirkleTile so we can call getColour etc on it
		otherTile = (SQwirkleTile) other;
		
		//return true only if the colour and the shape match
		return (this.colour == otherTile.getColour() &&
				this.shape == otherTile.getShape());
			
	}

	/**
	 * Generates an ArrayList of all 36 possible colour/shape combinations possible on a tile
	 * @return An ArrayList SQWirkleTile objects representing all of the possible colour, shape combinations
	 */
	public static ArrayList<SQwirkleTile>  allPossibleTiles() {
		ArrayList<SQwirkleTile> ret = new ArrayList<SQwirkleTile>(36);
		
		for (Colour c: Colour.values()) {
			for (Shape s: Shape.values()) {
				ret.add(new SQwirkleTile(c, s));
			}
		}
		
		return ret;
	}
	public String toString() {
		return colour + " " + shape;
	}
}

/**
 * The Colour class is used to represent the possible colours of a tile
 * 
 * @author Sam
 *
 */
enum Colour {
	GREEN {public String toString() { return "Gr"; }},
	BLUE {public String toString() { return "Bl"; }},
	ORANGE {public String toString() { return "Or"; }},
	RED {public String toString() { return "Re"; }},
	PURPLE {public String toString() { return "Pu"; }},
	YELLOW {public String toString() { return "Ye"; }}
}

/**
 * The Shape class is used to represent the possible shapes a tile can have
 * 
 * @author Sam
 *
 */
enum Shape {
	SQUARE {public String toString() { return "Sq"; }},
	CIRCLE {public String toString() { return "Ci"; }},
	DIAMOND {public String toString() { return "Di"; }},
	CLUB {public String toString() { return "Cl"; }},
	STAR {public String toString() { return "St"; }},
	EXPLOSION {public String toString() { return "Ex"; }}
}
