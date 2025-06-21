package final_project;

import javax.swing.*;
import apphelper.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private Sprite bg = new Sprite(0, 0, "imgs/bg.png");

    private Sprite m1 = new Sprite(0, 0, "imgs/m1.png", 100, 100);
    private Sprite m2 = new Sprite(0, 0, "imgs/m2.png", 150, 150);
    private Sprite m3 = new Sprite(0, 0, "imgs/m3.png", 80, 80);

    private int recoil = 0;

    private int width;
    private int height;
    private static int[] center;

    private Robot robot;
    private static int deltaX;
    private static int deltaY;
    private static int offsetX;
    private static int offsetY;
    private static int limitX;
    private static int limitY;
    
    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        bg.resize((int) (width * 2.5), (int) (height * 2.5));
        limitX = (int) (width * -1.5);
        limitY = (int) (height * -1.5);

        center = new int[]{width / 2, height / 2};

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

        Timer t = new Timer(7, e -> {

            //curFrame++;
            //curFrame = curFrame % 5;
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
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw background
        bg.draw(g);

        // draw modules
        m1.draw(g);

        m2.draw(g);
        m3.draw(g);

        //zombie[curFrame].moveOneStepX();
        //zombie[curFrame].draw(g);
        // draw weapon upgrade module
        //g.
        // draw crosshair
        g.setColor(Color.YELLOW);
        g.fillRect(width / 2 - 20 - recoil, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 + 5 + recoil, height / 2 - 2, 15, 4);
        g.fillRect(width / 2 - 2, height / 2 - 20 - recoil, 4, 15);
        g.fillRect(width / 2 - 2, height / 2 + 5 + recoil, 4, 15);
        g.drawString(deltaX + ", " + deltaY, 10, 10);
        g.drawString(offsetX + ", " + offsetY, 10, 40);
    }

    //all keys
    @Override
    public void keyPressed(KeyEvent e) {
    }

    //all keys
    @Override
    public void keyReleased(KeyEvent e) {
    }

    //visible keys
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // calculate the mouse movement amount
        deltaX = e.getXOnScreen() - center[0];
        deltaY = e.getYOnScreen() - center[1];

        // move mouse back to center
        robot.mouseMove(center[0], center[1]);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // calculate the mouse movement amount
        deltaX = e.getXOnScreen() - center[0];
        deltaY = e.getYOnScreen() - center[1];

        // move mouse back to center
        robot.mouseMove(center[0], center[1]);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.isMetaDown()) {
            if (m1.getX() - width / 2 < 0
                    && m1.getX() - width / 2 > -100
                    && m1.getY() - height / 2 < 0
                    && m1.getY() - height / 2 > -100) {
                JOptionPane.showMessageDialog(this, "Accessing module 1");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
