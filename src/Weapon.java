package src;

import apphelper.*;
import java.awt.*;

public class Weapon {

    protected int recoil = 0;

    private Sprite frame = new Sprite(0, 0, "imgs/gun.png");

    public Weapon() {
        frame.resize((int) (frame.getWidth() * 3 / GamePanel.globalScale), (int) (frame.getHeight() * 3 / GamePanel.globalScale));
        frame.setX((int) (GamePanel.width * 0.6));
        frame.setY(GamePanel.height - frame.getHeight());
    }

    public void draw(Graphics g) {
        frame.draw(g);
    }
}