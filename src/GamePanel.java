package src;

import javax.swing.*;
import apphelper.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private static final Sprite bg = new Sprite(0, 0, "imgs/bg.png");

    private static Sprite m = new Sprite(0, 0, "imgs/m1.png");

    protected static int width;
    protected static int height;
    protected static double scale;
    private static int[] center;

    private static Robot robot;
    private static Timer t;
    private static int deltaX;
    private static int deltaY;
    protected static int offsetX;
    protected static int offsetY;
    private static int limitX;
    private static int limitY;

    private static int health;
    private static int coins;

    private static final Random r = new Random();

    private static final ArrayList<Zombie> zombies = new ArrayList<>();

    private static Weapon w;

    boolean temp;

    public GamePanel(int width, int height, double scale) {
        GamePanel.width = width;
        GamePanel.height = height;
        GamePanel.scale = scale;

        bg.resize((int) (bg.getWidth() * 2.5 / GamePanel.scale), (int) (bg.getHeight() * 2.5 / GamePanel.scale));
        limitX = -bg.getWidth() + GamePanel.width;
        limitY = -bg.getHeight() + GamePanel.height;

        m.resize((int) (m.getWidth() / scale), (int) (m.getHeight() / scale));

        center = new int[] { width / 2, height / 2 };

        health = 100;
        coins = 0;

        generateZombies(5);

        w = new Weapon();

        t = new Timer(1, e -> {
            offsetX -= 2 * deltaX;
            offsetY -= 2 * deltaY;

            repaint();
        });

        new Timer(100, e -> {
            for (Zombie z : zombies) {
                z.nextFrame();
            }
        }).start();

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        requestFocusInWindow();

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        offsetX = Math.max(limitX, Math.min(0, offsetX));
        offsetY = Math.max(limitY, Math.min(0, offsetY));

        bg.setX(offsetX);
        bg.setY(offsetY);

        m.setX(offsetX + (int) (width * 1.1));
        m.setY(offsetY + (int) (height * 1.4));

        // draw background
        bg.draw(g);

        // draw modules
        m.draw(g);

        // draw zombies
        ArrayList<Zombie> reversed = new ArrayList<>(zombies);
        Collections.reverse(reversed);
        for (Zombie z : reversed) {
            z.draw(g);
        }

        w.draw(g);

        // draw crosshair
        g.setColor(Color.YELLOW);
        g.fillRect(width / 2 - 20 - w.recoil, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 + 5 + w.recoil, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 - 2, height / 2 - 20 - w.recoil, 4, 15);
        g.fillRect(width / 2 - 2, height / 2 + 5 + w.recoil, 4, 15);
        g.drawString("❤️ " + health, 10, 30);
        g.drawString("⭕ " + coins, 10, 45);

        g.drawString("" + zombies.size(), 10, 60);
    }

    public static void generateZombies(int n) {
        for (int i = 0; i < n; i++) {
            zombies.add(new Zombie((int) (width * (0.6 + r.nextDouble(0.1))),
                    (int) (height * (1.25 + r.nextDouble(0.08))),
                    0.4 / scale, r.nextInt(5)));
        }
    }

    // all keys
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F) {
            if (m.getX() - width / 2 <= 0
                    && m.getX() - width / 2 >= -m.getWidth()
                    && m.getY() - height / 2 <= 0
                    && m.getY() - height / 2 >= -m.getHeight()) {
                JOptionPane.showMessageDialog(this, "Accessing module 1");
            }
        }
    }

    // all keys
    @Override
    public void keyReleased(KeyEvent e) {
    }

    // this only happens for visible keys
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Calculate the mouse movement delta
        deltaX = e.getXOnScreen() - center[0];
        deltaY = e.getYOnScreen() - center[1];

        // Move mouse back to center
        robot.mouseMove(center[0], center[1]);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Calculate the mouse movement delta
        deltaX = e.getXOnScreen() - center[0];
        deltaY = e.getYOnScreen() - center[1];

        // Move mouse back to center
        robot.mouseMove(center[0], center[1]);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            w.recoil = 3;

            for (int i = 0; i < zombies.size(); i++) {
                Zombie z = zombies.get(i);
                if (offsetX + z.x + (z.width * 0.15) - width / 2 <= 0
                        && offsetX + z.x + (z.width * 0.15) - width / 2 >= -z.width * 0.4
                        && offsetY + z.y + (z.height * 0.15) - height / 2 <= 0
                        && offsetY + z.y + (z.height * 0.15) - height / 2 >= -z.height * 0.8) {
                    z.health--;
                    if (z.health == 0) {
                        zombies.remove(i);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            w.recoil = 0;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
