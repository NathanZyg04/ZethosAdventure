package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class NPC_oldMan extends Entity {

    public NPC_oldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getNPCImage();
    }

    // load the player images from the player package
    public void getNPCImage() {

        up1  = setupImage("/npc/oldman_up_1");
        up2  = setupImage("/npc/oldman_up_2");
        down1  = setupImage("/npc/oldman_down_1");
        down2  = setupImage("/npc/oldman_down_2");
        left1  = setupImage("/npc/oldman_left_1");
        left2  = setupImage("/npc/oldman_left_2");
        right1  = setupImage("/npc/oldman_right_1");
        right2  = setupImage("/npc/oldman_right_2");


    }

    @Override
    public void setAction() {

        actionLockCounter++;

        // actionLockCounter so it updates only 120 frames
        if(actionLockCounter == 120)
        {
            Random rand = new Random();
            int i = rand.nextInt(105) + 1;


            if(i <= 25) {
                direction = "up";

            }
            if(i >  25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75 && i <= 100) {
                direction = "right";
            } else {

                direction = "still";
            }
            actionLockCounter = 0;
        }

    }



}
