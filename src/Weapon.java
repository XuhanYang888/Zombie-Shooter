package src;

import apphelper.*;
import java.awt.*;

public class Weapon {

    protected int recoil = 0;

    protected int frame = 0;

    private final Sprite[] FRAMES = new Sprite[21];

    public Weapon() {
        for (int i = 0; i < 10; i++) {
            FRAMES[i] = new Sprite(0, 0, "imgs/pistol/pistol_0" + i + ".png");
        }
        for (int i = 0; i < 10; i++) {
            FRAMES[10 + i] = new Sprite(0, 0, "imgs/pistol/pistol_1" + i + ".png");
        }
        FRAMES[20] = new Sprite(0, 0, "imgs/pistol/pistol_20.png");

        for (Sprite s : FRAMES) {
            s.resize((int) (s.getWidth() / GamePanel.globalScale), (int) (s.getHeight() / GamePanel.globalScale));
            s.setX((int) (GamePanel.width * 0.6));
            s.setY(GamePanel.height - s.getHeight());
        }
    }

    public void draw(Graphics g) {
        if (recoil == 0) {
            FRAMES[frame].draw(g);
        } else {
            FRAMES[20].draw(g);
        }
    }
}