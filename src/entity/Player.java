package entity;

import main.AssetSetter;
import main.GamePanel;
import main.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {


    InputHandler keyH;

    // player pos on the screen, the player's pos doesnt change, we draw the map around the player
    public final int screenX;
    public final int screenY;


    // how many keys the player has
    public int hasKey = 0;

    public AssetSetter aSetter;

    public int npcIndex;


    // constructor
    public Player(GamePanel gp, InputHandler keyH) {

        super(gp);

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

        up1  = setupImage("/player/boy_up_1");
        up2  = setupImage("/player/boy_up_2");
        down1  = setupImage("/player/boy_down_1");
        down2  = setupImage("/player/boy_down_2");
        left1  = setupImage("/player/boy_left_1");
        left2  = setupImage("/player/boy_left_2");
        right1  = setupImage("/player/boy_right_1");
        right2  = setupImage("/player/boy_right_2");


    }




    @Override
    public void update() {

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
            try {
                pickUpObject(objIndex);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int npcIndex = gp.colHandler.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            gp.colHandler.clickOnEntity(gp.npc);


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

    private void interactNPC(int npcIndex) {

        // if the index is not 999 it is touching an NPC
        if(npcIndex != 999) {

            this.npcIndex = npcIndex;

            // make the NPC face the players direction when you interact
            switch (direction) {
                case "up":
                    gp.npc[npcIndex].direction = "down";
                    break;
                case "down":
                    gp.npc[npcIndex].direction = "up";
                    break;
                case "left":
                    gp.npc[npcIndex].direction = "right";
                    break;
                case "right":
                    gp.npc[npcIndex].direction = "left";
                    break;
            }

            gp.npc[npcIndex].Speak();

            gp.gameState = gp.dialogueState;

        }
    }

    // logic for picking up and object
    public void pickUpObject(int i) throws IOException {

        // if the index was 999, we didnt hit any objects
        if(i != 999) {

            // delete the object from the array
            String objectName = gp.obj[i].name;

            // logic for what happens when you pick up or interact with an object
            switch (objectName) {
                case "key":
                    hasKey++;
                    gp.playEffect(1);
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "door":
                    if(hasKey > 0) {
                        // change the door to open
                        aSetter.replaceObj("door_open",gp.obj[i].worldX, gp.obj[i].worldY,i);
                        gp.playEffect(5);
                        hasKey--;
                    }
                    else {
                        gp.ui.showMessage("This door is locked.");
                    }
                    break;
                case "boots":
                    speed += 2;
                    gp.obj[i] = null;
                    break;
                case "chest":
                    aSetter.replaceObj("chest_opened",gp.obj[i].worldX, gp.obj[i].worldY,i);
                    gp.ui.endGame();
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