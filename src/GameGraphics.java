import java.awt.*;
import java.util.ArrayList;

/**
 * Created by silasa on 10/25/16.
 */

public class GameGraphics extends Component {
    private static GameGraphics ourInstance = new GameGraphics();
    public boolean[][] myBoardHighlight;
    public int[][] myBoard;
    public int[][] theirBoard;
    public int boardFieldCount = 10;
    public int buttonWidth;
    public int buttonHeight;
    public int outerMargin = 10;
    public int buttonMargin = 6;
    public int highlightWidth = 4;
    public Position secondBoardStart;
    public Position physicalBoardSize;
    public boolean boardsArrangedHorizontally = true;
    private Config config = Config.getInstance();
    String stringToDraw;

    private GameGraphics() {

    }

    public static GameGraphics getInstance() {
        return ourInstance;
    }

    public void paint(Graphics g) {
        calculateSizes();
        drawBoardContents(g, new Position(0, 0), 0);
        drawBoardContents(g, secondBoardStart, 1);
        g.drawString(stringToDraw,secondBoardStart.getX()+outerMargin,secondBoardStart.getY()+outerMargin/2);

    }

    private void calculateSizes() {
        boardsArrangedHorizontally = getWidth() > getHeight();
        if (boardsArrangedHorizontally) {
            secondBoardStart = new Position(getWidth() / 2, 0);
            physicalBoardSize = new Position(getWidth() / 2, getHeight());
        } else {
            secondBoardStart = new Position(0, getHeight() / 2);
            physicalBoardSize = new Position(getWidth(), getHeight() / 2);
        }
        buttonWidth = ((physicalBoardSize.getX() - outerMargin) / boardFieldCount) - (buttonMargin);
        buttonHeight = ((physicalBoardSize.getY() - outerMargin) / boardFieldCount) - (buttonMargin);
    }

    private void drawBoardContents(Graphics g, Position position, int b) {
        for (int i = 0; i < boardFieldCount; i++) {
            for (int j = 0; j < boardFieldCount; j++) {
                int startX = position.getX() + outerMargin + i * buttonWidth + i * buttonMargin;
                int startY = position.getY() + outerMargin + j * buttonHeight + j * buttonMargin;
                if (b == 0 && myBoardHighlight[i][j]) {
                    g.setColor(Color.yellow);
                    g.fillRect(startX - highlightWidth, startY - highlightWidth, buttonWidth + 2 * highlightWidth, buttonHeight + 2 * highlightWidth);
                }
                int[][] cBoard;
                if (b == 0) {
                    cBoard = myBoard;
                } else {
                    cBoard = theirBoard;
                }
                if (cBoard[i][j] == Board.NOTHING) {
                    g.setColor(Color.blue);
                } else if (cBoard[i][j] == Board.SHIP) {
                    g.setColor(Color.orange);
                } else if (cBoard[i][j] == Board.HIT_SHIP) {
                    g.setColor(Color.red);
                } else if (cBoard[i][j] == Board.HIT_NOTHING) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.gray);
                }

                g.fillRect(startX, startY, buttonWidth, buttonHeight);
            }
        }
    }

    public void drawGrid(int[] boardSize, int board) {
        boardFieldCount = boardSize[0];
        if (board == 0) {
            myBoard = new int[boardSize[0]][boardSize[1]];
            myBoardHighlight = new boolean[boardSize[0]][boardSize[1]];
            for (int i = 0; i < boardSize[0]; i++) {
                for (int j = 0; j < boardSize[1]; j++) {
                    myBoard[i][j] = 99;
                    myBoardHighlight[i][j] = false;
                }
            }
        } else {
            theirBoard = new int[boardSize[0]][boardSize[1]];
            for (int i = 0; i < boardSize[0]; i++) {
                for (int j = 0; j < boardSize[1]; j++) {
                    theirBoard[i][j] = 99;
                }
            }
        }
        repaint();
        //Draw a board of boardsize, on position board (0,1)
    }

    public void highlightBoardSquares(ArrayList<Position> positions) {
        //Highlight specific positions on a board
        for (int i = 0; i < positions.size(); i++) {
            myBoardHighlight[positions.get(i).getX()][positions.get(i).getY()] = true;
        }
        repaint();
    }

    public void pushText(String text) {
        //Show some text on the screen
        System.out.println(text);
        stringToDraw=text;
        repaint();
    }

    public void clearBoardHighlights(int board) {
        //Remove all the set highlights on a board.
        for (int i = 0; i < boardFieldCount; i++) {
            for (int j = 0; j < boardFieldCount; j++) {
                myBoardHighlight[i][j] = false;
            }
        }
        repaint();
    }

    public void markBoard(Position markPos, int markType, int board) {
        //Mark a position on a board as some kind of hit
        if (board == 0) {
            myBoard[markPos.getX()][markPos.getY()] = markType;
        } else {
            theirBoard[markPos.getX()][markPos.getY()] = markType;
        }
        repaint();
    }

    public Position getPosFromCoords(int x, int y) {
        int originY = 0;
        int originX = 0;
        if (getBoardFromCoords(x, y) == 1) {
            originX = secondBoardStart.getX();
            originY = secondBoardStart.getY();
        }
        for (int i = 0; i < boardFieldCount; i++) {
            for (int j = 0; j < boardFieldCount; j++) {
                int buttonStartX = originX + outerMargin + i * buttonWidth + i * buttonMargin;
                int buttonStartY = originY + outerMargin + j * buttonHeight + j * buttonMargin;
                if (x > buttonStartX && x < buttonStartX + buttonWidth && y > buttonStartY && y < buttonStartY + buttonHeight) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    public int getBoardFromCoords(int x, int y) {
        if (boardsArrangedHorizontally) {
            if (x >= secondBoardStart.getX()) {
                return 1;
            }
        } else {
            if (y >= secondBoardStart.getY()) {
                return 1;
            }
        }
        return 0;
    }
}
