package entity;

import main.AssetSetter;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    // player pos on the screen, the player's pos doesnt change, we draw the map around the player
    public final int screenX;
    public final int screenY;

    // how many keys the player has
    int hasKey = 1;

    public AssetSetter aSetter;


    // constructor
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        aSetter = new AssetSetter(gp);

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        hitBox = new Rectangle();
        // startng point of the rectangle
        hitBox.x = 8;
        hitBox.y = 16;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 25;
        hitBox.height = 25;

        setDefaultValues();

        getPlayerImage();
    }


    public void setDefaultValues() {

        // from the Entity super class
        // starting pos for the player in the world.txt file map
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

    }

    // load the player images from the player package
    public void getPlayerImage() {

        try {

            up1  = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2  = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1  = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2  = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1  = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2  = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1  = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2  = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));



        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void update() throws IOException {

        // movement handling, wrap everything in the outer if statement so the player doesnt animate while no keys are pressed
        if(keyH.downPressed || keyH.upPressed || keyH.leftPressed || keyH.rightPressed)
        {
            if(keyH.upPressed)
            {
                direction = "up";

            }
            else if(keyH.downPressed)
            {
                direction = "down";

            }
            else if(keyH.leftPressed)
            {
                direction = "left";

            }
            else if(keyH.rightPressed)
            {
                direction = "right";

            }

            // check the collision with tiles
            collisionOn = false;
            // pass the player object to the checkTile method
            gp.colHandler.checkTile(this);

            // object collision
            int objIndex = gp.colHandler.checkObject(this, true);
            pickUpObject(objIndex);

            // if collision is false, player can move
            if(!collisionOn)
            {
                switch (direction) {
                    case"up": worldY -= speed; break;
                    case"down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
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
    }

    // logic for picking up and object
    public void pickUpObject(int i) throws IOException {

        // if the index was 999, we didnt hit any objects
        if(i != 999) {

            // delete the object from the array
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "key":
                    hasKey++;
                    gp.obj[i] = null;
                    break;
                case "door":
                    if(hasKey > 0) {
                        // change the door to open
                        gp.obj[i].collision = false;
                        gp.obj[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door_open.png")));
                        //gp.obj[i] = null;

                        hasKey--;
                    }
                    break;
            }
        }
    }


    public void draw(Graphics2D g2) {


        BufferedImage image = null;

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
        }

        // draws the iamge with x and y pos and Width and height
        g2.drawImage(image, screenX, screenY, gp.tileSize,gp.tileSize, null);



    }


}