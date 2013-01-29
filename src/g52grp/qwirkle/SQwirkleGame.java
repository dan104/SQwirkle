package g52grp.qwirkle;
import java.util.ArrayList;
import java.util.Collections;


public class SQwirkleGame {
	ArrayList<SQwirkleTile> tileBag;
	SQwirklePlayer[] players;
	
	public SQwirkleGame(int numberOfPlayers) {
		initializeTileBag();
		initializePlayers(numberOfPlayers);
		
		
	}
	
	private void initializeTileBag() {
		tileBag = new ArrayList<SQwirkleTile>(36*3);
		for (int i=0; i<3; i++) {
			tileBag.addAll(SQwirkleTile.allPossibleTiles());
		}
		Collections.shuffle(tileBag);
	}
	
	private void initializePlayers(int numberOfPlayers) {
		players = new SQwirklePlayer[numberOfPlayers];
		for (int i=0; i<numberOfPlayers; i++) {
			players[i] = new SQwirklePlayer();
			while(players[i].getNumberOfTiles() < 6) {
				players[i].addTile(tileBag.remove(0));
			}
		}
	}
	
}
