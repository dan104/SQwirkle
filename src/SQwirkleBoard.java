import java.util.HashMap;

//TODO: overload placeTile method
//TODO: 

public class SQwirkleBoard {
	private HashMap<CoordinateTuple, SQwirkleCell> coordsToCells;
	
	public SQwirkleBoard() {
		coordsToCells = new HashMap<CoordinateTuple, SQwirkleCell>();
		createCell(0,0);
	}
	/**
	 * Creates a cell with no tile on it at the coordinates represented by coords.
	 * 
	 * @param coords The CoordiateTuple object representing the coordinates at which a new cell must be created
	 */
	private void createCell(CoordinateTuple coords) {
		// assertion to ensure you're not trying to create a cell at coords where one already exists
		assert (getCellAtCoords(coords)==null);
		
		SQwirkleCell cellAbove = getCellAtCoords(coords.getCoordinatesAbove());
		SQwirkleCell cellBelow = getCellAtCoords(coords.getCoordinatesBelow());
		SQwirkleCell cellLeft = getCellAtCoords(coords.getCoordinatesLeft());
		SQwirkleCell cellRight = getCellAtCoords(coords.getCoordinatesRight());
		SQwirkleCell newCell = new SQwirkleCell(coords, cellAbove, cellLeft, cellBelow, cellRight);
		
		coordsToCells.put(coords, newCell);
	}
	/**
	 * Creates a cell with no tile on it at the coordinates (x, y).
	 * Implemented in terms of createCell(CoordinateTuple coords).
	 * 
	 * @param x The x part of the desired coordinates
	 * @param y The y part of the coordinates
	 */
	private void createCell(int x, int y) {
		CoordinateTuple coords = new CoordinateTuple(x, y);
		createCell(coords);
	}

	/**
	 * Retrieves the cell at the coordinates represented by coords.
	 * 
	 * @param coords The CoordinateTuple object representing the coordinates of the cell to retrieve
	 * @return The cell at the given coordinates or null if it doesn't exist
	 */
	private SQwirkleCell getCellAtCoords(CoordinateTuple coords) {
		SQwirkleCell result = null;
		if (coordsToCells.containsKey(coords))
			result = coordsToCells.get(coords);
		return result;
	}
	/**
	 * Retrieves the cell at coordinates (x,y).
	 * Implemented in terms of getCellAtCoords(CoordinateTuple coords).
	 * 
	 * @param x The x part of the desired coordinates
	 * @param y The y part of the coordinates
	 * @return The cell at the given coordinates or null if it doesn't exist
	 */
	private SQwirkleCell getCellAtCoords(int x, int y) {
		CoordinateTuple coords = new CoordinateTuple(x,y);
		return getCellAtCoords(coords);
	}
	
	public int xValueForCoords(CoordinateTuple coords) {
		return getCellAtCoords(coords).getXValue();
	}
	public int yValueForCoords(CoordinateTuple coords) {
		return getCellAtCoords(coords).getYValue();
	}
	public void placeTile(SQwirkleTile tile, CoordinateTuple coords) throws TilePlacementException {
		SQwirkleCell cell = getCellAtCoords(coords);
		if (cell == null) 
			throw new TilePlacementException("Tile can not be placed because cell does not exist");
		cell.setTile(tile);
		
		//create neighbouring cells that do not already exist
		CoordinateTuple[] neighbourCoords = {coords.getCoordinatesAbove(), coords.getCoordinatesBelow(), 
				coords.getCoordinatesLeft(), coords.getCoordinatesRight()};
		for (CoordinateTuple neighbourCoord : neighbourCoords) {
			if (getCellAtCoords(neighbourCoord) == null) {
				createCell(neighbourCoord);
			}
		}
	}
	public void placeTile(SQwirkleTile tile, int x, int y) throws TilePlacementException {
		placeTile(tile, new CoordinateTuple(x,y));
	}
}
