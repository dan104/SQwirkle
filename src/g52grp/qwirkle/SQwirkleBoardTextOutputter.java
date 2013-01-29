package g52grp.qwirkle;
/**
 * This class handles the text-based output of the game
 * @author Sam
 *
 */
public class SQwirkleBoardTextOutputter {
	SQwirkleBoard board;
	static String emptyTile = "     ";
	public SQwirkleBoardTextOutputter(SQwirkleBoard board) {
		this.board = board;
	}
	/**
	 * Outputs the current state of the board as text
	 */
	public String outputStringForCurrentBoardState() {
		int boardWidth = board.getMaxX()+1 - board.getMinX();
		
		String output = "";
		for (int y = board.getMinY(); y<=board.getMaxY(); y++) {
			SQwirkleTile[] tilesThisRow = new SQwirkleTile[boardWidth];
			for (int x = board.getMinX(); x<=board.getMaxX(); x++) {
				SQwirkleCell cell = board.getCellAtCoords(x, y);
				tilesThisRow[x] = cell==null ? null : cell.getTile(); 
				output += createStringFromRow(tilesThisRow);
			}
		}
		
		output+="\n\n";
		return output;
		
	}
	
	public void outputCurrentBoardState() {
		System.out.print(outputStringForCurrentBoardState());
	}
	public String createStringFromRow(SQwirkleTile[] rowTiles) {
		String output = "";
		for (int i=0; i<rowTiles.length; i++) {
			if (rowTiles[i] == null) {
				output += "         ";
			} else {
				output += " ------- ";
			}
		}
		output += "\n";
		for (int i=0; i<rowTiles.length; i++) {
			if (rowTiles[i] == null) {
				output += "         ";
			} else {
				output += "|       |";
			}
		}
		output += "\n";
		for (int i=0; i<rowTiles.length; i++) {
			if (rowTiles[i] == null) {
				output += "         ";
			} else {
				output += "| " + rowTiles[0].toString() + " |";
			}
		}
		output += "\n";
		for (int i=0; i<rowTiles.length; i++) {
			if (rowTiles[i] == null) {
				output += "         ";
			} else {
				output += "|       |";
			}
		}
		output += "\n";
		for (int i=0; i<rowTiles.length; i++) {
			if (rowTiles[i] == null) {
				output += "         ";
			} else {
				output += " ------- ";
			}
		}
		output += "\n";
		return output;
	}
}
