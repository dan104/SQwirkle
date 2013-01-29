package g52grp.qwirkle;
import java.util.ArrayList;
/**
 * 
 * @author Sam
 *
 */
//TODO: check moves are in a block with each other, not just on the same axis
public class MoveAccumulator {
	private SQwirkleBoard board;
	private ArrayList<CoordinateTuple> moveSet;
	private char axis = 0;
	
	public MoveAccumulator(SQwirkleBoard board) {
		moveSet = new ArrayList<CoordinateTuple>();
		this.board = board;

	}
	/**
	 * Checks if the move newMove is valid (in line with the other tiles played this round) and adds the move to the list of 
	 * accumulated moves this turn this turn if it is valid
	 * 
	 * NOTE: this method does not check if the tile being placed on a cell is valid according to the neighbouring tiles, it
	 * only checks that a group of moves are valid in line with each other and so can legally be played together in a single turn
	 *  
	 * @param newMove The move to be added to the move accumulator
	 * @return true if the move was valid and could be added to the move accumulator, or false if it was invalid
	 */
	public boolean addMove(CoordinateTuple newMove) {
		boolean moveIsValid;
		if (moveIsValid=moveIsInLine(newMove)) {
			moveSet.add(newMove);
		}
		return moveIsValid;
	}
	/**
	 * 
	 * @param newMove
	 * @return
	 */
	private boolean moveIsInLine(CoordinateTuple newMove) {
		boolean inLine = true;
		CoordinateTuple firstMove;
		//if the moves are supposed to be aligned on the x axis, check newMove has the same y value as an already validated move
		if (axis=='x') {
			firstMove = moveSet.get(0);
			inLine = newMove.getY() == firstMove.getY(); 
		} 
		//if the moves are supposed to be aligned on the y axis, check newMove has the same x value as an already validated move
		else if (axis=='y') {
			firstMove = moveSet.get(0);
			inLine = newMove.getX() == firstMove.getX();
		} 
		//if the alignment axis has still not been set, calculate it if we have more than one move, and if the two moves are not 
		//aligned set inLine to false
		else if (axis==0 && !moveSet.isEmpty()) {
			firstMove = moveSet.get(0);
			if (firstMove.getX() == newMove.getX()) {
				axis = 'y';
			} else if (firstMove.getY() == newMove.getY()){
				axis = 'x';
			} else {
				inLine = false;
			}
		}
		return inLine;
	}

	/**
	 * Calculates the highest of two integers
	 * @param a The first integer
	 * @param b The second integer
	 * @return The larger of a and b, or a if they are equal
	 */
	private int max(int a, int b) {
		return a > b? a : b;
	}
	/**
	 * Calculates the total score that is achieved by playing tiles on all of the cells in moveSet
	 * 
	 * @return The total score for all the moves currently stored is moveSet
	 */
	public int calculateTotalScore() {
		int xVal, yVal;
		
		xVal = board.xValueForCoords(moveSet.get(0));
		yVal = board.yValueForCoords(moveSet.get(0));
		
		if (moveSet.size() == 1) {
			return xVal+yVal;
		}
		for (int i=1; i<moveSet.size(); i++) {
			CoordinateTuple move = moveSet.get(i);
			if (axis == 'y') {
				xVal += board.xValueForCoords(move);
				yVal = max(yVal, board.yValueForCoords(move));
			} else {
				xVal = max(xVal, board.xValueForCoords(move));
				yVal += board.yValueForCoords(move);
			}
		}
		return xVal+yVal;
	}
	
}
