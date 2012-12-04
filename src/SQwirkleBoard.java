import java.util.HashMap;

public class SQwirkleBoard {
	private HashMap<CoordinateTuple, SQwirkleCell> coordsToCells;
	
	public SQwirkleBoard() {
		coordsToCells = new HashMap<CoordinateTuple, SQwirkleCell>();
	}
	
	private void createCell(int x, int y) {
		CoordinateTuple coords = new CoordinateTuple(x, y);
		createCell(coords);
	}
	private void createCell(CoordinateTuple coords) {
		// assertion to ensure you're not trying to create a cell at coords where one alredy exists
		assert (getCellAtCoords(coords)==null);
		
		SQwirkleCell cellAbove = getCellAtCoords(coords.getCoordinatesAbove());
		SQwirkleCell cellBelow = getCellAtCoords(coords.getCoordinatesBelow());
		SQwirkleCell cellLeft = getCellAtCoords(coords.getCoordinatesLeft());
		SQwirkleCell cellRight = getCellAtCoords(coords.getCoordinatesRight());
		SQwirkleCell newCell = new SQwirkleCell(coords, cellAbove, cellLeft, cellBelow, cellRight);
		
		coordsToCells.put(coords, newCell);
	}
	
	private SQwirkleCell getCellAtCoords(CoordinateTuple coords) {
		SQwirkleCell result = null;
		if (coordsToCells.containsKey(coords))
			result = coordsToCells.get(coords);
		return result;
	}
	private SQwirkleCell getCellAtCoords(int x, int y) {
		CoordinateTuple coords = new CoordinateTuple(x,y);
		return getCellAtCoords(coords);
	}
}
