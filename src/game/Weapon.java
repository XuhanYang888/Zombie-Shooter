package src.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Weapon {

    private int curAmmo;
    private int reserveAmmo;
    private int maxAmmo;
    protected int frame;

    private final Sprite[] FRAMES = new Sprite[21];

    private Timer reload;

    private final Sprite[] ammoIndicator = new Sprite[2];
    private final int[] ammoIndicatorCoords;
    private final Font f;

    public Weapon(int width, int height, double scale) {
        curAmmo = maxAmmo = 20;
        reserveAmmo = 100;
        frame = 0;
        int w = (int) (704 / scale);
        int h = (int) (875 / scale);
        int x = (int) (width * 0.6);
        int y = height - 875;
        for (int i = 0; i < 10; i++) {
            FRAMES[i] = new Sprite(x, y, "pistol/pistol_0" + i + ".png", w, h);
        }
        for (int i = 0; i < 10; i++) {
            FRAMES[10 + i] = new Sprite(x, y, "pistol/pistol_1" + i + ".png", w, h);
        }
        FRAMES[20] = new Sprite(x, y, "pistol/shoot.png", w, h);

        ammoIndicator[0] = new Sprite((int) (width * 0.85), (int) (height * 0.75), "pistol/yesAmmo.png",
                (int) (100 / scale), (int) (100 / scale));
        ammoIndicator[1] = new Sprite((int) (width * 0.85), (int) (height * 0.75), "pistol/noAmmo.png",
                (int) (100 / scale), (int) (100 / scale));
        ammoIndicatorCoords = new int[] { (int) (width * 0.85), (int) (height * 0.74) };
        f = new Font("Arial", Font.BOLD, width / 70);

        reload = new Timer(80, e -> {
            frame++;
            if (frame == 19) {
                frame = 0;
                reload.stop();
                int amount = Math.min(reserveAmmo, maxAmmo - curAmmo);
                reserveAmmo -= amount;
                curAmmo += amount;
            }
        });
    }

    public void draw(Graphics g) {
        FRAMES[frame].draw(g);
        g.setFont(f);
        g.setColor(Color.orange);
        g.drawString(curAmmo + " / " + reserveAmmo + " Ammo", ammoIndicatorCoords[0], ammoIndicatorCoords[1]);
        if (curAmmo == 0) {
            ammoIndicator[1].draw(g);
        } else {
            ammoIndicator[0].draw(g);
        }
    }

    public void reload() {
        if (reserveAmmo == 0) {
            JOptionPane.showMessageDialog(Game.getGamePanel(), "You have no ammo left. Buy some at the store.");
        } else if (curAmmo < maxAmmo) {
            reload.start();
        }
    }

    public void buyAmmo(int ammo) {
        reserveAmmo += ammo;
    }

    public boolean shootable() {
        return frame == 0 && curAmmo > 0;
    }

    public void shoot() {
        curAmmo--;
        frame = 20;
    }

    public void unshoot() {
        if (frame == 20) {
            frame = 0;
        }
    }
}