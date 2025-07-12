package src;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;

public class Game extends JFrame {

    private static final CardLayout cardLayout = new CardLayout();
    private static JPanel main;
    private static GamePanel game;
    private static JPanel start;
    private static JPanel end;

    private static int width;
    private static int height;
    private static double globalScale;

    public Game() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        DisplayMode dm = gd.getDisplayMode();
        AffineTransform af = gc.getDefaultTransform();
        width = (int) (dm.getWidth() / af.getScaleX());
        height = (int) (dm.getHeight() / af.getScaleY());
        globalScale = 1920.0 / width;

        // create frame
        setTitle("Zombie Shooter");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main = new JPanel(cardLayout);
        start = new StartPanel(width, height, globalScale);

        main.add(start, "start");
        add(main, BorderLayout.CENTER);
    }

    public static void showGame() {
        game = new GamePanel(width, height, globalScale);
        main.add(game, "game");
        cardLayout.show(main, "game");
        game.requestFocusInWindow();
        start = null;
    }

    public static void showEnding(boolean won) {
        end = new EndPanel(width, height, globalScale, won);
        main.add(end, "end");
        cardLayout.show(main, "end");
        end.requestFocusInWindow();
        game = null;
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.setVisible(true);
    }

    public static GamePanel getGamePanel() {
        return game;
    }
}
