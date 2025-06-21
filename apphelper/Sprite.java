package apphelper;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {

    private int x;
    private int y;
    private BufferedImage picture;
    private BufferedImage rotated;
    private int width;
    private int height;
    private int stepX;
    private int stepY;
    private int STEP = 3;
    private double rotationAngle = 0;

    //------------------------------------------------------------------------------     
    public Sprite(int x, int y, String picName) {
        this.x = x;
        this.y = y;
        try {
            this.picture = ImageIO.read(new File(picName));
        } catch (IOException ex) {
            System.out.println("File not found! " + picName);
        }
        this.width = this.picture.getWidth();
        this.height = this.picture.getHeight();
        this.stepX = STEP;
        this.stepY = STEP;
    }

    public Sprite(int x, int y, String picName, int width, int height) {
        this(x, y, picName);
        this.resize(width, height);
    }

    //------------------------------------------------------------------------------
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getStepX() {
        return this.stepX;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }

    //------------------------------------------------------------------------------
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform backup = g2d.getTransform();
               AffineTransform transform = new AffineTransform();           
               transform.rotate(Math.toRadians(getRotationAngle()), x + width / 2, y + height / 2);
              g2d.transform(transform);
               g2d.drawImage(picture, x, y, null);
               g2d.setTransform(backup);

    }

    public boolean inside(int x, int y) {
        return (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height);
    }

    //------------------------------------------------------------------------------
    public void moveOneStepX() {
        this.x = this.x + this.stepX;
    }

    public void bounceX() {
        this.stepX = -this.stepX;
    }

    public void moveOneStepY() {
        this.y = this.y + this.stepY;
    }

    public void bounceY() {
        this.stepY = -this.stepY;
    }

    public boolean crossingLeft(int boundary) {
        return (this.x + this.stepX < boundary);
    }

    public boolean crossingRight(int boundary) {
        return (this.x + this.width + this.stepX > boundary);
    }

    public boolean crossingTop(int boundary) {
        return (this.y + this.stepY < boundary);
    }

    public boolean crossingBottom(int boundary) {
        return (this.y + this.height + this.stepY > boundary);
    }

/**
 * Rotates the image by a certain number of degrees
 * @param angle 
 */
    public void rotate(double angle) {
        setRotationAngle(getRotationAngle() + angle);
        setRotationAngle(getRotationAngle() % 360);

    }

/**
 * Resize the sprite to new width and height
 * @param newWidth
 * @param newHeight 
 */
    public void resize(int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, this.picture.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        AffineTransform transform = AffineTransform.getScaleInstance((double) newWidth / this.width, (double) newHeight / this.height);
        g2d.drawRenderedImage(this.picture, transform);
        g2d.dispose();
        this.picture = resizedImage;
        this.width = newWidth;
        this.height = newHeight;
    }

    /**
     * Check collision with another sprite.  Just simple rectangular collision
     * @param other
     * @return true if collided, false otherwise
     */
    public boolean collidesWith(Sprite other) {
        return this.x < other.getX() + other.getWidth()
                && this.x + this.width > other.getX()
                && this.y < other.getY() + other.getHeight()
                && this.y + this.height > other.getY();
    }

    /**
     * @return the rotationAngle
     */
    public double getRotationAngle() {
        return rotationAngle;
    }

    /**
     * @param rotationAngle the rotationAngle to set
     */
    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

}
