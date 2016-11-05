import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by silasa on 11/4/16.
 */
public class BoardVisualiser {
    static void generateImage(Board board, String name) {
        int width = board.getBoardSizeX();
        int height = board.getBoardSizeY();
        int[][] boardContents = getBoardContents(board);
        int maxValue = getMaxValue(boardContents, width, height);
        int minValue = getMinValue(boardContents, width, height);
        BufferedImage mapImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int cVar = 0;
                if ((maxValue - minValue) != 0) {
                    cVar = (boardContents[i][j] - minValue) * 255 / (maxValue - minValue);
                }
                int color = new Color(cVar, cVar, cVar).getRGB();
                mapImg.setRGB(i, j, color);
            }
        }
        File outFile = new File(name + ".png");
        try {
            ImageIO.write(mapImg, "png", outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] getBoardContents(Board board) {
        int width = board.getBoardSizeX();
        int height = board.getBoardSizeY();
        int[][] contents = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                contents[i][j] = board.getSegment(i, j);
            }
        }
        return contents;
    }

    private static int getMaxValue(int[][] table, int width, int height) {
        int maxValue = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (table[i][j] > maxValue) {
                    maxValue = table[i][j];
                }
            }
        }
        return maxValue;
    }

    private static int getMinValue(int[][] table, int width, int height) {
        int minValue = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (table[i][j] < minValue) {
                    minValue = table[i][j];
                }
            }
        }
        return minValue;
    }
}
