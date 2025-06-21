package apphelper;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Text{
    private int x;
    private int y;    
    private String text;
    private Font font;
    private Color clr;    
    private int stepX;     
//------------------------------------------------------------------------------    

    /**
     *Create the Text
     * @param text
     * @param x
     * @param y
     */
    public Text(String text, int x, int y){
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = new Font("Arial", Font.BOLD, 48);;
        this.clr = Color.blue;
        this.stepX = 5;             
    }
//------------------------------------------------------------------------------ 

    /**
     *Get X coordinate
     * @return
     */
    public int getX(){
        return this.x;
    }

    /**
     *Get Y coordinate
     * @return
     */
    public int getY(){
        return this.y;
    }

    /**
     * Get text
     * @return
     */
    public String getText(){
        return this.text;
    }

    /**
     * Get the horizontal amount text will move each step, negative is left
     * @return
     */
    public int getStepX(){
        return this.stepX;
    }    

    /**
     *
     * @param x
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     *Get the vertical amount text will move each step, negative is up
     * @param y
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     *
     * @param text
     */
    public void setText(String text){
        this.text = text;
    }

    /**
     * Set the font for the text
     * @param font
     */
    public void setFont(Font font){
        this.font = font;
    }

    /**
     *
     * @param clr
     */
    public void setClr(Color clr){
        this.clr = clr;
    }

    /**
     *
     * @param stepX
     */
    public void setStepX(int stepX){
        this.stepX = stepX;
    }    
//------------------------------------------------------------------------------   

    /**
     *
     * @param g
     */
    public void draw(Graphics g){
        g.setColor(this.clr);
        g.setFont(this.font);
        g.drawString(this.text,this.x,this.y);
    }    
//------------------------------------------------------------------------------     

    /**
     *
     */
    public void moveOneStep(){
        this.x = this.x + this.stepX;
    }

    /**
     *
     */
    public void bounceX(){
        this.stepX = -this.stepX;
    }

    /**
     *
     * @param width
     */
    public void wrapAcross(int width){
        this.x = (this.x+this.stepX)%width;
    }    

    /**
     *
     * @param boundary
     * @return
     */
    public boolean crossingLeft(int boundary){
        return (this.x + this.stepX < boundary);
    }    

    /**
     *
     * @param boundary
     * @return
     */
    public boolean crossingRight(int boundary){
        return (this.x + this.stepX > boundary);
    }
}