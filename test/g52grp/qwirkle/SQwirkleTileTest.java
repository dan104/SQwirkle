package g52grp.qwirkle;


import static org.junit.Assert.*;

import g52grp.qwirkle.Colour;
import g52grp.qwirkle.SQwirkleTile;
import g52grp.qwirkle.Shape;

import java.util.ArrayList;

import org.junit.Test;


public class SQwirkleTileTest {

	@Test
	public void testInstantiation() {
		assertNotNull(new SQwirkleTile(Colour.BLUE, Shape.CIRCLE));
	}
	
	@Test
	public void testAllTileCreationSize() {
		ArrayList<SQwirkleTile> allTiles = SQwirkleTile.allPossibleTiles();
		if (allTiles.size() != 36) {
			fail("incorrect number of tiles created");
			return;
		}
	}
	
	@Test
	public void testAllTileCreationDuplicates() {
		ArrayList<SQwirkleTile> allTiles = SQwirkleTile.allPossibleTiles();
		for (int i=0; i<allTiles.size(); i++) {
			SQwirkleTile nextTile = allTiles.remove(0);
			if (allTiles.contains(nextTile)) {
				fail("duplicate tile found");
			}
		}
	}

	@Test 
	public void testToStringOutputFormat() {
		SQwirkleTile tile = new SQwirkleTile(Colour.BLUE, Shape.CIRCLE);
		if (!tile.toString().equals("Bl Ci")) {
			fail("bad output string format");
		}
	}
	
	@Test
	public void testEquivelence() {
		SQwirkleTile tile1, tile2;
		tile1 = new SQwirkleTile(Colour.BLUE, Shape.CIRCLE);
		tile2 = new SQwirkleTile(Colour.BLUE, Shape.CIRCLE);
		if (!tile1.equals(tile2)) {
			fail("Equal tiles are not being correctly evaluated as equal");
		}
	}
	
	@Test
	public void testInequivilence() {
		SQwirkleTile tile1, tile2;
		tile1 = new SQwirkleTile(Colour.BLUE, Shape.CIRCLE);
		tile2 = new SQwirkleTile(Colour.BLUE, Shape.SQUARE);
		if (tile1.equals(tile2)) {
			fail("Inequal tiles are not being correctly evaluated as inequal");
		}
	}
	
	@Test
	public void testCompatibleTiles() {
		SQwirkleTile tile = new SQwirkleTile(Colour.BLUE, Shape.CIRCLE);
		ArrayList<SQwirkleTile> compatibleTiles = tile.getCompatibleTiles();
		
		//a tile should be compatible with the 5 other tiles with the same shape, and 5 with the same colour
		//so there should be a total of 10 compatible tiles for each tile
		if (compatibleTiles.size()!=10) {
			fail("incorrect number of compatible tiles generated for tile " + tile);
		}
		for (SQwirkleTile compatibleTile: compatibleTiles) {
			if (compatibleTile.equals(tile)) {
				fail("Tiles should not be compatile with themselves");
			}
			if (compatibleTile.getColour() != tile.getColour() && 
					compatibleTile.getShape() != tile.getShape()) {
				fail("Compatible tile does not have the same colour or shape");
			}
		}
	}
}
