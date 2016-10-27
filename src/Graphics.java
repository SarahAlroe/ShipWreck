import java.util.ArrayList;

/**
 * Created by silasa on 10/25/16.
 */
public class Graphics {
    private static Graphics ourInstance = new Graphics();

    public static Graphics getInstance() {
        return ourInstance;
    }

    private Graphics() {
    }

    public void drawGrid(int[] boardSize, int board) {
        //Draw a board of boardsize, on position board (0,1)
    }

    public void highlightBoardSquares(ArrayList<Position> positions, int board) {
        //Highlight specific positions on a board
    }

    public void pushText(String text) {
        //Show some text on the screen
    }

    public void clearBoardHighlights(int board) {
        //Remove all the set highlights on a board.
    }

    public void markBoard(Position markPos, int markType, int board) {
        //Mark a position on a board as some kind of hit
    }
}
