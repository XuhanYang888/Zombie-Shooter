package src;

import apphelper.*;
import java.awt.*;

public class Zombie {

    protected static int count = 0;

    private final Sprite[] FRAMES = {
        new Sprite(0, 0, "imgs/zombie/zombie0.png"),
        new Sprite(0, 0, "imgs/zombie/zombie1.png"),
        new Sprite(0, 0, "imgs/zombie/zombie2.png"),
        new Sprite(0, 0, "imgs/zombie/zombie3.png"),
        new Sprite(0, 0, "imgs/zombie/zombie4.png")
    };

    private final int WIDTH = 360;
    private final int HEIGHT = 380;

    private int x;
    private int y;
    private double scale;

    private int health;
    private int damage;

    private int cur;

    public Zombie(int x, int y, double scale, int frame) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        health = 10;
        damage = 5;
        cur = frame;

        count++;
    }

    public void draw(Graphics g) {
        FRAMES[cur].setX(GamePanel.offsetX + x);
        FRAMES[cur].setY(GamePanel.offsetY + y);
        FRAMES[cur].resize((int) (WIDTH * scale), (int) (HEIGHT * scale));
        FRAMES[cur].draw(g);
    }

    public void nextFrame() {
        cur++;
        cur = cur % 5;
        if (cur == 0) {
            x += 10;
        }
    }
}
