import java.util.ArrayList;

/**
 * Created by silasa on 10/27/16.
 */
public class SmartAIPlayer extends Player {
    SmartAIPlayer(String name) {
        super(name);
    }

    protected void endGame() {
        if (gameLogic.getIsWinner(this)) {
            System.out.println("YES! I AM " + threadName + " and i won the game!");
        } else {
            System.out.println("aww... " + threadName + " lost the game");
        }
    }

    protected void makeAMove() {
        int[] enemyBoardSize = gameLogic.getEnemyBoardSize(this);
        Position target = new Position((int) (enemyBoardSize[0] * Math.random()), (int) (enemyBoardSize[1] * Math.random()));
        int whatHit = gameLogic.tryHitFrom(target, this);
        if (whatHit == Board.SHIP) {
            System.out.println(threadName + " hit something");
        } /*else {
            System.out.println(threadName + " hit nothing or something again..");
        }*/
    }

    protected void placeAShip() {
        while (true) {
            //Keep going until a successful placement.
            int shipLength = gameLogic.getNextPlacement(this);
            if (shipLength == 0) {
                break;
            }
            ArrayList<Position> placeablePositions = gameLogic.getPlaceablePositions(this);
            Position chosenPos = placeablePositions.get((int) (Math.random() * placeablePositions.size()));
            Position chosenEndPos = chosenPos;
            if (shipLength != 1) {
                ArrayList<Position> possibleEndPositions = gameLogic.getPossibleEndPositions(this, chosenPos, shipLength);
                if (possibleEndPositions.size() == 0) {
                    continue;
                }
                chosenEndPos = possibleEndPositions.get((int) (Math.random() * possibleEndPositions.size()));
            }
            if (gameLogic.placeShip(chosenPos, chosenEndPos, this)) {
                //System.out.println(chosenPos);
                break;
            }
        }

    }

    @Override
    public void hitByEnemy(Position hitPosition, int hitType) {
        //Don rly do anyting
    }


}