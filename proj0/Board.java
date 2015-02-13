public class Board {

	public static boolean shouldBeEmpty;
	public Piece[][] pieces;
	private boolean fireTurn;
	public Piece selected;
    private int xSelected;
    private int ySelected;
	public boolean hasMoved;
    private boolean hasCaptured;

	public static void main(String[] args) {
		Board b = new Board(false);
        int N = 8;
		b.addPieces(N);
        b.fireTurn = true;
        b.hasMoved = false;
        b.selected = null;
		StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);
		if (shouldBeEmpty == true) {
            b.drawBoard(N);
            }
        else {
            while(true) {
         		b.drawBoard(N);
                if (StdDrawPlus.mousePressed()) {
                    double xd = StdDrawPlus.mouseX();
                    double yd = StdDrawPlus.mouseY();
                    int x = (int)xd;
                    int y = (int)yd;
                    if (b.hasMoved == false) {
                        if (b.canSelect(x, y)) {
                            if (b.pieces[x][y] == null) {
                                b.selected.move(x, y);
                                b.hasMoved = true;
                            }
                            else {
                            b.select(x, y);
                        }
                    }
                }
                    if (b.hasMoved == true) {
                        if (b.selected.hasCaptured()) {
                            b.xSelected = b.getxSelected(b.selected);
                            b.ySelected = b.getySelected(b.selected);    
                        }
                        if (b.canSelect(x , y)) {
                            b.selected.move(x, y);
                        }
                    }
                }
                StdDrawPlus.show(25);
            	if (StdDrawPlus.isSpacePressed()) {
            		if (b.canEndTurn()) {
                    b.endTurn();
                } 
            	}
                StdDrawPlus.show(25);           
			}       
	}
}

	public Board(boolean shouldBeEmpty) {
		this.shouldBeEmpty = shouldBeEmpty;
		int N = 8;
		pieces = new Piece[N][N];
}
	
	public void addPieces(int N) {	
		for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((j == 0) && ((i % 2) == 0)) {
					pieces[i][j] = new Piece(true, this, i, j, "pawn");
				}
				else if ((j == 7) && ((i % 2) != 0)) {
					pieces[i][j] = new Piece(false, this, i, j, "pawn");
				}
				else if ((j == 1) && ((i % 2) != 0)) {
					pieces[i][j] = new Piece(true, this, i, j, "shield");
				}
				else if ((j == 6) && ((i % 2) == 0)) {
					pieces[i][j] = new Piece(false, this, i, j, "shield");
				}
				else if ((j == 2) && ((i % 2) == 0)) {
					pieces[i][j] = new Piece(true, this, i, j, "bomb");
				}
				else if ((j == 5) && ((i % 2) != 0)) {
					pieces[i][j] = new Piece(false, this, i, j, "bomb");
				}
		}
	}
	}

	private void drawBoard(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) {
                    if (selected != null && pieces[i][j] == selected) {
                	   StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                    }
                    else {

                       StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                    }
                }
                else {                  
                	StdDrawPlus.setPenColor(StdDrawPlus.RED);
                }
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
                if (pieces[i][j] != null) {
                place(pieces[i][j], i, j);
            }
            }
        }
    }

    public void place(Piece p, int x, int y) {
    	if ((x > 7) || (x < 0) || (y > 7) || (y < 0) || (p == null)) {
    		return;
    	}
    	StdDrawPlus.picture(x + .5, y + .5, imgFile(p), 1, 1);
        pieces[x][y] = p;
    }

    public Piece remove(int x, int y) {
    	if ((x > 7) || (x < 0) || (y > 7) || (y < 0)) {
    		System.out.println("Input out of bounds");
    		return null;
    	}
    	else if (pieces[x][y] == null) {
    		System.out.println("No piece to remove");
    		return null;
    	}
    	else {
    		Piece result = pieces[x][y];
    		pieces[x][y] = null;
    		return result;
		}
    }

    public Piece pieceAt(int x, int y) {
    	if (pieces[x][y] == null) {
    		return null;
    	}
    	else {
    		return pieces[x][y];
    	}
    	}

     public boolean canSelect(int x, int y) {
        if (selected == null && pieces[x][y] == null) {
            return false;
        }
        if (selected == null && pieces[x][y] != null) {
            if (fireTurn && getPlayer(pieces[x][y]).equals("fire")) {
                return true;
            }
            if (fireTurn == false && getPlayer(pieces[x][y]).equals("water")) {
                return true;
            }
            if (fireTurn == false && getPlayer(pieces[x][y]).equals("fire")) {
                return false;
            }
            if (fireTurn && getPlayer(pieces[x][y]).equals("water")) {
                return false;
            }
        }
        // already selected
        // reselection
        if (hasMoved == false && pieces[x][y] != null) {
            System.out.println("reselection");
            if (fireTurn && getPlayer(pieces[x][y]).equals("fire")) {
                return true;
            }
            if (fireTurn == false && getPlayer(pieces[x][y]).equals("water")) {
                return true;
            }
        }
        // valid move
        if (pieces[x][y] == null) {
            System.out.println("here");
            if (hasMoved && canCapture(xSelected, ySelected, x, y)) {
            System.out.println("multi");
                return true;
            }
            if (selected.isKing() && hasMoved == false) {
                return true;
            }
            if (fireTurn) {
                if (ySelected == y - 1 && (xSelected == x + 1 || xSelected == x - 1)) {
                System.out.println("print");
                return true;
            }
            if (xSelected == x) {
                return false;
            } 
            if (canCapture(xSelected, ySelected, x, y)) {
                return true;
            }      
        }
            if (fireTurn == false) { 
                if (ySelected == y + 1 && (xSelected == x + 1 || xSelected == x - 1)) {
                return true;
            }
            if (xSelected == x) {
                return false;
            } 
                if (canCapture(xSelected, ySelected, x, y)) {
                return true;
            }  
            }   
        }
        System.out.println("what?");
        return false;
    }





    public boolean canCapture(int xi, int yi, int xf, int yf) {
        System.out.println(xi);
        System.out.println(yi);
        System.out.println(xf);
        System.out.println(yf);
        int slope = ((yf - yi) / (xf - xi));
        int midx = ((xi + xf) / 2);
        int midy = ((yi + yf) / 2);
        if ((slope == -1 || slope == 1) && pieces[xf][yf] == null  && pieces[midx][midy] != null && (!getPlayer(pieces[midx][midy]).equals(getPlayer(pieces[xi][yi]))) && (yi == yf + 2 || yi == yf - 2)) {
            return true;
        }
        return false;
    }

    public void select(int x, int y) {
        // selects a piece if selected is null or reselection
    	if (selected == null || (selected != null && hasMoved == false)) {
    	selected = pieces[x][y];
        xSelected = x;
        ySelected = y;
        StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
    	StdDrawPlus.filledSquare(x + .5, y + .5, .5);
    	}
        // piece selected already, move
    	if (selected != null && pieces[x][y] == null) {
            selected.move(x,y);
            hasMoved = true;
            
        }
}

	public boolean canEndTurn() {
		if (hasMoved == true) {
			return true;
            }
		return false;
	}

	public void endTurn() {
		hasMoved = false;
		selected = null;
        hasCaptured = false;
		if (fireTurn == true) {
			fireTurn = false;
		}
		else {
			fireTurn = true;
		}
	}

	public String winner() {
		int firecount = 0;
		int watercount =0;
		for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	if (pieces[i][j] != null) {
            		if (pieces[i][j].isFire()) {
            			firecount += 1;
            		}
            		else {
            			watercount += 1;
            		}
            	}
            }
        }
        if ((firecount > 0) && (watercount == 0)) {
        	return "Fire";
        }
        else if ((watercount > 0) && (firecount == 0)) {
        	return "Water";
        }
        else if ((watercount == 0) && (firecount == 0)) {
        	return "No one";
        }
        else {
        	return null;
        }
        }


    private String getPlayer(Piece p) {
  		if (p.isFire() == true) {
  			return "fire";
  		}
  		else {
  			return "water";
  		}
    }



    private String imgFile(Piece p) {
    	if (p.isKing() == true) {
    		return "img/" + getType(p) + "-" + getPlayer(p) + "-" + "crowned.png";
    	}
    	else {
    		return "img/" + getType(p) + "-" + getPlayer(p) + ".png";
    	}
    }

    private String getType(Piece p) {
    	if (p.isShield() == true) {
    		return "shield";
    	}
    	else if (p.isBomb() == true) {
    		return "bomb";
    	}
    	else {
    		return "pawn";
    	}
    }

    private int getxSelected(Piece p) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieceAt(i, j).equals(p)) {
                    return i;
                }
    }
}
return 0;
}

    private int getySelected(Piece p) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieceAt(i, j).equals(p)) {
                    return j;
                }
    }
}
return 0;
}
    
}

