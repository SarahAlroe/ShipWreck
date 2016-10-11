import java.util.ArrayList;

/**
 * Created by silasa on 10/4/16.
 */

public class GameLogic {
    private int boardSize = 10;

    private Board board1 = new Board(boardSize);
    private Board board2 = new Board(boardSize);
    private int gameState;

    public void setup() {
        // load player and board, set gamestate
    }

    public ArrayList<Position> getPossibleEndPositions(Board board, int x, int y, int length) {
        ArrayList<Position> possiblePositions = new ArrayList<>();
        int maxLengthX = board.getBoardSizeX();
        int maxLengthY = board.getBoardSizeY();

        if (x + length < maxLengthX && board.getSegment(x + length, y) == Board.NOTHING) {
            possiblePositions.add(new Position(x + length, y));
        }
        if (x - length > 0 && board.getSegment(x - length, y) == Board.NOTHING) {
            possiblePositions.add(new Position(x - length, y));
        }
        if (y + length < maxLengthY && board.getSegment(x, y + length) == Board.NOTHING) {
            possiblePositions.add(new Position(x + length, y));
        }
        if (y - length > 0 && board.getSegment(x, y - length) == Board.NOTHING) {
            possiblePositions.add(new Position(x, y - length));
        }
        return possiblePositions;
    }
    public ArrayList<Position> getPossibleEndPositions(Board board, Position pos, int length) {
        return getPossibleEndPositions(board,pos.getX(),pos.getY(),length);
    }

}
