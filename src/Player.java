/**
 * Created by silasa on 10/4/16.
 */
abstract class Player implements Runnable {
    GameLogic gameLogic;
    String threadName;
    private long sleepTime = Config.getInstance().getPlayerSleepTime();
    private Thread t;

    Player(String name) {
        threadName = name;
    }

    public void run() {
        gameLogic = GameLogic.getInstance();
        while (true) {
            GameState nextState = gameLogic.getNextState(this);
            if (nextState == GameState.SETUP) {
                placeAShip();
            } else if (nextState == GameState.PLAY) {
                makeAMove();
            } else if (nextState == GameState.GAMEOVER) {
                endGame();
                break;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    abstract void placeAShip();

    abstract void makeAMove();

    abstract void endGame();

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public abstract void hitByEnemy(Position hitPosition, int hitType);
}
