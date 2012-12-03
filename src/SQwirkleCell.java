import java.util.ArrayList;

public class SQwirkleCell {

	//Constants used to access arrays such as `neighbours` which have a object for each direction
	private static final int UP = 0;
	private static final int LEFT = 1;
	private static final int DOWN = 2;
	private static final int RIGHT = 3;
	
	/**
	 * The coordinates of the cell on the board
	 */
	private CoordinateTuple coords;
	/**
	 * The tile currently placed on this cell
	 */
	private SQwirkleTile tile;
	/**
	 * The list of Tiles that can be placed on this cell according to the game rules
	 */
	private ArrayList<SQwirkleTile> validTiles;
	/**
	 * The number of placed tiles above this cell
	 */
	
	private ArrayList<SQwirkleTile> validTilesOnXAxis;
	private ArrayList<SQwirkleTile> validTilesOnYAxis;
	
	private int[] numberOfTilesInEachDirection;
	
	/**
	 * The score a player would receive by placing a tile on this cell
	 */
	private int value;
	
	private SQwirkleCell[] neighbours;
	

	
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
		
		numberOfTilesInEachDirection = new int[4];
		calculateNumberOfTilesInLine();
		updateValue();
		generateValidTiles();
		
		
	}
	public SQwirkleTile getTile() {
		return tile;
	}
	public void setTile(SQwirkleTile tile) {
		this.tile = tile;
	}

	public int getNumberOfTiles(int direction) {
		return numberOfTilesInEachDirection[direction];
	}
	public void incrementNumberOfTiles(int direction, int number) {
		numberOfTilesInEachDirection[direction] = number;
		//changing the number of tiles in line with this cell changes it's value so we need to update it
		updateValue();
	}
	public CoordinateTuple getCoords() {
		return coords;
	}
	
	public ArrayList<SQwirkleTile> getValidTiles() {
		return validTiles;
	}
	public int getValue() {
		return value;
	}
	
	private void calculateNumberOfTilesInLine() {
		
		for (int i=0; i<neighbours.length; i++) {
			int numOfTiles=0;
			SQwirkleCell neighbour = neighbours[i];
			
			if (neighbour != null && neighbour.getTile() != null) {
				numOfTiles = neighbour.getNumberOfTiles(i)+1;
			} 
			numberOfTilesInEachDirection[i] = numOfTiles;
		}

	}
	
	private void updateValue() {
		int upVal = numberOfTilesInEachDirection[UP] == 0? 0 : numberOfTilesInEachDirection[UP]+1;
		int downVal = numberOfTilesInEachDirection[DOWN] == 0? 0 : numberOfTilesInEachDirection[DOWN]+1;
		int leftVal = numberOfTilesInEachDirection[LEFT] == 0? 0 : numberOfTilesInEachDirection[LEFT]+1;
		int rightVal = numberOfTilesInEachDirection[RIGHT] == 0? 0 : numberOfTilesInEachDirection[RIGHT]+1;
		
		int yVal = upVal + downVal;
		int xVal = leftVal + rightVal;
		
		if (upVal>0 && downVal>0)
			yVal--;
		if (leftVal>0 && rightVal>0)
			xVal--;
		
		/* deal with Qwirkle's */
		if (xVal == 6)
			xVal *= 2;
		
		if (yVal == 6)
			yVal *= 2;
		
		value = xVal + yVal;
		
		//handle the original cell at 0,0 which has no neighbours but value of 1
		if (value == 0) 
			value = 1;
	}
	
	@SuppressWarnings("unchecked")
	private void generateValidTiles() {
		validTilesOnYAxis = (ArrayList<SQwirkleTile>) SQwirkleTile.allPossibleTiles().clone();
		validTilesOnXAxis = (ArrayList<SQwirkleTile>) SQwirkleTile.allPossibleTiles().clone();
		validTiles = new ArrayList<SQwirkleTile>();
		
		ArrayList<SQwirkleTile>[] tilesCompatibleWithEachDirection = new ArrayList[4];
		
		for (int i=0; i<neighbours.length; i++) {
			if (neighbours[i] == null) {
				tilesCompatibleWithEachDirection[i] = null;
			} else {
				tilesCompatibleWithEachDirection[i] = neighbours[i].getValidTilesOnXAxis();
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
	
	public SQwirkleCell getNeighbourInDirection(int direction) {
		return neighbours[direction];
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
	
	
	
	public ArrayList<SQwirkleTile> getValidTilesOnXAxis() {
		return validTilesOnXAxis;
	}
	public ArrayList<SQwirkleTile> getValidTilesOnYAxis() {
		return validTilesOnYAxis;
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
	
	
	public void notifyNeighboursOfChanges(int directionOfNeighbours, ArrayList<SQwirkleTile> compatibleTiles, 
			int numberOfNewTilesInLine) {
		SQwirkleCell neighbour = neighbours[directionOfNeighbours];
		int directionOfNewTiles = getOppositeDirection(directionOfNeighbours);
		char axisToUpdate = 0;
		
		if (directionOfNeighbours == UP || directionOfNeighbours == DOWN)
			axisToUpdate = 'y';
		if (directionOfNeighbours == LEFT || directionOfNeighbours == RIGHT)
			axisToUpdate = 'x';
		
		while (neighbour != null) {
			//update the cell's valid tiles and notify it of the new tiles in line with it
			neighbour.incrementNumberOfTiles(directionOfNewTiles, numberOfNewTilesInLine);
			neighbour.updateValidTiles(compatibleTiles, axisToUpdate);
			
			if (neighbour.getTile() != null) {
				neighbour = neighbour.getNeighbourInDirection(directionOfNeighbours);
			}
			else {
				neighbour = null;
			}
		}
	}
	
	
}

