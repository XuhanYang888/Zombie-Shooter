package final_project;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;

public class Game extends JFrame {
    
    private GamePanel p;
    
    private static int width;
    private static int height;

    public Game() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        DisplayMode dm = gd.getDisplayMode();
        AffineTransform af = gc.getDefaultTransform();
        width = (int) (dm.getWidth() / af.getScaleX());
        height = (int) (dm.getHeight() / af.getScaleY());
        
        // create frame
        setTitle("Game");
        setSize(width,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        p = new GamePanel(width,height);
        add(p);
        
        // make window focusable to receive key events
        setFocusable(true);
        
        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.createImage(new byte[0]);
        Cursor c = t.createCustomCursor(i, new Point(0,0), "c");
        p.setCursor(c);
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.setVisible(true);
    }
}
