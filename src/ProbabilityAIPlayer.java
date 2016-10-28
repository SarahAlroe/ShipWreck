import java.util.ArrayList;

/**
 * Created by silasa on 10/27/16.
 */
public class ProbabilityAIPlayer extends Player {
    ArrayList<Integer> ships;
    private boolean isFistMove = true;
    private int[] enemyBoardSize;
    private int largestShip = 0;
    private Board enemyBoard;
    private Board enemyProbabilityBoard;

    ProbabilityAIPlayer(String name) {
        super(name);
    }

    protected void endGame() {
        if (gameLogic.getIsWinner(this)) {
            System.out.println("Smarty mc smartypants " + threadName + " does it again!");
        } else {
            System.out.println("HOW? How could i loose, " + threadName + "?! It's the RNG's fault!");
        }
    }

    protected void makeAMove() {
        if (isFistMove) {
            isFistMove = false;
            enemyBoardSize = gameLogic.getEnemyBoardSize(this);
            enemyBoard = new Board(enemyBoardSize[0]);
            for (int shipLength : ships) {
                if (shipLength > largestShip) {
                    largestShip = shipLength;
                }
            }
        }


        Position target = new Position((int) (enemyBoardSize[0] * Math.random()), (int) (enemyBoardSize[1] * Math.random()));
        gameLogic.tryHitFrom(target, this);
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
                ships.add(shipLength);
                break;
            }
        }

    }

    @Override
    public void hitByEnemy(Position hitPosition, int hitType) {
        //Don rly do anyting
    }


}