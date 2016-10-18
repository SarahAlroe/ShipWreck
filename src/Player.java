/**
 * Created by silasa on 10/4/16.
 */
public abstract class Player implements Runnable {
    protected GameLogic gameLogic;
    protected String threadName;
    protected long sleepTime = 10;
    private Thread t;

    Player(String name) {
        threadName = name;
    }

    public void run() {
        gameLogic = GameLogic.getInstance();
        while (true) {
            GameState nextState = gameLogic.getNextState(this);
            if (nextState == GameState.WAIT || nextState == GameState.PRESETUP) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (nextState == GameState.SETUP) {
                placeAShip();
            } else if (nextState == GameState.PLAY) {
                makeAMove();
            } else if (nextState == GameState.GAMEOVER) {
                endGame();
                break;
            }
        }
    }

    abstract void endGame();

    abstract void makeAMove();

    abstract void placeAShip();

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
