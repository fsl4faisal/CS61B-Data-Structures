

public class Piece {
	private boolean isFire;
	private Board b;
	private int x;
	private int y;
	public String type;
	private boolean crowned;
	private boolean hasCaptured;

	public Piece(boolean isFire, Board b, int x, int y, String type) {
		this.isFire = isFire;
		this.b = b;
		this.x = x;
		this.y = y;
		this.type = type;
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

	public void move(int x, int y) {
		int xSum = x + this.x;
		int ySum = y + this.y;
		int tempx = this.x;
		int tempy = this.y;
			if (canCapture(this.x, this.y, x, y))	 {
				System.out.println("enter");
				if (this.type.equals("bomb")) {
					System.out.println("bomb");
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
            				if (b.pieces[i][j] != null) {
            					if (!b.pieces[i][j].type.equals("shield")) {
            						b.remove(i, j);	
            					}
            				}
            			}
            		}
            	}
            	else {
            		System.out.println("else");
            		b.remove(tempx, tempy);
            		this.x = x;
            		this.y = y;
            		b.place(this, this.x, this.y);
            		b.remove((xSum / 2), (ySum / 2));
            		hasCaptured = true;
            	}
			}
			else {
				b.remove(tempx, tempy);
				this.x = x;
				this.y = y;
				b.place(this, x, y);
				hasCaptured = false;
				System.out.println("hello");
	}
	}

	private boolean canCapture(int xi, int yi, int xf, int yf) {
        int slope = ((yf - yi) / (xf - xi));
        int midx = ((xi + xf) / 2);
        int midy = ((yi + yf) / 2);	
        if ((yf - yi) == 1 || (yf - yi) == -1 || (xf - xi) == 1 || (xf - xi) == -1) {
        	return false;
        }
        if ((slope == -1 || slope == 1) && b.pieces[xf][yf] == null && b.pieces[x][y] != null) {
            return true;
        }
        return false;
    }


	public boolean hasCaptured() {
		if (hasCaptured == true) {
			return true;
		}
		else {
			return false;
		}
	}

	public void doneCapturing() {
		hasCaptured = false;
	}

}