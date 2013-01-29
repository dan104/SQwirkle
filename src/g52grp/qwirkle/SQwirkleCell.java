package g52grp.qwirkle;
import java.util.ArrayList;

public class SQwirkleCell {
	//TODO: currently setTile does not check if the two sides of a bridge are compatible with each other.
	//Constants used to access arrays such as `neighbours` which have a object for each direction
	private static final int UP = 0;
	private static final int LEFT = 1;
	private static final int DOWN = 2;
	private static final int RIGHT = 3;

	/**
	 * The coordinates of the cell on the board
	 */
	private final CoordinateTuple coords;
	/**
	 * The tile currently placed on this cell
	 */
	private SQwirkleTile tile;
	/**
	 * The list of tiles valid in the line this cell creates on the x axis
	 */
	private ArrayList<SQwirkleTile> validTilesOnXAxis;
	/**
	 * The list of tiles valid in the line this cell creates on the y axis
	 */
	private ArrayList<SQwirkleTile> validTilesOnYAxis;
	/**
	 * The list of Tiles that can be placed on this cell according to the game rules.
	 * The intersection of validTilesOnXAxis and validTilesOYXAxis
	 */
	private ArrayList<SQwirkleTile> validTiles;
	/**
	 * An array holding the number of tiles in a continuous line adjacent to this cell in each direction
	 */
	private int[] numberOfAdjacentTiles;
	/**
	 * The value this cell has on the x axis
	 */
	private int xValue;
	/**
	 * The value this cell has on the y axis
	 */
	private int yValue;
	/**
	 * An array on the 4 cells adjacent to this cell, which can be accessed using the class constants UP, DOWN etc
	 * to retrieve the cell in the respective direction
	 */
	private SQwirkleCell[] neighbours;
	/**
	 * Creates a SQwirkleCell object with no tile placed on it. The neighbour parameters are used to correctly generate
	 * the list of tiles that can be placed on this cell according to the Qwirkle rules as well as generating the value 
	 * of this cell (ie. the score a player would get by placing a tile on this cell).
	 * 
	 * @param coords A CoordinateTuple object representing the cell's position on the board
	 * @param upNeighbour The SQWirkleCell object directly above this cell on the board, or null if there isn't one
	 * @param leftNeighbour The SQWirkleCell object directly to the left of this cell on the board, or null if there isn't one
	 * @param downNeighbour The SQWirkleCell object directly below this cell on the board, or null if there isn't one
	 * @param rightNeighbour The SQWirkleCell object directly to the left of this cell on the board, or null if there isn't one
	 */
	public SQwirkleCell(CoordinateTuple coords, SQwirkleCell upNeighbour,
			SQwirkleCell leftNeighbour, SQwirkleCell downNeighbour,
			SQwirkleCell rightNeighbour) {
		super();
		this.coords = coords;

		neighbours = new SQwirkleCell[4];
		neighbours[UP] = upNeighbour;
		neighbours[DOWN] = downNeighbour;
		neighbours[LEFT] = leftNeighbour;
		neighbours[RIGHT] = rightNeighbour;

		numberOfAdjacentTiles = new int[4];
		calculateNumberOfTilesInLine();
		updateValue();
		generateValidTiles();
		tile = null;
	}
	public SQwirkleTile getTile() {
		return tile;
	}
	public CoordinateTuple getCoords() {
		return coords;
	}
	public ArrayList<SQwirkleTile> getValidTilesOnXAxis() {
		return validTilesOnXAxis;
	}
	public ArrayList<SQwirkleTile> getValidTilesOnYAxis() {
		return validTilesOnYAxis;
	}
	public int getNumberOfAdjacentTiles(int direction) {
		return numberOfAdjacentTiles[direction];
	}
	public SQwirkleCell getNeighbourInDirection(int direction) {
		return neighbours[direction];
	}
	public ArrayList<SQwirkleTile> getValidTiles() {
		return validTiles;
	}
	public int getStandAloneValue() {
		return xValue + yValue;
	}
	public int getXValue() {
		return xValue;
	}
	public int getYValue() {
		return yValue;
	}
	/**
	 * Sets the number of adjacent tiles in <code>direction</code> and updates the value of the cell to match the change
	 * @param direction The direction for which to update the number of adjacent tiles
	 * @param number The new number of adjacent tiles in the respective direction
	 */
	public void setNumberOfAdjacentTiles(int direction, int number) {
		numberOfAdjacentTiles[direction] = number;
		//changing the number of tiles in line with this cell changes it's value so we need to update it
		updateValue();
	}
	/**
	 * Checks that <code>newTile</code> is in the list of valid tiles for this cell and if it is it sets tile to newTile
	 * and calls notifyNeighboursOfChanges to update the values and validTiles of surrounding cells
	 * 
	 * @param newTile The tile to place on the cell
	 * @throws TilePlacementException Thrown when an attempt is made to place a tile on a cell that already has a tile on it or if the tile is not valid for this cell
	 */
	public void setTile(SQwirkleTile newTile) throws TilePlacementException {
		ArrayList<SQwirkleTile> tilesCompatibleWithNewTile;
		// if the cell already has a tile on it throw an exception
		if (this.tile == null)
			throw new TilePlacementException(String.format("Cell at %s already has a tile placed on it", coords));

		// if the tile is not in the list of valid tiles to place on this cell, throw an exception
		if (!validTiles.contains(newTile))
			throw new TilePlacementException(String.format("Tile %s is not valid for the Cell at %s",
					newTile, coords));

		tile = newTile;
		tilesCompatibleWithNewTile = tile.getCompatibleTiles();

		//update x and y valid tile lists
		updateValidTiles(tilesCompatibleWithNewTile, 'x');
		updateValidTiles(tilesCompatibleWithNewTile, 'y');

		for (int direction=0; direction<neighbours.length; direction++) {
			notifyNeighboursOfChanges(direction);
		}
	}
	/**
	 * Used to get the opposite to a given direction, eg. the opposite of left is right, the opposite of up is down
	 * 
	 * @param direction
	 * @return the opposite direction to direction
	 */
	public int getOppositeDirection(int direction) {
		assert (direction >= 0 || direction < 5);
		return (direction+2) % 4;
	}
	/**
	 * Called when a tile is placed in line with the cell to update the list of tiles that can be placed on this tile
	 * All tiles that are not in newTiles are removed from validTilesOnXAxis or validTilesOnYAxis respectively
	 * 
	 * @param newList The list of tiles that are still valid
	 * @param axis The axis (x or y) for which to update the valid tiles
	 */
	public void updateValidTiles(ArrayList<SQwirkleTile> newList, char axis) {
		assert (axis=='x' || axis=='y');
		ArrayList<SQwirkleTile> listToUpdate = null;
	
		if (axis=='x')
			listToUpdate = validTilesOnXAxis;
		else if (axis=='y') 
			listToUpdate = validTilesOnYAxis;
	
		for (SQwirkleTile t: listToUpdate) {
			if (!newList.contains(t)) {
				listToUpdate.remove(t);
			}
		}
	
		//update the validTiles field based on the new contents of validTilesOnAxis
		for (SQwirkleTile t: validTiles) {
			if (!listToUpdate.contains(t)) {
				validTiles.remove(t);
			}
		}
	}
	/**
	 * Called in the class's constructor to work out the number of tiles on each side of this cell 
	 * in order to work out its value
	 */
	private void calculateNumberOfTilesInLine() {

		for (int i=0; i<neighbours.length; i++) {
			int numOfTiles=0;
			SQwirkleCell neighbour = neighbours[i];

			if (neighbour != null && neighbour.getTile() != null) {
				numOfTiles = neighbour.getNumberOfAdjacentTiles(i)+1;
			} 
			numberOfAdjacentTiles[i] = numOfTiles;
		}
	}
	/**
	 * Called whenever the number of tiles in a contiguous line with this cell changes in order to update its value
	 */
	private void updateValue() {
		int upVal = numberOfAdjacentTiles[UP] == 0? 0 : numberOfAdjacentTiles[UP]+1;
		int downVal = numberOfAdjacentTiles[DOWN] == 0? 0 : numberOfAdjacentTiles[DOWN]+1;
		int leftVal = numberOfAdjacentTiles[LEFT] == 0? 0 : numberOfAdjacentTiles[LEFT]+1;
		int rightVal = numberOfAdjacentTiles[RIGHT] == 0? 0 : numberOfAdjacentTiles[RIGHT]+1;
		
		yValue = upVal + downVal;
		xValue = leftVal + rightVal;
		if (upVal>0 && downVal>0)
			yValue--;
		if (leftVal>0 && rightVal>0)
			xValue--;
		/* deal with Qwirkle's */
		if (xValue == 6)
			xValue *= 2;
		if (yValue == 6)
			yValue *= 2;
	}
	/**
	 * Called by constructors. Uses the neighbour fields to generate the validTiles, validTilesOnXAxis 
	 * and validTilesOnYAxis arrayLists
	 */
	@SuppressWarnings("unchecked")
	private void generateValidTiles() {
		validTilesOnYAxis = (ArrayList<SQwirkleTile>) SQwirkleTile.allPossibleTiles().clone();
		validTilesOnXAxis = (ArrayList<SQwirkleTile>) SQwirkleTile.allPossibleTiles().clone();
		validTiles = new ArrayList<SQwirkleTile>();

		ArrayList<SQwirkleTile>[] tilesCompatibleWithEachDirection = new ArrayList[4];

		for (int i=0; i<neighbours.length; i++) {
			if (neighbours[i] == null) {
				tilesCompatibleWithEachDirection[i] = null;
			} else if (i==UP || i==DOWN){
				tilesCompatibleWithEachDirection[i] = neighbours[i].getValidTilesOnYAxis();
			} else if (i==LEFT || i==RIGHT) {
				tilesCompatibleWithEachDirection[i] = neighbours[i].getValidTilesOnYAxis();
			}
		}

		//get the tiles valid on the y axis by removing all tiles not valid for the line above and below
		for (SQwirkleTile t: validTilesOnYAxis) {
			if (tilesCompatibleWithEachDirection[UP] != null &&
					!tilesCompatibleWithEachDirection[UP].contains(t)) {
				validTilesOnYAxis.remove(t);
			}
			if (tilesCompatibleWithEachDirection[DOWN] != null &&
					!tilesCompatibleWithEachDirection[DOWN].contains(t)) {
				validTilesOnYAxis.remove(t);
			}
		}
		//get the tiles valid on the x axis by removing all tiles not valid to the left and right
		for (SQwirkleTile t: validTilesOnXAxis) {
			if (tilesCompatibleWithEachDirection[LEFT] != null &&
					!tilesCompatibleWithEachDirection[LEFT].contains(t)) {
				validTilesOnXAxis.remove(t);
			}
			if (tilesCompatibleWithEachDirection[RIGHT] != null &&
					!tilesCompatibleWithEachDirection[RIGHT].contains(t)) {
				validTilesOnXAxis.remove(t);
			}
		}

		//find the intersect of the valid tiles on each axis to get the tiles that are valid for the cell
		for (SQwirkleTile t: validTilesOnYAxis) {
			if (validTilesOnXAxis.contains(t)) {
				validTiles.add(t);
			}
		}
	}

