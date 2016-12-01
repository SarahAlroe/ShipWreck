import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by silasa on 10/4/16.
 */
public class ShipWreck extends JApplet {

    public GameGraphics gameGraphics;
    public JButton button;
    public JLabel label;
    private Config config = Config.getInstance();

    public static void main(String[] args) {
        JFrame f = new JFrame("ShipWreck");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JApplet ap = new ShipWreck();
        ap.init();
        ap.start();
        f.add("Center", ap);
        f.pack();
        f.setVisible(true);
        ap.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                GameLogic.getInstance().getRealPlayer().boardWasClicked(e.getX(), e.getY());
            }
        });
    }

    public void start() {
        setLayout(new BorderLayout());
        setBackground(Color.black);
        button = new JButton("Play");
        button.addActionListener(new ButtonClickListener(this));
        label = new JLabel("ShipWreck");
        add(label, BorderLayout.NORTH);
        add(button,BorderLayout.CENTER);
    }

    public void playButtonClicked() {
        GameLogic.getInstance().setup();
        removePlayButton();
        gameGraphics = GameGraphics.getInstance();
        add(gameGraphics);
        gameGraphics.setBackground(Color.black);
        gameGraphics.drawGrid(new int[]{config.getBoardSize(), config.getBoardSize()}, 0);
        gameGraphics.drawGrid(new int[]{config.getBoardSize(), config.getBoardSize()}, 1);
        revalidate();
        repaint();
    }

    public void removePlayButton() {
        remove(button);
        remove(label);
        revalidate();
        repaint();
    }
}

class ButtonClickListener implements ActionListener {
    public ShipWreck shipWreck;
    public ButtonClickListener(ShipWreck s) {
        super();
        shipWreck = s;
    }
    public void actionPerformed(ActionEvent ae) {
        shipWreck.playButtonClicked();
    }
}