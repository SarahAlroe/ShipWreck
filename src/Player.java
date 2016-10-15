/**
 * Created by silasa on 10/4/16.
 */
public abstract class Player implements Runnable {
    protected GameLogic gameLogic;
    private Thread t;
    protected String threadName;
    protected long sleepTime = 10;

    Player(String name) {
        threadName = name;
    }

    public void run() {
        gameLogic = GameLogic.getInstance();
        while (true) {
            int currentAction = gameLogic.getNextAction(this);
            if (currentAction == GameLogic.WAIT || currentAction == GameLogic.PRESETUP) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (currentAction == GameLogic.SETUP) {
                placeAShip();
            } else if (currentAction == GameLogic.PLAY) {
                makeAMove();
            } else if (currentAction == GameLogic.GAMEOVER) {
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