	/**
	 * 
	 * @param directionOfNeighbours
	 */
	private void notifyNeighboursOfChanges(int directionOfNeighbours) {
		ArrayList<SQwirkleTile> newValidTileList = null;
		SQwirkleCell neighbour = neighbours[directionOfNeighbours];
		int directionOfNewTiles = getOppositeDirection(directionOfNeighbours);
		int newNumberOfTilesInLine = numberOfAdjacentTiles[directionOfNewTiles]+1;
		char axisToUpdate = 0;

		if (directionOfNeighbours == UP || directionOfNeighbours == DOWN) {
			axisToUpdate = 'y';
			newValidTileList = validTilesOnYAxis;
		}
		if (directionOfNeighbours == LEFT || directionOfNeighbours == RIGHT) {
			axisToUpdate = 'x';
			newValidTileList = validTilesOnXAxis;
		}

		while (neighbour != null) {
			//update the cell's valid tiles and notify it of the new tiles in line with it
			neighbour.setNumberOfAdjacentTiles(directionOfNewTiles, newNumberOfTilesInLine);
			neighbour.updateValidTiles(newValidTileList, axisToUpdate);

			if (neighbour.getTile() != null) {
				neighbour = neighbour.getNeighbourInDirection(directionOfNeighbours);
			}
			else {
				neighbour = null;
			}
		}
	}
	
}