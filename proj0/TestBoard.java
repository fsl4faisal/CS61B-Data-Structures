import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Board class
 */

public class TestBoard {
	
	
	@Test
	public void testPlace() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		Piece what = new Piece(true, b, 0, 6, "pawn");
		b.place(what, 0, 6);
		assertEquals(b.pieces[0][6], what);
	}

	
	

	public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}