package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// this will be the parent class for any NPS or Players
public class Entity {

    // image for the object
    public BufferedImage image, image2, image3;
    // name of object
    public String name;
    // int ID for object
    public int objID;
    public boolean collision = false;

    // Utility
    public UtilityTool uTool = new UtilityTool();

    // entities X and Y pos with respect to the entire map
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";

    // vars to help with walking animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // players hit box
    public Rectangle hitBox;
    public boolean collisionOn = false;

    public int hitBoxDefaultX, hitBoxDefaultY;

    public GamePanel gp;

    public int actionLockCounter = 0;

    // Dialogue text
    public String[] dialogues = new String[20];
    public int dialogueIndex = 0;

    // Character Status;
    public int maxLife;
    public int life;



    public int screenX;
    public int screenY;

    public Entity(GamePanel gp) {

        this.gp = gp;
        this.hitBox = new Rectangle(0,0,gp.tileSize/2,gp.tileSize/2);


    }

    public BufferedImage setupImage(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image;

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
        gp.colHandler.checkObject(this, false);
        gp.colHandler.checkPlayer(this);

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
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        //System.out.println(screenX + ", " + screenY);

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

            // Draw the rectangle hix box for the entity
            g2.drawRect(hitBox.x+screenX,hitBox.y+screenY,hitBox.width,hitBox.height);

        }
    }

    public void Speak() {

        // so it doesn't go out of the bounds of the max lines of dialogue this npc has
        if(dialogues[dialogueIndex] != null) {
            gp.ui.currDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
        }
        else {
            dialogueIndex = 0;
            gp.gameState = gp.playState;
            System.out.println("No more lines");
        }


        // make the NPC face the players direction when you interact
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }


    public void onClick() {

    }

}
