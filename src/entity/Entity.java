package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

// this will be the parent class for any NPS or Players
public class Entity {

    // entitys X and Y pos with respect to the entire map
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    // vars to help with walking animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // players hit box
    public Rectangle hitBox;
    public boolean collisionOn = false;

}
