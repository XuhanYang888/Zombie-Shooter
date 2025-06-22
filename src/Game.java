package src;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;

public class Game extends JFrame {

    private GamePanel p;

    private static double scaleX;
    private static double scaleY;
    private static int width;
    private static int height;

    public Game() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        DisplayMode dm = gd.getDisplayMode();
        AffineTransform af = gc.getDefaultTransform();
        scaleX = af.getScaleX();
        scaleY = af.getScaleY();
        width = (int) (dm.getWidth() / scaleX);
        height = (int) (dm.getHeight() / scaleY);

        // create frame
        setTitle("Zombie Shooter");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        p = new GamePanel(width, height, scaleX, scaleY);
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
