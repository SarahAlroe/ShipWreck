import java.awt.*;
import java.util.ArrayList;

/**
 * Created by silasa on 10/25/16.
 */

public class GameGraphics extends Component{
    private static GameGraphics ourInstance = new GameGraphics();

    public static GameGraphics getInstance() {
        return ourInstance;
    }

    public boolean [] [] myBoardHighlight;
    public int [] [] myBoard;
    public int [] [] theirBoard;
    public int boardFieldCount = 10;
    public int buttonWidth;
    public int buttonHeight;
    public int outerMargin = 5;
    public int buttonMargin = 5;
    public int highlightWidth = 3;
    public Position secondBoardStart;
    public Position physicalBoardSize;
    public boolean boardsArrangedHorizontally=true;

    private Config config = Config.getInstance();

    private GameGraphics() {

    }
    public void paint(Graphics g) {
        calculateSizes();

        drawBoardContents(g,new Position(0,0),0);
        drawBoardContents(g,secondBoardStart,1);

    }

    private void drawBoardContents(Graphics g, Position position, int b) {
        for (int i=0;i<boardFieldCount;i++){
            for (int j=0;j<boardFieldCount;j++){
                int startX = position.getX()+outerMargin+i*buttonWidth+i*buttonMargin;
                int startY = position.getY()+outerMargin+j*buttonHeight+j*buttonMargin;
                g.setColor(Color.black);
                g.fillRect(startX,startY,buttonWidth,buttonHeight);
            }
        }
    }

    private void calculateSizes() {
        boardsArrangedHorizontally = getWidth() > getHeight();
        if (boardsArrangedHorizontally){
            secondBoardStart = new Position(getWidth()/2,0);
            physicalBoardSize = new Position(getWidth()/2,getHeight());
        }else{
            secondBoardStart = new Position(0,getHeight()/2);
            physicalBoardSize = new Position(getWidth(),getHeight()/2);
        }
        buttonWidth = ((physicalBoardSize.getX()-outerMargin)/boardFieldCount)-(buttonMargin);
        buttonHeight = ((physicalBoardSize.getY()-outerMargin)/boardFieldCount)-(buttonMargin);
    }

    public void drawGrid(int[] boardSize, int board) {
        boardFieldCount=boardSize[0];
        if (board == 0){
            myBoard = new int[boardSize[0]][boardSize[1]];
            myBoardHighlight = new boolean[boardSize[0]][boardSize[1]];
        }else {
            theirBoard = new int[boardSize[0]][boardSize[1]];
        }
        repaint();
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
