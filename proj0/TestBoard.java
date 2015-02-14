import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Board class
 */

public class TestBoard {
	
	
	@Test
	public void testPlace() {
		Board b = new Board(true);
		Piece what = new Piece(true, b, 0, 6, "pawn");
		b.place(what, 0, 6);
		assertEquals(b.pieces[0][6], what);
		assertEquals(b.pieceAt(0, 6), b.pieces[0][6]);
		assertEquals(b.pieceAt(0, 6), what);
	}

	@Test
	public void testPlace1() {
		Board b = new Board(false);
		Piece what = new Piece(true, b, 0, 6, "pawn");
		b.place(what, 0, 6);
		assertEquals(b.pieces[0][6], what);
		assertEquals(b.pieceAt(0, 6), b.pieces[0][6]);
		assertEquals(b.pieceAt(0, 6), what);
	}

	
	

	public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}