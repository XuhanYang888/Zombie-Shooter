package src.game;

import java.awt.Color;
import java.awt.Graphics;

public class Zombie {

    private int frame;
    protected final Sprite[] FRAMES = new Sprite[5];

    private final int WIDTH;
    private final int HEIGHT;
    private double scale;

    private int x;
    private int y;

    private static int[] center;

    private int health;
    private final int ORIGINAL_HEALTH;
    private int damage;
    private int direction;

    public Zombie(int x, int y, double scale, int frame, int wave) {
        this.x = x;
        this.y = y;
        this.scale = 1;
        WIDTH = (int) (144 / scale);
        HEIGHT = (int) (152 / scale);
        this.health = this.ORIGINAL_HEALTH = wave * 5;
        this.damage = wave * 5;
        this.frame = frame;
        direction = 1;
        for (int i = 0; i < 5; i++)
            FRAMES[i] = new Sprite(0, 0, "zombie/zombie" + i + ".png");
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        Sprite temp = new Sprite(FRAMES[frame]);
        temp.setX(offsetX + x);
        temp.setY(offsetY + y);
        temp.resize((int) (WIDTH * scale), (int) (HEIGHT * scale));
        temp.draw(g);
        temp = null;

        if (health < ORIGINAL_HEALTH) {
            g.setColor(Color.black);
            g.fillRect(offsetX + x + (int) (WIDTH * (scale - 1) * 0.45), offsetY + y - (int) (HEIGHT * 0.2), WIDTH,
                    (int) (HEIGHT * 0.1));
            g.setColor(Color.red);
            g.fillRect(offsetX + x + (int) (WIDTH * (scale - 1) * 0.45), offsetY + y - (int) (HEIGHT * 0.2),
                    (int) (WIDTH * health / ORIGINAL_HEALTH), (int) (HEIGHT * 0.1));
            g.drawString("" + health, offsetX + x + (int) (WIDTH * (scale - 1) * 0.3), offsetY + y);
            g.setColor(Color.black);
            g.drawRect(offsetX + x + (int) (WIDTH * (scale - 1) * 0.45), offsetY + y - (int) (HEIGHT * 0.2), WIDTH,
                    (int) (HEIGHT * 0.1));
        }
        /**
         *
         * g.setColor(Color.YELLOW); if (direction == 1) {
         * g.drawRect(GamePanel.offsetX + x + (int) (WIDTH * scale * 0.15),
         * GamePanel.offsetY + y + (int) (HEIGHT * scale * 0.15), (int) (WIDTH *
         * scale * 0.5), (int) (HEIGHT * scale * 0.8)); } else {
         * g.drawRect(GamePanel.offsetX + x + (int) (WIDTH * scale * 0.35),
         * GamePanel.offsetY + y + (int) (HEIGHT * scale * 0.15), (int) (WIDTH *
         * scale * 0.5), (int) (HEIGHT * scale * 0.8)); }
         *
         */
    }

    public boolean inRange(int offsetX, int offsetY) {
        if (direction == 1) {
            return (offsetX + x + (WIDTH * scale * 0.15) - center[0] <= 0
                    && offsetX + x + (WIDTH * scale * 0.15) - center[0] >= -WIDTH * scale * 0.5
                    && offsetY + y + (HEIGHT * scale * 0.15) - center[1] <= 0
                    && offsetY + y + (HEIGHT * scale * 0.15) - center[1] >= -HEIGHT * scale * 0.8);
        } else {
            return (offsetX + x + (WIDTH * scale * 0.35) - center[0] <= 0
                    && offsetX + x + (WIDTH * scale * 0.35) - center[0] >= -WIDTH * scale * 0.5
                    && offsetY + y + (HEIGHT * scale * 0.15) - center[1] <= 0
                    && offsetY + y + (HEIGHT * scale * 0.15) - center[1] >= -HEIGHT * scale * 0.8);
        }
    }

    public void deductHealth() {
        health--;
    }

    public boolean checkAlive() {
        return health > 0;
    }

    public int getDamage() {
        return damage;
    }

    public void nextFrame() {
        frame++;
        frame = frame % 5;
        if (frame == 0) {
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

    public boolean reachPlayer() {
        return scale >= 2.5;
    }

    public static void updateCenter(int[] center) {
        Zombie.center = center;
    }
}