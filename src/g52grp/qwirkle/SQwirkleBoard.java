package g52grp.qwirkle;
import java.util.HashMap;

public class SQwirkleBoard {
	private static SQwirkleBoard sharedInstance = null;
	private HashMap<CoordinateTuple, SQwirkleCell> coordsToCells;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	public static SQwirkleBoard sharedInstance() {
		if (sharedInstance == null) {
			sharedInstance = new SQwirkleBoard();
		}
		return sharedInstance;
	}
	
	private SQwirkleBoard() {
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
		
		setMaxAndMinValues(coords);
		
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

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	/**
	 * Retrieves the cell at the coordinates represented by coords.
	 * 
	 * @param coords The CoordinateTuple object representing the coordinates of the cell to retrieve
	 * @return The cell at the given coordinates or null if it doesn't exist
	 */
	public SQwirkleCell getCellAtCoords(CoordinateTuple coords) {
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
	public SQwirkleCell getCellAtCoords(int x, int y) {
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
	private void setMaxAndMinValues(CoordinateTuple coords) {
		int x = coords.getX();
		int y = coords.getY();
		
		if (x < minX) {
			minX = x;
		} else if (x > maxX) {
			maxX = x;
		}
		
		if (y < minY) {
			minY = y;
		} else if (y > maxY) {
			maxY = y;
		}
	}
}
