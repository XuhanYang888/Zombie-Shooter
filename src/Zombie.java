package src;

import apphelper.*;
import java.awt.*;

public class Zombie {

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
    protected int originalHealth;
    protected int damage;
    private int direction;

    private int cur;

    public Zombie(int x, int y, double scale, int frame, int health, int damage) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        width = (int) (width * 0.4 / scale);
        height = (int) (height * 0.4 / scale);
        this.health = this.originalHealth = health;
        this.damage = damage;
        direction = 1;
        cur = frame;
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        Sprite temp = new Sprite(FRAMES[cur]);
        temp.setX(offsetX + x);
        temp.setY(offsetY + y);
        temp.resize((int) (width * scale), (int) (height * scale));
        temp.draw(g);
        temp = null;

        if (health < originalHealth) {
            g.setColor(Color.black);
            g.fillRect(offsetX + x + (int) (width * (scale - 1) * 0.45), offsetY + y - (int) (height * 0.2), width,
                    (int) (height * 0.1));
            g.setColor(Color.red);
            g.fillRect(offsetX + x + (int) (width * (scale - 1) * 0.45), offsetY + y - (int) (height * 0.2),
                    (int) (width * health / originalHealth), (int) (height * 0.1));
            g.drawString("" + health, offsetX + x + (int) (width * (scale - 1) * 0.3), offsetY + y);
            g.setColor(Color.black);
            g.drawRect(offsetX + x + (int) (width * (scale - 1) * 0.45), offsetY + y - (int) (height * 0.2), width,
                    (int) (height * 0.1));
        }
        /**
         *
         * g.setColor(Color.YELLOW); if (direction == 1) {
         * g.drawRect(GamePanel.offsetX + x + (int) (width * scale * 0.15),
         * GamePanel.offsetY + y + (int) (height * scale * 0.15), (int) (width *
         * scale * 0.5), (int) (height * scale * 0.8)); } else {
         * g.drawRect(GamePanel.offsetX + x + (int) (width * scale * 0.35),
         * GamePanel.offsetY + y + (int) (height * scale * 0.15), (int) (width *
         * scale * 0.5), (int) (height * scale * 0.8)); }
         *
         */
    }

    public boolean inRange(int offsetX, int offsetY) {
        if (direction == 1) {
            return (offsetX + x + (width * scale * 0.15) - GamePanel.width / 2 <= 0
                    && offsetX + x + (width * scale * 0.15) - GamePanel.width / 2 >= -width * scale * 0.5
                    && offsetY + y + (height * scale * 0.15) - GamePanel.height / 2 <= 0
                    && offsetY + y + (height * scale * 0.15) - GamePanel.height / 2 >= -height * scale * 0.8);
        } else {
            return (offsetX + x + (width * scale * 0.35) - GamePanel.width / 2 <= 0
                    && offsetX + x + (width * scale * 0.35) - GamePanel.width / 2 >= -width * scale * 0.5
                    && offsetY + y + (height * scale * 0.15) - GamePanel.height / 2 <= 0
                    && offsetY + y + (height * scale * 0.15) - GamePanel.height / 2 >= -height * scale * 0.8);
        }
    }

    public void nextFrame() {
        cur++;
        cur = cur % 5;
        if (cur == 0) {
            x += direction * scale * 8;
            scale += 0.02;
        }
    }

    public void flipHorizontal() {
        for (Sprite s : FRAMES) {
            direction = -direction;
            s.flipHorizontal();
        }
    }
}