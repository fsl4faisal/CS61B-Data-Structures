public class Piece {
	private boolean isFire;
	private Board b;
	private int x;
	private int y;
	private String type;
	private boolean crowned;
	private boolean hasCaptured;

	public Piece(boolean isFire, Board b, int x, int y, String type) {
		this.isFire = isFire;
		this.b = b;
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public void move(int x, int y) {
		int xSum = this.x + x;
		int ySum = this.y + y;
		if ((x == this.x + 1 && y == this.y + 1) || (x == this.x - 1 && y == this.y + 1) || (x == this.x - 1 && y == this.y - 1) || (x == this.x + 1 && y == this.y - 1)) {
			b.remove(this.x, this.y);
			this.x = x;
			this.y = y;
			b.place(this, x, y);
			kingChecker();
			return;
		}
		if ((x == this.x + 2 && y == this.y + 2) || (x == this.x - 2 && y == this.y + 2) || (x == this.x - 2 && y == this.y - 2) || (x == this.x + 2 && y == this.y - 2)) {
			if (this.type.equals("bomb")) {
					b.remove((xSum / 2), (ySum / 2));
					b.remove(this.x, this.y);
					int h = x + 2;
					int p = x - 1;
					int a = y - 1;
					int c = y + 2;
					if (x == 0) {
						p = x;
					}
					if (x == 7) {
						h = x + 1;
					}
					if (y == 0) {
						a = y;
					}
					if (y == 7) {
						c = y + 1;
					}
					for (int i = (p); i < (h); i++) {
            			for (int j = a; j < c; j++) {
            				if (b.pieceAt(i, j) != null) {
            					if (!b.pieceAt(i, j).type.equals("shield")) {
            						b.remove(i, j);	
            					}
            				}
            			}
            		}
            	}
            else {
            	b.remove((xSum / 2), (ySum / 2));
            	b.remove(this.x, this.y);
				this.x = x;
				this.y = y;
				kingChecker();
				b.place(this, x, y);
				hasCaptured = true;
            }
        }
         
		
		} 	



	private void kingChecker() {
		if (isFire) {
			if (y == 7) {
				crowned = true;
			}
		}
		if (isFire == false) {
			if (y == 0) {
				crowned = true;
			}
		}
	}

	public boolean hasCaptured() {
		if (hasCaptured) {
			return true;
		}
		return false;
	}

	public void doneCapturing() {
		hasCaptured = false;
	}


	public boolean isFire() {
		if (isFire == true) {
			return true;
		}
		return false;
	}
	

	public int side() {
		if (isFire == true) {
			return 0;
		}
		return 1;
	}

	public boolean isKing() {
		if (crowned == true) {
			return true;
		}
		return false;
	}

	public boolean isBomb() {
		if (type.equals("bomb")) {
			return true;
		}
		return false;
	}

	public boolean isShield() {
		if (type.equals("shield")) {
			return true;
		}
		return false;
	}
}