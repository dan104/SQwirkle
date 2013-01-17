import java.util.ArrayList;


public class ScoreCalculator {
	private SQwirkleBoard board;
	private ArrayList<CoordinateTuple> moveSet;
	private char axis = 0;
	
	public ScoreCalculator(SQwirkleBoard board) {
		moveSet = new ArrayList<CoordinateTuple>();
		this.board = board;

	}
	public void addMove(CoordinateTuple coords) {
		moveSet.add(coords);
		if (axis==0 && moveSet.size()>0) {
			determineAxis();
		}
	}
	private void determineAxis() {
		assert moveSet.size()>1;
		CoordinateTuple coords1 = moveSet.get(0);
		CoordinateTuple coords2 = moveSet.get(1);
		
		if (coords1.getX() == coords2.getX()) {
			axis = 'y';
		} else {
			axis = 'x';
		}
		
	}

	private int max(int a, int b) {
		return a > b? a : b;
	}
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
