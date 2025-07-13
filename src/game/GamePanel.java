package src.game;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import src.apphelper.Sprite;

public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private static Sprite bg;
    private static Sprite store;
    private static Sprite exit;
    private static Sprite damage;

    private static int storeX;
    private static int storeY;
    private static int exitX;
    private static int exitY;

    protected static int width;
    protected static int height;
    protected static double globalScale;
    private static int[] center;

    private static Robot robot;
    private static Timer repaint;
    private static Timer viewOffset;
    private static Timer zombieFrames;
    private static Timer hit;

    private static int deltaX;
    private static int deltaY;
    protected static int offsetX;
    protected static int offsetY;
    private static int limitX;
    private static int limitY;

    private static int health;
    private static boolean red;
    private static int coins;
    private static int target;
    private static int wave;
    private static int[] infoDisplayCoords;
    private static Sprite heart;
    private static Sprite coin;

    private static final Random r = new Random();

    private static final ArrayList<Zombie> zombies = new ArrayList<>();

    private static Weapon w;

    private static Font f;

    public GamePanel(int width, int height, double globalScale) {
        GamePanel.width = width;
        GamePanel.height = height;
        GamePanel.globalScale = globalScale;

        bg = new Sprite(0, 0, "src/imgs/bg.png", (int) (5400 / globalScale), (int) (1800 / globalScale));
        offsetX = (int) (-5400 / globalScale * 0.09);
        offsetY = (int) (-1800 / globalScale * 0.21);
        bg.setX(offsetX);
        bg.setY(offsetY);
        limitX = -bg.getWidth() + GamePanel.width;
        limitY = -bg.getHeight() + GamePanel.height;
        storeX = (int) (width * 1.343 / globalScale);
        storeY = (int) (height * 0.75 / globalScale);
        exitX = (int) (width * 2.03 / globalScale);
        exitY = (int) (height * 0.87 / globalScale);
        store = new Sprite(offsetX + storeX, offsetY + storeY, "src/imgs/store.png", (int) (264 / globalScale),
                (int) (404 / globalScale));
        exit = new Sprite(offsetX + exitX, offsetY + exitY, "src/imgs/exit.png", (int) (476 / globalScale),
                (int) (204 / globalScale));

        damage = new Sprite(0, 0, "src/imgs/damage.png", width, height);

        center = new int[] { width / 2, height / 2 };

        health = 100;
        red = false;
        coins = 0;
        target = 200;

        heart = new Sprite((int) (width * 0.01), (int) (height * 0.01), "src/imgs/heart.png", (int) (45 / globalScale),
                (int) (45 / globalScale));
        coin = new Sprite((int) (width * 0.01), (int) (height * 0.07), "src/imgs/coin.png", (int) (45 / globalScale),
                (int) (45 / globalScale));
        infoDisplayCoords = new int[] { (int) (width * 0.04), (int) (height * 0.045),
                (int) (width * 0.04), (int) (height * 0.105),
                (int) (width * 0.01), (int) (height * 0.165)
        };

        wave = 0;
        Zombie.updateCenter(center);

        w = new Weapon(width, height, globalScale);

        viewOffset = new Timer(1, e -> {
            offsetX -= 2 * deltaX;
            offsetY -= 2 * deltaY;
            offsetX = Math.max(limitX, Math.min(0, offsetX));
            offsetY = Math.max(limitY, Math.min(0, offsetY));
        });

        repaint = new Timer(5, e -> {
            repaint();
        });

        hit = new Timer(1000, e -> {
            red = false;
        });

        zombieFrames = new Timer(80, e -> {
            if (zombies.isEmpty()) {
                wave++;
                generateZombies(2 + wave, 5 * wave, 5 * wave);
            } else {
                for (int i = 0; i < zombies.size(); i++) {
                    Zombie z = zombies.get(i);
                    z.nextFrame();
                    if (r.nextInt(20) == 0) {
                        z.flipHorizontal();
                    }
                    if (z.reachPlayer()) {
                        zombies.remove(i);
                        health -= z.getDamage();
                        if (health > 0) {
                            red = true;
                            hit.restart();
                        } else {
                            repaint.stop();
                            viewOffset.stop();
                            zombieFrames.stop();
                            hit.stop();
                            Game.showEnding(false);
                        }
                    }
                }
            }
        });

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

        viewOffset.start();
        repaint.start();
        zombieFrames.start();

        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.createImage(new byte[0]);
        Cursor c = t.createCustomCursor(i, new Point(0, 0), "c");
        setCursor(c);

        f = new Font("Arial", Font.BOLD, (int) (width / 47));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        bg.setX(offsetX);
        bg.setY(offsetY);

        store.setX(offsetX + storeX);
        store.setY(offsetY + storeY);
        exit.setX(offsetX + exitX);
        exit.setY(offsetY + exitY);

        // draw background
        bg.draw(g);

        // draw modules
        store.draw(g);
        exit.draw(g);

        // draw zombies
        ArrayList<Zombie> reversed = new ArrayList<>(zombies);
        Collections.reverse(reversed);
        for (Zombie z : reversed) {
            z.draw(g, offsetX, offsetY);
        }

        w.draw(g);

        // draw crosshair
        g.setColor(Color.YELLOW);
        g.fillRect(width / 2 - 20, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 + 5, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 - 2, height / 2 - 20, 4, 15);
        g.fillRect(width / 2 - 2, height / 2 + 5, 4, 15);

        g.setFont(f);
        g.setColor(Color.red);
        heart.draw(g);
        g.drawString("" + health, infoDisplayCoords[0], infoDisplayCoords[1]);
        g.setColor(Color.yellow);
        coin.draw(g);
        g.drawString("" + coins, infoDisplayCoords[2], infoDisplayCoords[3]);
        g.setColor(Color.orange);
        g.drawString("Current Wave: " + wave, infoDisplayCoords[4], infoDisplayCoords[5]);

        if (red) {
            damage.draw(g);
        }
    }

    public static void generateZombies(int n, int h, int d) {
        for (int i = 0; i < n; i++) {
            zombies.add(new Zombie((int) (width * (0.7 + r.nextDouble(0.1))),
                    (int) (height * (1.3 / 1.5 + r.nextDouble(0.05))),
                    globalScale, r.nextInt(5), wave));
        }
    }

    public void ammoModule() {
        int choice = JOptionPane.showConfirmDialog(this, "Buy 100 ammo for $20?");
        if (choice == 0) {
            if (coins >= 20) {
                coins -= 20;
                w.buyAmmo(100);
            }
        }
    }

    public void exit() {
        if (coins >= target) {
            JOptionPane.showMessageDialog(this,
                    "You have collected " + coins + " / " + target + " required coins. You won!");
            repaint.stop();
            viewOffset.stop();
            zombieFrames.stop();
            hit.stop();

            Game.showEnding(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "You have collected " + coins + " / " + target + " required coins. Keep working!");
        }
    }

    // all keys
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F:
                if (store.getX() - width / 2 <= 0
                        && store.getX() - width / 2 >= -store.getWidth()
                        && store.getY() - height / 2 <= 0
                        && store.getY() - height / 2 >= -store.getHeight()) {
                    ammoModule();
                } else if (exit.getX() - width / 2 <= 0
                        && exit.getX() - width / 2 >= -exit.getWidth()
                        && exit.getY() - height / 2 <= 0
                        && exit.getY() - height / 2 >= -exit.getHeight()) {
                    exit();
                }
                break;
            case KeyEvent.VK_R:
                w.reload();
                break;
            case KeyEvent.VK_ENTER:
                coins += 1000;
                break;
            case KeyEvent.VK_ESCAPE:
                Game.showEnding(false);
                break;
            default:
                break;
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
        if (e.getButton() == MouseEvent.BUTTON1 && w.shootable()) {
            w.shoot();

            for (int i = 0; i < zombies.size(); i++) {
                Zombie z = zombies.get(i);
                if (z.inRange(offsetX, offsetY)) {
                    z.deductHealth();
                    if (!z.checkAlive()) {
                        zombies.remove(i);
                        coins += wave * 10;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            w.unshoot();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
