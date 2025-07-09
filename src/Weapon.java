package src;

import apphelper.*;
import java.awt.*;
import javax.swing.Timer;

public class Weapon {

    private int curAmmo;
    private int reserveAmmo;
    private int maxAmmo;
    protected int frame;

    private final Sprite[] FRAMES = new Sprite[21];

    private Timer reload;

    public Weapon(int width, int height, double scale) {
        curAmmo = maxAmmo = 20;
        reserveAmmo = 100;
        frame = 0;
        int w = (int) (704 / scale);
        int h = (int) (875 / scale);
        int x = (int) (width * 0.6);
        int y = height - 875;
        for (int i = 0; i < 10; i++)
            FRAMES[i] = new Sprite(x, y, "imgs/pistol/pistol_0" + i + ".png", w, h);
        for (int i = 0; i < 10; i++)
            FRAMES[10 + i] = new Sprite(x, y, "imgs/pistol/pistol_1" + i + ".png", w, h);
        FRAMES[20] = new Sprite(x, y, "imgs/pistol/shoot.png", w, h);

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
        g.setColor(Color.YELLOW);
        g.drawString(curAmmo + " / " + reserveAmmo + " Ammo", 100, 275);
    }

    public void reload() {
        if (reserveAmmo > 0 && curAmmo < maxAmmo) {
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