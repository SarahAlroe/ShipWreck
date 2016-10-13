import java.util.ArrayList;

/**
 * Created by silasa on 10/4/16.
 */

public class GameLogic {
    //GameLogic constants
    public static final int WAIT = 0;
    public static final int PRESETUP = 1;
    public static final int SETUP = 2;
    public static final int PLAY = 3;
    public static final int GAMEOVER = 4;

    private int boardSize = 10;

    private Board board1;
    private Board board2;

    private Player player1;
    private Player player2;

    private ArrayList<Integer> shipsToPlace1 = new ArrayList<>();
    private ArrayList<Integer> shipsToPlace2 = new ArrayList<>();

    private int gameState = PRESETUP;

    private int activePlayer = 0;
    private int hasWon = 0;

    public void setup() {
        // load player and board, set gamestate
        board1 = new Board(boardSize);
        board2 = new Board(boardSize);
        player1 = new Player(); //TODO Replace with proper player class
        player2 = new Player(); //TODO Replace with proper player class
        shipsToPlace1 = generateShipsFromBoardSize(board1.getBoardSizeX(), board1.getBoardSizeY());
        shipsToPlace1 = generateShipsFromBoardSize(board2.getBoardSizeX(), board2.getBoardSizeY());
        gameState = SETUP;
    }

    private ArrayList<Integer> generateShipsFromBoardSize(int maxX, int maxY) {
        ArrayList<Integer> ships = new ArrayList<>();
        int boardSize = maxX * maxY;
        double[] shipAmount = {0.03, 0.02, 0.01, 0.005, 0.001};// Amount of [n+1] ships per square
        int maxShipSize = 3;
        for (int i = 1; i < maxShipSize; i++) {
            int iShipsToPlace = (int) (shipAmount[i - 1] * boardSize);
            for (int j = 0; j < iShipsToPlace; j++) {
                ships.add(i);
            }
        }
        return ships;
    }

    public int getNextAction(Player player) {
        if (gameState == PRESETUP) {
            return WAIT;
        } else if (gameState == PLAY) {
            if (player.equals(player1)) {
                if (activePlayer == 1) {
                    return PLAY;
                } else {
                    return WAIT;
                }
            } else {
                if (activePlayer == 2) {
                    return PLAY;
                } else {
                    return WAIT;
                }
            }
        } else return gameState;
    }

    public int tryHitFrom(Position position, Player player) {
        Board boardToHit;
        if (player.equals(player1)) {
            boardToHit = board2;
        } else {
            boardToHit = board1;
        }
        int hit = boardToHit.tryHit(position);
        if (boardToHit.isCleared()) {
            gameState = GAMEOVER;
            if (player.equals(player1)) {
                hasWon = 1;
            } else {
                hasWon = 2;
            }
        }
        return hit;
        //TODO Find a way to make this independent of player count
    }

    public void switchActivePlayer() {
        if (activePlayer == 1) {
            activePlayer = 2;
        } else {
            activePlayer = 1;
        }
    }

    public boolean getIsWinner(Player player) {
        if (player.equals(player1)) {
            return (hasWon == 1);
        } else if (player.equals(player2)) {
            return (hasWon == 2);
        }
        return false;
    }

    public int getNextPlacement(Player player) {
        if (player.equals(player1)) {
            return shipsToPlace1.get(0);
        } else {
            return shipsToPlace2.get(0);
        }
    }

    public ArrayList<Position> getPlaceablePositions(Player player) {
        if (player == player1) {
            return getPlaceablePositions(board1);
        } else {
            return getPlaceablePositions(board2);
        }

    }

    public ArrayList<Position> getPlaceablePositions(Board board) {
        ArrayList<Position> positions = new ArrayList<>();
        for (int i = 0; i > board.getBoardSizeX(); i++) {
            for (int j = 0; j > board.getBoardSizeY(); j++) {
                if (board.getSegment(i, j) == Board.NOTHING) {
                    positions.add(new Position(i, j));
                }
            }
        }
        return positions;
    }

    public boolean placeShip(Position startPosition, Position endPosition, Player player) {
        if (player.equals(player1)) {
            return placeShip(startPosition, endPosition, board1);
        } else {
            return placeShip(startPosition, endPosition, board2);
        }
    }

    public boolean placeShip(Position startPosition, Position endPosition, Board board) {
        if (startPosition.equals(endPosition)) {
            board.setSegment(startPosition, Board.SHIP);
        } else if (startPosition.getX() != endPosition.getX()) {
            int changePos = startPosition.getX() - endPosition.getX();
            if (changePos > 0) {
                for (int i = 0; i < changePos; i++) {
                    board.setSegment(startPosition.getX() + i, startPosition.getY(), Board.SHIP);
                }
            } else {
                for (int i = 0; i > changePos; i--) {
                    board.setSegment(startPosition.getX() + i, startPosition.getY(), Board.SHIP);
                }
            }
        } else if (startPosition.getY() != endPosition.getY()) {
            int changePos = startPosition.getY() - endPosition.getY();
            if (changePos > 0) {
                for (int i = 0; i < changePos; i++) {
                    board.setSegment(startPosition.getX(), startPosition.getY() + i, Board.SHIP);
                }
            } else {
                for (int i = 0; i > changePos; i--) {
                    board.setSegment(startPosition.getX(), startPosition.getY() + i, Board.SHIP);
                }
            }
        }
        //Pop placed ship from the proper list - Possibly do extract
        //TODO only if actually placed
        if (board.equals(board1)) {
            shipsToPlace1.remove(0);
        } else {
            shipsToPlace2.remove(0);
        }
        //Check if we are done placing ships
        checkIfSetupDone();
        return true; //TODO Do check if placement can actually be done and return false if not
    }

    public void checkIfSetupDone() {
        if (shipsToPlace1.size() == 0 && shipsToPlace2.size() == 0) {
            gameState = PLAY;
        }
    }

    public ArrayList<Position> getPossibleEndPositions(Board board, Position pos, int length) {
        return getPossibleEndPositions(board, pos.getX(), pos.getY(), length);
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
        return possiblePositions; //TODO Note: Can place ships through other ships, recommend make test function.
    }

}
