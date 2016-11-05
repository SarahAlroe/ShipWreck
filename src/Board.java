/**
 * Created by alman on 10/4/16.
 */
public class Board {
    //Board enums
    public static final int NOTHING = 0;
    public static final int SHIP = 1;
    public static final int HIT_NOTHING = 3;
    public static final int HIT_SHIP = 4;

    private int[][] boardContents;
    private int boardSizeX;
    private int boardSizeY;

    public Board(int size) {
        boardSizeX = size;
        boardSizeY = size;
        boardContents = new int[boardSizeX][boardSizeY];
    }

    public int getSegment(Position position) {
        return getSegment(position.getX(), position.getY());
    }

    public int getSegment(int x, int y) {
        if (x < boardSizeX && x >= 0 && y < boardSizeY && y >= 0) {
            return boardContents[x][y];
        }
        return NOTHING;
    }

    public void setSegment(Position position, int value) {
        setSegment(position.getX(), position.getY(), value);
    }

    public void setSegment(int x, int y, int value) {
        if (x < boardSizeX && x >= 0 && y < boardSizeY && y >= 0) {
            boardContents[x][y] = value;
        }
    }

    public void addSegment(int x, int y, int value) {
        if (x < boardSizeX && x >= 0 && y < boardSizeY && y >= 0) {
            boardContents[x][y] += value;
        }
    }

    public int[] getBoardSize() {
        return new int[]{getBoardSizeX(), getBoardSizeY()};
    }

    public int getBoardSizeX() {
        return boardSizeX;
    }

    public int getBoardSizeY() {
        return boardSizeY;
    }

    public int tryHit(Position position) {
        return tryHit(position.getX(), position.getY());
    }

    public int tryHit(int x, int y) {
        if (x >= getBoardSizeX() || y >= getBoardSizeY()) {
            return NOTHING;
        }
        int segment = getSegment(x, y);
        if (segment == SHIP) {
            setSegment(x, y, HIT_SHIP);
        } else if (segment == NOTHING) {
            setSegment(x, y, HIT_NOTHING);
        }
        return segment;
    }

    public boolean isCleared() {
        boolean isClear = true;
        for (int i = 0; i < getBoardSizeX(); i++) {
            for (int j = 0; j < getBoardSizeY(); j++) {
                if (getSegment(i, j) == SHIP) {
                    isClear = false;
                }
            }
        }
        return isClear;
    }
}
