package src;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;

public class Game extends JFrame {

    private GamePanel p;

    private static int width;
    private static int height;
    private static double scale;

    public Game() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        DisplayMode dm = gd.getDisplayMode();
        AffineTransform af = gc.getDefaultTransform();
        width = (int) (dm.getWidth() / af.getScaleX());
        height = (int) (dm.getHeight() / af.getScaleY());
        scale = 1920.0 / width;

        // create frame
        setTitle("Zombie Shooter");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        p = new GamePanel(width, height, scale);
        add(p);

        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.createImage(new byte[0]);
        Cursor c = t.createCustomCursor(i, new Point(0, 0), "c");
        p.setCursor(c);
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.setVisible(true);
    }
}
