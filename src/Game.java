package src;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;

public class Game extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel main;
    private GamePanel p;

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
        JPanel panel2 = createPanel("Panel 2", Color.cyan);

        p = new GamePanel(width, height, globalScale);
        main.add(p, "game");
        main.add(panel2, "p2");
        JPanel buttonPanel = new JPanel();
        JButton switchButton1 = new JButton("Show game");
        JButton switchButton2 = new JButton("Show Panel 2");

        switchButton1.addActionListener(e -> cardLayout.show(main, "game"));
        switchButton2.addActionListener(e -> cardLayout.show(main, "p2"));

        buttonPanel.add(switchButton1);
        buttonPanel.add(switchButton2);

        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Toolkit t = Toolkit.getDefaultToolkit();
        // Image i = t.createImage(new byte[0]);
        // Cursor c = t.createCustomCursor(i, new Point(0, 0), "c");
        // p.setCursor(c);
    }

    private JPanel createPanel(String text, Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.add(new JLabel(text));
        return panel;
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.setVisible(true);
    }
}
