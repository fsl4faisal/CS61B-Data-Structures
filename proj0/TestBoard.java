import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Board class
 */

public class TestBoard {
	
	@Test
	public void testMove() {
		Board b = new Board(false);
		b.addPieces(8);
		b.select(2, 2);
		b.selected.move(3, 3);
		assertEquals(b.pieces[3][3], null);
	}

	@Test
	public void testSelect() {
		Board b = new Board(false);
		b.addPieces(8);
		b.hasMoved = false;
		b.selected = b.pieces[2][2];
		b.selected.move(3, 3);
		assertEquals(b.pieces[2][2], null);;
	}

	@Test
	public void testcanCapture() {
		Board b = new Board(false);
		b.addPieces(8);
		boolean result = b.canCapture(1, 1, 3, 3);
		assertEquals(true, result);
	}
	

	public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}