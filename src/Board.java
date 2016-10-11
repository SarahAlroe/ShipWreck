/**
 * Created by alman on 10/4/16.
 */
public class Board {
    //Board constants
    public static final int NOTHING = 0;
    public static final int SHIP = 1;
    public static final int HIT_NOTHING = 3;
    public static final int HIT_SHIP = 4;

    private int[][] boardContents;
    private int boardSizeX;
    private int boardSizeY;

    public int getBoardSizeX() {
        return boardSizeX;
    }

    public int getBoardSizeY() {
        return boardSizeY;
    }

    public Board(int size) {
        boardSizeX = size;
        boardSizeY = size;
        boardContents = new int[boardSizeX][boardSizeY];
    }

    public int getSegment (int x,int y){
        return boardContents[x][y];
    }
    public int getSegment (Position position){return getSegment(position.getX(),position.getY());}
    public void setSegment (int x,int y, int value) {
        boardContents[x][y] = value;
    }
    public void setSegment (Position position, int value) {setSegment(position.getX(),position.getY(),value);}

    public int[] getBoardSize() {
        return new int[]{getBoardSizeX(), getBoardSizeY()};
    }
    public int tryHit(int x, int y){
        if(x>=getBoardSizeX() || y>=getBoardSizeY()){
            return NOTHING;
        }
        int segment=getSegment(x,y);
        if (segment == SHIP){
            setSegment(x,y,HIT_SHIP);
            return SHIP;
        }
        else if (segment == NOTHING){
            setSegment(x,y,HIT_NOTHING);
            return NOTHING;
        }
        else {
            return segment;
        }
    }
    public int tryHit(Position position){
        return tryHit(position.getX(),position.getY());
    }
}
