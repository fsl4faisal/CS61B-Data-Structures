import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Board class
 */

public class TestBoard {
	
	@Test
	public void testMove() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.pieces[0][0] = new Piece(true, b, 0, 0, "pawn");
		b.select(0, 0);
		assertEquals(b.selected, b.pieces[0][0]);
		b.selected.move(1, 1);
		assertEquals(b.selected, b.pieces[1][1]);
	}

	@Test
	public void testcanSelect() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.pieces[0][0] = new Piece(true, b, 0, 0, "pawn");
		b.select(0, 0);
		assertEquals(b.selected, b.pieces[0][0]);
		boolean result = b.canSelect(1, 1);
		assertEquals(result, true);
	}

	@Test
	public void testValidFireMove() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.pieces[0][0] = new Piece(true, b, 0, 0, "pawn");
		b.select(0, 0);
		assertEquals(b.selected, b.pieces[0][0]);
		assertEquals(b.xSelected, 0);
	}

	@Test
	public void testMove1() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.pieces[0][0] = new Piece(true, b, 0, 0, "pawn");
		b.select(0, 0);
		b.select(1, 1);
		assertEquals(b.pieces[1][1], b.selected);
	}

	@Test
	public void testRemove() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.pieces[0][0] = new Piece(true, b, 0, 0, "pawn");
		b.select(0, 0);
		b.remove(0, 0);
		assertEquals(null, b.pieces[0][0]);
	}

	@Test
	public void testMove2() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.addPieces(8);
		b.select(6, 2);
		assertEquals(b.selected, b.pieces[6][2]);
		b.select(7, 3);
		assertEquals(b.pieces[7][3], b.selected);
		assertEquals(b.pieces[6][2], null);
	}

	@Test
	public void testMove3() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.addPieces(8);
		b.select(6, 2);
		assertEquals(b.selected, b.pieces[6][2]);
		boolean result = b.canSelect(7, 3);
		assertEquals(true, result);
	}

	@Test
	public void testMove4() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.addPieces(8);
		b.select(6, 2);
		assertEquals(b.selected, b.pieceAt(6, 2));
		b.selected.move(7, 3);
		assertEquals(b.selected, b.pieceAt(7, 3));
	}
	

	@Test
	public void testCapture() {
		Board b = new Board(false);
		b.pieces = new Piece[8][8];
		b.pieces[6][2] = new Piece(true, b, 6, 2, "bomb");
		b.pieces[5][3] = new Piece(false, b, 5, 3, "bomb");
		b.select(6, 2);
		boolean result = b.validCapture(4, 4);
		assertEquals(result, true);
		b.select(4, 4);
		assertEquals(b.selected, b.pieceAt(4, 4));
		assertEquals(b.pieceAt(5, 3), null);
	}
	

	public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}