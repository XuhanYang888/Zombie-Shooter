package src;

import javax.swing.*;
import apphelper.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private static final Sprite bg = new Sprite(0, 0, "imgs/bg_1.png");

    private static Sprite m = new Sprite(0, 0, "imgs/moduleTest.png");

    private static Sprite exit = new Sprite(0, 0, "imgs/moduleTest.png");

    private static Sprite damage = new Sprite(0, 0, "imgs/damage.png");

    protected static int width;
    protected static int height;
    protected static double globalScale;
    private static int[] center;

    private static Robot robot;
    private static Timer repaint;
    private static Timer viewOffset;
    private static Timer zombieFrames;

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

    private static final Random r = new Random();

    private static final ArrayList<Zombie> zombies = new ArrayList<>();

    private static int wave;

    private static Weapon w;

    static Timer t;

    static Timer hit;

    public GamePanel(int width, int height, double globalScale) {
        GamePanel.width = width;
        GamePanel.height = height;
        GamePanel.globalScale = globalScale;

        bg.resize((int) (bg.getWidth() * 3.75 / globalScale), (int) (bg.getHeight() * 3.75 / globalScale));
        limitX = -bg.getWidth() + GamePanel.width;
        limitY = -bg.getHeight() + GamePanel.height;

        m.resize((int) (m.getWidth() / globalScale), (int) (m.getHeight() / globalScale));
        exit.resize((int) (exit.getWidth() / globalScale), (int) (exit.getHeight() / globalScale));

        damage.resize(width, height);

        center = new int[] { width / 2, height / 2 };

        health = 100;
        red = false;
        coins = 0;
        target = 100;

        wave = 0;

        w = new Weapon();

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
                    if (z.scale >= 2.5) {
                        zombies.remove(i);
                        health -= z.damage;
                        red = true;
                        hit.restart();
                    }
                }
            }
        });

        t = new Timer(80, e -> {
            w.frame++;
            if (w.frame == 19) {
                w.frame = 0;
                t.stop();
                int amount = Math.min(w.reserveAmmo, 20 - w.curAmmo);
                w.reserveAmmo -= amount;
                w.curAmmo += amount;
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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        bg.setX(offsetX);
        bg.setY(offsetY);

        m.setX(offsetX + (int) (width * 1.1));
        m.setY(offsetY + (int) (height * 1.4));
        exit.setX(offsetX + (int) (width * 2.1));
        exit.setY(offsetY + (int) (height * 1.4));

        // draw background
        bg.draw(g);

        // draw modules
        m.draw(g);
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
        g.fillRect(width / 2 - 20 - w.recoil, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 + 5 + w.recoil, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 - 2, height / 2 - 20 - w.recoil, 4, 15);
        g.fillRect(width / 2 - 2, height / 2 + 5 + w.recoil, 4, 15);
        g.drawString("❤️ " + health, 10, 30);
        g.drawString("⭕ " + coins, 10, 45);

        g.drawString("" + zombies.size(), 10, 60);
        g.drawString("Current Wave: " + wave, 10, 60);
        g.drawString(w.curAmmo + " / " + w.reserveAmmo + " Ammo", 10, 75);

        if (red) {
            damage.draw(g);
        }
    }

    public static void generateZombies(int n, int h, int d) {
        for (int i = 0; i < n; i++) {
            zombies.add(new Zombie((int) (width * (0.7 + r.nextDouble(0.1))),
                    (int) (height * (1.3 + r.nextDouble(0.05))),
                    globalScale, r.nextInt(5), h, d));
        }
    }

    public void ammoModule() {
        int choice = JOptionPane.showConfirmDialog(this, "Buy 100 ammo for $20?");
        if (choice == 0) {
            if (coins >= 20) {
                coins -= 20;
                w.reserveAmmo += 100;
            }
        }
    }

    public void exit() {
        if (coins >= target) {
            JOptionPane.showMessageDialog(this,
                    "You have collected " + coins + " / " + target + " required coins. You won!");
            // repaint.stop();
            viewOffset.stop();
            zombieFrames.stop();
            t.stop();
            hit.stop();
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
                if (m.getX() - width / 2 <= 0
                        && m.getX() - width / 2 >= -m.getWidth()
                        && m.getY() - height / 2 <= 0
                        && m.getY() - height / 2 >= -m.getHeight()) {
                    ammoModule();
                } else if (exit.getX() - width / 2 <= 0
                        && exit.getX() - width / 2 >= -exit.getWidth()
                        && exit.getY() - height / 2 <= 0
                        && exit.getY() - height / 2 >= -exit.getHeight()) {
                    exit();
                }
                break;
            case KeyEvent.VK_R:
                if (w.reserveAmmo > 0) {
                    t.start();
                }
                break;
            case KeyEvent.VK_ENTER:
                coins += 1000;
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
        if (e.getButton() == MouseEvent.BUTTON1 && w.frame == 0 && w.curAmmo > 0) {
            w.curAmmo--;
            w.recoil = 3;

            for (int i = 0; i < zombies.size(); i++) {
                Zombie z = zombies.get(i);
                if (z.inRange(offsetX, offsetY)) {
                    z.health--;
                    if (z.health == 0) {
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
