package final_project;

import javax.swing.*;
import apphelper.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private static Sprite bg = new Sprite(0, 0, "imgs/bg.png");

    private static Sprite m1 = new Sprite(0, 0, "imgs/m1.png", 100, 100);
    private static Sprite m2 = new Sprite(0, 0, "imgs/m2.png", 150, 150);
    private static Sprite m3 = new Sprite(0, 0, "imgs/m3.png", 80, 80);

    private static int width;
    private static int height;
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

    private static Random r = new Random();

    private static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    int count;

    public GamePanel(int width, int height) {
        GamePanel.width = width;
        GamePanel.height = height;
        bg.resize((int) (width * 2.5), (int) (height * 2.5));
        limitX = (int) (width * -1.5);
        limitY = (int) (height * -1.5);

        center = new int[]{width / 2, height / 2};

        health = 100;
        coins = 0;

        generateZombies(1);

        t = new Timer(7, e -> {
            offsetX -= 2 * deltaX;
            offsetX = offsetX > 0 ? 0 : offsetX;
            offsetX = offsetX < limitX ? limitX : offsetX;
            offsetY -= 2 * deltaY;
            offsetY = offsetY > 0 ? 0 : offsetY;
            offsetY = offsetY < limitY ? limitY : offsetY;

            bg.setX(offsetX);
            bg.setY(offsetY);

            m1.setX(offsetX + (int) (width * 1.1));
            m1.setY(offsetY + (int) (height * 1.4));

            m2.setX(offsetX + (int) (width * 1.2));
            m2.setY(offsetY + (int) (height * 1.35));

            m3.setX(offsetX + (int) (width * 1.4));
            m3.setY(offsetY + (int) (height * 1.4));

            repaint();
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

        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        count++;
        count = count % 7;
        if (count == 0) {
            for (Zombie z : zombies) {
                z.nextFrame();
            }
        }

        // draw background
        bg.draw(g);

        // draw modules
        m1.draw(g);
        m2.draw(g);
        m3.draw(g);

        // draw zombies
        for (Zombie z : zombies) {
            z.draw(g);
        }

        // draw crosshair
        g.setColor(Color.YELLOW);
        g.fillRect(width / 2 - 20 - Weapon.recoil, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 + 5 + Weapon.recoil, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 - 2, height / 2 - 20 - Weapon.recoil, 4, 15);
        g.fillRect(width / 2 - 2, height / 2 + 5 + Weapon.recoil, 4, 15);
        g.drawString("❤️ " + health, 10, 30);
        g.drawString("⭕ " + coins, 10, 45);
        g.drawString("" + count, 10, 60);
        g.drawString("" + Zombie.count, 10, 80);
    }

    public static void generateZombies(int n) {
        for (int i = 0; i < n; i++) {
            zombies.add(new Zombie((int) (width * (0.6 + r.nextDouble(0.1))), (int) (height * (1.25 + r.nextDouble(0.08))), 0.3, r.nextInt(5)));
        }
    }

    //all keys
    @Override
    public void keyPressed(KeyEvent e) {
    }

    //all keys
    @Override
    public void keyReleased(KeyEvent e) {
    }

    //this only happens for visible keys
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
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (m1.getX() - width / 2 < 0
                    && m1.getX() - width / 2 > -100
                    && m1.getY() - height / 2 < 0
                    && m1.getY() - height / 2 > -100) {
                JOptionPane.showMessageDialog(this, "Accessing module 1");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Weapon.recoil = 2;
            generateZombies(1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Weapon.recoil = 0;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
