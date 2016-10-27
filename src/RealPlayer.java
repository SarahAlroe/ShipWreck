/**
 * Created by silasa on 10/25/16.
 */
public class RealPlayer extends Player {
    Graphics graphics;
    boolean isNew;
    boolean waitingForHuman = false;
    Position startPosition;
    boolean startPositionChosen = false;

    RealPlayer(String name) {
        super(name);
        graphics = Graphics.getInstance();
        isNew = true;
    }

    @Override
    void endGame() {
        if (gameLogic.getIsWinner(this)) {
            graphics.pushText("You won! You won! Congratulations! You won!");
        } else {
            graphics.pushText("GGWP betr luk nex tiem.");
        }
    }

    @Override
    void makeAMove() {
        if (!waitingForHuman) {
            graphics.pushText("Make a move.");
            waitingForHuman = true;
        }
    }

    @Override
    void placeAShip() {
        if (!waitingForHuman) {
            if (isNew) {
                graphics.drawGrid(gameLogic.getPlayerBoardSize(this), 0);
                graphics.drawGrid(gameLogic.getEnemyBoardSize(this), 1);
            }
            waitingForHuman = true;
            graphics.highlightBoardSquares(gameLogic.getPlaceablePositions(this), 0);
            if (gameLogic.getNextPlacement(this) > 1) {
                graphics.pushText("Choose the start location for a " + gameLogic.getNextPlacement(this) + " block ship");
            } else {
                graphics.pushText("Choose the location for a " + gameLogic.getNextPlacement(this) + " block ship");
            }
        }
    }

    @Override
    public void hitByEnemy(Position hitPosition, int hitType) {
        graphics.markBoard(hitPosition, hitType, 0);
        if (hitType == Board.NOTHING) {
            graphics.pushText("Enemy hit nothing...");
        } else if (hitType == Board.SHIP) {
            graphics.pushText("Enemy hit one of your ships!");
        } else if (hitType == Board.HIT_NOTHING) {
            graphics.pushText("Enemy hit nothing");
        } else if (hitType == Board.HIT_SHIP) {
            graphics.pushText("Enemy hit one of your ships, but it was already hit.");
        }
    }

    void handleClick(Position position, int board) {
        if (gameLogic.getNextState(this) == GameState.SETUP && board == 0) {
            if (!startPositionChosen) {
                chooseStartPosition(position);
            } else {
                placeShipEnd(position);
            }
        } else if (gameLogic.getNextState(this) == GameState.PLAY && board == 1) {
            int hitResult = gameLogic.tryHitFrom(position, this);
            graphics.markBoard(position, hitResult, 1);
            if (hitResult == Board.NOTHING) {
                graphics.pushText("You hit nothing...");
            } else if (hitResult == Board.SHIP) {
                graphics.pushText("You hit one of their ships!");
            } else if (hitResult == Board.HIT_NOTHING) {
                graphics.pushText("You hit nothing... again.");
            } else if (hitResult == Board.HIT_SHIP) {
                graphics.pushText("You hit one of their ships, but it was already hit.");
            }
        }
    }

    void chooseStartPosition(Position position) {
        startPosition = position;
        startPositionChosen = true;
        if (gameLogic.getNextPlacement(this) == 1) {
            placeShipEnd(position);
            return;
        }
        graphics.clearBoardHighlights(0);
        graphics.highlightBoardSquares(gameLogic.getPossibleEndPositions(this, position, gameLogic.getNextPlacement(this)), 0);
        graphics.pushText("Choose the end location for the " + gameLogic.getNextPlacement(this) + " block ship");
    }

    void placeShipEnd(Position position) {
        if (gameLogic.placeShip(startPosition, position, this)) {
            graphics.pushText("Ship placed");
        } else {
            graphics.pushText("Ship placement error");
        }
        graphics.clearBoardHighlights(0);
        waitingForHuman = false;
        startPositionChosen = false;
    }

    //TODO add to clicklistener (make click listener)

}
