package g52grp.qwirkle;
import static org.junit.Assert.*;

import g52grp.qwirkle.CoordinateTuple;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;


public class CoordinateTupleTest {

	@Test
	public void testInstantiation() {
		assertNotNull(new CoordinateTuple(2,2));
	}
	
	@Test
	public void testHashCodeCollisions() {
		ArrayList<Integer> hashes = new ArrayList<Integer>();
		for (int x=-100; x<100; x++) {
			for (int y=-100; y<100; y++) {
				CoordinateTuple coords = new CoordinateTuple(x,y);
				Integer hash = new Integer(coords.hashCode());
				if (hashes.contains(hash)) {
					fail("hash collision occured with hash" + hash);
				}
				hashes.add(hash);
			}
		}
	}
	
	@Test
	public void testEqualHashes() {
		Random rng = new Random();
		for (int i=0; i<100; i++) {
			int x = rng.nextInt();
			int y = rng.nextInt();
			CoordinateTuple coords1 = new CoordinateTuple(x,y);
			CoordinateTuple coords2 = new CoordinateTuple(x,y);
			
			if (coords1.hashCode() != coords2.hashCode()) {
				fail(String.format("%s and %s do not have the same hashCode", coords1, coords2));
			}
		}
	}
	
	@Test
	public void testEquivelence() {
		for (int x=-100; x<100; x++) {
			for (int y=-100; y<100; y++) {
				CoordinateTuple tuple1 = new CoordinateTuple(x,y);
				CoordinateTuple tuple2 = new CoordinateTuple(x,y);
				if (!tuple1.equals(tuple2)) {
					fail(String.format("%s and %s are incorrectly being evaluated as inequal", tuple1, tuple2));
				}
				if (!tuple2.equals(tuple1)) {
					fail(String.format("%s and %s are incorrectly being evaluated as inequal", tuple2, tuple1));
				}
			}
		}
	}
	
	@Test
	public void testInequivelence() {
		Random rng = new Random();
		for (int i=0; i<100; i++) {
			int x1 = rng.nextInt();
			int y1 = rng.nextInt();
			int x2 = rng.nextInt();
			int y2 = rng.nextInt();
			
			if (x1 != x2 || y1 != y2) {
				CoordinateTuple tuple1 = new CoordinateTuple(x1, y1);
				CoordinateTuple tuple2 = new CoordinateTuple(x2, y2);
				
				if (tuple1.equals(tuple2)) {
					fail(String.format("%s and %s are incorrectly being evaluated as equal", tuple1, tuple2));
				}
				if (tuple2.equals(tuple1)) {
					fail(String.format("%s and %s are incorrectly being evaluated as equal", tuple2, tuple1));
				}
			}
		}
	}

}
