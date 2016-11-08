import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Created by silasa on 10/25/16.
 */
public class Graphics {
    private static Graphics ourInstance = new Graphics();

    public static Graphics getInstance() {
        return ourInstance;
    }

    private JFrame a;
    private JButton button;
    private Config config = Config.getInstance();

    private Graphics() {
        // Create the window
        a = new JFrame("Shipwreck");
        // Sets the behavior for when the window is closed
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add a layout manager so that the button is not placed on top of the label
        a.setLayout(new FlowLayout());
        // Arrange the components inside the window
        a.pack();
        a.setSize(config.getHRes(),config.getVRes());
        // By default, the window is not visible. Make it visible.
        a.setVisible(true);
        button = new JButton("Play");
        a.add(button);
        button.addActionListener(new MyAction());

    }
    public void removePlayButton(){
        a.remove(button);
        a.revalidate();
        a.repaint();
    }

    public void drawGrid(int[] boardSize, int board) {
        //Draw a board of boardsize, on position board (0,1)
    }

    public void highlightBoardSquares(ArrayList<Position> positions, int board) {
        //Highlight specific positions on a board
    }

    public void pushText(String text) {
        //Show some text on the screen
    }

    public void clearBoardHighlights(int board) {
        //Remove all the set highlights on a board.
    }

    public void markBoard(Position markPos, int markType, int board) {
        //Mark a position on a board as some kind of hit
    }
}
//Myaction calls on gamelogic when the actionlistener is triggered along with removing the button afterwards.
class MyAction implements ActionListener {
    public void actionPerformed(ActionEvent ae){
        GameLogic.getInstance().setup();
        Graphics.getInstance().removePlayButton();
    }
}