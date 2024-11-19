package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

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
    public Rectangle hitBox = new Rectangle(0,0,48,48);
    public boolean collisionOn = false;

    public int hitBoxDefaultX, hitBoxDefaultY;

    public GamePanel gp;

    public int actionLockCounter = 0;

    public Entity(GamePanel gp) {

        this.gp = gp;


    }

    public BufferedImage setupImage(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaledImage(image, gp.tileSize,gp.tileSize);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;

    }

    public void setAction() {


    }

    public void update() {
        // this will call the subclasses setAction method that overrides the super classes method
        setAction();
        collisionOn = false;
        gp.colHandler.checkTile(this);

        // if collision is false, player can move
        if(!collisionOn)
        {
            switch (direction) {
                case"up": worldY -= speed; break;
                case"down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
                case "still": break;
            }
        }


        // Handle the logic of switching sprites for the player model's walking animation
        spriteCounter++;

        if(spriteCounter > 10) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else {
                spriteNum = 1;
            }

            spriteCounter = 0;
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {



            switch (direction) {
                case "up":
                    if(spriteNum == 1) {
                        image = up1;
                    }
                    if(spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }

                    break;
                case "right":
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }

                    break;
                case "left":
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }

                    break;
                case "still":
                    image = down1;
                    break;
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }

}
