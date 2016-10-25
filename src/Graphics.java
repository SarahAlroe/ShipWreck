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

    }

    public void highlightBoardSquares(ArrayList<Position> positions, int board) {

    }

    public void pushText(String s) {
    }

    public void clearBoardHighlights(int i) {
    }
}
