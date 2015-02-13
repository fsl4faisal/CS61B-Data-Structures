/** 
 *  @author Josh Hug
 */

public class HDraw {
    /** Location of pieces. */
    public static boolean shouldBeEmpty;
    public static Piece[][] pieces;
    private boolean fireTurn;
    private Piece selected;
    private int xSelected;
    private int ySelected;
    private boolean hasMoved;
    private boolean hasCaptured;

    /** Draws an N x N board. Adapted from:
        http://introcs.cs.princeton.edu/java/15inout/CheckerBoard.java.html
     */

    private static void drawBoard(int N) {
         for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) {
                    StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                }
                else {                  
                    StdDrawPlus.setPenColor(StdDrawPlus.RED);
                }
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
                StdDrawPlus.picture(i + .5, j + .5, "img/bomb-fire-crowned.png", 1, 1);
                }
            }
        }

    
    public static void main(String[] args) {
        int N = 8;
        StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);    

        /** Monitors for mouse presses. Wherever the mouse is pressed,
            a new piece appears. */
        while(true) {
            Board b = new Board(false);
            b.addPieces(N);
            drawBoard(N);
            if (StdDrawPlus.mousePressed()) {
                double xd = StdDrawPlus.mouseX();
                double yd = StdDrawPlus.mouseY();
                int x = (int)xd;
                int y = (int)yd;
                b.select(x, y);
            }            
            StdDrawPlus.show(100);
            if (StdDrawPlus.isSpacePressed()) {
            }
            StdDrawPlus.show(100);
        }
    }
}