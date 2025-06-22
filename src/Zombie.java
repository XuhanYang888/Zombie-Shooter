package src;

import apphelper.*;
import java.awt.*;

public class Zombie {

    protected static int count = 0;

    protected final Sprite[] FRAMES = {
            new Sprite(0, 0, "imgs/zombie/zombie0.png"),
            new Sprite(0, 0, "imgs/zombie/zombie1.png"),
            new Sprite(0, 0, "imgs/zombie/zombie2.png"),
            new Sprite(0, 0, "imgs/zombie/zombie3.png"),
            new Sprite(0, 0, "imgs/zombie/zombie4.png")
    };

    protected int width = 360;
    protected int height = 380;
    protected double scale;

    protected int x;
    protected int y;

    protected int health;
    private int damage;

    private int cur;

    public Zombie(int x, int y, double scale, int frame) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        width = (int) (width * scale);
        height = (int) (height * scale);
        health = 10;
        damage = 5;
        cur = frame;

        count++;
    }

    public void draw(Graphics g) {
        Sprite temp = new Sprite(FRAMES[cur]);
        temp.setX(GamePanel.offsetX + x);
        temp.setY(GamePanel.offsetY + y);
        temp.resize(width, height);
        temp.draw(g);

        g.setColor(Color.red);
        g.fillRect(GamePanel.offsetX + x, GamePanel.offsetY + y - (int) (height * 0.2), (int) (width * health / 10),
                (int) (height * 0.1));
        g.drawString("" + health, GamePanel.offsetX + x, GamePanel.offsetY + y);

        g.drawRect(GamePanel.offsetX + x + (int) (width * 0.15), GamePanel.offsetY + y + (int) (height * 0.15),
                (int) (width * 0.4), (int) (height * 0.8));

        g.setColor(Color.black);
        g.drawRect(GamePanel.offsetX + x, GamePanel.offsetY + y - (int) (height * 0.2), width, (int) (height * 0.1));
    }

    public void nextFrame() {
        cur++;
        cur = cur % 5;
        if (cur == 0) {
            x += 10;
            scale += 0.1;
        }
    }
}