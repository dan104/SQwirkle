import java.util.ArrayList;


public class SQwirklePlayer {
	ArrayList <SQwirkleTile> hand;
	int score;
	int numOfTiles;
	
	public SQwirklePlayer() {
		score = 0;
		numOfTiles = 0;
		hand = new ArrayList<SQwirkleTile>();
	}

	public void addTile(SQwirkleTile tile) {
		hand.add(tile);
		assert (hand.size() <= 6);
	}
	
	public SQwirkleTile removeTileAtIndex(int index) {
		return hand.remove(index);
	}
	
	public int incrementScore(int increment) {
		return score += increment;
	}
}
