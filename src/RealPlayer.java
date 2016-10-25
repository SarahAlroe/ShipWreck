/**
 * Created by silasa on 10/25/16.
 */
public class RealPlayer extends Player {
    Graphics graphics;
    boolean isNew;
    boolean waitingForHuman = false;

    RealPlayer(String name) {
        super(name);
        graphics = Graphics.getInstance();
        isNew = true;
    }

    @Override
    void endGame() {
        if (gameLogic.getIsWinner(this)){
            graphics.pushText("You won! You won! Congratulations! You won!");
        }else {
            graphics.pushText("GGWP betr luk nex tiem.");
        }
    }

    @Override
    void makeAMove() {

    }

    @Override
    void placeAShip() {
        if (!waitingForHuman) {
            if (isNew) {
                graphics.drawGrid(gameLogic.getPlayerBoardSize(this), 0);
                graphics.drawGrid(gameLogic.getEnemyBoardSize(this), 1);
            }
            waitingForHuman=true;
            graphics.highlightBoardSquares(gameLogic.getPlaceablePositions(this), 0);
            graphics.pushText("Choose the start location for a " + gameLogic.getNextPlacement(this) + " block ship");
        }
    }
    void startPositionChosen(Position position){
        graphics.clearBoardHighlights(0);
        graphics.highlightBoardSquares(gameLogic.getPossibleEndPositions(this,position,gameLogic.getNextPlacement(this)),0);
        graphics.pushText("Choose the end location for the " + gameLogic.getNextPlacement(this) + " block ship");
    }
    //TODO add to clicklistener (make click listener)

}
