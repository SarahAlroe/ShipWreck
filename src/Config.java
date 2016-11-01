/**
 * Created by silasa on 11/1/16.
 */
public class Config {
    private static Config instance = new Config();
    private int vRes = 600;
    private int hRes = 800;
    private int playerSleepTime = 10;
    private int boardSize = 10;

    public static Config getInstance() {
        return instance;
    }

    public int getHRes() {
        return hRes;
    }

    public int getVRes() {
        return vRes;
    }

    public int getPlayerSleepTime() {
        return playerSleepTime;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
