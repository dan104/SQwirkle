package g52grp.qwirkle;
import static org.junit.Assert.*;
import g52grp.qwirkle.SQwirkleCell;
import g52grp.qwirkle.TilePlacementException;

import org.junit.Test;


public class SQwirkleCellTest {

	@Test
	public void testInstatiation() {
		assertNotNull(new SQwirkleCell(null, null, null, null, null));
	}

	@Test
	public void testValues() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAdjacentCellNumberGeneration() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testValidTileList() {
		fail("Not yet implemented");
	}
	
	@Test (expected=TilePlacementException.class)
	public void testInvalidTilePlacement() {
		fail("Not yet implemented");
	}
}
